package karashokleo.spell_dimension.content.recipe.locate;

import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.Structure;

import java.util.Optional;

public class LocateStructureRecipe extends LocateRecipe
{
    protected final Identifier structure;

    public LocateStructureRecipe(Identifier id, Identifier world, Ingredient ingredient, Identifier structure)
    {
        super(id, world, ingredient);
        this.structure = structure;
    }

    @Override
    public LocationType getLocationType()
    {
        return LocationType.STRUCTURE;
    }

    @Override
    public Identifier getTargetId()
    {
        return structure;
    }

    @Override
    public Optional<BlockPos> locate(ServerWorld world, BlockPos pos, PlayerEntity player)
    {
        MutableText targetName = this.getTargetName();
        player.sendMessage(SDTexts.TEXT$LOCATING.get(targetName), false);

        Optional<RegistryEntryList<Structure>> optional = world
            .getRegistryManager()
            .get(RegistryKeys.STRUCTURE)
            .getEntry(RegistryKey.of(RegistryKeys.STRUCTURE, structure))
            .map(RegistryEntryList::of);

        if (optional.isEmpty())
        {
            player.sendMessage(Text.translatable("commands.locate.structure.not_found", targetName), false);
            return Optional.empty();
        }

        Pair<BlockPos, RegistryEntry<Structure>> pair = world
            .getChunkManager()
            .getChunkGenerator()
            .locateStructure(world, optional.get(), pos, 100, false);

        if (pair == null)
        {
            player.sendMessage(Text.translatable("commands.locate.structure.not_found", targetName), false);
            return Optional.empty();
        }

        return Optional.of(pair.getFirst());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return LocateStructureRecipeSerializer.INSTANCE;
    }
}
