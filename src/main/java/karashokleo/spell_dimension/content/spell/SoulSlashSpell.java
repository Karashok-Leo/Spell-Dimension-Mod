package karashokleo.spell_dimension.content.spell;

import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class SoulSlashSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!spellInfo.id().equals(AllSpells.SOUL_SLASH))
        {
            return;
        }

        if (targets.isEmpty())
        {
            return;
        }

        if (!(caster instanceof PlayerEntity player))
        {
            return;
        }

        int totalLevel = DifficultyLevel.ofAny(player);
        int count = 1;
        SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
        for (MobEntity minion : controllerComponent.getActiveMinions())
        {
            totalLevel += DifficultyLevel.ofAny(minion);
            count++;
        }
        float extraDamage = Math.max(0, (float) totalLevel / (float) count);

        for (Entity target : targets)
        {
            if (!(target instanceof LivingEntity living))
            {
                continue;
            }
            DamageUtil.spellDamage(living, SpellSchools.SOUL, player, extraDamage, false);
        }
    }
}
