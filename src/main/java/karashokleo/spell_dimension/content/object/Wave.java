package karashokleo.spell_dimension.content.object;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.Nullable;

public record Wave(Summoner summoner, int count)
{
    public static final String SUMMONER_KEY = "Summoner";
    public static final String COUNT_KEY = "Count";

    @Nullable
    public static Wave fromNbt(NbtCompound nbt)
    {
        int count = nbt.getInt(COUNT_KEY);
        if (count <= 0)
        {
            return null;
        }
        NbtElement summonerNbt = nbt.get(SUMMONER_KEY);
        if (summonerNbt == null)
        {
            return null;
        }
        Summoner summoner = Summoner.fromNbt(summonerNbt);
        if (summoner == null)
        {
            return null;
        }
        return new Wave(summoner, count);
    }

    @Nullable
    public NbtCompound toNbt()
    {
        NbtElement summonerNbt = this.summoner().toNbt();
        if (summonerNbt == null)
        {
            return null;
        }
        NbtCompound nbt = new NbtCompound();
        nbt.put(SUMMONER_KEY, summonerNbt);
        nbt.putInt(COUNT_KEY, this.count());
        return nbt;
    }
}
