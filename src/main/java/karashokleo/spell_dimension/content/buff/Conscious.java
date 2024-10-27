package karashokleo.spell_dimension.content.buff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.api.buff.BuffType;
import karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllWorldGen;
import karashokleo.spell_dimension.util.TeleportUtil;
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
    public static final int COUNT_DOWN = 20 * 60 * 10;

    private final ServerBossBar bossBar = new ServerBossBar(Text.empty(), ServerBossBar.Color.PURPLE, ServerBossBar.Style.PROGRESS);
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
            BlockPos destinationPos = TeleportUtil.findTeleportPos(serverWorld, destinationWorld, player.getBlockPos());

            TeleportUtil.teleportPlayerChangeDimension(player, destinationWorld, destinationPos);
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
        bossBar.setName(SDTexts.TEXT$CONSCIOUS$COUNTDOWN.get(countDown / 20));
        if (!bossBar.getPlayers().contains(player))
            bossBar.addPlayer(player);
    }
}
