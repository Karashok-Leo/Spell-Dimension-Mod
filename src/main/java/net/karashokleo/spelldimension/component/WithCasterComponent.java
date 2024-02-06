package net.karashokleo.spelldimension.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public abstract class WithCasterComponent implements Component
{
    private static final String CASTER_KEY = "caster";
    @Nullable
    protected java.util.UUID casterUuid;
    @Nullable
    protected LivingEntity caster;
    protected final LivingEntity source;

    protected WithCasterComponent(LivingEntity source)
    {
        this.source = source;
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        if (tag.contains(CASTER_KEY))
            this.casterUuid = tag.getUuid(CASTER_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag)
    {
        if (casterUuid != null)
            tag.putUuid(CASTER_KEY, casterUuid);
    }

    public void setCaster(@Nullable LivingEntity entity)
    {
        if (entity == null)
        {
            this.casterUuid = null;
            this.caster = null;
        } else
        {
            this.casterUuid = entity.getUuid();
            this.caster = entity;
        }
    }

    @Nullable
    public LivingEntity getCaster()
    {
        if (this.caster != null && !this.caster.isRemoved())
            return this.caster;
        else if (this.casterUuid != null &&
                this.source.getWorld() instanceof ServerWorld serverWorld &&
                serverWorld.getEntity(this.casterUuid) instanceof LivingEntity livingEntity)
        {
            this.caster = livingEntity;
            return this.caster;
        } else
            return null;
    }
}
