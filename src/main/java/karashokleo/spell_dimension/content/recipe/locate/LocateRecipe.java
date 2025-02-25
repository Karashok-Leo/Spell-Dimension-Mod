package karashokleo.spell_dimension.content.recipe.locate;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public abstract class LocateRecipe implements Recipe<PlayerInventory>
{
    public static final String NAME = "locate";
    public static final Identifier ID = SpellDimension.modLoc(NAME);
    public static final RecipeType<LocateRecipe> TYPE = new RecipeType<>()
    {
        @Override
        public String toString()
        {
            return NAME;
        }
    };

    protected final Identifier id;
    protected final Identifier world;
    protected final Ingredient ingredient;

    public LocateRecipe(Identifier id, Identifier world, Ingredient ingredient)
    {
        this.id = id;
        this.world = world;
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(PlayerInventory inventory, World world)
    {
        return world.getRegistryKey().getValue().equals(this.world) &&
               ingredient.test(inventory.offHand.get(0));
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
    public RecipeType<?> getType()
    {
        return TYPE;
    }

    public Ingredient getIngredient()
    {
        return ingredient;
    }

    public MutableText getWorldName()
    {
        return Text.translatable(
                world.toTranslationKey("travelerstitles")
        );
    }

    public abstract MutableText getTargetName();

    public abstract Identifier getTargetId();

    public abstract Optional<BlockPos> locate(ServerWorld world, BlockPos pos, PlayerEntity player);
}
