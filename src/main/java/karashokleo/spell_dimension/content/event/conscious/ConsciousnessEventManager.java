package karashokleo.spell_dimension.content.event.conscious;

import karashokleo.spell_dimension.content.block.ProtectiveCoverBlock;
import karashokleo.spell_dimension.content.entity.ConsciousnessEventEntity;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ConsciousnessEventManager
{
    public static final int TIME_LIMIT = 20 * 60 * 10;
    public static final int RADIUS = 32;
    public static final int SPAWN_LIMIT = 100;

    public static int randomEventLevel(Random random, double playerLevel, double levelFactor)
    {
        return (int) (playerLevel * (1 + levelFactor * (1 + random.nextGaussian() * 0.4)));
    }

    @Nullable
    public static ConsciousnessEventEntity startEvent(ServerWorld world, BlockPos pos, int level, EventAward award)
    {
        ConsciousnessEventEntity event = AllEntities.CONSCIOUSNESS_EVENT.spawn(world, pos, SpawnReason.EVENT);
        if (event == null) return null;
        event.init(level, award);
        ProtectiveCoverBlock.placeAsBarrier(world, pos, RADIUS, TIME_LIMIT);
        return event;
    }

    public static void giveReward(ServerWorld world, BlockPos pos, ConsciousnessEventEntity event)
    {
        world.setBlockState(pos, Blocks.CHEST.getDefaultState(), 2);
        if (world.getBlockEntity(pos) instanceof
                    LootableContainerBlockEntity lootChest &&
            event.getAward() != null)
            lootChest.setLootTable(event.getAward().lootTable, world.getSeed() * pos.getX() * pos.getY() * pos.getZ());

        event.getPlayers(world)
                .forEach(player -> player.getInventory().offerOrDrop(AllItems.BROKEN_MAGIC_MIRROR.getDefaultStack()));
    }

    public static void breakBarrier(World world, BlockPos pos, int radius)
    {
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++)
                    if (x == -radius || x == radius ||
                        y == -radius || y == radius ||
                        z == -radius || z == radius)
                    {
                        BlockPos breakPos = pos.add(x, y, z);
                        BlockState blockState = world.getBlockState(breakPos);
                        if (blockState.isOf(AllBlocks.PROTECTIVE_COVER.block()))
                        {
                            world.breakBlock(breakPos, false);
                            world.removeBlockEntity(breakPos);
                        }
                    }
    }

    public static Optional<BlockPos> tryFindSummonPos(ServerWorld world, BlockPos pos, EntityType<?> entityType)
    {
        Random random = world.getRandom();
        int range = RADIUS / 2;
        int x = random.nextBetweenExclusive(-range, range);
        int z = random.nextBetweenExclusive(-range, range);
        int y = -range;
        while (y < pos.getY() + range)
        {
            BlockPos checkPos = pos.add(x, y, z);
            if (world.getBlockState(checkPos).isAir() &&
                world.isSpaceEmpty(entityType.createSimpleBoundingBox(checkPos.getX(), checkPos.getY(), checkPos.getZ())))
                return Optional.of(checkPos);
            y++;
        }
        return Optional.empty();
    }
}
