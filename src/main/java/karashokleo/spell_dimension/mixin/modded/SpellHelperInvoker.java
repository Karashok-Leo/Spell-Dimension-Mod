package karashokleo.spell_dimension.mixin.modded;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(value = SpellHelper.class, remap = false)
public interface SpellHelperInvoker
{
    @Invoker("applyAreaImpact")
    static void applyAreaImpact(World world, LivingEntity caster, List<Entity> targets, float range, Spell.Release.Target.Area area, SpellInfo spellInfo, SpellHelper.ImpactContext context, boolean additionalTargetLookup)
    {
        throw new AssertionError();
    }

    @Invoker("beamImpact")
    static void beamImpact(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo, SpellHelper.ImpactContext context)
    {
        throw new AssertionError();
    }

    @Invoker("directImpact")
    static void directImpact(World world, LivingEntity caster, Entity target, SpellInfo spellInfo, SpellHelper.ImpactContext context)
    {
        throw new AssertionError();
    }
}
