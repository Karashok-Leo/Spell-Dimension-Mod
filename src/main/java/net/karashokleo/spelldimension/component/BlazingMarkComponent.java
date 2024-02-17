package net.karashokleo.spelldimension.component;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.karashokleo.spelldimension.SpellDimensionComponents;
import net.karashokleo.spelldimension.spell.BlazingMark;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class BlazingMarkComponent extends WithCasterComponent implements ServerTickingComponent
{
    private static final String DURATION_KEY = "duration";
    private static final String AMPLIFIER_KEY = "amplifier";
    private static final String DAMAGE_KEY = "damage";

    private int duration = 0;
    private int amplifier = 0;
    private float damage = 0;

    public BlazingMarkComponent(LivingEntity source)
    {
        super(source);
    }

    @Override
    public void serverTick()
    {
        if (duration == 0) return;
        --duration;
        if (duration == BlazingMark.getTriggerDuration())
            BlazingMark.trigger(source, caster, damage, amplifier);
        if (duration % 20 == 0)
        {
            if (duration == 0 || source.isSubmergedInWater())
                clear();
            particle();
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        this.duration = tag.getInt(DURATION_KEY);
        this.amplifier = tag.getInt(AMPLIFIER_KEY);
        this.damage = tag.getFloat(DAMAGE_KEY);
        super.readFromNbt(tag);
    }

    @Override
    public void writeToNbt(NbtCompound tag)
    {
        tag.putInt(DURATION_KEY, this.duration);
        tag.putInt(AMPLIFIER_KEY, this.amplifier);
        tag.putFloat(DAMAGE_KEY, this.damage);
        super.writeToNbt(tag);
    }

    public int getDuration()
    {
        return this.duration;
    }

    public void accumulateDamage(float amount)
    {
        this.damage = Math.min(this.damage + amount, this.amplifier * BlazingMark.getMaxDamage());
    }

    public void clear()
    {
        this.source.setGlowing(false);
        this.duration = 0;
        this.amplifier = 0;
        this.damage = 0;
        this.setCaster(null);
    }

    private void particle()
    {
        float f = Math.min(source.getWidth(), source.getHeight()) * 0.5F;
        Vec3d pos = source.getPos().add(0, source.getHeight() + f, 0).addRandom(source.getRandom(), 0.5F);
        int color = duration >= BlazingMark.getTriggerDuration() ? 0xffff00 - 0x100 * (int) (0xff * damage / (amplifier * BlazingMark.getMaxDamage())) : 0x888888;
        BlazingMark.sendDustPacket(source, pos, (int) (f * 100), color, f);
    }

    public static BlazingMarkComponent get(LivingEntity livingEntity)
    {
        return SpellDimensionComponents.BLAZING_MARK.get(livingEntity);
    }

    public static void applyToLiving(LivingEntity source, LivingEntity caster, int amplifier)
    {
        BlazingMarkComponent component = get(source);
        source.setGlowing(true);
        component.duration = BlazingMark.getTotalDuration();
        component.amplifier = amplifier;
        component.damage = 0;
        component.setCaster(caster);
    }
}
