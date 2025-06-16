package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.content.entity.ChainLightningEntity;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellContainerHelper;

import java.util.List;
import java.util.Optional;

public class ChainLightningSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        Optional<Entity> target = targets.stream().findFirst();
        if (target.isEmpty())
        {
            return;
        }
        LivingEntity living = ImpactUtil.castToLiving(target.get());
        if (living == null)
        {
            return;
        }
        if (!living.isAttackable())
        {
            return;
        }

        ChainLightningEntity chainLightning = new ChainLightningEntity(world, caster, living);
        if (caster instanceof PlayerEntity player)
        {
            SpellContainer spellContainer = SpellContainerHelper.getEquipped(player.getMainHandStack(), player);
            if (spellContainer != null)
            {
                applyPassives(spellContainer.spell_ids, chainLightning);
            }
        }
        world.spawnEntity(chainLightning);
    }

    private static void applyPassives(List<String> spells, ChainLightningEntity chainLightning)
    {
        if (spells.contains(AllSpells.SURGE.toString()))
        {
            chainLightning.power += SpellConfig.CHAIN_LIGHTNING_PASSIVE_CONFIG.surgePower();
        }
        if (spells.contains(AllSpells.ARCLIGHT.toString()))
        {
            chainLightning.power += SpellConfig.CHAIN_LIGHTNING_PASSIVE_CONFIG.arclightPower();
        }
        if (spells.contains(AllSpells.STEADY_CURRENT.toString()))
        {
            chainLightning.lifespan *= SpellConfig.CHAIN_LIGHTNING_PASSIVE_CONFIG.steadyCurrentLifespan();
        }
        if (spells.contains(AllSpells.CONSTANT_CURRENT.toString()))
        {
            chainLightning.lifespan *= SpellConfig.CHAIN_LIGHTNING_PASSIVE_CONFIG.constantCurrentLifespan();
        }
        if (spells.contains(AllSpells.FISSION.toString()))
        {
            chainLightning.chainStep += SpellConfig.CHAIN_LIGHTNING_PASSIVE_CONFIG.fissionChainStep();
        }
        if (spells.contains(AllSpells.RESONANCE.toString()))
        {
            chainLightning.range += SpellConfig.CHAIN_LIGHTNING_PASSIVE_CONFIG.resonanceRange();
        }
        if (spells.contains(AllSpells.BREAKDOWN.toString()))
        {
            chainLightning.canPenetrate = true;
        }
    }
}
