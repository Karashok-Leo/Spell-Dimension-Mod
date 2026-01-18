package karashokleo.spell_dimension.init;

import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.registry.ItemRegistry;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.data.book.MagicGuideProvider;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.spell_engine.api.spell.SpellInfo;
import vazkii.patchouli.common.item.ItemModBook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AllStacks
{
    public static final ItemStack NEW_GUIDE_BOOK = ItemModBook.forBook(AllItems.MAGIC_GUIDE);
    public static final ItemStack GUIDE_BOOK;

    public static List<ItemStack> getBaseEssences()
    {
        return AllItems.BASE_ESSENCES
            .values()
            .stream()
            .sorted(Comparator.comparing(Registries.ITEM::getId))
            .map(Item::getDefaultStack)
            .toList();
    }

    public static List<ItemStack> getSpellBooks()
    {
        return AllItems.SPELL_BOOKS
            .values()
            .stream()
            .sorted(Comparator.comparing(Registries.ITEM::getId))
            .map(Item::getDefaultStack)
            .toList();
    }

    public static List<ItemStack> getEnchantedEssences()
    {
        List<ItemStack> stacks = new ArrayList<>();
        List<AttributeModifier> allModifiers = AttributeModifier.getAll();
        for (AttributeModifier modifier : allModifiers)
        {
            for (EquipmentSlot slot : EquipmentSlot.values())
            {
                for (int i = 0; i < 3; i++)
                {
                    stacks.add(AllItems.ENCHANTED_ESSENCE.getStack(
                        new EnchantedModifier(
                            (i + 1) * 10,
                            slot,
                            modifier.toELM(slot)
                        )
                    ));
                }
            }
        }
        return stacks;
    }

    public static List<ItemStack> getEnlighteningEssences()
    {
        List<AttributeModifier> allModifiers = AttributeModifier.getAll();
        return allModifiers.stream().map(modifier -> AllItems.ENLIGHTENING_ESSENCE.getStack(modifier.toELM())).toList();
    }

    public static List<ItemStack> getSpellScrolls()
    {
        //        Stream<Identifier> stream = SpellRegistry.all().keySet().stream();
        return AllSpells.getAll()
            .stream()
            .map(AllItems.SPELL_SCROLL::getStack)
            .sorted(Comparator.comparing(stack ->
            {
                SpellInfo spellInfo = AllItems.SPELL_SCROLL.getSpellInfo(stack);
                return spellInfo == null ? "" : spellInfo.spell().school.id.toString();
            }))
            .toList();
    }

    public static List<ItemStack> getQuestScrolls()
    {
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(AllItems.QUEST_SCROLL.getDefaultStack());
        stacks.addAll(QuestRegistry.QUEST_REGISTRY.streamEntries().map(entry -> AllItems.QUEST_SCROLL.getStack(entry)).toList());
        return stacks;
    }

    static
    {
        GUIDE_BOOK = ItemRegistry.MODONOMICON.get().getDefaultStack();
        GUIDE_BOOK.getOrCreateNbt().putString(ModonomiconConstants.Nbt.ITEM_BOOK_ID_TAG, MagicGuideProvider.BOOK_ID.toString());
    }
}
