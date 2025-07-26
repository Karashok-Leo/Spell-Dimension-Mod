package karashokleo.spell_dimension.content.spell;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.content.buff.Electrocution;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_power.api.SpellSchools;

import java.util.Optional;

public class ElectrocutionSpell
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
        if (!spellContainer.spell_ids.contains(AllSpells.ELECTROCUTION.toString()))
        {
            return;
        }

        LivingEntity entity = event.getEntity();

        Optional<Electrocution> electrocution = Buff.get(entity, Electrocution.TYPE);
        if (electrocution.isPresent())
        {
            electrocution.get().incrementStacks();
            if (electrocution.get().shouldExecute())
            {
                Buff.remove(entity, Electrocution.TYPE);
                event.setAmount(event.getAmount() * SpellConfig.ELECTROCUTION_CONFIG.damageFactor());
            }
        } else
        {
            Buff.apply(entity, Electrocution.TYPE, new Electrocution(), player);
        }
    }
}
