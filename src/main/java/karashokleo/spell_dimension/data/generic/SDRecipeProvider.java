package karashokleo.spell_dimension.data.generic;

import karashokleo.enchantment_infusion.api.util.EIRecipeUtil;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.essence.EnchantedEssenceRecipeJsonProvider;
import karashokleo.spell_dimension.init.AllEnchantments;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.combatroll.api.Enchantments_CombatRoll;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.spell_engine.api.enchantment.Enchantments_SpellEngine;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.spell_power.api.enchantment.Enchantments_SpellPower;
import net.spell_power.api.enchantment.Enchantments_SpellPowerMechanics;

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
        addEnchantmentRecipe(exporter);
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

                ShapelessRecipeJsonBuilder
                        .create(RecipeCategory.MISC, highGrade)
                        .input(lowGrade, 9)
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
                    exporter.accept(new EnchantedEssenceRecipeJsonProvider(SpellDimension.modLoc(school.id.getPath() + "/" + slot.getName() + "/" + i), i, (i + 1) * 10, slot, school));
    }

    private static void addEnchantmentRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        int[] baseIngredientArray = {1, 1, 2, 2, 3};
        int[] essenceIngredientArray = {1, 2, 2, 3, 3};
        int[] essenceGradeArray = {0, 1, 1, 2, 2};

        // SpellPower Enchantments
        {
            for (int i = 0; i < 5; i++)
            {
                int level = i + 1;
                int baseIngredient = baseIngredientArray[i];
                int essenceIngredient = essenceIngredientArray[i];
                int essenceGrade = essenceGradeArray[i];

                // Spell Power 1,2,3,4,5
                EIRecipeUtil.add(
                        builder -> SchoolUtil.SCHOOLS.forEach(
                                school -> builder.withPedestalItem(1, AllItems.BASE_ESSENCES.get(school).get(essenceGrade))),
                        Enchantments_SpellPower.SPELL_POWER,
                        level,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_power/" + level)
                );

                // SunFire 1,2,3,4,5
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(baseIngredient,
                                        Ingredient.ofItems(
                                                ComplementItems.RESONANT_FEATHER,
                                                ComplementItems.SOUL_FLAME
                                        ))
                                .withPedestalItem(essenceIngredient,
                                        Ingredient.ofItems(
                                                AllItems.BASE_ESSENCES.get(SpellSchools.ARCANE).get(essenceGrade),
                                                AllItems.BASE_ESSENCES.get(SpellSchools.FIRE).get(essenceGrade)
                                        )
                                ),
                        Enchantments_SpellPower.SUNFIRE,
                        level,
                        exporter,
                        SpellDimension.modLoc("enchantment/sunfire/" + level)
                );

                // SoulFrost 1,2,3,4,5
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(baseIngredient,
                                        Ingredient.ofItems(
                                                ComplementItems.HARD_ICE,
                                                ComplementItems.CURSED_DROPLET
                                        ))
                                .withPedestalItem(essenceIngredient,
                                        Ingredient.ofItems(
                                                AllItems.BASE_ESSENCES.get(SpellSchools.FROST).get(essenceGrade),
                                                AllItems.BASE_ESSENCES.get(SpellSchools.SOUL).get(essenceGrade)
                                        )
                                ),
                        Enchantments_SpellPower.SOULFROST,
                        level,
                        exporter,
                        SpellDimension.modLoc("enchantment/soulfrost/" + level)
                );

                // Energize 1,2,3,4,5
                EIRecipeUtil.set(
                        builder -> builder
                                .withPedestalItem(baseIngredient,
                                        Ingredient.ofItems(
                                                ComplementItems.LIFE_ESSENCE,
                                                ComplementItems.CAPTURED_WIND
                                        ))
                                .withPedestalItem(essenceIngredient,
                                        Ingredient.ofItems(
                                                AllItems.BASE_ESSENCES.get(SpellSchools.HEALING).get(essenceGrade),
                                                AllItems.BASE_ESSENCES.get(SpellSchools.LIGHTNING).get(essenceGrade)
                                        )
                                ),
                        Enchantments_SpellPower.ENERGIZE,
                        level,
                        exporter,
                        SpellDimension.modLoc("enchantment/energize/" + level)
                );
            }
        }

        // SpellPowerMechanics Enchantments
        {
            for (int i = 0; i < 5; i++)
            {
                int level = i + 1;
                int baseIngredient = baseIngredientArray[i];
                int essenceIngredient = essenceIngredientArray[i];
                int essenceGrade = essenceGradeArray[i];

                if (i == 0)
                {
                    EIRecipeUtil.set(
                            builder -> builder
                                    .withTableIngredient(Enchantments.EFFICIENCY, 1)
                                    .withPedestalItem(baseIngredient, MiscItems.CHAOS.ingot())
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            Enchantments_SpellPowerMechanics.HASTE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/haste/" + level)
                    );
                    EIRecipeUtil.set(
                            builder -> builder
                                    .withTableIngredient(Enchantments.POWER, level)
                                    .withPedestalItem(baseIngredient, MiscItems.CHAOS.ingot())
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            Enchantments_SpellPowerMechanics.CRITICAL_CHANCE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/critical_chance/" + level)
                    );
                    EIRecipeUtil.set(
                            builder -> builder
                                    .withTableIngredient(Enchantments.SHARPNESS, level)
                                    .withPedestalItem(baseIngredient, MiscItems.CHAOS.ingot())
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            Enchantments_SpellPowerMechanics.CRITICAL_DAMAGE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/critical_damage/" + level)
                    );
                } else
                {
                    EIRecipeUtil.add(
                            builder -> builder
                                    .withPedestalItem(baseIngredient, MiscItems.CHAOS.ingot())
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            Enchantments_SpellPowerMechanics.HASTE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/haste/" + level)
                    );
                    EIRecipeUtil.add(
                            builder -> builder
                                    .withPedestalItem(baseIngredient, MiscItems.CHAOS.ingot())
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            Enchantments_SpellPowerMechanics.CRITICAL_CHANCE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/critical_chance/" + level)
                    );
                    EIRecipeUtil.add(
                            builder -> builder
                                    .withPedestalItem(baseIngredient, MiscItems.CHAOS.ingot())
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            Enchantments_SpellPowerMechanics.CRITICAL_DAMAGE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/critical_damage/" + level)
                    );
                }
            }
        }

        // SpellEngine Enchantments
        {
            EIRecipeUtil.set(
                    builder -> builder
                            .withTableIngredient(Enchantments.INFINITY, 1)
                            .withPedestalItem(1, ComplementItems.SUN_MEMBRANE)
                            .withPedestalItem(1, ComplementItems.STORM_CORE)
                            .withPedestalItem(1, Ingredient.fromTag(AllTags.ESSENCE.get(2))),
                    Enchantments_SpellEngine.INFINITY,
                    1,
                    exporter,
                    SpellDimension.modLoc("enchantment/spell_infinity/1")
            );
        }

        // SpellDimension Enchantments
        {
            // Spell Curse, Spell Haste
            for (int i = 0; i < 5; i++)
            {
                int level = i + 1;
                int baseIngredient = baseIngredientArray[i];
                int essenceIngredient = essenceIngredientArray[i];
                int essenceGrade = essenceGradeArray[i];

                if (i == 0)
                {
                    EIRecipeUtil.set(
                            builder -> builder
                                    .withTableIngredient(LHEnchantments.CURSE_BLADE, level)
                                    .withPedestalItem(baseIngredient, ComplementItems.CURSED_DROPLET)
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            AllEnchantments.SPELL_CURSE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/spell_curse/" + level)
                    );
                    EIRecipeUtil.set(
                            builder -> builder
                                    .withTableIngredient(Enchantments_SpellPowerMechanics.HASTE, level)
                                    .withPedestalItem(baseIngredient, ComplementItems.RESONANT_FEATHER)
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            AllEnchantments.SPELL_HASTE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/spell_haste/" + level)
                    );
                } else
                {
                    EIRecipeUtil.add(
                            builder -> builder
                                    .withPedestalItem(baseIngredient, ComplementItems.CURSED_DROPLET)
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            AllEnchantments.SPELL_CURSE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/spell_curse/" + level)
                    );
                    EIRecipeUtil.add(
                            builder -> builder
                                    .withPedestalItem(baseIngredient, ComplementItems.RESONANT_FEATHER)
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            AllEnchantments.SPELL_HASTE,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/spell_haste/" + level)
                    );
                }
            }

            // Stress Response
            for (int i = 0; i < 3; i++)
            {
                int finalI = i;
                int level = i + 1;
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(finalI + 1, MiscItems.MIRACLE_POWDER)
                                .withPedestalItem(finalI + 1,
                                        Ingredient.fromTag(AllTags.ESSENCE.get(finalI))),
                        AllEnchantments.STRESS_RESPONSE,
                        level,
                        exporter,
                        SpellDimension.modLoc("enchantment/stress_response/" + level)
                );
            }

            // Spell Dash
            {
                EIRecipeUtil.add(
                        builder -> builder
                                .withTableIngredient(Enchantments_CombatRoll.RECHARGE, 10)
                                .withPedestalItem(2, ComplementItems.CAPTURED_WIND)
                                .withPedestalItem(2, ComplementItems.STORM_CORE)
                                .withPedestalItem(2, ComplementItems.SUN_MEMBRANE)
                                .withPedestalItem(2, ComplementItems.RESONANT_FEATHER),
                        AllEnchantments.SPELL_DASH,
                        1,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_dash/" + 1)
                );
            }
        }
    }
}