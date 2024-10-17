package karashokleo.spell_dimension.content.item.logic;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class SpellGoal
{
    private static final String GOAL = "Goal";
    private static final String PROGRESS = "Progress";

    public final String name;

    public SpellGoal(String name)
    {
        this.name = name;
    }

    public NbtCompound getOrCreate(NbtCompound nbt)
    {
        if (!nbt.contains(this.name, NbtElement.COMPOUND_TYPE))
            nbt.put(this.name, new NbtCompound());
        return nbt.getCompound(this.name);
    }

    public int getGoal(NbtCompound nbt)
    {
        return this.getOrCreate(nbt).getInt(GOAL);
    }

    public void setGoal(NbtCompound nbt, int goal)
    {
        this.getOrCreate(nbt).putInt(GOAL, goal);
    }

    public int getProgress(NbtCompound nbt)
    {
        return this.getOrCreate(nbt).getInt(PROGRESS);
    }

    public void setProgress(NbtCompound nbt, int progress)
    {
        this.getOrCreate(nbt).putInt(PROGRESS, progress);
    }

    public boolean increaseProgress(NbtCompound nbt)
    {
        NbtCompound compound = this.getOrCreate(nbt);
        int progress = compound.getInt(PROGRESS);
        int goal = compound.getInt(GOAL);
        if (progress >= goal) return false;
        compound.putInt(PROGRESS, progress + 1);
        return true;
    }

    public boolean reachGoal(NbtCompound nbt)
    {
        int goal = this.getGoal(nbt);
        int progress = this.getProgress(nbt);
        return progress >= goal;
    }
}
