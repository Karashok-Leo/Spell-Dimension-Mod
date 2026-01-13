package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;

@Mixin(SpellHelper.class)
public abstract class SpellHelperMixin
{
    @Inject(
        method = "attemptCasting(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Identifier;Z)Lnet/spell_engine/internals/casting/SpellCast$Attempt;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void inject_attemptCasting(PlayerEntity player, ItemStack itemStack, Identifier spellId, boolean checkAmmo, CallbackInfoReturnable<SpellCast.Attempt> cir)
    {
        if (AllSpells.isPassive(spellId))
        {
            cir.setReturnValue(SpellCast.Attempt.none());
        }
    }

    // fix context position being null
    @ModifyArg(
        method = "directImpact",
        at = @At(value = "INVOKE", target = "Lnet/spell_engine/internals/SpellHelper;performImpacts(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/spell_engine/api/spell/SpellInfo;Lnet/spell_engine/internals/SpellHelper$ImpactContext;)Z"
        ),
        index = 5
    )
    private static SpellHelper.ImpactContext inject_directImpact(SpellHelper.ImpactContext context, @Local(argsOnly = true) Entity target)
    {
        return context.position(target.getPos());
    }

    @Inject(
        method = "performSpell",
        at = @At(
            value = "INVOKE",
            target = "Lnet/spell_engine/internals/SpellHelper$ImpactContext;<init>(FFLnet/minecraft/util/math/Vec3d;Lnet/spell_power/api/SpellPower$Result;Lnet/spell_engine/utils/TargetHelper$TargetingMode;)V"
        )
    )
    private static void inject_performSpell(World world, PlayerEntity player, Identifier spellId, List<Entity> targets, SpellCast.Action action, float progress, CallbackInfo ci, @Local SpellInfo spellInfo)
    {
        SpellImpactEvents.PRE.invoker().invoke(world, player, targets, spellInfo);
    }

    @WrapMethod(
        method = "performImpact"
    )
    private static boolean wrap_performImpact(World world, LivingEntity caster, Entity target, SpellInfo spellInfo, Spell.Impact impact, SpellHelper.ImpactContext context, Collection<ServerPlayerEntity> trackers, Operation<Boolean> original)
    {
        boolean success = original.call(world, caster, target, spellInfo, impact, context, trackers);
        if (success)
        {
            SpellImpactEvents.POST.invoker().invoke(world, caster, List.of(target), spellInfo);
        }
        return success;
    }

    @Inject(
        method = "attemptCasting(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Identifier;Z)Lnet/spell_engine/internals/casting/SpellCast$Attempt;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/spell_engine/internals/SpellRegistry;getSpell(Lnet/minecraft/util/Identifier;)Lnet/spell_engine/api/spell/Spell;",
            shift = At.Shift.BY,
            by = 2
        ),
        cancellable = true
    )
    private static void inject_attemptCasting(PlayerEntity player, ItemStack itemStack, Identifier spellId, boolean checkAmmo, CallbackInfoReturnable<SpellCast.Attempt> cir, @Local Spell spell)
    {
        if (player.getAbilities().creativeMode)
        {
            return;
        }
        if (spell.school == AllSpells.GENERIC)
        {
            return;
        }
        if (!itemStack.isOf(AllItems.SPELL_SCROLL))
        {
            return;
        }
        if (SchoolUtil.getLivingSchools(player).contains(spell.school))
        {
            return;
        }
        if (SchoolUtil.getLivingSecondarySchools(player).contains(spell.school))
        {
            return;
        }
        player.sendMessage(SDTexts.TEXT$SKILLED_SCHOOL.get(), true);
        cir.setReturnValue(SpellCast.Attempt.none());
    }

    @Redirect(
        method = "performImpact",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z"
        )
    )
    private static boolean inject_performImpact(LivingEntity target, StatusEffectInstance effect, Entity caster)
    {
        MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(target, effect);
        event.sendEvent();
        if (event.getResult() == MobEffectEvent.Result.DENY)
        {
            return false;
        } else
        {
            EffectHelper.forceAddEffectWithEvent(target, effect, caster);
            return true;
        }
    }

    @Redirect(
        method = "intent",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/effect/StatusEffect;isBeneficial()Z"
        )
    )
    private static boolean wrap_intent(StatusEffect instance)
    {
        return instance.getCategory() != StatusEffectCategory.HARMFUL;
    }
}
