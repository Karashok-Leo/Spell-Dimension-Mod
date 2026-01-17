package karashokleo.spell_dimension.content.spell;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

public class ElectricBondageSpell
{
    public static void onDamage(LivingDamageEvent event)
    {
        DamageSource source = event.getSource();
        if (!source.isOf(SpellSchools.LIGHTNING.damageType))
        {
            return;
        }
        if (!(ImpactUtil.castToLiving(source.getAttacker()) instanceof PlayerEntity player))
        {
            return;
        }
        SpellContainer spellContainer = SpellContainerHelper.getEquipped(player.getMainHandStack(), player);
        if (spellContainer == null)
        {
            return;
        }
        if (!spellContainer.spell_ids.contains(AllSpells.ELECTRIC_BONDAGE.toString()))
        {
            return;
        }
        double baseValue = SpellPower.getSpellPower(SpellSchools.LIGHTNING, player).baseValue();
        int level = (int) (baseValue / 100);
        if (level <= 0)
        {
            return;
        }
        int ticks = 2 * level;
        EffectHelper.forceAddEffectWithEvent(
            event.getEntity(),
            new StatusEffectInstance(AllStatusEffects.STUN, ticks, 0, false, false, true),
            player
        );
    }
}
