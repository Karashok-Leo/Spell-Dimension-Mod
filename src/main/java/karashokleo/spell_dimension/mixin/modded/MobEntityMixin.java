package karashokleo.spell_dimension.mixin.modded;

import karashokleo.spell_dimension.api.BeamProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.spell_engine.api.spell.Spell;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin implements BeamProvider
{
    @Unique
    @Nullable
    private LivingEntity beamTarget = null;
    @Unique
    @Nullable
    private Spell.Release.Target.Beam beam = null;

    @Override
    public @Nullable LivingEntity getBeamTarget()
    {
        return beamTarget;
    }

    @Override
    public void setBeamTarget(@Nullable LivingEntity beamTarget)
    {
        this.beamTarget = beamTarget;
    }

    @Override
    public @Nullable Spell.Release.Target.Beam getBeam()
    {
        return beam;
    }

    @Override
    public void setBeam(@Nullable Spell.Release.Target.Beam beam)
    {
        this.beam = beam;
    }
}
