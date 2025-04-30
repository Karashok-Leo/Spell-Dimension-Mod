package karashokleo.spell_dimension.data.generic;

import karashokleo.fusion_smithing.item.FusionSmithingTemplateItem;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.item.essence.EnchantedEssenceItem;
import karashokleo.spell_dimension.content.object.Tier;
import karashokleo.spell_dimension.content.recipe.essence.EnchantedEssenceRecipeJsonProvider;
import karashokleo.spell_dimension.data.generic.recipe.*;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.adventurez.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
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
        addMiscItemRecipe(exporter);
        addBaseEssenceRecipe(exporter);
        addEnchantedEssenceRecipe(exporter);
        SDEnchantmentRecipes.add(exporter);
        SDLocateRecipes.add(exporter);
        SDSummonRecipes.add(exporter);
        SDSimpleInfusionRecipes.add(exporter);
        SDSpellInfusionRecipes.add(exporter);
    }

    private static void addMiscItemRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        // Mending Essence
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.MENDING_ESSENCE)
                .pattern(" E ")
                .pattern("ESE")
                .pattern(" E ")
                .input('S', AllItems.SPAWNER_SOUL)
                .input('E', AllItems.ENCHANTED_ESSENCE)
                .criterion(
                        FabricRecipeProvider.hasItem(AllItems.SPAWNER_SOUL),
                        FabricRecipeProvider.conditionsFromItem(AllItems.SPAWNER_SOUL)
                )
                .criterion(
                        FabricRecipeProvider.hasItem(AllItems.ENCHANTED_ESSENCE),
                        FabricRecipeProvider.conditionsFromItem(AllItems.ENCHANTED_ESSENCE)
                )
                .offerTo(exporter);

        // Empty Quest Scroll
        ShapelessRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.QUEST_SCROLL)
                .input(Items.PAPER)
                .input(Ingredient.fromTag(AllTags.ESSENCE_ALL))
                .criterion(
                        FabricRecipeProvider.hasItem(Items.PAPER),
                        FabricRecipeProvider.conditionsFromItem(Items.PAPER)
                )
                .offerTo(exporter, SpellDimension.modLoc("empty_quest_scroll"));

        // Broken Magic Mirror
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.BROKEN_MAGIC_MIRROR)
                .pattern("MMM")
                .pattern("LPL")
                .pattern("MMM")
                .input('M', Ingredient.fromTag(AllTags.ESSENCE.get(1)))
                .input('L', Ingredient.fromTag(AllTags.ESSENCE.get(2)))
                .input('P', Items.ENDER_PEARL)
                .criterion(
                        FabricRecipeProvider.hasItem(Items.ENDER_PEARL),
                        FabricRecipeProvider.conditionsFromItem(Items.ENDER_PEARL)
                )
                .offerTo(exporter);

        // Magic Mirror
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.MAGIC_MIRROR)
                .pattern("MMM")
                .pattern("MBM")
                .pattern("MMM")
                .input('M', Ingredient.fromTag(Tier.LEGENDARY.getTag("material")))
                .input('B', AllItems.BROKEN_MAGIC_MIRROR)
                .criterion(
                        FabricRecipeProvider.hasItem(AllItems.BROKEN_MAGIC_MIRROR),
                        FabricRecipeProvider.conditionsFromItem(AllItems.BROKEN_MAGIC_MIRROR)
                )
                .offerTo(exporter);

        // Bottle of Nightmare
        ShapelessRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.BOTTLE_NIGHTMARE)
                .input(ConsumableItems.BOTTLE_CURSE)
                .input(Ingredient.fromTag(AllTags.ESSENCE_ALL))
                .criterion(
                        FabricRecipeProvider.hasItem(ConsumableItems.BOTTLE_CURSE),
                        FabricRecipeProvider.conditionsFromItem(ConsumableItems.BOTTLE_CURSE)
                )
                .offerTo(exporter, SpellDimension.modLoc("bottle_nightmare"));

        // Bottle of Soul Binding
        ShapelessRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.BOTTLE_SOUL_BINDING)
                .input(ConsumableItems.BOTTLE_SANITY)
                .input(Ingredient.fromTag(AllTags.ESSENCE_ALL))
                .criterion(
                        FabricRecipeProvider.hasItem(ConsumableItems.BOTTLE_SANITY),
                        FabricRecipeProvider.conditionsFromItem(ConsumableItems.BOTTLE_SANITY)
                )
                .offerTo(exporter, SpellDimension.modLoc("bottle_soul_binding"));

        // Netherite Upgrade Smithing Template
        SmithingTransformRecipeJsonBuilder
                .create(
                        Ingredient.EMPTY,
                        Ingredient.ofItems(FusionSmithingTemplateItem.FUSION_SMITHING_TEMPLATE),
                        Ingredient.ofItems(Items.NETHERITE_INGOT),
                        RecipeCategory.MISC,
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE
                )
                .criterion(FabricRecipeProvider.hasItem(Items.NETHERITE_INGOT), FabricRecipeProvider.conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(exporter, SpellDimension.modLoc("netherite_upgrade_smithing_template"));

        // Gilded Blackstone Shard
        ShapelessRecipeJsonBuilder
                .create(RecipeCategory.MISC, ItemInit.GILDED_BLACKSTONE_SHARD)
                .input(ComplementItems.BLACKSTONE_CORE)
                .criterion(
                        FabricRecipeProvider.hasItem(ComplementItems.BLACKSTONE_CORE),
                        FabricRecipeProvider.conditionsFromItem(ComplementItems.BLACKSTONE_CORE)
                )
                .offerTo(exporter, SpellDimension.modLoc("gilded_blackstone_shard"));

        // Celestial Luminary
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.CELESTIAL_LUMINARY)
                .pattern(" D ")
                .pattern("DDD")
                .pattern(" D ")
                .input('D', AllItems.CELESTIAL_DEBRIS)
                .criterion(
                        FabricRecipeProvider.hasItem(AllItems.CELESTIAL_DEBRIS),
                        FabricRecipeProvider.conditionsFromItem(AllItems.CELESTIAL_DEBRIS)
                )
                .offerTo(exporter);

        //
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.ILLUSION_CONTAINER)
                .pattern("FMF")
                .pattern("RCR")
                .pattern("FMF")
                .input('C', AllItems.CELESTIAL_LUMINARY)
                .input('R', AllItems.RANDOM_MATERIAL.get(4))
                .input('F', ComplementItems.SOUL_FLAME)
                .input('M', ComplementItems.SUN_MEMBRANE)
                .criterion(
                        FabricRecipeProvider.hasItem(AllItems.CELESTIAL_LUMINARY),
                        FabricRecipeProvider.conditionsFromItem(AllItems.CELESTIAL_LUMINARY)
                )
                .offerTo(exporter);
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.ILLUSION_UPGRADE)
                .pattern(" D ")
                .pattern("DDD")
                .pattern(" D ")
                .input('D', AllItems.CELESTIAL_DEBRIS)
                .criterion(
                        FabricRecipeProvider.hasItem(AllItems.ILLUSION_CONTAINER),
                        FabricRecipeProvider.conditionsFromItem(AllItems.ILLUSION_CONTAINER)
                )
                .offerTo(exporter);
    }

    private static void addBaseEssenceRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            for (int i = 0; i < 2; i++)
            {
                Item lowGrade = AllItems.BASE_ESSENCES.get(school).get(i);
                Item highGrade = AllItems.BASE_ESSENCES.get(school).get(i + 1);

//                ShapelessRecipeJsonBuilder
//                        .create(RecipeCategory.MISC, lowGrade, 9)
//                        .input(highGrade)
//                        .criterion(FabricRecipeProvider.hasItem(lowGrade), FabricRecipeProvider.conditionsFromItem(lowGrade))
//                        .criterion(FabricRecipeProvider.hasItem(highGrade), FabricRecipeProvider.conditionsFromItem(highGrade))
//                        .offerTo(exporter, SpellDimension.modLoc(school.id.getPath() + "/decompose_" + i));

                ShapedRecipeJsonBuilder
                        .create(RecipeCategory.MISC, highGrade)
                        .pattern(" L ")
                        .pattern("LLL")
                        .pattern(" L ")
                        .input('L', lowGrade)
                        .criterion(FabricRecipeProvider.hasItem(lowGrade), FabricRecipeProvider.conditionsFromItem(lowGrade))
                        .criterion(FabricRecipeProvider.hasItem(highGrade), FabricRecipeProvider.conditionsFromItem(highGrade))
                        .offerTo(exporter, SpellDimension.modLoc(school.id.getPath() + "/compose_" + i));
            }
        }
    }

    private static void addEnchantedEssenceRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        for (SpellSchool school : SchoolUtil.SCHOOLS)
            for (int i = 0; i < 3; i++)
                for (EquipmentSlot slot : EquipmentSlot.values())
                    exporter.accept(
                            new EnchantedEssenceRecipeJsonProvider(
                                    SpellDimension.modLoc(school.id.getPath() + "/" + slot.getName() + "/" + i),
                                    i,
                                    EnchantedEssenceItem.CRAFT_THRESHOLD[i],
                                    slot,
                                    school
                            )
                    );
    }
}