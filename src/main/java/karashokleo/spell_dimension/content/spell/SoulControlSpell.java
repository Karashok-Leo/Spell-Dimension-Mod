package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SoulControlSpell
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
        minionComponent.mode = minionComponent.mode.next();
        player.sendMessage(Text.literal(minionComponent.mode.name()));
    }
}
