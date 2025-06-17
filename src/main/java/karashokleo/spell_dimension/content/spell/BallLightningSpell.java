package karashokleo.spell_dimension.content.spell;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.content.entity.BallLightningEntity;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellContainerHelper;

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
        if (caster instanceof PlayerEntity player)
        {
            SpellContainer spellContainer = SpellContainerHelper.getEquipped(player.getMainHandStack(), player);
            if (spellContainer != null)
            {
                applyPassives(spellContainer.spell_ids, ballLightning);
            }
        }
        ballLightning.setPosition(caster.getPos().add(0, caster.getStandingEyeHeight() - ballLightning.getBoundingBox().getYLength() * 0.5f, 0));
        ballLightning.setVelocity(caster.getRotationVector().multiply(0.7f));
        world.spawnEntity(ballLightning);
    }

    private static void applyPassives(List<String> spells, BallLightningEntity ballLightning)
    {
        if (spells.contains(AllSpells.SURGE.toString()))
        {
            ballLightning.power *= SpellConfig.POWER_PASSIVE_CONFIG.surge();
        }
        if (spells.contains(AllSpells.ARCLIGHT.toString()))
        {
            ballLightning.power *= SpellConfig.POWER_PASSIVE_CONFIG.arclight();
        }
    }
}
