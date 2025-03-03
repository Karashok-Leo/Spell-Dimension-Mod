package karashokleo.spell_dimension.init;

import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.registry.ItemRegistry;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AllStacks
{
    public static final List<ItemStack> BASE_ESSENCE_STACKS;
    public static final List<ItemStack> SPELL_BOOK_STACKS;
    public static final List<ItemStack> ELES_STACKS;
    public static final List<ItemStack> ECES_STACKS;
    private static final List<ItemStack> SPELL_SCROLL_STACKS;
    public static final List<ItemStack> QUEST_SCROLL_STACKS;
    public static final ItemStack GUIDE_BOOK;

    static
    {
        BASE_ESSENCE_STACKS = AllItems.BASE_ESSENCES
                .values()
                .stream()
                .sorted(Comparator.comparing(Registries.ITEM::getId))
                .map(Item::getDefaultStack)
                .toList();

        SPELL_BOOK_STACKS = AllItems.SPELL_BOOKS
                .values()
                .stream()
                .sorted(Comparator.comparing(Registries.ITEM::getId))
                .map(Item::getDefaultStack)
                .toList();

        List<AttributeModifier> allModifiers = AttributeModifier.getAll();

        ELES_STACKS = allModifiers.stream().map(modifier -> AllItems.ENLIGHTENING_ESSENCE.getStack(modifier.toELM())).toList();

        ECES_STACKS = new ArrayList<>();
        for (AttributeModifier modifier : allModifiers)
            for (EquipmentSlot slot : EquipmentSlot.values())
                for (int i = 0; i < 3; i++)
                    ECES_STACKS.add(AllItems.ENCHANTED_ESSENCE.getStack(
                            new EnchantedModifier(
                                    (i + 1) * 10,
                                    slot,
                                    modifier.toELM(slot)
                            )
                    ));

        SPELL_SCROLL_STACKS = new ArrayList<>();

        QUEST_SCROLL_STACKS = new ArrayList<>();
        QUEST_SCROLL_STACKS.add(AllItems.QUEST_SCROLL.getDefaultStack());
        QUEST_SCROLL_STACKS.addAll(QuestRegistry.QUEST_REGISTRY.streamEntries().map(entry -> AllItems.QUEST_SCROLL.getStack(entry)).toList());

        GUIDE_BOOK = ItemRegistry.MODONOMICON.get().getDefaultStack();
        GUIDE_BOOK.getOrCreateNbt().putString(ModonomiconConstants.Nbt.ITEM_BOOK_ID_TAG, MagicGuidanceProvider.BOOK_ID.toString());
    }

    public static List<ItemStack> getScrolls()
    {
        SPELL_SCROLL_STACKS.clear();
//        Stream<Identifier> stream = SpellRegistry.all().keySet().stream();
        Stream<Identifier> stream = AllSpells.getAll().stream();
        SPELL_SCROLL_STACKS.addAll(stream.map(AllItems.SPELL_SCROLL::getStack).sorted(Comparator.comparing(stack ->
        {
            SpellInfo spellInfo = AllItems.SPELL_SCROLL.getSpellInfo(stack);
            return spellInfo == null ? "" : spellInfo.spell().school.id.toString();
        })).toList());
        return SPELL_SCROLL_STACKS;
    }
}
