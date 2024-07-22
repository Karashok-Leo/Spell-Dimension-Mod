package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.content.recipe.essence.EnchantedEssenceRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AllRecipeSerializers
{
    public static void register()
    {
        Registry.register(Registries.RECIPE_SERIALIZER, EnchantedEssenceRecipe.Serializer.ID, EnchantedEssenceRecipe.Serializer.INSTANCE);
    }
}
