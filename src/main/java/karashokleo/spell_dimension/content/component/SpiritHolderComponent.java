package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public class SpiritHolderComponent implements AutoSyncedComponent
{
    private static final String SPIRIT_KEY = "Spirit";

    private int spirit;

    public static SpiritHolderComponent get(PlayerEntity player)
    {
        return AllComponents.SPIRIT_HOLDER.get(player);
    }

    public static int getSpirit(PlayerEntity player)
    {
        return get(player).spirit;
    }

    public static void addSpirit(ServerPlayerEntity player, int amount)
    {
        if (amount <= 0)
        {
            return;
        }
        SpiritHolderComponent component = get(player);
        component.spirit += amount;
        sync(player);
    }

    public static void setSpirit(ServerPlayerEntity player, int amount)
    {
        SpiritHolderComponent component = get(player);
        component.spirit = Math.max(0, amount);
        sync(player);
    }

    public static boolean consumeSpirit(ServerPlayerEntity player, int amount)
    {
        if (amount <= 0)
        {
            return true;
        }
        SpiritHolderComponent component = get(player);
        if (component.spirit < amount)
        {
            return false;
        }
        component.spirit -= amount;
        sync(player);
        return true;
    }

    public static void sync(ServerPlayerEntity player)
    {
        AllComponents.SPIRIT_HOLDER.sync(player);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.spirit = Math.max(0, tag.getInt(SPIRIT_KEY));
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putInt(SPIRIT_KEY, this.spirit);
    }
}
