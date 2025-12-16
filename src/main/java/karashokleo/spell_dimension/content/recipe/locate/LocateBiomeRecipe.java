package karashokleo.spell_dimension.content.recipe.locate;

import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

public class LocateBiomeRecipe extends LocateRecipe
{
    protected final Identifier biomeTag;

    public LocateBiomeRecipe(Identifier id, Identifier world, Ingredient ingredient, Identifier biomeTag)
    {
        super(id, world, ingredient);
        this.biomeTag = biomeTag;
    }

    @Override
    public LocationType getLocationType()
    {
        return LocationType.BIOME;
    }

    @Override
    public Identifier getTargetId()
    {
        return biomeTag;
    }

    @Override
    public Optional<BlockPos> locate(ServerWorld world, BlockPos pos, PlayerEntity player)
    {
        MutableText targetName = this.getTargetName();
        player.sendMessage(SDTexts.TEXT$LOCATING.get(targetName), false);

        Pair<BlockPos, RegistryEntry<Biome>> pair = world.locateBiome(
            e -> e.isIn(TagKey.of(RegistryKeys.BIOME, biomeTag)),
            pos,
            6400,
            32,
            64
        );

        if (pair == null)
        {
            player.sendMessage(Text.translatable("commands.locate.biome.not_found", targetName), false);
            return Optional.empty();
        }

        return Optional.of(pair.getFirst());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return LocateBiomeRecipeSerializer.INSTANCE;
    }
}
