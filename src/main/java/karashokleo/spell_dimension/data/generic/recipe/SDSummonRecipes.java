package karashokleo.spell_dimension.data.generic.recipe;

import artifacts.registry.ModEntityTypes;
import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class SDSummonRecipes
{
    public static void add(Consumer<RecipeJsonProvider> exporter, Identifier recipeId, Ingredient ingredient, EntityType<? extends LivingEntity> entityType, int count)
    {
        exporter.accept(
                new SummonRecipeJsonProvider(
                        recipeId,
                        ingredient,
                        entityType,
                        count
                )
        );
    }

    public static void add(Consumer<RecipeJsonProvider> exporter, Ingredient ingredient, EntityType<? extends LivingEntity> entityType, int count)
    {
        Identifier id = Registries.ENTITY_TYPE.getId(entityType);
        Identifier recipeId = SpellDimension.modLoc("summon/%s/%s_%d".formatted(id.getNamespace(), id.getPath(), count));
        add(exporter, recipeId, ingredient, entityType, count);
    }

    public static void add(Consumer<RecipeJsonProvider> exporter, Item item, EntityType<? extends LivingEntity> entityType, int count)
    {
        add(exporter, Ingredient.ofItems(item), entityType, count);
    }

    public static void add(Consumer<RecipeJsonProvider> exporter, Item item, EntityType<? extends LivingEntity> entityType)
    {
        add(exporter, item, entityType, ISpawnerExtension.DEFAULT_REMAIN);
    }

    public static void add(Consumer<RecipeJsonProvider> exporter)
    {
        add(exporter, Items.CRYING_OBSIDIAN, EntityType.ENDERMAN);
        add(exporter, Items.GREEN_DYE, EntityType.SLIME);
        add(exporter, Items.POTION, EntityType.WITCH);
        add(exporter, Items.PIGLIN_HEAD, EntityType.PIGLIN_BRUTE);
        add(exporter, Items.ZOMBIE_HEAD, ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get(), 1);
        add(exporter, Items.SKELETON_SKULL, ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get(), 1);
        add(exporter, Items.CREEPER_HEAD, ModRegistry.MUTANT_CREEPER_ENTITY_TYPE.get(), 1);
        add(exporter, Items.END_CRYSTAL, ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get(), 1);
        add(exporter, MiscItems.CHAOS.ingot(), ModEntityTypes.MIMIC.get(), 1);
        add(exporter, MiscItems.MIRACLE.ingot(), ModEntityTypes.MIMIC.get());
    }
}
