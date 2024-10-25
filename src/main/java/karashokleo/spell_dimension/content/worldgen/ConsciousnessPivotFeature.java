package karashokleo.spell_dimension.content.worldgen;

import com.google.common.collect.Sets;
import karashokleo.spell_dimension.content.block.tile.ConsciousnessCoreTile;
import karashokleo.spell_dimension.content.event.conscious.EventAward;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Set;

public class ConsciousnessPivotFeature extends Feature<DefaultFeatureConfig>
{
    public ConsciousnessPivotFeature()
    {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context)
    {
        double levelFactor = context.getRandom().nextDouble();
        int maxHeight = (int) (levelFactor / 0.1);
        StructureWorldAccess worldAccess = context.getWorld();
        BlockPos origin = context.getOrigin().down();
        Set<BlockPos> set = Sets.newHashSet();
        for (int height = 0; height < maxHeight; height++)
        {
            int radius = maxHeight - height;
            for (int x = -radius; x <= radius; x++)
                for (int z = -radius; z <= radius; z++)
                    if (x * x + z * z <= radius * radius)
                    {
                        BlockPos pos = origin.add(x, height, z);
                        if (worldAccess.getBlockState(pos).isReplaceable())
                            set.add(pos);
                        else return false;
                    }
        }
        BlockPos corePos = origin.add(0, maxHeight, 0);
        worldAccess.setBlockState(
                corePos,
                AllBlocks.CONSCIOUSNESS_CORE.block().getDefaultState(),
                3
        );
        if (worldAccess.getBlockEntity(corePos) instanceof ConsciousnessCoreTile tile)
        {
            tile.init(levelFactor, RandomUtil.randomEnum(context.getRandom(), EventAward.class));
        }
        for (BlockPos pos : set)
            worldAccess.setBlockState(
                    pos,
                    AllBlocks.CONSCIOUSNESS_BASE.block().getDefaultState(),
                    3
            );
        return true;
    }
}
