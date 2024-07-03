package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.content.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.item.*;
import karashokleo.spell_dimension.content.misc.MageMajor;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.spell_power.api.SpellSchool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllItems
{
    public static final Map<SpellSchool, SpellBooksEntry> SPELL_BOOKS = new HashMap<>();
    public static final Map<SpellSchool, List<Item>> BASE_ESSENCES = new HashMap<>();

    public static Item DEBUG_STAFF;
    public static MageMedalItem MAGE_MEDAL;
    public static EnlighteningEssenceItem ENLIGHTENING_ESSENCE;
    public static EnchantedEssenceItem ENCHANTED_ESSENCE;
    public static DisenchantedEssenceItem DISENCHANTED_ESSENCE;
    public static MendingEssenceItem MENDING_ESSENCE;

    public static void register()
    {
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            SPELL_BOOKS.put(school, new SpellBooksEntry(school, MageMajor.getMajors(school)));
            BASE_ESSENCES.put(school, new ArrayList<>());

            for (int i = 0; i < 3; i++)
                BASE_ESSENCES.get(school).add(registerBaseEssence(i, school));
        }
        SPELL_BOOKS.values().forEach(SpellBooksEntry::register);
        DEBUG_STAFF = registerItem("debug_staff", new Item(new FabricItemSettings().maxCount(1)));
        MAGE_MEDAL = registerItem("mage_medal", new MageMedalItem());
        ENLIGHTENING_ESSENCE = registerItem("enlightening_essence", new EnlighteningEssenceItem());
        ENCHANTED_ESSENCE = registerItem("enchanted_essence", new EnchantedEssenceItem());
        DISENCHANTED_ESSENCE = registerItem("disenchanted_essence", new DisenchantedEssenceItem());
        MENDING_ESSENCE = registerItem("mending_essence", new MendingEssenceItem());
    }

    public static <T extends Item> T registerItem(String id, T item)
    {
        Registry.register(Registries.ITEM, SpellDimension.modLoc(id), item);
        return item;
    }

    public static Item registerBaseEssence(int grade, SpellSchool school)
    {
        return registerItem(school.id.getPath() + "_essence_"+grade, new Item(new FabricItemSettings()));
    }
}