package karashokleo.spell_dimension.content.buff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.api.buff.BuffType;
import karashokleo.spell_dimension.config.SpellConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;

public class Electrocution implements Buff
{
    public static final Codec<Electrocution> CODEC = RecordCodecBuilder.create(
        ins -> ins.group(
            Codecs.NONNEGATIVE_INT.fieldOf("duration").forGetter(Electrocution::getDuration),
            Codecs.POSITIVE_INT.fieldOf("stacks").forGetter(Electrocution::getStacks)
        ).apply(ins, Electrocution::new)
    );
    public static final BuffType<Electrocution> TYPE = new BuffType<>(CODEC, true);

    private int duration;
    private int stacks;

    public Electrocution(int duration, int stacks)
    {
        this.duration = duration;
        this.stacks = stacks;
    }

    public Electrocution()
    {
        this(SpellConfig.ELECTROCUTION_CONFIG.maxDuration(), 1);
    }

    @Override
    public void serverTick(LivingEntity entity, @Nullable LivingEntity source)
    {
        if (duration <= 0)
        {
            Buff.remove(entity, TYPE);
            return;
        }
        --duration;
    }

    public void incrementStacks()
    {
        this.stacks++;
    }

    public boolean shouldExecute()
    {
        return stacks >= SpellConfig.ELECTROCUTION_CONFIG.maxStacks();
    }

    public int getDuration()
    {
        return duration;
    }

    public int getStacks()
    {
        return stacks;
    }

    @Override
    public BuffType<?> getType()
    {
        return TYPE;
    }
}
