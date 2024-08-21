package karashokleo.spell_dimension.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SpellHelper.class)
public abstract class SpellHelperMixin
{
    @Inject(
            method = "performSpell",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/spell_engine/internals/SpellHelper$ImpactContext;<init>(FFLnet/minecraft/util/math/Vec3d;Lnet/spell_power/api/SpellPower$Result;Lnet/spell_engine/utils/TargetHelper$TargetingMode;)V"
            )
    )
    private static void inject_performSpell_before(World world, PlayerEntity player, Identifier spellId, List<Entity> targets, SpellCast.Action action, float progress, CallbackInfo ci, @Local SpellInfo spellInfo)
    {
        SpellImpactEvents.BEFORE.invoker().beforeImpact(world, player, targets, spellInfo);
    }

    @Inject(
            method = "performSpell",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/spell_engine/particle/ParticleHelper;sendBatches(Lnet/minecraft/entity/Entity;[Lnet/spell_engine/api/spell/ParticleBatch;)V"
            )
    )
    private static void inject_performSpell_after(World world, PlayerEntity player, Identifier spellId, List<Entity> targets, SpellCast.Action action, float progress, CallbackInfo ci, @Local SpellInfo spellInfo)
    {
        SpellImpactEvents.AFTER.invoker().afterImpact(world, player, targets, spellInfo);
    }
}
