package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SoulCommandSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }

        if (targets.isEmpty())
        {
            return;
        }

        var target = ImpactUtil.castToLiving(targets.get(0));

        if (!(target instanceof MobEntity mob))
        {
            return;
        }

        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);

        if (player.isSneaking())
        {
            minionComponent.attackMode = minionComponent.attackMode.next();
            player.sendMessage(SDTexts.TEXT$SOUL_COMMAND$ATTACK_MODE.get(SDTexts.getSoulMinionAttackModeText(minionComponent.attackMode).formatted(Formatting.BOLD)), true);
        } else
        {
            minionComponent.moveMode = minionComponent.moveMode.next();
            player.sendMessage(SDTexts.TEXT$SOUL_COMMAND$MOVE_MODE.get(SDTexts.getSoulMinionMoveModeText(minionComponent.moveMode).formatted(Formatting.BOLD)), true);
        }
    }
}
