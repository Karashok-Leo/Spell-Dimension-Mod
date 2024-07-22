package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.item.essence.*;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
    public static final List<Item> COLORABLE = new ArrayList<>();
    public static final Map<SpellSchool, List<BaseEssenceItem>> BASE_ESSENCES = new HashMap<>();

    public static Item DEBUG_STAFF;
    public static EnlighteningEssenceItem ENLIGHTENING_ESSENCE;
    public static EnchantedEssenceItem ENCHANTED_ESSENCE;
    public static DisenchantedEssenceItem DISENCHANTED_ESSENCE;
    public static MendingEssenceItem MENDING_ESSENCE;

    public static void register()
    {
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            BASE_ESSENCES.put(school, new ArrayList<>());

            for (int grade = 0; grade < 3; grade++)
                BASE_ESSENCES.get(school).add(registerItem(school.id.getPath() + "_essence_" + grade, new BaseEssenceItem(school, grade)));
        }
        DEBUG_STAFF = registerItem("debug_staff", new Item(new FabricItemSettings().maxCount(1)));
        ENLIGHTENING_ESSENCE = registerItem("enlightening_essence", new EnlighteningEssenceItem());
        ENCHANTED_ESSENCE = registerItem("enchanted_essence", new EnchantedEssenceItem());
        DISENCHANTED_ESSENCE = registerItem("disenchanted_essence", new DisenchantedEssenceItem());
        MENDING_ESSENCE = registerItem("mending_essence", new MendingEssenceItem());
    }

    public static <T extends Item> T registerItem(String id, T item)
    {
        Registry.register(Registries.ITEM, SpellDimension.modLoc(id), item);
        if (item instanceof ColorProvider) COLORABLE.add(item);
        return item;
    }
}