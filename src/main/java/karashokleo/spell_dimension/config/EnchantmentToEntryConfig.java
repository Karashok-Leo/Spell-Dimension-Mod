package karashokleo.spell_dimension.config;

import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.PatchouliLookupCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentToEntryConfig
{
    private static final Map<Identifier, Pair<Identifier, Integer>> ENCHANTMENT_TO_ENTRY = new HashMap<>();

    public static void init()
    {
        register("veinmining:vein_mining", "util/mining", 0);
        register("l2hostility:insulator", "mechanic/hostility", 0);
        register("spell-dimension:weakness_immunity", "mechanic/hostility", 1);
        register("spell-dimension:slowness_immunity", "mechanic/hostility", 1);
        register("spell-dimension:poison_immunity", "mechanic/hostility", 1);
        register("spell-dimension:wither_immunity", "mechanic/hostility", 1);
        register("spell-dimension:blindness_immunity", "mechanic/hostility", 1);
        register("spell-dimension:nausea_immunity", "mechanic/hostility", 1);
        register("spell-dimension:levitation_immunity", "mechanic/hostility", 1);
        register("spell-dimension:soul_burner_immunity", "mechanic/hostility", 1);
        register("spell-dimension:freezing_immunity", "mechanic/hostility", 1);
        register("spell-dimension:cursed_immunity", "mechanic/hostility", 1);
        register("spell-dimension:spell_curse", "mechanic/hostility", 1);
        register("spell-dimension:spell_tearing", "mechanic/hostility", 1);
        register("spell-dimension:anti_adaption", "mechanic/hostility", 2);
        register("l2hostility:split_suppress", "mechanic/hostility", 2);
        register("l2hostility:magic_reject", "mechanic/hostility", 3);
        register("l2hostility:hardened", "mechanic/durability", 0);
        register("l2hostility:durable_armor", "mechanic/durability", 1);
        register("l2hostility:safeguard", "mechanic/durability", 1);
        register("spell_engine:spell_infinity", "mage/rune", 0);
        register("spell_power:spell_power", "mage/power", 0);
        register("spell_power:sunfire", "mage/power", 0);
        register("spell_power:soulfrost", "mage/power", 0);
        register("spell_power:energize", "mage/power", 0);

        PatchouliLookupCallback.EVENT.register(stack ->
        {
            if (!stack.isOf(Items.ENCHANTED_BOOK))
            {
                return null;
            }
            NbtList nbtList = EnchantedBookItem.getEnchantmentNbt(stack);
            if (nbtList.size() != 1)
            {
                return null;
            }
            NbtCompound compound = nbtList.getCompound(0);
            Identifier id = EnchantmentHelper.getIdFromNbt(compound);
            return ENCHANTMENT_TO_ENTRY.get(id);
        });
    }

    public static void register(String enchantment, String entryId, int page)
    {
        register(new Identifier(enchantment), SpellDimension.modLoc(entryId), page);
    }

    public static void register(Identifier enchantment, Identifier entryId, int page)
    {
        ENCHANTMENT_TO_ENTRY.put(enchantment, Pair.of(entryId, page));
    }
}
