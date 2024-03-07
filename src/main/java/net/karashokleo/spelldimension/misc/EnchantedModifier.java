package net.karashokleo.spelldimension.misc;

import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.data.LangData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;

import java.util.List;

@SuppressWarnings("unused")
public class EnchantedModifier
{
    private static final String ENCHANTED_MODIFIER_KEY = "EnchantedModifier";
    private static final String LEVEL_KEY = "Level";
    private static final String MODIFIERS_KEY = "Modifiers";

    private static final String THRESHOLD_KEY = "Threshold";
    private static final String SLOT_KEY = "Slot";

    public final int threshold;
    public final EquipmentSlot slot;
    public final AttrModifier modifier;

    public EnchantedModifier(int threshold, EquipmentSlot slot, MagicSchool school)
    {
        this(threshold, slot, AttrModifier.forEquip(school.attributeId(), slot));
    }

    public EnchantedModifier(int threshold, EquipmentSlot slot, Identifier attributeId, double amount, EntityAttributeModifier.Operation operation)
    {
        this(threshold, slot, AttrModifier.forEquip(attributeId, amount, operation, slot));
    }

    public EnchantedModifier(int threshold, EquipmentSlot slot, AttrModifier modifier)
    {
        if (slot == null || modifier == null)
            throw new IllegalArgumentException("Field 'slot' or 'modifier' can not be null.");
        this.threshold = Math.max(0, threshold);
        this.slot = slot;
        this.modifier = modifier;
    }

    public Identifier getSlotTexture()
    {
        return SpellDimension.modLoc("textures/slot/" + this.slot.getName() + ".png");
    }

    public static boolean canApply(ItemStack stack, EquipmentSlot slot)
    {
        Item item = stack.getItem();
        if (item instanceof Equipment equipment)
            return slot == equipment.getSlotType();
        else
            return slot == EquipmentSlot.MAINHAND;
    }

    public boolean apply(ItemStack stack)
    {
        if (!canApply(stack, slot)) return false;
        NbtCompound enchantedModifiers = stack.getOrCreateSubNbt(ENCHANTED_MODIFIER_KEY);
        int level = enchantedModifiers.getInt(LEVEL_KEY);
        if (level > threshold) return false;
        enchantedModifiers.putInt(LEVEL_KEY, level + 1);
        if (!enchantedModifiers.contains(MODIFIERS_KEY, NbtElement.LIST_TYPE))
            enchantedModifiers.put(MODIFIERS_KEY, new NbtList());
        NbtList nbtList = enchantedModifiers.getList(MODIFIERS_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); i++)
            if (modifier.overWriteNbt(nbtList.getCompound(i))) return true;
        nbtList.add(modifier.toNbt());
        return true;
    }

    public static boolean remove(ItemStack stack)
    {
        if (stack.isEmpty()) return false;
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains(ENCHANTED_MODIFIER_KEY))
        {
            nbt.remove(ENCHANTED_MODIFIER_KEY);
            return true;
        }
        return false;
    }

    public static void modifyItemAttributeModifiers(ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> modifiers)
    {
        if (!canApply(stack, slot)) return;
        NbtCompound enchantedModifier = stack.getSubNbt(ENCHANTED_MODIFIER_KEY);
        if (enchantedModifier == null) return;
        if (!enchantedModifier.contains(MODIFIERS_KEY, NbtElement.LIST_TYPE)) return;
        NbtList nbtList = enchantedModifier.getList(MODIFIERS_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i)
        {
            AttrModifier attrModifier = AttrModifier.fromNbt(nbtList.getCompound(i));
            if (attrModifier == null) continue;
            attrModifier.applyToStack(modifiers);
        }
    }

    public static void levelTooltip(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(ENCHANTED_MODIFIER_KEY)) return;
        NbtCompound extraModifiers = nbt.getCompound(ENCHANTED_MODIFIER_KEY);
        if (!extraModifiers.contains(LEVEL_KEY)) return;
        lines.add(ScreenTexts.EMPTY);
        lines.add(Text.translatable(LangData.TOOLTIP_LEVEL, extraModifiers.getInt(LEVEL_KEY)).formatted(Formatting.BOLD));
        lines.add(ScreenTexts.EMPTY);
    }

    public void toNbt(NbtCompound nbt)
    {
        nbt.putInt(THRESHOLD_KEY, threshold);
        nbt.putString(SLOT_KEY, slot.getName());
        modifier.writeNbt(nbt);
    }

    public static EnchantedModifier fromNbt(NbtCompound nbt)
    {
        try
        {
            return new EnchantedModifier(
                    nbt.getInt(THRESHOLD_KEY),
                    EquipmentSlot.byName(nbt.getString(SLOT_KEY)),
                    AttrModifier.fromNbt(nbt)
            );
        } catch (Exception e)
        {
            return null;
        }
    }

    public static void init()
    {
        ModifyItemAttributeModifiersCallback.EVENT.register(EnchantedModifier::modifyItemAttributeModifiers);
    }
}