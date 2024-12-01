package karashokleo.spell_dimension.content.block;

import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.spell_dimension.content.block.tile.ConsciousnessCoreTile;
import karashokleo.spell_dimension.init.AllBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ConsciousnessCoreBlock extends BlockWithEntity
{
    public ConsciousnessCoreBlock()
    {
        super(
                FabricBlockSettings.create()
                        .mapColor(MapColor.DIAMOND_BLUE)
                        .instrument(Instrument.HAT)
                        .sounds(BlockSoundGroup.GLASS)
                        .strength(-1.0F, 3600000.0F)
                        .luminance(state -> 15)
                        .nonOpaque()
                        .dropsNothing()
                        .solidBlock(Blocks::never)
                        .allowsSpawning(Blocks::never)
        );
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance)
    {
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if (world.getBlockEntity(pos) instanceof ConsciousnessCoreTile tile &&
            tile.testStack(player.getStackInHand(hand)))
            tile.activate(pos, PlayerDifficulty.get(player).getLevel().getLevel());
        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new ConsciousnessCoreTile(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        return checkType(type, AllBlocks.CONSCIOUSNESS_CORE_TILE, ConsciousnessCoreTile::tick);
    }
}
