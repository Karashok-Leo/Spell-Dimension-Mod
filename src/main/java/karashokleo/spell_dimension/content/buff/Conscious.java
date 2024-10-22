package karashokleo.spell_dimension.content.buff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.wispforest.owo.ops.WorldOps;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.api.buff.BuffType;
import karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class Conscious implements Buff
{
    public static final Codec<Conscious> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                    Codecs.NONNEGATIVE_INT.fieldOf("countDown").forGetter(e -> e.countDown)
            ).apply(ins, Conscious::new)
    );
    public static final BuffType<Conscious> TYPE = new BuffType<>(CODEC, false);
    private static final int COUNT_DOWN = 60 * 20;

    private final ServerBossBar bossBar = new ServerBossBar(Text.of(""), ServerBossBar.Color.PURPLE, ServerBossBar.Style.PROGRESS);
    private ServerPlayerEntity player;
    private int countDown;

    public Conscious(int countDown)
    {
        this.countDown = countDown;
    }

    public Conscious()
    {
        this(COUNT_DOWN);
    }

    @Override
    public BuffType<?> getType()
    {
        return TYPE;
    }

    @Override
    public void onApplied(LivingEntity entity, @Nullable LivingEntity source)
    {
        if (entity instanceof ServerPlayerEntity serverPlayer)
            this.player = serverPlayer;
    }

    @Override
    public void onRemoved(LivingEntity entity, @Nullable LivingEntity source)
    {
        if (player == null) return;
        bossBar.removePlayer(player);
        ServerWorld serverWorld = player.getServerWorld();
        if (serverWorld.getRegistryKey().equals(AllWorldGen.OC_WORLD))
        {
            ServerWorld destinationWorld = serverWorld.getServer().getOverworld();
            BlockPos destinationPos = ConsciousnessEventManager.findTeleportPos(serverWorld, destinationWorld, player.getBlockPos());
            WorldOps.teleportToWorld(player, destinationWorld, destinationPos.toCenterPos());
        }
    }

    @Override
    public void serverTick(LivingEntity entity, @Nullable LivingEntity source)
    {
        if (player == null)
        {
            if (entity instanceof ServerPlayerEntity serverPlayer)
                this.player = serverPlayer;
            else return;
        }
        if (countDown <= 0)
        {
            Buff.remove(entity, this.getType());
        } else
        {
            countDown--;
            if (countDown % 10 == 0)
                tickBossBar();
        }
    }

    private void tickBossBar()
    {
        bossBar.setPercent(1.0F * countDown / COUNT_DOWN);
        bossBar.setName(SDTexts.TEXT$CONSCIOUS_TIMER.get(countDown / 20));
        if (!bossBar.getPlayers().contains(player))
            bossBar.addPlayer(player);
    }
}
