package net.karashokleo.spelldimension.misc;

import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.data.LangData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class ExtraModifier
{
    private static final String EXTRA_MODIFIER_KEY = "ExtraModifier";
    private static final String LEVEL_KEY = "Level";
    private static final String MODIFIERS_KEY = "AttributeModifiers";

    private static final String THRESHOLD_KEY = "Threshold";
    private static final String SLOT_KEY = "Slot";
    private static final String ATTRIBUTE_NAME_KEY = "AttributeName";
    private static final String VALUE_KEY = "Value";
    private static final String AMOUNT_KEY = "Amount";
    private static final String OPERATION_KEY = "Operation";

    public final int threshold;
    public final EquipmentSlot slot;
    public final EntityAttribute attribute;
    public final double value;
    public final EntityAttributeModifier.Operation operation;

    public ExtraModifier(int threshold, EquipmentSlot slot, Identifier attributeId)
    {
        this(threshold, slot, Registries.ATTRIBUTE.get(attributeId), 1.0);
    }

    public ExtraModifier(int threshold, EquipmentSlot slot, EntityAttribute attribute)
    {
        this(threshold, slot, attribute, 1.0);
    }

    public ExtraModifier(int threshold, EquipmentSlot slot, EntityAttribute attribute, double value)
    {
        this(threshold, slot, attribute, value, EntityAttributeModifier.Operation.ADDITION);
    }

    public ExtraModifier(int threshold, EquipmentSlot slot, EntityAttribute attribute, double value, EntityAttributeModifier.Operation operation)
    {
        this.threshold = Math.max(0, threshold);
        this.slot = slot;
        this.attribute = attribute;
        this.value = value;
        this.operation = operation;
    }

    public String getAttributeId()
    {
        Identifier id = Registries.ATTRIBUTE.getId(attribute);
        return id == null ? "" : id.toString();
    }

    public Identifier getSlotTexture()
    {
        return SpellDimension.modLoc("textures/slot/" + this.slot.getName() + ".png");
    }

    public boolean apply(ItemStack stack)
    {
        if (stack.isEmpty()) return false;
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(EXTRA_MODIFIER_KEY))
            nbt.put(EXTRA_MODIFIER_KEY, new NbtCompound());
        NbtCompound extraModifiers = nbt.getCompound(EXTRA_MODIFIER_KEY);
        if (!extraModifiers.contains(LEVEL_KEY))
            extraModifiers.putInt(LEVEL_KEY, 0);
        int level = extraModifiers.getInt(LEVEL_KEY);
        if (level >= threshold) return false;
        extraModifiers.putInt(LEVEL_KEY, level + 1);
        if (!extraModifiers.contains(MODIFIERS_KEY, NbtElement.LIST_TYPE))
            extraModifiers.put(MODIFIERS_KEY, new NbtList());
        NbtList nbtList = extraModifiers.getList(MODIFIERS_KEY, NbtElement.COMPOUND_TYPE);
        for (NbtElement element : nbtList)
            if (element instanceof NbtCompound modifier &&
                    modifier.getString(ATTRIBUTE_NAME_KEY).equals(getAttributeId()) &&
                    (modifier.getString(SLOT_KEY).isEmpty() || modifier.getString(SLOT_KEY).equals(slot.getName())))
            {
                modifier.putDouble(AMOUNT_KEY, modifier.getDouble(AMOUNT_KEY) + value);
                return true;
            }
        EntityAttributeModifier modifier = new EntityAttributeModifier(EXTRA_MODIFIER_KEY, value, operation);
        NbtCompound nbtCompound = modifier.toNbt();
        nbtCompound.putString(ATTRIBUTE_NAME_KEY, getAttributeId());
        if (slot != null)
            nbtCompound.putString(SLOT_KEY, slot.getName());
        nbtList.add(nbtCompound);
        return true;
    }

    public static boolean remove(ItemStack stack)
    {
        if (stack.isEmpty()) return false;
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains(EXTRA_MODIFIER_KEY))
        {
            nbt.remove(EXTRA_MODIFIER_KEY);
            return true;
        }
        return false;
    }

    public static void addModifiers(ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> modifiers)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return;
        if (!nbt.contains(EXTRA_MODIFIER_KEY))
            return;
        NbtCompound extraModifiers = nbt.getCompound(EXTRA_MODIFIER_KEY);
        if (!extraModifiers.contains(MODIFIERS_KEY, NbtElement.LIST_TYPE))
            return;
        NbtList nbtList = extraModifiers.getList(MODIFIERS_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i)
        {
            EntityAttributeModifier modifier;
            Optional<EntityAttribute> optional;
            NbtCompound nbtCompound = nbtList.getCompound(i);
            if (
                    nbtCompound.contains(SLOT_KEY, NbtElement.STRING_TYPE) &&
                            !nbtCompound.getString(SLOT_KEY).equals(slot.getName()) ||
                            (optional = Registries.ATTRIBUTE.getOrEmpty(Identifier.tryParse(nbtCompound.getString(ATTRIBUTE_NAME_KEY)))).isEmpty() ||
                            (modifier = EntityAttributeModifier.fromNbt(nbtCompound)) == null ||
                            modifier.getId().getLeastSignificantBits() == 0L ||
                            modifier.getId().getMostSignificantBits() == 0L
            ) continue;
            modifiers.put(optional.get(), modifier);
        }
    }

    public static void levelTooltip(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(EXTRA_MODIFIER_KEY))
            return;
        NbtCompound extraModifiers = nbt.getCompound(EXTRA_MODIFIER_KEY);
        if (!extraModifiers.contains(LEVEL_KEY))
            return;
        lines.add(ScreenTexts.EMPTY);
        lines.add(Text.translatable(LangData.TOOLTIP_LEVEL, extraModifiers.getInt(LEVEL_KEY)).formatted(Formatting.BOLD));
        lines.add(ScreenTexts.EMPTY);
    }

    public void toNbt(NbtCompound nbt)
    {
        nbt.putDouble(THRESHOLD_KEY, threshold);
        nbt.putString(SLOT_KEY, slot.getName());
        nbt.putString(ATTRIBUTE_NAME_KEY, getAttributeId());
        nbt.putDouble(VALUE_KEY, value);
        nbt.putString(OPERATION_KEY, operation.name());
    }

    public static ExtraModifier fromNbt(NbtCompound nbt)
    {
        try
        {
            String slot = nbt.getString(SLOT_KEY);
            return new ExtraModifier(
                    nbt.getInt(THRESHOLD_KEY),
                    EquipmentSlot.byName(slot),
                    Registries.ATTRIBUTE.get(new Identifier(nbt.getString(ATTRIBUTE_NAME_KEY))),
                    nbt.getDouble(VALUE_KEY),
                    EntityAttributeModifier.Operation.valueOf(nbt.getString(OPERATION_KEY))
            );
        } catch (Exception e)
        {
            return null;
        }
    }

    public static void register()
    {
        ModifyItemAttributeModifiersCallback.EVENT.register(ExtraModifier::addModifiers);
    }
}