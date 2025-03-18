package karashokleo.spell_dimension.api;

import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.spell.Spell;
import org.jetbrains.annotations.Nullable;

public interface BeamProvider
{
    @Nullable
    LivingEntity getBeamTarget();

    void setBeamTarget(@Nullable LivingEntity target);

    @Nullable
    Spell.Release.Target.Beam getBeam();

    void setBeam(@Nullable Spell.Release.Target.Beam beam);
}
