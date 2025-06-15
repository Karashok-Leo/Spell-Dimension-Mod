package karashokleo.spell_dimension.data.generic;

import com.kyanite.deeperdarker.content.DDItems;
import karashokleo.fusion_smithing.item.FusionSmithingTemplateItem;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.item.armor.ArmorSet;
import karashokleo.spell_dimension.content.item.essence.EnchantedEssenceItem;
import karashokleo.spell_dimension.content.object.Tier;
import karashokleo.spell_dimension.content.recipe.essence.EnchantedEssenceRecipeJsonProvider;
import karashokleo.spell_dimension.data.generic.recipe.*;
import karashokleo.spell_dimension.init.AllArmors;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.init.AllWeapons;
import karashokleo.spell_dimension.util.SchoolUtil;
import karashokleo.spell_dimension.util.TagUtil;
import net.adventurez.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.spell_power.api.SpellSchool;
import nourl.mythicmetals.item.MythicItems;

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
        addWeaponRecipes(exporter);
        addArmorRecipes(exporter, AllArmors.LIGHTNING_ROBE, AllArmors.NETHERITE_LIGHTNING_ROBE, Items.LIGHTNING_ROD);
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

        // Illusion Container
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.ILLUSION_CONTAINER)
                .pattern("MSM")
                .pattern("RCR")
                .pattern("MSM")
                .input('C', AllItems.CELESTIAL_LUMINARY)
                .input('R', AllItems.RANDOM_MATERIAL.get(4))
                .input('M', ComplementItems.SUN_MEMBRANE)
                .input('S', AllItems.SPAWNER_SOUL)
                .criterion(
                        FabricRecipeProvider.hasItem(AllItems.CELESTIAL_LUMINARY),
                        FabricRecipeProvider.conditionsFromItem(AllItems.CELESTIAL_LUMINARY)
                )
                .offerTo(exporter);
        // Illusion Upgrade
        SmithingTransformRecipeJsonBuilder
                .create(
                        Ingredient.EMPTY,
                        Ingredient.ofItems(AllItems.ILLUSION_CONTAINER),
                        Ingredient.ofItems(ModItems.ADVANCED_PICKUP_UPGRADE),
                        RecipeCategory.MISC,
                        AllItems.ILLUSION_UPGRADE
                )
                .criterion(
                        FabricRecipeProvider.hasItem(AllItems.ILLUSION_CONTAINER),
                        FabricRecipeProvider.conditionsFromItem(AllItems.ILLUSION_CONTAINER)
                )
                .offerTo(exporter, SpellDimension.modLoc("illusion_upgrade"));
        // Protective Spell Container
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.SPELL_CONTAINER)
                .pattern("DMD")
                .pattern("MEM")
                .pattern("DMD")
                .input('E', AllTags.ESSENCE.get(2))
                .input('M', MiscItems.MIRACLE_POWDER)
                .input('D', DDItems.RESONARIUM_PLATE)
                .criterion(
                        FabricRecipeProvider.hasItem(MiscItems.MIRACLE_POWDER),
                        FabricRecipeProvider.conditionsFromItem(MiscItems.MIRACLE_POWDER)
                )
                .offerTo(exporter);

        // Spell Prism
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllItems.SPELL_PRISM)
                .pattern("EME")
                .pattern("MGM")
                .pattern("EME")
                .input('E', AllTags.ESSENCE.get(0))
                .input('M', Ingredient.fromTag(TagUtil.itemTag("common/material")))
                .input('G', Items.GLASS_PANE)
                .criterion(
                        FabricRecipeProvider.hasItem(Items.GLASS_PANE),
                        FabricRecipeProvider.conditionsFromItem(Items.GLASS_PANE)
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

    private static void addWeaponRecipes(Consumer<RecipeJsonProvider> exporter)
    {
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllWeapons.LIGHTNING_WAND)
                .pattern(" A")
                .pattern("G ")
                .input('A', MythicItems.Mats.MORKITE)
                .input('G', Items.COPPER_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT), FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .criterion(FabricRecipeProvider.hasItem(AllWeapons.LIGHTNING_WAND), FabricRecipeProvider.conditionsFromItem(AllWeapons.LIGHTNING_WAND))
                .offerTo(exporter);
        RecipeProvider.offerNetheriteUpgradeRecipe(
                exporter,
                AllWeapons.LIGHTNING_WAND,
                RecipeCategory.MISC,
                AllWeapons.NETHERITE_LIGHTNING_WAND
        );
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, AllWeapons.LIGHTNING_STAFF)
                .pattern(" RP")
                .pattern(" SL")
                .pattern("G  ")
                .input('P', MythicItems.Mats.STARRITE)
                .input('R', Items.REDSTONE_BLOCK)
                .input('L', Items.LAPIS_BLOCK)
                .input('S', Items.STICK)
                .input('G', Items.COPPER_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT), FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .criterion(FabricRecipeProvider.hasItem(AllWeapons.LIGHTNING_STAFF), FabricRecipeProvider.conditionsFromItem(AllWeapons.LIGHTNING_STAFF))
                .offerTo(exporter);
        RecipeProvider.offerNetheriteUpgradeRecipe(
                exporter,
                AllWeapons.LIGHTNING_STAFF,
                RecipeCategory.MISC,
                AllWeapons.NETHERITE_LIGHTNING_STAFF
        );
    }

    private static void addArmorRecipes(Consumer<RecipeJsonProvider> exporter, ArmorSet baseSet, ArmorSet netheriteSet, Item ingredient)
    {
        Ingredient wool = Ingredient.fromTag(ItemTags.WOOL);
        ArmorItem baseHelmet = baseSet.helmet();
        ArmorItem baseChestplate = baseSet.chestplate();
        ArmorItem baseLeggings = baseSet.leggings();
        ArmorItem baseBoots = baseSet.boots();
        ArmorItem netheriteHelmet = netheriteSet.helmet();
        ArmorItem netheriteChestplate = netheriteSet.chestplate();
        ArmorItem netheriteLeggings = netheriteSet.leggings();
        ArmorItem netheriteBoots = netheriteSet.boots();
        // base set
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, baseHelmet)
                .pattern("  W")
                .pattern(" W ")
                .pattern("WLW")
                .input('W', wool)
                .input('L', ingredient)
                .criterion(FabricRecipeProvider.hasItem(ingredient), FabricRecipeProvider.conditionsFromItem(ingredient))
                .criterion(FabricRecipeProvider.hasItem(baseHelmet), FabricRecipeProvider.conditionsFromItem(baseHelmet))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, baseChestplate)
                .pattern("L L")
                .pattern("WLW")
                .pattern("WWW")
                .input('W', wool)
                .input('L', ingredient)
                .criterion(FabricRecipeProvider.hasItem(ingredient), FabricRecipeProvider.conditionsFromItem(ingredient))
                .criterion(FabricRecipeProvider.hasItem(baseChestplate), FabricRecipeProvider.conditionsFromItem(baseChestplate))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, baseLeggings)
                .pattern("LLL")
                .pattern("W W")
                .pattern("W W")
                .input('W', wool)
                .input('L', ingredient)
                .criterion(FabricRecipeProvider.hasItem(ingredient), FabricRecipeProvider.conditionsFromItem(ingredient))
                .criterion(FabricRecipeProvider.hasItem(baseLeggings), FabricRecipeProvider.conditionsFromItem(baseLeggings))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder
                .create(RecipeCategory.MISC, baseBoots)
                .pattern("L L")
                .pattern("W W")
                .input('W', wool)
                .input('L', ingredient)
                .criterion(FabricRecipeProvider.hasItem(ingredient), FabricRecipeProvider.conditionsFromItem(ingredient))
                .criterion(FabricRecipeProvider.hasItem(baseBoots), FabricRecipeProvider.conditionsFromItem(baseBoots))
                .offerTo(exporter);
        // netherite set
        RecipeProvider.offerNetheriteUpgradeRecipe(
                exporter,
                baseHelmet,
                RecipeCategory.MISC,
                netheriteHelmet
        );
        RecipeProvider.offerNetheriteUpgradeRecipe(
                exporter,
                baseChestplate,
                RecipeCategory.MISC,
                netheriteChestplate
        );
        RecipeProvider.offerNetheriteUpgradeRecipe(
                exporter,
                baseLeggings,
                RecipeCategory.MISC,
                netheriteLeggings
        );
        RecipeProvider.offerNetheriteUpgradeRecipe(
                exporter,
                baseBoots,
                RecipeCategory.MISC,
                netheriteBoots
        );
    }
}