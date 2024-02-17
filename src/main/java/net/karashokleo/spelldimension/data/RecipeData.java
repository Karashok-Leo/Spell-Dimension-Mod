package net.karashokleo.spelldimension.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.item.SpellBooksEntry;
import net.karashokleo.spelldimension.item.mod_item.MageSpellBookItem;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.karashokleo.spelldimension.recipe.EnchantedEssenceRecipeJsonProvider;
import net.karashokleo.spelldimension.recipe.MedalUpgradeRecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RecipeData extends FabricRecipeProvider
{
    private static final Map<MageMajor, Item> map = new HashMap<>();

    static
    {
        map.put(MageMajor.CONVERGE, Items.ECHO_SHARD);
        map.put(MageMajor.PHASE, Items.ENDER_PEARL);
        map.put(MageMajor.FLOURISH, Items.DIAMOND);
        map.put(MageMajor.BREATH, Items.BLAZE_POWDER);
        map.put(MageMajor.BLAST, Items.FIRE_CHARGE);
        map.put(MageMajor.IGNITE, Items.GUNPOWDER);
        map.put(MageMajor.ICICLE, Items.ICE);
        map.put(MageMajor.NUCLEUS, Items.BLUE_ICE);
        map.put(MageMajor.AURA, Items.SNOW);
        map.put(MageMajor.POWER, Items.FLINT);
        map.put(MageMajor.REGEN, Items.GHAST_TEAR);
        map.put(MageMajor.RESIST, Items.SCUTE);
    }

    public RecipeData(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter)
    {
        addSpellBookRecipe(exporter);
        addMageMedalRecipe(exporter);
        addBaseEssenceRecipe(exporter);
        addEnchantedEssenceRecipe(exporter);
    }

    private static void addSpellBookRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        for (SpellBooksEntry entry : AllItems.SPELL_BOOKS.values())
            for (Map.Entry<MageMajor, List<MageSpellBookItem>> listEntry : entry.majors.entrySet())
            {
                MageMajor major = listEntry.getKey();
                assert major.school != null;
                Identifier id = SpellDimension.modLoc(major.school.spellName() + "/" + major.majorName() + "/spell_book/");
                Item ingredient = map.get(major);
                List<MageSpellBookItem> items = listEntry.getValue();
                ShapedRecipeJsonBuilder
                        .create(RecipeCategory.MISC, items.get(0))
                        .pattern("###")
                        .pattern("#*#")
                        .pattern("###")
                        .input('#', ingredient)
                        .input('*', entry.primary)
                        .criterion(FabricRecipeProvider.hasItem(entry.primary), FabricRecipeProvider.conditionsFromItem(entry.primary))
                        .criterion(FabricRecipeProvider.hasItem(items.get(0)), FabricRecipeProvider.conditionsFromItem(items.get(0)))
                        .offerTo(exporter, id.withSuffixedPath("1"));
                ShapedRecipeJsonBuilder
                        .create(RecipeCategory.MISC, items.get(1))
                        .pattern("###")
                        .pattern("#*#")
                        .pattern("###")
                        .input('#', ingredient)
                        .input('*', items.get(0))
                        .criterion(FabricRecipeProvider.hasItem(items.get(0)), FabricRecipeProvider.conditionsFromItem(items.get(0)))
                        .criterion(FabricRecipeProvider.hasItem(items.get(1)), FabricRecipeProvider.conditionsFromItem(items.get(1)))
                        .offerTo(exporter, id.withSuffixedPath("2"));
                ShapedRecipeJsonBuilder
                        .create(RecipeCategory.MISC, items.get(2))
                        .pattern("###")
                        .pattern("#*#")
                        .pattern("###")
                        .input('#', ingredient)
                        .input('*', items.get(1))
                        .criterion(FabricRecipeProvider.hasItem(items.get(1)), FabricRecipeProvider.conditionsFromItem(items.get(1)))
                        .criterion(FabricRecipeProvider.hasItem(items.get(2)), FabricRecipeProvider.conditionsFromItem(items.get(2)))
                        .offerTo(exporter, id.withSuffixedPath("3"));
            }
    }

    private static void addMageMedalRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        for (MageMajor major : MageMajor.values())
        {
            Identifier id = SpellDimension.modLoc(major.school.spellName() + "/" + major.majorName() + "/medal/");
            exporter.accept(new MedalUpgradeRecipeJsonProvider(id.withSuffixedPath("1"), Ingredient.ofItems(map.get(major)), new Mage(0, null, null), new Mage(1, major.school, major)));
            exporter.accept(new MedalUpgradeRecipeJsonProvider(id.withSuffixedPath("2"), Ingredient.ofItems(AllItems.BASE_ESSENCES.get(major.school).get(1)), new Mage(1, major.school, major), new Mage(2, major.school, major)));
            exporter.accept(new MedalUpgradeRecipeJsonProvider(id.withSuffixedPath("3"), Ingredient.ofItems(AllItems.BASE_ESSENCES.get(major.school).get(2)), new Mage(2, major.school, major), new Mage(3, major.school, major)));
        }
    }

    private static void addBaseEssenceRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        for (MagicSchool school : MagicSchool.values())
        {
            if (!school.isMagical) continue;

            for (int i = 0; i < 2; i++)
            {
                Item sub = AllItems.BASE_ESSENCES.get(school).get(i);
                Item sur = AllItems.BASE_ESSENCES.get(school).get(i + 1);

                ShapelessRecipeJsonBuilder
                        .create(RecipeCategory.MISC, sub, 9)
                        .input(sur)
                        .criterion(FabricRecipeProvider.hasItem(sub), FabricRecipeProvider.conditionsFromItem(sub))
                        .criterion(FabricRecipeProvider.hasItem(sur), FabricRecipeProvider.conditionsFromItem(sur))
                        .offerTo(exporter, SpellDimension.modLoc(school.spellName() + "/compose_" + i));

                ShapelessRecipeJsonBuilder
                        .create(RecipeCategory.MISC, sur)
                        .input(sub, 9)
                        .criterion(FabricRecipeProvider.hasItem(sub), FabricRecipeProvider.conditionsFromItem(sub))
                        .criterion(FabricRecipeProvider.hasItem(sur), FabricRecipeProvider.conditionsFromItem(sur))
                        .offerTo(exporter, SpellDimension.modLoc(school.spellName() + "/decompose_" + i));
            }
        }
    }

    private static void addEnchantedEssenceRecipe(Consumer<RecipeJsonProvider> exporter)
    {
        for (MagicSchool school : MagicSchool.values())
        {
            if (!school.isMagical) continue;

            for (int i = 0; i < 3; i++)
                for (EquipmentSlot slot : EquipmentSlot.values())
                    exporter.accept(new EnchantedEssenceRecipeJsonProvider(SpellDimension.modLoc(school.spellName() + "/" + slot.getName() + "/" + i), i, school, slot, (i + 1) * 10, school.attributeId()));
        }
    }
}