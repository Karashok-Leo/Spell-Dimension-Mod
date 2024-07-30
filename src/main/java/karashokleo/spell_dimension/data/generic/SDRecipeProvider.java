package karashokleo.spell_dimension.data.generic;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.essence.EnchantedEssenceRecipeJsonProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.spell_power.api.SpellSchool;

import java.util.function.Consumer;

public class SDRecipeProvider extends FabricRecipeProvider
{
    public SDRecipeProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter)
    {
        addBaseEssenceRecipe(exporter);
        addEnchantedEssenceRecipe(exporter);
    }

    private static void addBaseEssenceRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            for (int i = 0; i < 2; i++)
            {
                Item sub = AllItems.BASE_ESSENCES.get(school).get(i);
                Item sur = AllItems.BASE_ESSENCES.get(school).get(i + 1);

                ShapelessRecipeJsonBuilder
                        .create(RecipeCategory.MISC, sub, 9)
                        .input(sur)
                        .criterion(FabricRecipeProvider.hasItem(sub), FabricRecipeProvider.conditionsFromItem(sub))
                        .criterion(FabricRecipeProvider.hasItem(sur), FabricRecipeProvider.conditionsFromItem(sur))
                        .offerTo(exporter, SpellDimension.modLoc(school.id.getPath() + "/compose_" + i));

                ShapelessRecipeJsonBuilder
                        .create(RecipeCategory.MISC, sur)
                        .input(sub, 9)
                        .criterion(FabricRecipeProvider.hasItem(sub), FabricRecipeProvider.conditionsFromItem(sub))
                        .criterion(FabricRecipeProvider.hasItem(sur), FabricRecipeProvider.conditionsFromItem(sur))
                        .offerTo(exporter, SpellDimension.modLoc(school.id.getPath() + "/decompose_" + i));
            }
        }
    }

    private static void addEnchantedEssenceRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        for (SpellSchool school : SchoolUtil.SCHOOLS)
            for (int i = 0; i < 3; i++)
                for (EquipmentSlot slot : EquipmentSlot.values())
                    exporter.accept(new EnchantedEssenceRecipeJsonProvider(SpellDimension.modLoc(school.id.getPath() + "/" + slot.getName() + "/" + i), i, (i + 1) * 10, slot, school));
    }
}