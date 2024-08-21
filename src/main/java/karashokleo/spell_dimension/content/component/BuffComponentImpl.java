package karashokleo.spell_dimension.content.component;

import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.api.buff.BuffType;
import karashokleo.spell_dimension.api.buff.BuffTypeRegistry;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public record BuffComponentImpl(
        LivingEntity owner,
        Map<BuffType<?>, BuffEntry<?>> buffMap
) implements BuffComponent
{
    private static final String ENTRIES_KEY = "Entries";

    public BuffComponentImpl(LivingEntity owner)
    {
        this(owner, new HashMap<>());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <B extends Buff> Optional<B> get(BuffType<B> type)
    {
        if (this.buffMap.get(type) != null)
            return Optional.of((B) this.buffMap.get(type).buff);
        else return Optional.empty();
    }

    @Override
    public <B extends Buff> void apply(BuffType<B> type, B buff, @Nullable LivingEntity source)
    {
        this.buffMap.put(type, new BuffEntry<>(type, buff, source));
        buff.onApplied(this.owner, source);
    }

    @Override
    public <B extends Buff> void remove(BuffType<B> type)
    {
        BuffEntry<?> buffEntry = this.buffMap.remove(type);
        if (buffEntry != null)
            buffEntry.buff.onRemoved(this.owner, buffEntry.source);
    }

    @Override
    public void serverTick()
    {
        AllComponents.BUFF.sync(this.owner);
        for (BuffEntry<?> entry : this.buffMap.values())
            entry.buff.serverTick(owner, entry.source);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.buffMap.clear();
        for (NbtElement element : tag.getList(ENTRIES_KEY, NbtElement.COMPOUND_TYPE))
            if (element instanceof NbtCompound compound)
            {
                BuffEntry<?> entry = BuffEntry.readFromNbt(compound, owner.getWorld());
                if (entry != null) this.buffMap.put(entry.type, entry);
            }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        NbtList list = new NbtList();
        for (BuffEntry<?> entry : this.buffMap.values())
        {
            NbtCompound compound = new NbtCompound();
            entry.writeToNbt(compound);
            list.add(compound);
        }
        tag.put(ENTRIES_KEY, list);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf)
    {
        this.buffMap.clear();
        int size = buf.readInt();
        for (int i = 0; i < size; i++)
        {
            BuffEntry<Buff> entry = BuffEntry.readFromPacket(buf, owner.getWorld());
            this.buffMap.put(entry.type, entry);
        }
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient)
    {
        List<BuffEntry<?>> values = this.buffMap.values().stream().filter(buffEntry -> buffEntry.type.sync()).toList();
        buf.writeInt(values.size());
        values.forEach(entry -> entry.writeToPacket(buf));
    }

    record BuffEntry<B extends Buff>(BuffType<B> type, B buff, @Nullable LivingEntity source)
    {
        private static final String TYPE_KEY = "Type";
        private static final String BUFF_KEY = "Buff";
        private static final String SOURCE_KEY = "Source";

        @Nullable
        @SuppressWarnings("unchecked")
        static <B extends Buff> BuffEntry<B> readFromNbt(NbtCompound tag, World world)
        {
            var result = BuffTypeRegistry.BUFF_TYPE_REGISTRY.getCodec().decode(NbtOps.INSTANCE, tag.get(TYPE_KEY)).result();
            if (result.isPresent())
            {
                BuffType<B> buffType = (BuffType<B>) result.get().getFirst();
                Optional<? extends Pair<B, NbtElement>> buffResult = buffType.codec().decode(NbtOps.INSTANCE, tag.get(BUFF_KEY)).result();
                if (buffResult.isPresent() && tag.contains(SOURCE_KEY))
                {
                    B buff = buffResult.get().getFirst();
                    LivingEntity source = null;
                    if (world instanceof ServerWorld serverWorld &&
                            tag.containsUuid(SOURCE_KEY) &&
                            serverWorld.getEntity(tag.getUuid(SOURCE_KEY)) instanceof LivingEntity living)
                        source = living;
                    return new BuffEntry<>(buffType, buff, source);
                }
            }
            return null;
        }

        void writeToNbt(NbtCompound tag)
        {
            Optional<NbtElement> typeResult = BuffTypeRegistry.BUFF_TYPE_REGISTRY.getCodec().encodeStart(NbtOps.INSTANCE, this.type).result();
            typeResult.ifPresent(nbtElement -> tag.put(TYPE_KEY, nbtElement));
            Optional<NbtElement> buffResult = this.type.codec().encodeStart(NbtOps.INSTANCE, this.buff).result();
            buffResult.ifPresent(nbtElement -> tag.put(BUFF_KEY, nbtElement));
            if (source != null)
                tag.putUuid(SOURCE_KEY, source.getUuid());
        }

        @SuppressWarnings("unchecked")
        static <B extends Buff> BuffEntry<B> readFromPacket(PacketByteBuf buf, World world)
        {
            BuffType<B> buffType = (BuffType<B>) buf.decodeAsJson(BuffTypeRegistry.BUFF_TYPE_REGISTRY.getCodec());
            B buff = buf.decodeAsJson(buffType.codec());
            LivingEntity source = null;
            if (buf.readBoolean() &&
                    world.getEntityById(buf.readInt()) instanceof LivingEntity living)
                source = living;
            return new BuffEntry<>(buffType, buff, source);
        }

        void writeToPacket(PacketByteBuf buf)
        {
            buf.encodeAsJson(BuffTypeRegistry.BUFF_TYPE_REGISTRY.getCodec(), this.type);
            buf.encodeAsJson(this.type.codec(), this.buff);
            buf.writeBoolean(this.source != null);
            if (this.source != null) buf.writeInt(this.source.getId());
        }
    }
}
