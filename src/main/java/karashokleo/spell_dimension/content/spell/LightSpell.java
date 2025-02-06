package karashokleo.spell_dimension.content.spell;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import karashokleo.spell_dimension.content.block.SpellLightBlock;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.init.AllSpells;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.LightType;
import net.spell_engine.entity.SpellProjectile;

import java.util.*;

public class LightSpell
{
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

    public record LightSpawner(
            BlockPos center,
            int lightThreshold,
            int lightSpacing,
            int total,
            int step,
            Queue<BlockPos> border,
            Set<BlockPos> visited,
            Set<BlockPos> spawned
    )
    {
        public LightSpawner(BlockPos center, int lightThreshold, int total, int step)
        {
            this(center, lightThreshold, SpellLightBlock.LUMINANCE - lightThreshold, total, step, new LinkedList<>(), new HashSet<>(), new HashSet<>());
            border.add(center);
            visited.add(center);
        }

        private static final Vec3i[] EXPANDS = {
                new Vec3i(1, 0, 0),
                new Vec3i(-1, 0, 0),
                new Vec3i(0, 1, 0),
                new Vec3i(0, -1, 0),
                new Vec3i(0, 0, 1),
                new Vec3i(0, 0, -1),

                new Vec3i(1, 1, 0),
                new Vec3i(-1, 1, 0),
                new Vec3i(0, 1, 1),
                new Vec3i(0, 1, -1),
                new Vec3i(1, 1, 1),
                new Vec3i(1, 1, -1),
                new Vec3i(-1, 1, 1),
                new Vec3i(-1, 1, -1),

                new Vec3i(1, -1, 0),
                new Vec3i(-1, -1, 0),
                new Vec3i(0, -1, 1),
                new Vec3i(0, -1, -1),
                new Vec3i(1, -1, 1),
                new Vec3i(1, -1, -1),
                new Vec3i(-1, -1, 1),
                new Vec3i(-1, -1, -1),
        };

        public void spawn(ServerWorld world)
        {
            int expand = 0;
            while (expand <= step && !border.isEmpty())
            {
                BlockPos pos = border.poll();
                for (Vec3i direction : EXPANDS)
                {
                    BlockPos neighbor = pos.add(direction);
                    if (tryVisit(world, neighbor))
                        expand++;
                    if (expand > step) break;
                }
            }
        }

        private boolean tryVisit(ServerWorld world, BlockPos pos)
        {
            if (visited.add(pos))
            {
                border.add(pos);

                if (world.getBlockState(pos).isAir() &&
                    world.getLightLevel(LightType.BLOCK, pos) <= lightThreshold)
                {
                    boolean canSpawn = true;
                    for (BlockPos existed : spawned)
                        if (existed.getManhattanDistance(pos) < 7)
                        {
                            canSpawn = false;
                            break;
                        }
                    if (canSpawn)
                    {
                        spawned.add(pos);
                        world.setBlockState(pos, AllBlocks.SPELL_LIGHT.getDefaultState());

                        Vec3d centerPos = center.toCenterPos();
                        Vec3d direction = pos.toCenterPos().subtract(centerPos).normalize();
                        double distance = pos.toCenterPos().distanceTo(centerPos);
                        for (double i = 0; i < distance; i += 0.5)
                        {
                            Vec3d particlePos = centerPos.add(direction.multiply(i));
                            world.spawnParticles(
                                    ParticleTypes.END_ROD,
                                    particlePos.getX(),
                                    particlePos.getY(),
                                    particlePos.getZ(),
                                    1, 0, 0, 0, 0
                            );
                        }
                    }
                }
                return true;
            }
            return false;
        }

        public boolean isDone()
        {
            return visited.size() > total;
        }
    }

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.LIGHT)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        Entity owner = projectile.getOwner();
        if (owner == null) return;
        if (AllSpells.inDungeon(world))
        {
            owner.sendMessage(SDTexts.TEXT$BANNED_SPELL.get().formatted(Formatting.RED));
            return;
        }
        SPAWNERS.put(world, new LightSpawner(hitResult.getBlockPos(), 7, 8000, 20));
    }
}
