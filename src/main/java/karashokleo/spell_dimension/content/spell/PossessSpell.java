package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.ImpactUtil;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class PossessSpell
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

        if (target instanceof MobEntity mob)
        {
            if (SoulControl.isSoulMinion(player, mob))
            {
                SoulControl.setControllingMinion(player, mob);
            }
        } else if (target instanceof FakePlayerEntity)
        {
            SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
            if (controllerComponent.getFakePlayerSelf() == target)
            {
                SoulControl.setControllingMinion(player, null);
            }
        } else if (target instanceof PlayerEntity &&
            RelationUtil.isPartner(caster, target))
        {
            ItemStack offHandStack = caster.getOffHandStack();
            if (offHandStack.isOf(AllItems.SPELL_PRISM))
            {
                offHandStack.damage(1, player, e -> e.sendToolBreakStatus(Hand.OFF_HAND));
                caster.startRiding(target, true);
            }
        }
    }
}
