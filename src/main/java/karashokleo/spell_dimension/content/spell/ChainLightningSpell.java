package karashokleo.spell_dimension.content.spell;

import com.mojang.datafixers.util.Either;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.content.entity.ChainLightningEntity;
import karashokleo.spell_dimension.content.particle.ZapParticleOption;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
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
            emptyCast(world, caster);
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

    private static void emptyCast(World world, LivingEntity caster)
    {
        if (!(caster instanceof PlayerEntity player))
        {
            return;
        }
        SpellContainer spellContainer = SpellContainerHelper.getEquipped(player.getMainHandStack(), player);
        if (spellContainer == null)
        {
            return;
        }
        List<String> spells = spellContainer.spell_ids;
        if (!spells.contains(AllSpells.STORMFLASH.toString()))
        {
            return;
        }

        int power = 1;

        if (spells.contains(AllSpells.SURGE.toString()))
        {
            power *= SpellConfig.POWER_PASSIVE_CONFIG.surge();
        }
        if (spells.contains(AllSpells.ARCLIGHT.toString()))
        {
            power *= SpellConfig.POWER_PASSIVE_CONFIG.arclight();
        }

        Vec3d vector = caster.getRotationVector();
        caster.setVelocity(vector.x * power, vector.y * power, vector.z * power);
        caster.velocityModified = true;
        caster.fallDistance = 0;

        if (world instanceof ServerWorld serverWorld)
        {
            Vec3d length = vector.multiply(power * 5);
            Vec3d pos = caster.getPos();
            Vec3d from = pos.subtract(length);
            Vec3d to = pos.add(length);

            serverWorld.spawnParticles(new ZapParticleOption(Either.right(from), Either.left(caster.getId()), power), pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0, 0, 0);
            serverWorld.spawnParticles(new ZapParticleOption(Either.left(caster.getId()), Either.right(to), power), pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0, 0, 0);
        }
    }

    private static void applyPassives(List<String> spells, ChainLightningEntity chainLightning)
    {
        if (spells.contains(AllSpells.SURGE.toString()))
        {
            chainLightning.power *= SpellConfig.POWER_PASSIVE_CONFIG.surge();
        }
        if (spells.contains(AllSpells.ARCLIGHT.toString()))
        {
            chainLightning.power *= SpellConfig.POWER_PASSIVE_CONFIG.arclight();
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
