package net.karashokleo.spelldimension.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AllRecipeSerializers
{
    public static void register()
    {
        Registry.register(Registries.RECIPE_SERIALIZER, EnchantedEssenceRecipe.Serializer.ID, EnchantedEssenceRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, MedalUpgradeRecipe.Serializer.ID, MedalUpgradeRecipe.Serializer.INSTANCE);
    }
}
