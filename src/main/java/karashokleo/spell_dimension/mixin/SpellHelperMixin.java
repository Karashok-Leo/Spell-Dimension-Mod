package karashokleo.spell_dimension.mixin;

import karashokleo.spell_dimension.api.SpellImpactCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(SpellHelper.class)
public abstract class SpellHelperMixin
{
    @Inject(
            method = "performImpact",
            at = @At("RETURN")
    )
    private static void dropEssenceLoot(World world, LivingEntity caster, Entity target, SpellInfo spellInfo, Spell.Impact impact, SpellHelper.ImpactContext context, Collection<ServerPlayerEntity> trackers, CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue())
            SpellImpactCallback.EVENT.invoker().onImpact(world, caster, target, spellInfo, impact, context);
    }
}
