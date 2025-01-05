package karashokleo.spell_dimension.content.block.tile;

import com.google.common.collect.Lists;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.spell_dimension.content.block.ConsciousnessBaseBlock;
import karashokleo.spell_dimension.content.block.ProtectiveCoverBlock;
import karashokleo.spell_dimension.content.entity.ConsciousnessEventLogic;
import karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager;
import karashokleo.spell_dimension.content.event.conscious.EventAward;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.util.FutureTask;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.TeleportUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Stainable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
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
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ConsciousnessCoreTile extends BlockEntity
{
    public static final String STATE_KEY = "CoreState";
    public static final String LEVEL_FACTOR_KEY = "LevelFactor";
    public static final String AWARD_KEY = "Award";
    public static final String LEVEL_KEY = "Level";
    public static final String DESTINATION_WORLD_KEY = "DestinationWorld";
    public static final String DESTINATION_POS_KEY = "DestinationPos";
    public static final String EVENT_KEY = "Event";

    public int tick = 0;
    private CoreState state = CoreState.INACTIVE;

    public enum CoreState
    {
        INACTIVE,
        TRIGGERING,
        TRIGGERED,
        ACTIVATED
    }

    private double levelFactor;
    @Nullable
    private EventAward award;
    @Nullable
    private Integer level = null;
    @Nullable
    private RegistryKey<World> destinationWorld;
    @Nullable
    private BlockPos destinationPos;
    @Nullable
    private ConsciousnessEventLogic event;

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
    public EventAward getAward()
    {
        return award;
    }

    @Nullable
    public Integer getLevel()
    {
        return level;
    }

    public void init(double levelFactor, EventAward award)
    {
        this.levelFactor = levelFactor;
        this.award = award;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ConsciousnessCoreTile tile)
    {
        tile.tick++;
        boolean dirty = false;
        if ((tile.levelFactor == 0 || tile.award == null) &&
            !world.isClient())
        {
            Random random = world.getRandom();
            tile.init(
                    random.nextDouble(),
                    RandomUtil.randomEnum(random, EventAward.class)
            );
            dirty = true;
        }
        if (tile.event != null && world instanceof ServerWorld serverWorld)
        {
            tile.event.tick(serverWorld);
            if (tile.event.isFinishedLast())
            {
                ConsciousnessEventManager.breakBarrier(world, pos, ConsciousnessEventManager.RADIUS);
                tile.event.discard();
                tile.event = null;
            }
            dirty = true;
        }
        switch (tile.state)
        {
            case TRIGGERING -> tile.tickTriggering(world);
            case TRIGGERED -> tile.tickTriggered(world);
            case ACTIVATED -> tile.tickActivated(world);
        }
        if (dirty)
            tile.markDirty();
    }

    private void tickTriggering(World world)
    {
        if (event == null || !event.isFinished()) return;

        this.state = CoreState.TRIGGERED;
        this.markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    private void tickTriggered(World world)
    {
        if (world instanceof ServerWorld serverWorld)
            tryActivate(serverWorld);
    }

    private void tickActivated(World world)
    {
        if (world.isClient())
        {
            if (world.getTime() % 80L == 0L)
            {
                world.playSound(null, pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1f, 1f);
            }

            int posX = pos.getX();
            int posY = pos.getY();
            int posZ = pos.getZ();
            BlockPos currentPos;

            if (minY < posY)
            {
                currentPos = pos;
                beamSegmentsTemp = Lists.newArrayList();
                minY = currentPos.getY() - 1;
            } else currentPos = new BlockPos(posX, minY + 1, posZ);

            BeamSegment beamSegment = beamSegmentsTemp.isEmpty() ? null : beamSegmentsTemp.get(beamSegmentsTemp.size() - 1);
            int topY = world.getTopY(Heightmap.Type.WORLD_SURFACE, posX, posZ);

            int i = 0;
            for (; i < 10 && currentPos.getY() <= topY; i++)
            {
                BlockState blockState = world.getBlockState(currentPos);
                Block block = blockState.getBlock();

                float[] colors = {1.0F, 1.0F, 1.0F};

                if (block instanceof Stainable stainable)
                    colors = stainable.getColor().getColorComponents();

                if (beamSegmentsTemp.size() <= 1)
                {
                    beamSegment = new BeamSegment(colors);
                    beamSegmentsTemp.add(beamSegment);
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
                        beamSegmentsTemp.add(beamSegment);
                    }
                }

                currentPos = currentPos.up();
                ++minY;
            }

            if (minY >= topY)
            {
                minY = world.getBottomY() - 1;
                beamSegments = beamSegmentsTemp;
            }
        } else if (world instanceof ServerWorld serverWorld)
            tryTeleport(serverWorld);
    }

    private void tryTeleport(ServerWorld world)
    {
        ServerWorld destinationWorld = world.getServer().getWorld(this.destinationWorld);
        if (destinationWorld == null || this.destinationPos == null) return;

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
                    TeleportUtil.teleportPlayerChangeDimension(player, destinationWorld, destinationPos);
                    this.playerTicks.remove(playerUuid);
                }
            } else this.playerTicks.remove(playerUuid);
        }
    }

    public List<BeamSegment> getBeamSegments()
    {
        return beamSegments;
    }

    public CoreState getState()
    {
        return state;
    }

    private boolean testStack(ItemStack stack)
    {
        return true;
//        int tier = MathHelper.clamp((int) (this.levelFactor / 0.1), 0, 4);
//        for (int i = tier; i < 5; i++)
//            if (stack.isIn(AllTags.MATERIAL.get(i)))
//                return true;
//        return false;
    }

    public void setDestinationPos(@NotNull BlockPos destinationPos)
    {
        this.destinationPos = pos;

        if (this.world instanceof ServerWorld serverWorld)
        {
            this.markDirty();
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
            serverWorld.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.0F);

            BlockPos logPos = pos.down();
            BlockState logState = serverWorld.getBlockState(logPos);
            if (logState.getBlock() instanceof ConsciousnessBaseBlock logBlock)
                logBlock.tryTurnSelf(logState, serverWorld, logPos);
        }
    }

    public void selfDestruct()
    {
        if (world == null) return;
        world.breakBlock(this.pos, false);
        world.createExplosion(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 1.0F, false, World.ExplosionSourceType.BLOCK);
    }

    private void tryTrigger(ServerWorld serverWorld, ServerPlayerEntity player)
    {
        this.state = CoreState.TRIGGERING;
        this.level = ConsciousnessEventManager.randomEventLevel(serverWorld.getRandom(), PlayerDifficulty.get(player).getLevel().getLevel(), this.levelFactor);

        ServerWorld destinationWorld = serverWorld.getServer().getOverworld();
        this.destinationWorld = destinationWorld.getRegistryKey();
        FutureTask.submit(
                TeleportUtil.getTeleportPosFuture(serverWorld, destinationWorld, this.pos),
                optional -> optional.ifPresentOrElse(
                        this::setDestinationPos,
                        this::selfDestruct
                )
        );

        this.markDirty();
        serverWorld.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);

        this.event = new ConsciousnessEventLogic(serverWorld.getRandom(), pos, level);
        ProtectiveCoverBlock.placeAsBarrier(world, pos, ConsciousnessEventManager.RADIUS, ConsciousnessEventManager.TIME_LIMIT);
    }

    private void tryActivate(ServerWorld world)
    {
        if (this.event == null) return;
        if (this.event.isSuccess())
            reward(world);

        this.state = CoreState.ACTIVATED;
        this.markDirty();
        world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    private void reward(ServerWorld world)
    {
        if (this.award == null) return;
        LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(world)
                .add(LootContextParameters.ORIGIN, pos.toCenterPos())
                .build(LootContextTypes.CHEST);
        LootTable lootTable = world.getServer().getLootManager().getLootTable(award.lootTable);
        var stacks = lootTable.generateLoot(lootContextParameterSet);
        stacks.forEach(stack -> Block.dropStack(world, pos, stack));
    }

    public void onUse(PlayerEntity player, Hand hand)
    {
        if (!testStack(player.getStackInHand(hand))) return;
        if (this.world instanceof ServerWorld serverWorld &&
            player instanceof ServerPlayerEntity serverPlayer &&
            state == CoreState.INACTIVE)
            tryTrigger(serverWorld, serverPlayer);
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
        nbt.putString(STATE_KEY, this.state.name());
        nbt.putDouble(LEVEL_FACTOR_KEY, this.levelFactor);
        if (this.award != null)
            nbt.putString(AWARD_KEY, this.award.name());
        if (this.level != null)
            nbt.putInt(LEVEL_KEY, this.level);
        if (this.destinationWorld != null)
            nbt.putString(DESTINATION_WORLD_KEY, this.destinationWorld.getValue().toString());
        if (this.destinationPos != null)
            nbt.putLong(DESTINATION_POS_KEY, this.destinationPos.asLong());
        if (this.event != null)
            nbt.put(EVENT_KEY, this.event.toNbt());
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        this.state = CoreState.valueOf(nbt.getString(STATE_KEY));
        this.levelFactor = nbt.getDouble(LEVEL_FACTOR_KEY);
        if (nbt.contains(AWARD_KEY))
            this.award = EventAward.valueOf(nbt.getString(AWARD_KEY));
        if (nbt.contains(LEVEL_KEY))
            this.level = nbt.getInt(LEVEL_KEY);
        if (nbt.contains(DESTINATION_WORLD_KEY))
            this.destinationWorld = RegistryKey.of(RegistryKeys.WORLD, new Identifier(nbt.getString(DESTINATION_WORLD_KEY)));
        if (nbt.contains(DESTINATION_POS_KEY))
            this.destinationPos = BlockPos.fromLong(nbt.getLong(DESTINATION_POS_KEY));
        if (nbt.contains(EVENT_KEY))
            this.event = ConsciousnessEventLogic.fromNbt(pos, nbt.getCompound(EVENT_KEY));
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
