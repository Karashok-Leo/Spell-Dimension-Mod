package net.karashokleo.spelldimension.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.item.mod_item.DisenchantedEssenceItem;
import net.karashokleo.spelldimension.item.mod_item.EnchantedEssenceItem;
import net.karashokleo.spelldimension.item.mod_item.MageMedalItem;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.spell_power.api.MagicSchool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllItems
{
    public static final String[] GRADES = {"primary_", "secondary_", "advanced_", "perfect_"};

    public static final Map<MagicSchool, SpellBooksEntry> SPELL_BOOKS = new HashMap<>();
    public static final Map<MagicSchool, List<Item>> BASE_ESSENCES = new HashMap<>();

    public static Item DEBUG_STAFF;
    public static MageMedalItem MAGE_MEDAL;
    public static EnchantedEssenceItem ENCHANTED_ESSENCE;
    public static DisenchantedEssenceItem DISENCHANTED_ESSENCE;

    public static void register()
    {
        for (MagicSchool school : MagicSchool.values())
        {
            if (!school.isMagical) continue;

            SPELL_BOOKS.put(school, new SpellBooksEntry(school, MageMajor.getMajors(school)));
            BASE_ESSENCES.put(school, new ArrayList<>());

            for (int i = 0; i < 3; i++)
                BASE_ESSENCES.get(school).add(registerBaseEssence(i, school));
        }
        SPELL_BOOKS.values().forEach(SpellBooksEntry::register);
        DEBUG_STAFF = registerItem("debug_staff", new Item(new FabricItemSettings().maxCount(1)));
        MAGE_MEDAL = registerItem("mage_medal", new MageMedalItem());
        ENCHANTED_ESSENCE = registerItem("enchanted_essence", new EnchantedEssenceItem());
        DISENCHANTED_ESSENCE = registerItem("disenchanted_essence", new DisenchantedEssenceItem());
    }

    public static <T extends Item> T registerItem(String id, T item)
    {
        Registry.register(Registries.ITEM, SpellDimension.modLoc(id), item);
        return item;
    }

    public static Item registerBaseEssence(int tier, MagicSchool school)
    {
        return registerItem(GRADES[tier] + school.spellName() + "_essence", new Item(new FabricItemSettings()));
    }
}