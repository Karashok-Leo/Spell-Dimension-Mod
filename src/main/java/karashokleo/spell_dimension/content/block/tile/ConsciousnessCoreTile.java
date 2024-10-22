package karashokleo.spell_dimension.content.block.tile;

import com.google.common.collect.Lists;
import io.wispforest.owo.ops.WorldOps;
import karashokleo.spell_dimension.content.block.ConsciousnessBaseBlock;
import karashokleo.spell_dimension.content.entity.ConsciousnessEventEntity;
import karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager;
import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Stainable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
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
    public static final String TRIGGERED_KEY = "Triggered";
    public static final String LEVEL_FACTOR_KEY = "LevelFactor";
    public static final String LEVEL_KEY = "Level";
    public static final String DESTINATION_WORLD_KEY = "DestinationWorld";
    public static final String EVENT_KEY = "EventUUID";

    private boolean activated = false;
    private boolean triggered = false;
    private double levelFactor;
    @Nullable
    private Integer level = null;
    @Nullable
    private RegistryKey<World> destinationWorld;
    @Nullable
    private ConsciousnessEventEntity event;

    private final HashMap<UUID, Integer> playerTicks = new HashMap<>();

    private int minY;
    private List<BeamSegment> beamSegments = Lists.newArrayList();
    private List<BeamSegment> beamSegmentsTemp = Lists.newArrayList();

    public ConsciousnessCoreTile(BlockPos pos, BlockState state)
    {
        super(AllBlocks.CONSCIOUSNESS_CORE_TILE, pos, state);
    }

    public double getLevelFactor()
    {
        return levelFactor;
    }

    @Nullable
    public Integer getLevel()
    {
        return level;
    }

    public void setLevelFactor(double levelFactor)
    {
        this.levelFactor = levelFactor;
    }

    public void initLevelFactor(World world)
    {
        this.initLevelFactor(world, world.getRandom().nextDouble());
    }

    public void initLevelFactor(World world, double levelFactor)
    {
        this.levelFactor = levelFactor;
        this.markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ConsciousnessCoreTile tile)
    {
        if (tile.levelFactor == 0 && !world.isClient())
            tile.initLevelFactor(world);

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
                player.addVelocity(0, 0.09, 0);
                player.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player));

                int currentTick = this.playerTicks.compute(playerUuid, (uuid, ticks) -> ticks == null ? 1 : ticks + 1);
                if (currentTick >= 100)
                {
                    ServerWorld destinationWorld = world.getServer().getOverworld();
                    this.destinationWorld = destinationWorld.getRegistryKey();
                    BlockPos destinationPos = ConsciousnessEventManager.findTeleportPos(world, destinationWorld, pos);
                    WorldOps.teleportToWorld(player, destinationWorld, destinationPos.toCenterPos());
                    this.playerTicks.remove(playerUuid);
                    if (!this.triggered && this.level != null)
                    {
                        this.triggered = true;
                        this.event = ConsciousnessEventManager.startEvent(destinationWorld, destinationPos, this.level);

                        this.markDirty();
                        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
                    }
                }
            } else this.playerTicks.remove(playerUuid);
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

    public boolean testStack(ItemStack stack)
    {
        return true;
    }

    public void activate(BlockPos pos, double playerLevel)
    {
        if (this.activated) return;
        if (world instanceof ServerWorld serverWorld)
        {
            this.activated = true;
            this.level = ConsciousnessEventManager.randomEventLevel(serverWorld.getRandom(), playerLevel, this.levelFactor);
            this.markDirty();
            serverWorld.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
            serverWorld.playSound(null, pos, activated ? SoundEvents.BLOCK_BEACON_ACTIVATE : SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);

            BlockPos logPos = pos.down();
            BlockState logState = serverWorld.getBlockState(logPos);
            if (logState.getBlock() instanceof ConsciousnessBaseBlock logBlock)
                logBlock.tryTurnSelf(logState, serverWorld, logPos);
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
        nbt.putBoolean(TRIGGERED_KEY, this.triggered);
        nbt.putDouble(LEVEL_FACTOR_KEY, this.levelFactor);
        if (this.level != null)
            nbt.putInt(LEVEL_KEY, this.level);
        if (this.destinationWorld != null)
            nbt.putString(DESTINATION_WORLD_KEY, this.destinationWorld.getValue().toString());
        if (this.event != null)
            nbt.putUuid(EVENT_KEY, this.event.getUuid());
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        this.activated = nbt.getBoolean(ACTIVATED_KEY);
        this.triggered = nbt.getBoolean(TRIGGERED_KEY);
        this.levelFactor = nbt.getDouble(LEVEL_FACTOR_KEY);
        if (nbt.contains(LEVEL_KEY))
            this.level = nbt.getInt(LEVEL_KEY);
        if (nbt.contains(DESTINATION_WORLD_KEY))
            this.destinationWorld = RegistryKey.of(RegistryKeys.WORLD, new Identifier(nbt.getString(DESTINATION_WORLD_KEY)));
        if (nbt.containsUuid(EVENT_KEY) &&
            world instanceof ServerWorld serverWorld &&
            serverWorld.getEntity(nbt.getUuid(EVENT_KEY)) instanceof ConsciousnessEventEntity eventEntity)
            this.event = eventEntity;
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
