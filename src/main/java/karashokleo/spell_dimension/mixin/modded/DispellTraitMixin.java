package karashokleo.spell_dimension.mixin.modded;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.content.trait.legendary.DispellTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.spell_dimension.init.AllEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.IntSupplier;

@Mixin(DispellTrait.class)
public abstract class DispellTraitMixin extends MobTrait
{
    private DispellTraitMixin(IntSupplier color)
    {
        super(color);
    }

    // cancel the original logic
    @Inject(
            method = "onAttacked",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_onAttacked(MobDifficulty difficulty, LivingEntity entity, int level, LivingAttackEvent event, CallbackInfo ci)
    {
        ci.cancel();
    }

    // new logic with Spell Tearing enchantment
    @Override
    public void onDamaged(MobDifficulty difficulty, LivingEntity entity, int level, LivingDamageEvent event)
    {
        var data = difficulty.getOrCreateData(getId(), DispellTrait.Data::new);
        if (data.immuneCooldown > 0) return;

        DamageSource source = event.getSource();
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) ||
            source.isIn(DamageTypeTags.BYPASSES_EFFECTS) ||
            !source.isIn(LHTags.MAGIC))
            return;

        data.immuneCooldown = LHConfig.common().traits.dispellImmuneCooldown;
        if (source.getAttacker() instanceof LivingEntity attacker)
        {
            int tearingLv = EnchantmentHelper.getEquipmentLevel(AllEnchantments.SPELL_TEARING, attacker);
            float factor = MathHelper.clamp(tearingLv * 0.2f, 0f, 1f);
            event.setAmount(event.getAmount() * factor);
        } else
        {
            event.setCanceled(true);
        }
    }
}
