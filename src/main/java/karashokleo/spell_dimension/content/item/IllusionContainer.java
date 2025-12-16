package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public interface IllusionContainer
{
    int GEAR_CONSUME = 8;
    int ENCHANTMENT_CONSUME = 2048;
    String[] MATERIAL_KEYS = new String[]{
        "CommonMaterial",
        "UncommonMaterial",
        "RareMaterial",
        "EpicMaterial",
        "LegendaryMaterial"
    };
    String ENCHANTMENT_KEY = "EnchantmentPoints";

    static int getMaterialTier(ItemStack stack)
    {
        for (int i = 0; i < 5; i++)
        {
            if (stack.isIn(AllTags.GEARS.get(i)))
            {
                return i * stack.getCount();
            }
        }
        return -1;
    }

    static int getEnchantmentPoints(ItemStack stack)
    {
        return EnchantmentHelper.get(stack)
            .values()
            .stream()
            .mapToInt(lv -> 1 << lv)
            .sum()
            * stack.getCount();
    }

    static void convert(ItemStack stack, int materialTier, int enchantmentPoints)
    {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (materialTier >= 0)
        {
            int material = nbt.getInt(MATERIAL_KEYS[materialTier]);
            nbt.putInt(MATERIAL_KEYS[materialTier], material + 1);
        }
        if (enchantmentPoints >= 0)
        {
            enchantmentPoints += nbt.getInt(ENCHANTMENT_KEY);
            nbt.putInt(ENCHANTMENT_KEY, enchantmentPoints);
        }
    }

    static void retrieve(ItemStack stack, PlayerInventory inventory)
    {
        NbtCompound nbt = stack.getOrCreateNbt();
        for (int i = 0; i < 5; i++)
        {
            int material = nbt.getInt(MATERIAL_KEYS[i]);
            int count = material / IllusionContainer.GEAR_CONSUME;
            inventory.offerOrDrop(new ItemStack(AllItems.RANDOM_MATERIAL.get(i), count));
            nbt.putInt(MATERIAL_KEYS[i], material - count * IllusionContainer.GEAR_CONSUME);
        }
        int enchantmentPoints = nbt.getInt(ENCHANTMENT_KEY);
        int count = enchantmentPoints / IllusionContainer.ENCHANTMENT_CONSUME;
        inventory.offerOrDrop(new ItemStack(ConsumableItems.BOOK_OMNISCIENCE, count));
        nbt.putInt(ENCHANTMENT_KEY, enchantmentPoints - count * IllusionContainer.ENCHANTMENT_CONSUME);
    }

    static void appendTooltip(ItemStack stack, List<Text> tooltip)
    {
        NbtCompound nbt = stack.getOrCreateNbt();
        tooltip.add(SDTexts.TOOLTIP$CONTAINER$RETRIEVE.get().formatted(Formatting.AQUA));
        tooltip.add(SDTexts.TOOLTIP$CONTAINER$CONVERTED.get().formatted(Formatting.GOLD));
        for (int i = 0; i < 5; i++)
        {
            int materialValue = nbt.getInt(MATERIAL_KEYS[i]);
            tooltip.add(SDTexts.TOOLTIP$CONTAINER$CONVERTED$VALUE.get(
                materialValue / GEAR_CONSUME,
                materialValue % GEAR_CONSUME,
                GEAR_CONSUME,
                AllItems.RANDOM_MATERIAL.get(i).getName()
            ).formatted(Formatting.DARK_AQUA));
        }
        int enchantmentValue = nbt.getInt(ENCHANTMENT_KEY);
        tooltip.add(SDTexts.TOOLTIP$CONTAINER$CONVERTED$VALUE.get(
            enchantmentValue / ENCHANTMENT_CONSUME,
            enchantmentValue % ENCHANTMENT_CONSUME,
            ENCHANTMENT_CONSUME,
            ConsumableItems.BOOK_OMNISCIENCE.getName()
        ).formatted(Formatting.DARK_PURPLE));
    }
}
