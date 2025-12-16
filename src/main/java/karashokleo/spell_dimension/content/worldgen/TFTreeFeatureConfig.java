package karashokleo.spell_dimension.content.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

import java.util.List;

public record TFTreeFeatureConfig(
    int minRadius,
    int maxRadius,
    int minHeight,
    int maxHeight,
    BlockStateProvider trunkProvider,
    BlockStateProvider leavesProvider,
    BlockStateProvider branchProvider,
    BlockStateProvider rootsProvider,
    List<TreeDecorator> decorators
) implements FeatureConfig
{
    public static final Codec<TFTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("min_radius").orElse(2).forGetter(obj -> obj.minRadius),
        Codec.INT.fieldOf("max_radius").orElse(4).forGetter(obj -> obj.maxRadius),
        Codec.INT.fieldOf("min_height").orElse(24).forGetter(obj -> obj.minHeight),
        Codec.INT.fieldOf("max_height").orElse(36).forGetter(obj -> obj.maxHeight),
        BlockStateProvider.TYPE_CODEC.fieldOf("trunk_provider").forGetter(obj -> obj.trunkProvider),
        BlockStateProvider.TYPE_CODEC.fieldOf("leaves_provider").forGetter(obj -> obj.leavesProvider),
        BlockStateProvider.TYPE_CODEC.fieldOf("branch_provider").forGetter(obj -> obj.branchProvider),
        BlockStateProvider.TYPE_CODEC.fieldOf("roots_provider").forGetter(obj -> obj.rootsProvider),
        TreeDecorator.TYPE_CODEC.listOf().fieldOf("decorators").orElseGet(ImmutableList::of).forGetter(obj -> obj.decorators)
    ).apply(instance, TFTreeFeatureConfig::new));
}