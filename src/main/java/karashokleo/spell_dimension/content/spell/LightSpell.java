package karashokleo.spell_dimension.content.spell;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.init.AllBlocks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.spell_engine.entity.SpellProjectile;

import java.util.Collection;
import java.util.HashSet;

public class LightSpell
{
    public static final Identifier SPELL_ID = SpellDimension.modLoc("light");

    private static final Multimap<ServerWorld, LightSpawner> SPAWNERS = HashMultimap.create();

    public static void init()
    {
        ServerTickEvents.END_WORLD_TICK.register(world ->
        {
            Collection<LightSpawner> spawners = SPAWNERS.get(world);
            spawners.forEach(spawner -> spawner.spawn(world));
            spawners.removeIf(LightSpawner::isDone);
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> SPAWNERS.clear());
    }

    public static class LightSpawner
    {
        private final HashSet<BlockPos> affectedPoses = new HashSet<>();
        private final int lightThreshold;
        private final int tickInterval;
        private int ticks = 0;

        public LightSpawner(BlockPos pos, int radius, int lightThreshold, int tickInterval)
        {
            this.lightThreshold = lightThreshold;
            this.tickInterval = tickInterval;
            for (int i = 0; i < radius; i++)
            {
                for (int j = 0; j < radius; j++)
                {
                    for (int k = 0; k < radius; k++)
                    {
                        affectedPoses.add(pos.add(i, j, k));
                        affectedPoses.add(pos.add(-i, j, k));
                        affectedPoses.add(pos.add(i, -j, k));
                        affectedPoses.add(pos.add(i, j, -k));
                        affectedPoses.add(pos.add(-i, -j, k));
                        affectedPoses.add(pos.add(i, -j, -k));
                        affectedPoses.add(pos.add(-i, j, -k));
                        affectedPoses.add(pos.add(-i, -j, -k));
                    }
                }
            }
        }

        public void spawn(ServerWorld world)
        {
            ticks++;
            if (ticks % tickInterval != 0) return;
            affectedPoses.stream().findAny().ifPresent(pos ->
            {
                affectedPoses.remove(pos);
                if (world.getBlockState(pos).isAir() &&
                    world.getLightLevel(LightType.BLOCK, pos) <= lightThreshold)
                    world.setBlockState(pos, AllBlocks.SPELL_LIGHT.getDefaultState());
            });
        }

        public boolean isDone()
        {
            return affectedPoses.isEmpty();
        }
    }

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(SPELL_ID)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        SPAWNERS.put(world, new LightSpawner(hitResult.getBlockPos(), 9, 9, 3));
    }
}
