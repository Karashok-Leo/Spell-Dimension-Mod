package karashokleo.spell_dimension.content.object;

import com.google.common.collect.Multimap;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public record EnchantedModifier(
        int threshold,
        EquipmentSlot slot,
        EnlighteningModifier modifier
)
{
    private static final String ENCHANTED_MODIFIER_KEY = "EnchantedModifier";
    private static final String LEVEL_KEY = "Level";
    private static final String MODIFIERS_KEY = "Modifiers";

    private static final String THRESHOLD_KEY = "Threshold";
    private static final String SLOT_KEY = "Slot";
    private static final String MODIFIER_KEY = "Modifier";

    public EnchantedModifier(int threshold, EquipmentSlot slot, EnlighteningModifier modifier)
    {
        Objects.requireNonNull(slot);
        Objects.requireNonNull(modifier);
        this.threshold = Math.max(0, threshold);
        this.slot = slot;
        this.modifier = modifier;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean canApply(ItemStack stack, EquipmentSlot slot)
    {
        if (stack.getItem() instanceof Equipment equipment)
        {
            EquipmentSlot stackSlot = equipment.getSlotType();
            if (stackSlot.isArmorSlot())
            {
                return slot == stackSlot;
            }
        }
        return !slot.isArmorSlot();
    }

    public boolean apply(ItemStack stack)
    {
        if (!canApply(stack, slot))
        {
            return false;
        }
        // get enchanted modifier data
        NbtCompound enchantedModifiers = stack.getOrCreateSubNbt(ENCHANTED_MODIFIER_KEY);
        // get level, fail if level is greater than threshold
        int level = enchantedModifiers.getInt(LEVEL_KEY);
        if (level > threshold)
        {
            return false;
        }
        // else increment level
        enchantedModifiers.putInt(LEVEL_KEY, level + 1);
        // get modifier list
        if (!enchantedModifiers.contains(MODIFIERS_KEY, NbtElement.LIST_TYPE))
        {
            enchantedModifiers.put(MODIFIERS_KEY, new NbtList());
        }
        NbtList nbtList = enchantedModifiers.getList(MODIFIERS_KEY, NbtElement.COMPOUND_TYPE);
        // try to overwrite existing modifier
        for (int i = 0; i < nbtList.size(); i++)
        {
            NbtCompound entry = nbtList.getCompound(i);
            // check if slot is valid
            @Nullable EquipmentSlot modifierSlot = getSlotFromString(entry.getString(SLOT_KEY));
            if (slot != modifierSlot) continue;
            // try overwrite enlightening modifier
            NbtCompound enlighteningModifier = entry.getCompound(MODIFIER_KEY);
            if (this.modifier.tryOverWriteNbt(enlighteningModifier))
            {
                return true;
            }
        }
        // if not found, add new modifier
        NbtCompound entry = new NbtCompound();
        entry.putString(SLOT_KEY, slot.getName());
        entry.put(MODIFIER_KEY, modifier.toNbt());
        nbtList.add(entry);
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
        // get enchanted modifier data
        NbtCompound enchantedModifier = stack.getSubNbt(ENCHANTED_MODIFIER_KEY);
        if (enchantedModifier == null)
        {
            return;
        }
        // get modifier list
        if (!enchantedModifier.contains(MODIFIERS_KEY, NbtElement.LIST_TYPE))
        {
            return;
        }
        NbtList nbtList = enchantedModifier.getList(MODIFIERS_KEY, NbtElement.COMPOUND_TYPE);
        // apply valid modifiers
        for (int i = 0; i < nbtList.size(); ++i)
        {
            NbtCompound entry = nbtList.getCompound(i);
            // check if slot is valid
            @Nullable EquipmentSlot modifierSlot = getSlotFromString(entry.getString(SLOT_KEY));
            if (slot != modifierSlot) continue;
            // get enlightening modifier
            EnlighteningModifier enlighteningModifier = EnlighteningModifier.fromNbt(entry.getCompound(MODIFIER_KEY));
            if (enlighteningModifier == null) continue;
            enlighteningModifier.applyToStack(modifiers);
        }
    }

    public static void tryConvert(ItemStack stack)
    {
        // get enchanted modifier data
        NbtCompound enchantedModifier = stack.getSubNbt(ENCHANTED_MODIFIER_KEY);
        if (enchantedModifier == null)
        {
            return;
        }
        // get modifier list
        if (!enchantedModifier.contains(MODIFIERS_KEY, NbtElement.LIST_TYPE))
        {
            return;
        }
        NbtList nbtList = enchantedModifier.getList(MODIFIERS_KEY, NbtElement.COMPOUND_TYPE);
        // get slot
        EquipmentSlot slot = EquipmentSlot.MAINHAND;
        if (stack.getItem() instanceof Equipment equipment)
        {
            slot = equipment.getSlotType();
        }
        // apply valid modifiers
        for (int i = 0; i < nbtList.size(); ++i)
        {
            // get enlightening modifier
            EnlighteningModifier enlighteningModifier = EnlighteningModifier.fromNbt(nbtList.getCompound(i));
            if (enlighteningModifier == null) continue;
            NbtCompound entry = new NbtCompound();
            entry.putString(SLOT_KEY, slot.getName());
            entry.put(MODIFIER_KEY, enlighteningModifier.toNbt());
            nbtList.set(i, entry);
        }
    }

    @Nullable
    private static EquipmentSlot getSlotFromString(String slotName)
    {
        for (EquipmentSlot slot : EquipmentSlot.values())
        {
            if (slot.getName().equals(slotName))
            {
                return slot;
            }
        }
        return null;
    }

    public static void levelTooltip(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(ENCHANTED_MODIFIER_KEY)) return;
        NbtCompound extraModifiers = nbt.getCompound(ENCHANTED_MODIFIER_KEY);
        if (!extraModifiers.contains(LEVEL_KEY)) return;
        lines.add(ScreenTexts.EMPTY);
        lines.add(SDTexts.TOOLTIP$LEVEL.get(extraModifiers.getInt(LEVEL_KEY)).formatted(Formatting.BOLD));
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
                    EnlighteningModifier.fromNbt(nbt)
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