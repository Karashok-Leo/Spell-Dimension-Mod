package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.entity.BlackHoleEntity;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.spell_engine.entity.SpellProjectile;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import static karashokleo.spell_dimension.content.entity.BlackHoleEntity.MAX_RADIUS;
import static karashokleo.spell_dimension.content.entity.BlackHoleEntity.MIN_RADIUS;

public class BlackHoleSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId)
    {
        if (spellId.equals(AllSpells.BLACK_HOLE))
            blackHoleImpact(projectile);
    }

    public static void blackHoleImpact(SpellProjectile projectile)
    {
        Entity owner = projectile.getOwner();
        if (owner == null ||
            owner.isRemoved() ||
            (!(owner instanceof PlayerEntity player)))
            return;
        if (!(projectile.getWorld() instanceof ServerWorld serverWorld))
            return;
        BlackHoleEntity blackHole = AllEntities.BLACK_HOLE.create(
                serverWorld,
                null,
                null,
                projectile.getBlockPos(),
                SpawnReason.CONVERSION,
                false,
                false
        );
        if (blackHole == null)
            return;
        double power = SpellPower.getSpellPower(SpellSchools.ARCANE, player).randomValue();
        double radius = MathHelper.clamp(power * 0.02, MIN_RADIUS, MAX_RADIUS);
        blackHole.setOwner(player);
        blackHole.setRadius((float) radius);
        blackHole.setPosition(projectile.getPos().add(0, -radius, 0));
        serverWorld.spawnEntity(blackHole);
    }
}
