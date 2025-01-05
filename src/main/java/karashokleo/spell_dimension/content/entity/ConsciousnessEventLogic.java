package karashokleo.spell_dimension.content.entity;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager;
import karashokleo.spell_dimension.content.event.conscious.Wave;
import karashokleo.spell_dimension.content.event.conscious.WaveFactory;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.*;
import java.util.function.Predicate;

import static karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager.RADIUS;

public class ConsciousnessEventLogic
{
    public static final int PREPARE_TIME = 20 * 10;
    public static final int WAIT_TIME = 20 * 5;
    public static final int PENDING_TIME = 20 * 10;
    public static final int FINISH_TIME = 20 * 5;

    public static final double FACTOR_PER_WAVE = 0.4;
    public static final String STATE_KEY = "EventState";
    public static final String TICK_KEY = "EventTick";
    public static final String WAIT_TIMER_KEY = "WaitTimer";
    public static final String FINISH_TIMER_KEY = "FinishTimer";
    public static final String PENDING_TIMER_KEY = "PendingTimer";
    public static final String WAVE_INDEX_KEY = "WaveIndex";
    public static final String LEVEL_KEY = "Level";
    public static final String WAVES_KEY = "Waves";
    public static final String SUMMONED_KEY = "Summoned";
    public static final String TOTAL_MAX_HEALTH_KEY = "TotalMaxHealth";
    public static final String SUCCESS_KEY = "Success";

    private final BlockPos pos;
    private EventState state = EventState.PREPARE;
    private int tick = 0;
    private int waitTimer = 0;
    private int finishTimer = 0;
    private int pendingTimer = 0;
    private int waveIndex = 0;
    private int level;
    private float totalMaxHealth = 0;
    private boolean success = false;
    private final ArrayList<Wave> waves = new ArrayList<>();
    private final LinkedList<UUID> summonedUuids = new LinkedList<>();
    private final Set<LivingEntity> summoned = new HashSet<>();
    private final ServerBossBar bossBar = new ServerBossBar(Text.empty(), ServerBossBar.Color.RED, ServerBossBar.Style.NOTCHED_10);

    enum EventState
    {
        PREPARE,
        RUNNING,
        WAITING,
        FINISH
    }

    private ConsciousnessEventLogic(BlockPos pos)
    {
        this.pos = pos;
    }

    public ConsciousnessEventLogic(Random random, BlockPos pos, int level)
    {
        this.pos = pos;
        this.level = level;
        WaveFactory.getRandom(random, this.level).fillWaves(random, this.waves);
    }

    public void discard()
    {
        this.summoned.clear();
        this.summonedUuids.clear();
        this.bossBar.clearPlayers();
    }

    protected void updateBarToPlayers(ServerWorld world)
    {
        Set<ServerPlayerEntity> set = new HashSet<>(this.bossBar.getPlayers());
        List<ServerPlayerEntity> list = this.getPlayers(world);
        for (ServerPlayerEntity player : list)
            if (!set.contains(player))
                this.bossBar.addPlayer(player);
        for (ServerPlayerEntity player : set)
            if (!list.contains(player))
                this.bossBar.removePlayer(player);
    }

    public boolean isSuccess()
    {
        return success;
    }

    public boolean isFinished()
    {
        return this.state == EventState.FINISH;
    }

    public boolean isFinishedLast()
    {
        return this.state == EventState.FINISH && this.finishTimer >= FINISH_TIME;
    }

    public void tick(ServerWorld world)
    {
        this.tick++;
        while (!this.summonedUuids.isEmpty())
        {
            UUID uuid = this.summonedUuids.poll();
            if (uuid == null) continue;
            Entity entity = world.getEntity(uuid);
            if (entity instanceof LivingEntity living)
                this.summoned.add(living);
        }

        if (this.tick % 20 == 0)
            this.updateBarToPlayers(world);

        switch (this.state)
        {
            case PREPARE -> this.tickPrepare(world);
            case RUNNING -> this.tickRunning(world);
            case WAITING -> this.tickWaiting(world);
            case FINISH -> this.tickFinish(world);
        }
    }

    public List<ServerPlayerEntity> getPlayers(ServerWorld world)
    {
        return world.getPlayers(this.isInEventRange());
    }

    protected Predicate<ServerPlayerEntity> isInEventRange()
    {
        return player ->
        {
            Vec3d subtract = this.pos.toCenterPos().subtract(player.getPos());
            return Math.abs(subtract.getX()) <= RADIUS &&
                   Math.abs(subtract.getY()) <= RADIUS &&
                   Math.abs(subtract.getZ()) <= RADIUS;
        };
    }

    protected void tickPrepare(ServerWorld world)
    {
        if (this.tick == PREPARE_TIME)
            this.turnToRunning(world);

        this.bossBar.setPercent((float) this.tick / PREPARE_TIME);
        if (this.tick % 10 == 0)
            this.bossBar.setName(SDTexts.TEXT$EVENT$PREPARE.get((PREPARE_TIME - this.tick) / 20));
    }

    protected void tickRunning(ServerWorld world)
    {
        // if enemies are cleared, go to next wave or finish
        if (checkSummonedClear(world))
        {
            this.waveIndex++;
            if (this.waveIndex >= this.waves.size())
                this.turnToFinish(world, true);
            else this.turnToWaiting(world);
        }

        // if no player, pending
        else if (this.getPlayers(world).isEmpty())
        {
            this.pendingTimer++;
            if (this.pendingTimer > PENDING_TIME)
                this.turnToFinish(world, false);
        }
        //
        else this.pendingTimer = 0;

        // if exceed time limit or players escape, fail
        if (this.pendingTimer > PENDING_TIME ||
            this.tick > ConsciousnessEventManager.TIME_LIMIT)
        {
            this.turnToFinish(world, false);
        }

        // Update boss bar
        float current = (float) this.summoned.stream().mapToDouble(LivingEntity::getHealth).sum();
        this.bossBar.setPercent(current / this.totalMaxHealth);
        if (this.tick % 10 == 0)
            this.bossBar.setName(
                    SDTexts.TEXT$EVENT$RUNNING.get(
                            this.waveIndex + 1,
                            this.waves.size(),
                            this.summoned.size()
                    )
            );
    }

    protected void tickWaiting(ServerWorld world)
    {
        if (this.waitTimer < WAIT_TIME)
        {
            this.waitTimer++;
            if (this.waitTimer == WAIT_TIME)
                this.turnToRunning(world);
        }

        this.bossBar.setPercent((float) this.waitTimer / WAIT_TIME);
        if (this.tick % 10 == 0)
            this.bossBar.setName(
                    SDTexts.TEXT$EVENT$WAITING.get(
                            this.waveIndex + 1,
                            this.waves.size(),
                            (WAIT_TIME - this.waitTimer) / 20
                    )
            );
    }

    protected void tickFinish(ServerWorld world)
    {
        if (this.finishTimer < FINISH_TIME)
        {
            this.finishTimer++;
        }
    }

    protected void turnToRunning(ServerWorld world)
    {
        this.state = EventState.RUNNING;
        this.summon(world);
    }

    protected void turnToWaiting(ServerWorld world)
    {
        this.state = EventState.WAITING;
        this.waitTimer = 0;
    }

    protected void turnToFinish(ServerWorld world, boolean success)
    {
        this.state = EventState.FINISH;
        this.finishTimer = 0;
        this.bossBar.setName(
                success ?
                        SDTexts.TEXT$EVENT$FINISH$SUCCESS.get() :
                        SDTexts.TEXT$EVENT$FINISH$FAIL.get()
        );
    }

    protected void summon(ServerWorld world)
    {
        if (waveIndex >= waves.size()) return;
        Wave wave = waves.get(waveIndex);
        this.summoned.clear();
        double playerLevel = this.calcPlayerLevel(world);

        int time = 0;
        while (this.summoned.size() < wave.count())
        {
            if ((++time) > ConsciousnessEventManager.SPAWN_LIMIT) break;

            Optional<EntityType<?>> entityTypeOptional = wave.summoner().getEntityType(world);
            if (entityTypeOptional.isEmpty()) continue;
            EntityType<?> entityType = entityTypeOptional.get();

            Optional<BlockPos> posOptional = ConsciousnessEventManager.tryFindSummonPos(world, this.pos, entityType);
            if (posOptional.isEmpty()) continue;

            Entity spawn = entityType.spawn(world, posOptional.get(), SpawnReason.EVENT);

            if (spawn instanceof LivingEntity living)
            {
                this.summoned.add(living);
                living.setGlowing(true);
                MobDifficulty.get(living).ifPresent(difficulty -> difficulty.init((_pos, instance) ->
                {
                    instance.base = this.level;
                    instance.difficulty = (int) playerLevel;
                    instance.finalFactor = 1 + this.waveIndex * FACTOR_PER_WAVE;
                }));
            }
            if (spawn instanceof MobEntity mob)
                mob.playSpawnEffects();
        }

        this.totalMaxHealth = (float) this.summoned.stream().mapToDouble(LivingEntity::getMaxHealth).sum();
    }

    protected double calcPlayerLevel(ServerWorld world)
    {
        return this.getPlayers(world)
                .stream()
                .mapToDouble(player -> PlayerDifficulty.get(player).getLevel().getLevel())
                .average()
                .orElse(0);
    }

    protected boolean checkSummonedClear(ServerWorld world)
    {
        this.summoned.removeIf(LivingEntity::isDead);
        return summoned.isEmpty();
    }

    public static ConsciousnessEventLogic fromNbt(BlockPos pos, NbtCompound nbt)
    {
        ConsciousnessEventLogic logic = new ConsciousnessEventLogic(pos);
        logic.state = EventState.valueOf(nbt.getString(STATE_KEY));
        logic.tick = nbt.getInt("EventTick");
        logic.waitTimer = nbt.getInt(WAIT_TIMER_KEY);
        logic.finishTimer = nbt.getInt(FINISH_TIMER_KEY);
        logic.pendingTimer = nbt.getInt(PENDING_TIMER_KEY);
        logic.waveIndex = nbt.getInt(WAVE_INDEX_KEY);
        logic.level = nbt.getInt(LEVEL_KEY);
        logic.totalMaxHealth = nbt.getFloat(TOTAL_MAX_HEALTH_KEY);
        logic.success = nbt.getBoolean(SUCCESS_KEY);
        logic.waves.clear();
        NbtList wavesNbt = nbt.getList(WAVES_KEY, NbtElement.COMPOUND_TYPE);
        for (NbtElement element : wavesNbt)
            if (element instanceof NbtCompound waveNbt)
            {
                Wave wave = Wave.fromNbt(waveNbt);
                if (wave != null)
                    logic.waves.add(wave);
            }
        logic.summonedUuids.clear();
        NbtList summonedNbt = nbt.getList(SUMMONED_KEY, NbtElement.INT_ARRAY_TYPE);
        for (NbtElement i : summonedNbt)
            if (i instanceof NbtIntArray uuidNbt)
                logic.summonedUuids.add(NbtHelper.toUuid(uuidNbt));
        return logic;
    }

    public NbtCompound toNbt()
    {
        NbtCompound nbt = new NbtCompound();
        nbt.putString(STATE_KEY, this.state.name());
        nbt.putInt(TICK_KEY, this.tick);
        nbt.putInt(WAIT_TIMER_KEY, this.waitTimer);
        nbt.putInt(FINISH_TIMER_KEY, this.finishTimer);
        nbt.putInt(PENDING_TIMER_KEY, this.pendingTimer);
        nbt.putInt(WAVE_INDEX_KEY, this.waveIndex);
        nbt.putInt(LEVEL_KEY, this.level);
        nbt.putFloat(TOTAL_MAX_HEALTH_KEY, this.totalMaxHealth);
        nbt.putBoolean(SUCCESS_KEY, this.success);
        NbtList wavesNbt = new NbtList();
        for (Wave wave : this.waves)
        {
            NbtCompound waveNbt = wave.toNbt();
            if (waveNbt != null)
                wavesNbt.add(waveNbt);
        }
        nbt.put(WAVES_KEY, wavesNbt);
        NbtList summonedNbt = new NbtList();
        for (Entity entity : this.summoned)
            summonedNbt.add(NbtHelper.fromUuid(entity.getUuid()));
        nbt.put(SUMMONED_KEY, summonedNbt);
        return nbt;
    }
}
