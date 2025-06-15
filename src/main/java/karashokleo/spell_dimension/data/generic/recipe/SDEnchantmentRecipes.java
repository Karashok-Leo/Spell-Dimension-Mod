package karashokleo.spell_dimension.data.generic.recipe;

import karashokleo.enchantment_infusion.api.recipe.EnchantmentIngredient;
import karashokleo.enchantment_infusion.api.util.EIRecipeUtil;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.trait.base.TargetEffectTrait;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.enchantment.TraitEffectImmunityEnchantment;
import karashokleo.spell_dimension.init.AllEnchantments;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.SchoolUtil;
import karashokleo.spell_dimension.util.TagUtil;
import net.aleganza.plentyofarmors.item.ModItems;
import net.combatroll.api.Enchantments_CombatRoll;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.spell_engine.api.enchantment.Enchantments_SpellEngine;
import net.spell_power.api.SpellSchools;
import net.spell_power.api.enchantment.Enchantments_SpellPower;
import net.spell_power.api.enchantment.Enchantments_SpellPowerMechanics;

import java.util.Map;
import java.util.function.Consumer;

public class SDEnchantmentRecipes
{
    public static void add(Consumer<RecipeJsonProvider> exporter)
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
            // Spell Curse
            {
                EIRecipeUtil.set(
                        builder -> builder
                                .withTableIngredient(LHEnchantments.CURSE_BLADE, 1)
                                .withPedestalItem(1, Items.FERMENTED_SPIDER_EYE)
                                .withPedestalItem(1, Items.SUGAR)
                                .withPedestalItem(1, ComplementItems.CURSED_DROPLET)
                                .withPedestalItem(1,
                                        Ingredient.fromTag(AllTags.ESSENCE.get(0))),
                        AllEnchantments.SPELL_CURSE,
                        3,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_curse/3")
                );
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(1, ComplementItems.CURSED_DROPLET)
                                .withPedestalItem(1, Items.FERMENTED_SPIDER_EYE)
                                .withPedestalItem(1, Items.SUGAR)
                                .withPedestalItem(1,
                                        Ingredient.fromTag(AllTags.ESSENCE.get(1))),
                        AllEnchantments.SPELL_CURSE,
                        4,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_curse/4")
                );
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(2, ComplementItems.CURSED_DROPLET)
                                .withPedestalItem(1, Items.FERMENTED_SPIDER_EYE)
                                .withPedestalItem(1, Items.SUGAR)
                                .withPedestalItem(2,
                                        Ingredient.fromTag(AllTags.ESSENCE.get(1))),
                        AllEnchantments.SPELL_CURSE,
                        5,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_curse/5")
                );
            }

            // Spell Haste
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
                                    .withTableIngredient(Enchantments_SpellPowerMechanics.HASTE, level)
                                    .withPedestalItem(2, MiscItems.CHAOS.ingot())
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
                                    .withPedestalItem(2, MiscItems.CHAOS.ingot())
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

            // Spell Tearing
            for (int i = 0; i < 5; i++)
            {
                int level = i + 1;
                int baseIngredient = baseIngredientArray[i];
                int essenceIngredient = essenceIngredientArray[i];
                int essenceGrade = essenceGradeArray[i];

                Ingredient weaponIngredient = i < 3 ?
                        Ingredient.fromTag(TagUtil.itemTag("common/weapon")) :
                        Ingredient.fromTag(TagUtil.itemTag("uncommon/weapon"));

                if (i == 0)
                {
                    EIRecipeUtil.set(
                            builder -> builder
                                    .withPedestalItem(baseIngredient, weaponIngredient)
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            AllEnchantments.SPELL_TEARING,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/spell_tearing/" + level)
                    );
                } else
                {
                    EIRecipeUtil.add(
                            builder -> builder
                                    .withPedestalItem(baseIngredient, weaponIngredient)
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            AllEnchantments.SPELL_TEARING,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/spell_tearing/" + level)
                    );
                }
            }

            // Anti-Adaption
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
                                    .withPedestalItem(1, Items.SCUTE)
                                    .withPedestalItem(baseIngredient, Ingredient.fromTag(AllTags.MAGIC_WEAPON))
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            AllEnchantments.ANTI_ADAPTION,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/anti_adaption/" + level)
                    );
                } else
                {
                    EIRecipeUtil.add(
                            builder -> builder
                                    .withPedestalItem(1, Items.SCUTE)
                                    .withPedestalItem(baseIngredient, Ingredient.fromTag(AllTags.MAGIC_WEAPON))
                                    .withPedestalItem(essenceIngredient,
                                            Ingredient.fromTag(AllTags.ESSENCE.get(essenceGrade))),
                            AllEnchantments.ANTI_ADAPTION,
                            level,
                            exporter,
                            SpellDimension.modLoc("enchantment/anti_adaption/" + level)
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
                EIRecipeUtil.set(
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

            // Spell Leech
            {
                EIRecipeUtil.set(
                        builder -> builder
                                .withTableIngredient(Enchantments.SMITE, 4)
                                .withPedestalItem(2, ConsumableItems.BOTTLE_CURSE)
                                .withPedestalItem(2, ConsumableItems.BOTTLE_SANITY)
                                .withPedestalItem(2, Ingredient.fromTag(AllTags.ESSENCE_ALL)),
                        AllEnchantments.SPELL_LEECH,
                        1,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_leech/" + 1)
                );
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(2, Items.GOLD_INGOT)
                                .withPedestalItem(2, Items.EMERALD)
                                .withPedestalItem(2, Items.DIAMOND)
                                .withPedestalItem(2, Items.NETHERITE_INGOT),
                        AllEnchantments.SPELL_LEECH,
                        3,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_leech/" + 3)
                );
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(2, Items.BLAZE_POWDER)
                                .withPedestalItem(2, Items.GHAST_TEAR)
                                .withPedestalItem(2, Items.DRAGON_BREATH)
                                .withPedestalItem(2, Items.ENCHANTED_GOLDEN_APPLE),
                        AllEnchantments.SPELL_LEECH,
                        5,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_leech/" + 5)
                );
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(2, MiscItems.WITCH_DROPLET)
                                .withPedestalItem(2, ComplementItems.CURSED_DROPLET)
                                .withPedestalItem(2, ComplementItems.SOUL_FLAME)
                                .withPedestalItem(2, ComplementItems.LIFE_ESSENCE),
                        AllEnchantments.SPELL_LEECH,
                        7,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_leech/" + 7)
                );
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(2, MiscItems.MIRACLE_POWDER)
                                .withPedestalItem(2, MiscItems.HOSTILITY_ESSENCE)
                                .withPedestalItem(2, ComplementItems.VOID_EYE)
                                .withPedestalItem(2, ComplementItems.FORCE_FIELD),
                        AllEnchantments.SPELL_LEECH,
                        9,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_leech/" + 9)
                );
            }

            // Spell Resistance
            for (int i = 0; i < 3; i++)
            {
                int finalI = i;
                int level = i + 1;
                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(finalI + 1, ModItems.STARDUSITE_INGOT)
                                .withPedestalItem(finalI + 1,
                                        Ingredient.fromTag(AllTags.ESSENCE.get(finalI))),
                        AllEnchantments.SPELL_RESISTANCE,
                        level,
                        exporter,
                        SpellDimension.modLoc("enchantment/spell_resistance/" + level)
                );
            }

            // Hardened
            EIRecipeUtil.set(
                    builder -> builder
                            .withTableIngredient(Enchantments.UNBREAKING, 3)
                            .withPedestalItem(2, ComplementItems.SHULKERATE.ingot())
                            .withPedestalItem(2, ComplementItems.EXPLOSION_SHARD)
                            .withPedestalItem(2, AllItems.MENDING_ESSENCE),
                    LHEnchantments.HARDENED,
                    1,
                    exporter,
                    SpellDimension.modLoc("enchantment/hardened")
            );

            // Mending
            {
                EIRecipeUtil.set(
                        builder -> builder
                                .withPedestalItem(1, Ingredient.fromTag(AllTags.ESSENCE.get(2)))
                                .withPedestalItem(3, AllItems.MENDING_ESSENCE),
                        Enchantments.MENDING,
                        1,
                        exporter,
                        SpellDimension.modLoc("enchantment/mending/vanilla_mending")
                );

                EIRecipeUtil.set(
                        builder -> builder
                                .withTableIngredient(Enchantments.MENDING, 1)
                                .withPedestalItem(5, Ingredient.fromTag(AllTags.ESSENCE.get(0))),
                        AllEnchantments.SPELL_MENDING,
                        1,
                        exporter,
                        SpellDimension.modLoc("enchantment/mending/spell_mending/" + 1)
                );

                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(3, Ingredient.fromTag(AllTags.ESSENCE.get(1))),
                        AllEnchantments.SPELL_MENDING,
                        2,
                        exporter,
                        SpellDimension.modLoc("enchantment/mending/spell_mending/" + 2)
                );

                EIRecipeUtil.add(
                        builder -> builder
                                .withPedestalItem(1, Ingredient.fromTag(AllTags.ESSENCE.get(2))),
                        AllEnchantments.SPELL_MENDING,
                        3,
                        exporter,
                        SpellDimension.modLoc("enchantment/mending/spell_mending/" + 3)
                );
            }
        }

        // Effect Immunity Enchantments
        {
            Map<TraitEffectImmunityEnchantment, TargetEffectTrait> effect_immunity = Map.of(
                    AllEnchantments.WEAKNESS_IMMUNITY, LHTraits.WEAKNESS,
                    AllEnchantments.SLOWNESS_IMMUNITY, LHTraits.SLOWNESS,
                    AllEnchantments.POISON_IMMUNITY, LHTraits.POISON,
                    AllEnchantments.WITHER_IMMUNITY, LHTraits.WITHER,
                    AllEnchantments.BLINDNESS_IMMUNITY, LHTraits.BLIND,
                    AllEnchantments.CONFUSION_IMMUNITY, LHTraits.CONFUSION,
                    AllEnchantments.LEVITATION_IMMUNITY, LHTraits.LEVITATION,
                    AllEnchantments.SOUL_BURNER_IMMUNITY, LHTraits.SOUL_BURNER,
                    AllEnchantments.FREEZING_IMMUNITY, LHTraits.FREEZING,
                    AllEnchantments.CURSED_IMMUNITY, LHTraits.CURSED
            );
            effect_immunity.forEach((enchantment, trait) ->
            {
                Identifier id = Registries.STATUS_EFFECT.getId(enchantment.getEffect());
                assert id != null;
                EIRecipeUtil.set(
                        builder -> builder
                                .withPedestalItem(3, Ingredient.fromTag(AllTags.ESSENCE.get(2)))
                                .withPedestalItem(3, trait),
                        enchantment,
                        1,
                        exporter,
                        SpellDimension.modLoc("enchantment/effect_immunity/" + id.getPath())
                );
            });
        }

        // Spell Blade Enchantments
        {
            EIRecipeUtil.set(
                    builder -> builder
                            .withTableIngredient(Enchantments_SpellPower.SUNFIRE, 1)
                            .withPedestalItem(1, EnchantmentIngredient.of(Enchantments.SHARPNESS, 1)),
                    AllEnchantments.SPELL_BLADE_SUNFIRE,
                    1,
                    exporter,
                    SpellDimension.modLoc("enchantment/spell_blade/sunfire")
            );
            EIRecipeUtil.set(
                    builder -> builder
                            .withTableIngredient(Enchantments_SpellPower.SOULFROST, 1)
                            .withPedestalItem(1, EnchantmentIngredient.of(Enchantments.SHARPNESS, 1)),
                    AllEnchantments.SPELL_BLADE_SOULFROST,
                    1,
                    exporter,
                    SpellDimension.modLoc("enchantment/spell_blade/soulfrost")
            );
            EIRecipeUtil.set(
                    builder -> builder
                            .withTableIngredient(Enchantments_SpellPower.ENERGIZE, 1)
                            .withPedestalItem(1, EnchantmentIngredient.of(Enchantments.SHARPNESS, 1)),
                    AllEnchantments.SPELL_BLADE_ENERGIZE,
                    1,
                    exporter,
                    SpellDimension.modLoc("enchantment/spell_blade/enegize")
            );
            EIRecipeUtil.set(
                    builder -> builder
                            .withTableIngredient(Enchantments_SpellPowerMechanics.HASTE, 1)
                            .withPedestalItem(1, EnchantmentIngredient.of(Enchantments.SHARPNESS, 1)),
                    AllEnchantments.SPELL_BLADE_HASTE,
                    1,
                    exporter,
                    SpellDimension.modLoc("enchantment/spell_blade/haste")
            );
        }

        // Vanilla
        {
            EIRecipeUtil.set(
                    builder -> builder
                            .withPedestalItem(1, Items.LAVA_BUCKET)
                            .withPedestalItem(1, Items.CACTUS)
                            .withPedestalItem(1, Items.ENDER_EYE)
                            .withPedestalItem(1, Ingredient.fromTag(ItemTags.NOTEBLOCK_TOP_INSTRUMENTS)),
                    Enchantments.VANISHING_CURSE,
                    1,
                    exporter,
                    SpellDimension.modLoc("enchantment/vanishing_curse")
            );
        }
    }
}
