package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.api.buff.BuffType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface BuffComponent extends ServerTickingComponent, AutoSyncedComponent
{
    <B extends Buff> Optional<B> get(BuffType<B> type);

    <B extends Buff> void apply(BuffType<B> type, B buff, @Nullable LivingEntity source);

    <B extends Buff> void remove(BuffType<B> type);

    void serverTick();

    @Override
    void readFromNbt(@NotNull NbtCompound tag);

    @Override
    void writeToNbt(@NotNull NbtCompound tag);

    @Override
    void applySyncPacket(PacketByteBuf buf);

    @Override
    void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient);
}
