package karashokleo.spell_dimension.content.block.tile;

import com.google.common.collect.Lists;
import io.wispforest.owo.ops.WorldOps;
import karashokleo.spell_dimension.content.item.logic.ConsciousnessEventManager;
import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Stainable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ConsciousnessCoreTile extends BlockEntity
{
    public static final String ACTIVATED_KEY = "Activated";
    public static final String DESTINATION_WORLD_KEY = "DestinationWorld";
    public static final String DESTINATION_POS_KEY = "DestinationPos";
    private int age = 0;
    private boolean activated = false;
    private RegistryKey<World> destinationWorld = World.OVERWORLD;
    private BlockPos destinationPos = BlockPos.ORIGIN;

    private final HashMap<UUID, Integer> playerTicks = new HashMap<>();

    private int minY;
    private List<BeamSegment> beamSegments = Lists.newArrayList();
    private List<BeamSegment> beamSegmentsTemp = Lists.newArrayList();

    public ConsciousnessCoreTile(BlockPos pos, BlockState state)
    {
        super(AllBlocks.CONSCIOUSNESS_CORE_TILE, pos, state);
    }

    @Override
    public void setWorld(World world)
    {
        super.setWorld(world);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ConsciousnessCoreTile tile)
    {
        tile.age++;
        if (!tile.isActivated()) return;

        if (world.getTime() % 80L == 0L)
        {
            world.playSound(null, pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1f, 1f);
        }

        int posX = pos.getX();
        int posY = pos.getY();
        int posZ = pos.getZ();
        BlockPos currentPos;

        if (tile.minY < posY)
        {
            currentPos = pos;
            tile.beamSegmentsTemp = Lists.newArrayList();
            tile.minY = currentPos.getY() - 1;
        } else currentPos = new BlockPos(posX, tile.minY + 1, posZ);

        BeamSegment beamSegment = tile.beamSegmentsTemp.isEmpty() ? null : tile.beamSegmentsTemp.get(tile.beamSegmentsTemp.size() - 1);
        int topY = world.getTopY(Heightmap.Type.WORLD_SURFACE, posX, posZ);

        int i = 0;
        for (; i < 10 && currentPos.getY() <= topY; i++)
        {
            BlockState blockState = world.getBlockState(currentPos);
            Block block = blockState.getBlock();

            float[] colors = {1.0F, 1.0F, 1.0F};

            if (block instanceof Stainable stainable)
                colors = stainable.getColor().getColorComponents();

            if (tile.beamSegmentsTemp.size() <= 1)
            {
                beamSegment = new BeamSegment(colors);
                tile.beamSegmentsTemp.add(beamSegment);
            } else if (beamSegment != null)
            {
                if (Arrays.equals(colors, beamSegment.color))
                    beamSegment.increaseHeight();
                else
                {
                    beamSegment = new BeamSegment(new float[]{
                            (beamSegment.color[0] + colors[0]) / 2.0F,
                            (beamSegment.color[1] + colors[1]) / 2.0F,
                            (beamSegment.color[2] + colors[2]) / 2.0F
                    });
                    tile.beamSegmentsTemp.add(beamSegment);
                }
            }

            currentPos = currentPos.up();
            ++tile.minY;
        }

        if (tile.minY >= topY)
        {
            tile.minY = world.getBottomY() - 1;
            tile.beamSegments = tile.beamSegmentsTemp;
        }

        if (tile.isActivated() && world instanceof ServerWorld serverWorld)
            tile.tryTeleport(serverWorld);
    }

    private void tryTeleport(ServerWorld world)
    {
        List<ServerPlayerEntity> players = List.copyOf(world.getPlayers());
        Box box = new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, world.getTopY(), pos.getZ() + 1);
        for (ServerPlayerEntity player : players)
        {
            UUID playerUuid = player.getUuid();
            if (box.contains(player.getX(), player.getY(), player.getZ()))
            {
//                if (!player.hasStatusEffect(StatusEffects.LEVITATION) ||
//                    (player.hasStatusEffect(StatusEffects.LEVITATION) &&
//                     player.getStatusEffect(StatusEffects.LEVITATION).getDuration() <= 2))
//                {
//                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 2, 5, false, false));
//                }

                player.addVelocity(0, 0.09, 0);
                player.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player));

                int currentTick = playerTicks.compute(playerUuid, (uuid, ticks) -> ticks == null ? 1 : ticks + 1);
                if (currentTick >= 100)
                {
                    ServerWorld destinationWorld = world.getServer().getWorld(this.destinationWorld);
                    WorldOps.teleportToWorld(player, destinationWorld, destinationPos.toCenterPos());
                    playerTicks.remove(playerUuid);
                }
            } else
            {
                playerTicks.remove(playerUuid);
            }
        }
    }

    public List<BeamSegment> getBeamSegments()
    {
        return beamSegments;
    }

    public boolean isActivated()
    {
        return this.activated;
    }

    public void activate(BlockPos pos)
    {
        if (world instanceof ServerWorld serverWorld)
        {
            ServerWorld overworld = serverWorld.getServer().getOverworld();

            this.destinationWorld = RegistryKey.of(RegistryKeys.WORLD, overworld.getRegistryKey().getValue());
            this.destinationPos = ConsciousnessEventManager.findTeleportTarget(serverWorld, overworld, pos);

            this.activated = true;
            this.markDirty();
            world.playSound(null, pos, activated ? SoundEvents.BLOCK_BEACON_ACTIVATE : SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket()
    {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt()
    {
        return this.createNbt();
    }

    @Override
    protected void writeNbt(NbtCompound nbt)
    {
        super.writeNbt(nbt);
        nbt.putBoolean(ACTIVATED_KEY, this.activated);
        nbt.putString(DESTINATION_WORLD_KEY, destinationWorld.getValue().toString());
        nbt.putLong(DESTINATION_POS_KEY, destinationPos.asLong());
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        this.activated = nbt.getBoolean(ACTIVATED_KEY);
        this.destinationWorld = RegistryKey.of(RegistryKeys.WORLD, new Identifier(nbt.getString(DESTINATION_WORLD_KEY)));
        this.destinationPos = BlockPos.fromLong(nbt.getLong(DESTINATION_POS_KEY));
    }

    /**
     * Vanilla Copy
     */
    public static class BeamSegment
    {
        final float[] color;
        private int height;

        public BeamSegment(float[] color)
        {
            this.color = color;
            this.height = 1;
        }

        protected void increaseHeight()
        {
            ++this.height;
        }

        public float[] getColor()
        {
            return this.color;
        }

        public int getHeight()
        {
            return this.height;
        }
    }
}
