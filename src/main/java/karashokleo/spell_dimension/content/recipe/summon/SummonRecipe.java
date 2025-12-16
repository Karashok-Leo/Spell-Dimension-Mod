package karashokleo.spell_dimension.content.recipe.summon;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record SummonRecipe(
    Identifier id,
    Ingredient ingredient,
    EntityType<?> entityType,
    int count
) implements Recipe<PlayerInventory>
{
    public static final String NAME = "summon";
    public static final Identifier ID = SpellDimension.modLoc(NAME);
    public static final RecipeType<SummonRecipe> TYPE = new RecipeType<>()
    {
        @Override
        public String toString()
        {
            return NAME;
        }
    };

    @Override
    public boolean matches(PlayerInventory inventory, World world)
    {
        return ingredient.test(inventory.offHand.get(0));
    }

    @Override
    public ItemStack craft(PlayerInventory inventory, DynamicRegistryManager registryManager)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height)
    {
        return false;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId()
    {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SummonRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return TYPE;
    }
}
