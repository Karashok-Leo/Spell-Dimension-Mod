package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.entity.ChainLightningEntity;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;
import java.util.Optional;

public class ChainLightningSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!spellInfo.id().equals(AllSpells.CHAIN_LIGHTNING)) return;
        Optional<Entity> target = targets.stream().findFirst();
        if (target.isEmpty()) return;
        if (!(target.get() instanceof LivingEntity living)) return;
        if (!living.isAttackable()) return;

        ChainLightningEntity chainLightning = new ChainLightningEntity(world, caster, living);
        chainLightning.lifespan = 300;
        chainLightning.maxChains = 100;
        chainLightning.maxChainStep = 3;
        chainLightning.range = 20;
        chainLightning.maxRange = 100;
        chainLightning.canPenetrate = false;
        world.spawnEntity(chainLightning);
    }
}
