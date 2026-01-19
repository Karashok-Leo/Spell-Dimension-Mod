package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.util.OptionalEntityRef;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SoulControllerComponent implements AutoSyncedComponent, ServerTickingComponent
{
    private static final String CONTROLLING_MINION_KEY = "ControllingMinionData";
    private static final String FAKE_PLAYER_SELF_KEY = "FakePlayerSelf";

    private final PlayerEntity player;
    private final HashSet<UUID> activeMinions;

    @Nullable
    private NbtCompound controllingMinionData;

    private final OptionalEntityRef<FakePlayerEntity> fakePlayerSelf;

    /**
     * used in client-side only
     */
    private boolean controlling = false;

    public SoulControllerComponent(PlayerEntity player)
    {
        this.player = player;
        this.activeMinions = new HashSet<>();
        this.fakePlayerSelf = new OptionalEntityRef<>(FakePlayerEntity.class, e -> !e.isRemoved());
    }

    public void onMinionAdded(MobEntity minion)
    {
        activeMinions.add(minion.getUuid());
    }

    public void onMinionRemoved(MobEntity minion)
    {
        activeMinions.remove(minion.getUuid());
    }

    public List<MobEntity> getActiveMinions()
    {
        if (!(player.getWorld() instanceof ServerWorld world) ||
            activeMinions.isEmpty())
        {
            return Collections.emptyList();
        }

        List<MobEntity> result = new ArrayList<>(activeMinions.size());
        for (UUID uuid : activeMinions)
        {
            if (world.getEntity(uuid) instanceof MobEntity mob)
            {
                result.add(mob);
            }
        }
        return result;
    }

    public boolean isControlling()
    {
        if (!player.getWorld().isClient())
        {
            controlling = getControllingMinionData() != null &&
                fakePlayerSelf.isPresent();
        }

        return controlling;
    }

    @Nullable
    public NbtCompound getControllingMinionData()
    {
        return controllingMinionData;
    }

    public void setControllingMinionData(@Nullable NbtCompound controllingMinionData)
    {
        this.controllingMinionData = controllingMinionData;
    }

    @Nullable
    public FakePlayerEntity getFakePlayerSelf()
    {
        MinecraftServer server = player.getServer();
        if (server == null)
        {
            return null;
        }
        return fakePlayerSelf.get(server);
    }

    public void setFakePlayerSelf(@Nullable FakePlayerEntity fakePlayerSelf)
    {
        this.fakePlayerSelf.set(fakePlayerSelf);
    }

    @Override
    public void serverTick()
    {
        if (player.age % 20 != 0)
        {
            return;
        }

        if (!isControlling())
        {
            return;
        }

        fakePlayerSelf.keepLoaded(player.getServer());

        FakePlayerEntity self = getFakePlayerSelf();
        if (self == null)
        {
            SpellDimension.sendErrorMsg(player, "Your body was accidentally lost while possessing a soul minion! This may be caused by a program error. Please report it to the GitHub issue tracker. Click this message to jump to the issue page.");
            SpellDimension.sendErrorMsg(player, "你的本体意外丢失了！这可能是程序错误导致的，请将此问题报告至 GitHub Issue（点击此消息跳转）。或进入QQ群反馈给作者。");
            SpellDimension.LOGGER.warn("FakePlayerEntity is null!!!");
//            player.damage(player.getDamageSources().outOfWorld(), Float.MAX_VALUE);
//            // ensure death
//            player.setHealth(0);
            return;
        }

        if (self.isAlive())
        {
            return;
        }

        SpellDimension.sendErrorMsg(player, "Your body accidentally died while possessing a soul minion! This may be caused by a program error. Please report it to the GitHub issue tracker. Click this message to jump to the issue page.");
        SpellDimension.sendErrorMsg(player, "你的本体意外死亡了！这可能是程序错误导致的，请将此问题报告至 GitHub Issue（点击此消息跳转）。或进入QQ群反馈给作者。");
        SpellDimension.LOGGER.warn("FakePlayerEntity is dead or removed!!!");
        SoulControl.onSelfBodyDeath((ServerPlayerEntity) player);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        if (tag.contains(CONTROLLING_MINION_KEY, NbtCompound.COMPOUND_TYPE))
        {
            controllingMinionData = tag.getCompound(CONTROLLING_MINION_KEY);
        }
        if (tag.contains(FAKE_PLAYER_SELF_KEY, NbtCompound.COMPOUND_TYPE))
        {
            fakePlayerSelf.readFromNbt(tag.getCompound(FAKE_PLAYER_SELF_KEY));
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (controllingMinionData != null)
        {
            tag.put(CONTROLLING_MINION_KEY, controllingMinionData);
        }
        if (fakePlayerSelf.isPresent())
        {
            NbtCompound fakePlayerTag = new NbtCompound();
            fakePlayerSelf.writeToNbt(fakePlayerTag);
            tag.put(FAKE_PLAYER_SELF_KEY, fakePlayerTag);
        }
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf)
    {
        controlling = buf.readBoolean();
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient)
    {
        buf.writeBoolean(isControlling());
    }
}
