package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.config.ScrollLootConfig;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellInfo;
import net.wizards.WizardsMod;

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
    public static final ItemStack SCROLL = AllItems.SPELL_SCROLL.getStack(new Identifier(WizardsMod.ID, "fire_breath"));

    static
    {
        BASE_ESSENCE_STACKS = AllItems.BASE_ESSENCES.values().stream().map(Item::getDefaultStack).toList();

        SPELL_BOOK_STACKS = AllItems.SPELL_BOOKS.values().stream().map(Item::getDefaultStack).toList();

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

        QUEST_SCROLL_STACKS = QuestRegistry.QUEST_REGISTRY.streamEntries().map(entry -> AllItems.QUEST_SCROLL.getStack(entry)).toList();
    }

    public static List<ItemStack> getScrolls()
    {
        SPELL_SCROLL_STACKS.clear();
//        Stream<Identifier> stream = SpellRegistry.all().keySet().stream();
        Stream<Identifier> stream = ScrollLootConfig.getAllSpells().stream();
        SPELL_SCROLL_STACKS.addAll(stream.map(AllItems.SPELL_SCROLL::getStack).sorted(Comparator.comparing(stack ->
        {
            SpellInfo spellInfo = AllItems.SPELL_SCROLL.getSpellInfo(stack);
            return spellInfo == null ? "" : spellInfo.spell().school.id.toString();
        })).toList());
        return SPELL_SCROLL_STACKS;
    }
}
