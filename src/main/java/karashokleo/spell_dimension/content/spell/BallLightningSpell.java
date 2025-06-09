package karashokleo.spell_dimension.content.spell;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.content.entity.BallLightningEntity;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class BallLightningSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
//        Optional<Entity> target = targets.stream().findFirst();
//        if (target.isEmpty()) return;
//        if (!(target.get() instanceof LivingEntity living)) return;

        BallLightningEntity ballLightning = new BallLightningEntity(world, caster);
        if (TrinketCompat.hasItemInTrinket(caster, AllItems.MACRO_ELECTRON))
        {
            ballLightning.macro = true;
        }
        ballLightning.setPosition(caster.getPos().add(0, caster.getStandingEyeHeight() - ballLightning.getBoundingBox().getYLength() * 0.5f, 0));
        ballLightning.setVelocity(caster.getRotationVector().multiply(0.6f));
        world.spawnEntity(ballLightning);
    }
}
