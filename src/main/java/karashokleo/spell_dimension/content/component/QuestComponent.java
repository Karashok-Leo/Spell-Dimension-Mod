package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record QuestComponent(Set<RegistryEntry<Quest>> completed) implements AutoSyncedComponent
{
    public static void addCompleted(ServerPlayerEntity player, RegistryEntry<Quest> entry)
    {
        player.getComponent(AllComponents.QUEST).completed.add(entry);
        AllComponents.QUEST.sync(player);
    }

    public static void removeCompleted(ServerPlayerEntity player, RegistryEntry<Quest> entry)
    {
        player.getComponent(AllComponents.QUEST).completed.remove(entry);
        AllComponents.QUEST.sync(player);
    }

    public static boolean isCompleted(PlayerEntity player, RegistryEntry<Quest> entry)
    {
        return player.getComponent(AllComponents.QUEST).completed.contains(entry);
    }

    private static final String KEY = "Completed";

    public QuestComponent()
    {
        this(new HashSet<>());
    }

    @Override
    public Set<RegistryEntry<Quest>> completed()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.completed.clear();
        NbtList list = tag.getList(KEY, NbtElement.STRING_TYPE);
        for (NbtElement element : list)
        {
            var optional = QuestRegistry.ENTRY_CODEC.decode(NbtOps.INSTANCE, element).result();
            if (optional.isPresent()) this.completed.add(optional.get().getFirst());
            else throw new IllegalArgumentException("Unknown quest entry: " + element);
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        NbtList list = new NbtList();
        for (RegistryEntry<Quest> entry : this.completed)
        {
            Optional<NbtElement> optional = QuestRegistry.ENTRY_CODEC.encodeStart(NbtOps.INSTANCE, entry).result();
            if (optional.isPresent()) list.add(optional.get());
            else throw new IllegalArgumentException("Unknown quest entry: " + entry);
        }
        tag.put(KEY, list);
    }
}
