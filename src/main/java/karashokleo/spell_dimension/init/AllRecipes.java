package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.essence.EnchantedEssenceRecipeSerializer;
import karashokleo.spell_dimension.content.recipe.locate.LocateBiomeRecipeSerializer;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.content.recipe.locate.LocateStructureRecipeSerializer;
import karashokleo.spell_dimension.content.recipe.spell.SpellInfusionRecipeSerializer;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AllRecipes
{
    public static void register()
    {
        Registry.register(Registries.RECIPE_TYPE, LocateRecipe.ID, LocateRecipe.TYPE);
        Registry.register(Registries.RECIPE_TYPE, SummonRecipe.ID, SummonRecipe.TYPE);

        Registry.register(Registries.RECIPE_SERIALIZER, SpellDimension.modLoc("enchanted_essence"), EnchantedEssenceRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, SpellDimension.modLoc("locate_structure"), LocateStructureRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, SpellDimension.modLoc("locate_biome"), LocateBiomeRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, SpellDimension.modLoc("summon"), SummonRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, SpellDimension.modLoc("spell_infusion"), SpellInfusionRecipeSerializer.INSTANCE);
    }
}
