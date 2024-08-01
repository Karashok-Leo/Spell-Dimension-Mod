package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.item.logic.EnlighteningModifier;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellRegistry;
import net.wizards.WizardsMod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class AllStacks
{
    public static final List<ItemStack> BASE_ESSENCE_STACKS;
    public static final List<ItemStack> SPELL_BOOK_STACKS;
    public static final List<ItemStack> ELES_STACKS;
    public static final List<ItemStack> ECES_STACKS;
    public static final List<ItemStack> SPELL_SCROLL_STACKS;
    public static final ItemStack SCROLL = AllItems.SPELL_SCROLL.getStack(new Identifier(WizardsMod.ID, "fire_breath"));

    static
    {
        BASE_ESSENCE_STACKS = AllItems.BASE_ESSENCES.values().stream().flatMap(Collection::stream).map(Item::getDefaultStack).toList();

        SPELL_BOOK_STACKS = AllItems.SPELL_BOOKS.values().stream().flatMap(Collection::stream).map(Item::getDefaultStack).toList();

        List<AttributeModifier> allModifiers = AttributeModifier.getAll();

        ELES_STACKS = allModifiers.stream().map(modifier -> AllItems.ENLIGHTENING_ESSENCE.getStack(
                new EnlighteningModifier(
                        modifier.attribute(),
                        UuidUtil.getSelfUuid(modifier.operation()),
                        modifier.amount(),
                        modifier.operation()
                )
        )).toList();

        ECES_STACKS = new ArrayList<>();
        for (AttributeModifier modifier : allModifiers)
            for (EquipmentSlot slot : EquipmentSlot.values())
                for (int i = 0; i < 3; i++)
                    ECES_STACKS.add(AllItems.ENCHANTED_ESSENCE.getStack(
                            new EnchantedModifier(
                                    (i + 1) * 10,
                                    slot,
                                    new EnlighteningModifier(
                                            modifier.attribute(),
                                            UuidUtil.getEquipmentUuid(slot, modifier.operation()),
                                            modifier.amount(),
                                            modifier.operation()
                                    )
                            )
                    ));

        SPELL_SCROLL_STACKS = new ArrayList<>();
    }

    public static List<ItemStack> getScrolls()
    {
        SPELL_SCROLL_STACKS.clear();
        SPELL_SCROLL_STACKS.addAll(SpellRegistry.all().keySet().stream().map(AllItems.SPELL_SCROLL::getStack).sorted(Comparator.comparing(stack ->
        {
            SpellInfo spellInfo = AllItems.SPELL_SCROLL.getSpellInfo(stack);
            return spellInfo == null ? "" : spellInfo.spell().school.id.toString();
        })).toList());
        return SPELL_SCROLL_STACKS;
    }
}
