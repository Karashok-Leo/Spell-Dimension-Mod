package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulControllerComponent implements AutoSyncedComponent
{
    private static final String CONTROLLING_MINION_KEY = "ControllingMinionData";
    private static final String FAKE_PLAYER_SELF_KEY = "FakePlayerSelf";

    private final PlayerEntity player;

    @Nullable
    private NbtCompound controllingMinionData;

    @Nullable
    private FakePlayerEntity fakePlayerSelf;
    @Nullable
    private UUID fakePlayerSelfUuid;

    /**
     * used in client-side only
     */
    private boolean controlling = false;

    public SoulControllerComponent(PlayerEntity player)
    {
        this.player = player;
    }

    public boolean isControlling()
    {
        if (!player.getWorld().isClient())
        {
            controlling = getControllingMinionData() != null && fakePlayerSelfUuid != null;
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
        if (fakePlayerSelf == null)
        {
            return null;
        }
        if (!(player.getWorld() instanceof ServerWorld world))
        {
            return null;
        }
        if (fakePlayerSelf == null &&
            world.getEntity(fakePlayerSelfUuid) instanceof FakePlayerEntity fakePlayer)
        {
            fakePlayerSelf = fakePlayer;
        }
        return fakePlayerSelf;
    }

    public void setFakePlayerSelf(@Nullable FakePlayerEntity fakePlayerSelf)
    {
        if (fakePlayerSelf == null ||
            fakePlayerSelf.isDead() ||
            fakePlayerSelf.isRemoved())
        {
            this.fakePlayerSelf = null;
            this.fakePlayerSelfUuid = null;
        } else
        {
            this.fakePlayerSelf = fakePlayerSelf;
            this.fakePlayerSelfUuid = fakePlayerSelf.getUuid();
        }
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        if (tag.contains(CONTROLLING_MINION_KEY, NbtCompound.COMPOUND_TYPE))
        {
            controllingMinionData = tag.getCompound(CONTROLLING_MINION_KEY);
        }
        if (tag.containsUuid(FAKE_PLAYER_SELF_KEY))
        {
            fakePlayerSelfUuid = tag.getUuid(FAKE_PLAYER_SELF_KEY);
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (controllingMinionData != null)
        {
            tag.put(CONTROLLING_MINION_KEY, controllingMinionData);
        }
        if (fakePlayerSelfUuid != null)
        {
            tag.putUuid(FAKE_PLAYER_SELF_KEY, fakePlayerSelfUuid);
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
