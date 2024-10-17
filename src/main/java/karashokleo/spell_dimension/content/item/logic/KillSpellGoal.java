package karashokleo.spell_dimension.content.item.logic;

import net.minecraft.nbt.NbtCompound;

public class KillSpellGoal extends SpellGoal
{
    private static final String MIN_LEVEL = "MinLevel";

    public KillSpellGoal()
    {
        super("KillMob");
    }

    public int getMinLevel(NbtCompound nbt)
    {
        return this.getOrCreate(nbt).getInt(MIN_LEVEL);
    }

    public boolean addKillCredit(NbtCompound nbt, int level)
    {
        if (level < this.getMinLevel(nbt)) return false;
        return this.increaseProgress(nbt);
    }
}
