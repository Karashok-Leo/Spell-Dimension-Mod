package karashokleo.spell_dimension.content.entity;

import com.google.common.collect.Sets;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager;
import karashokleo.spell_dimension.content.event.conscious.Wave;
import karashokleo.spell_dimension.content.event.conscious.WaveFactory;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
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
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.Predicate;

public class ConsciousnessEventEntity extends Entity
{
    public static final int PREPARE_TIME = 200;
    public static final int WAIT_TIME = 200;
    public static final int FINISH_TIME = 200;

    public static final double FACTOR_PER_WAVE = 0.4;
    public static final String STATE_KEY = "State";
    public static final String WAIT_TIMER_KEY = "WaitTimer";
    public static final String FINISH_TIMER_KEY = "FinishTimer";
    public static final String WAVE_INDEX_KEY = "WaveIndex";
    public static final String LEVEL_KEY = "Level";
    public static final String WAVES_KEY = "Waves";
    public static final String SUMMONED_KEY = "Summoned";
    public static final String TOTAL_MAX_HEALTH_KEY = "TotalMaxHealth";

    private State state = State.PREPARE;
    private int waitTimer = 0;
    private int finishTimer = 0;
    private int waveIndex = 0;
    private int level;
    private float totalMaxHealth = 0;
    private final ArrayList<Wave> waves = new ArrayList<>();
    private final Set<LivingEntity> summoned = new HashSet<>();
    private final ServerBossBar bossBar = new ServerBossBar(Text.empty(), ServerBossBar.Color.RED, ServerBossBar.Style.NOTCHED_10);

    enum State
    {
        PREPARE,
        RUNNING,
        WAITING,
        FINISH
    }

    public ConsciousnessEventEntity(EntityType<?> type, World world)
    {
        super(type, world);
    }

    public void initLevel(int level)
    {
        this.level = level;
        WaveFactory.getRandom(this.random, this.level).fillWaves(this.waves);
    }

    public void setBoundingBox(int radius)
    {
        BlockPos pos = this.getBlockPos();
        Box box = new Box(
                pos.add(-radius, -radius, -radius),
                pos.add(radius, radius, radius)
        );
        this.setBoundingBox(box);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (!(this.getWorld() instanceof ServerWorld world)) return;
        switch (this.state)
        {
            case PREPARE -> this.tickPrepare(world);
            case RUNNING -> this.tickRunning(world);
            case WAITING -> this.tickWaiting(world);
            case FINISH -> this.tickFinish(world);
        }

//        if (this.age % 10 == 0)
//            this.updateBarToPlayers(world);
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player)
    {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player)
    {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    protected Predicate<ServerPlayerEntity> isInEventRange()
    {
        return player -> this.getBoundingBox().contains(player.getPos());
    }

    protected void updateBarToPlayers(ServerWorld world)
    {
        Set<ServerPlayerEntity> set = Sets.newHashSet(this.bossBar.getPlayers());
        List<ServerPlayerEntity> list = world.getPlayers(this.isInEventRange());
        for (ServerPlayerEntity player : list)
            if (!set.contains(player))
                this.bossBar.addPlayer(player);
        for (ServerPlayerEntity player : set)
            if (!list.contains(player))
                this.bossBar.removePlayer(player);
    }

    protected void tickPrepare(ServerWorld world)
    {
        if (this.age == PREPARE_TIME)
            this.turnToRunning(world);

        this.bossBar.setPercent((float) this.age / PREPARE_TIME);
        if (this.age % 10 == 0)
            this.bossBar.setName(SDTexts.TEXT$EVENT$PREPARE.get((PREPARE_TIME - this.age) / 20));
    }

    protected void tickRunning(ServerWorld world)
    {
        if (checkSummonedClear(world))
        {
            if (this.waveIndex >= this.waves.size())
                this.turnToFinish(world, true);
            else this.turnToWaiting(world);
        } else if (this.age > ConsciousnessEventManager.TIME_LIMIT)
        {
            this.turnToFinish(world, false);
        }

        float current = (float) this.summoned.stream().mapToDouble(LivingEntity::getHealth).sum();
        this.bossBar.setPercent(current / this.totalMaxHealth);
        if (this.age % 10 == 0)
            this.bossBar.setName(
                    SDTexts.TEXT$EVENT$RUNNING.get(
                            this.waveIndex,
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
        if (this.age % 10 == 0)
            this.bossBar.setName(
                    SDTexts.TEXT$EVENT$WAITING.get(
                            this.waveIndex,
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
            if (this.finishTimer == FINISH_TIME)
            {
                this.bossBar.clearPlayers();
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    protected void turnToRunning(ServerWorld world)
    {
        this.state = State.RUNNING;
        this.summon(world);
    }

    protected void turnToWaiting(ServerWorld world)
    {
        this.state = State.WAITING;
        this.waitTimer = 0;
    }

    protected void turnToFinish(ServerWorld world, boolean success)
    {
        this.state = State.FINISH;
        this.finishTimer = 0;
        this.bossBar.setName(
                success ?
                        SDTexts.TEXT$EVENT$FINISH$SUCCESS.get() :
                        SDTexts.TEXT$EVENT$FINISH$FAIL.get()
        );

        world.getPlayers(this.isInEventRange())
                .forEach(player -> player.getInventory().offerOrDrop(AllItems.BROKEN_MAGIC_MIRROR.getDefaultStack()));
    }

    protected void summon(ServerWorld world)
    {
        if (waveIndex >= waves.size()) return;
        Wave wave = waves.get(waveIndex);
        this.waveIndex++;
        this.summoned.clear();
        double playerLevel = this.calcPlayerLevel(world);

        int time = 0;
        while (this.summoned.size() < wave.count())
        {
            if ((++time) > ConsciousnessEventManager.SPAWN_LIMIT) break;

            Optional<EntityType<?>> entityTypeOptional = wave.summoner().getEntityType(world);
            if (entityTypeOptional.isEmpty()) continue;
            EntityType<?> entityType = entityTypeOptional.get();

            Optional<BlockPos> posOptional = ConsciousnessEventManager.tryFindSummonPos(world, this.getBlockPos(), entityType);
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
        return world.getPlayers(this.isInEventRange())
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

    @Override
    protected void setWorld(World world)
    {
        super.setWorld(world);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {
        this.state = State.valueOf(nbt.getString(STATE_KEY));
        this.waitTimer = nbt.getInt(WAIT_TIMER_KEY);
        this.finishTimer = nbt.getInt(FINISH_TIMER_KEY);
        this.waveIndex = nbt.getInt(WAVE_INDEX_KEY);
        this.level = nbt.getInt(LEVEL_KEY);
        this.totalMaxHealth = nbt.getFloat(TOTAL_MAX_HEALTH_KEY);
        this.waves.clear();
        NbtList wavesNbt = nbt.getList(WAVES_KEY, NbtElement.COMPOUND_TYPE);
        for (NbtElement element : wavesNbt)
            if (element instanceof NbtCompound waveNbt)
            {
                Wave wave = Wave.fromNbt(waveNbt);
                if (wave != null)
                    this.waves.add(wave);
            }
        this.summoned.clear();
        if (this.getWorld() instanceof ServerWorld world)
        {
            NbtList summonedNbt = nbt.getList(SUMMONED_KEY, NbtElement.INT_ARRAY_TYPE);
            for (NbtElement i : summonedNbt)
                if (i instanceof NbtIntArray uuidNbt)
                {
                    Entity entity = world.getEntity(NbtHelper.toUuid(uuidNbt));
                    if (entity instanceof LivingEntity living)
                        this.summoned.add(living);
                }
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt)
    {
        nbt.putString(STATE_KEY, this.state.name());
        nbt.putInt(WAIT_TIMER_KEY, this.waitTimer);
        nbt.putInt(FINISH_TIMER_KEY, this.finishTimer);
        nbt.putInt(WAVE_INDEX_KEY, this.waveIndex);
        nbt.putInt(LEVEL_KEY, this.level);
        nbt.putFloat(TOTAL_MAX_HEALTH_KEY, this.totalMaxHealth);
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
    }

    @Override
    protected void initDataTracker()
    {
    }
}
