package karashokleo.spell_dimension.content.event.conscious;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.Nullable;

public record Wave(Summoner summoner, int count)
{
    public static final String SUMMONER_KEY = "Summoner";
    public static final String COUNT_KEY = "Count";

    //    MELEE(
//            Summoner.fromTag(LHTags.MELEE_WEAPON_TARGET), 8
//    ),
//    RANGED(
//            Summoner.fromTag(LHTags.RANGED_WEAPON_TARGET), 8
//    ),
//    BOSS(
//            Summoner.fromTag(LHTags.SEMIBOSS), 1
//    ),
//    ZOMBIE(
//            Summoner.fromTag(AllTags.ZOMBIE), 8
//    ),
//    SKELETON(
//            Summoner.fromTag(AllTags.SKELETON), 8
//    ),
//    CREEPER(
//            Summoner.fromTag(AllTags.CREEPER), 8
//    ),
//    MINI_BOSS(
//            Summoner.fromTag(AllTags.MINI_BOSS), 1
//    ),
//    RAID(
//            Summoner.fromTag(AllTags.RAID), 8
//    ),
    ;

    @Nullable
    public static Wave fromNbt(NbtCompound nbt)
    {
        int count = nbt.getInt(COUNT_KEY);
        if (count <= 0) return null;
        NbtElement summonerNbt = nbt.get(SUMMONER_KEY);
        if (summonerNbt == null) return null;
        Summoner summoner = Summoner.fromNbt(summonerNbt);
        if (summoner == null) return null;
        return new Wave(summoner, count);
    }

    @Nullable
    public NbtCompound toNbt()
    {
        NbtElement summonerNbt = this.summoner().toNbt();
        if (summonerNbt == null) return null;
        NbtCompound nbt = new NbtCompound();
        nbt.put(SUMMONER_KEY, summonerNbt);
        nbt.putInt(COUNT_KEY, this.count());
        return nbt;
    }
}
