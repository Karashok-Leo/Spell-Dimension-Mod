package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.entity.BlackHoleEntity;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
        spawn(serverWorld, player, projectile.getPos());
    }

    public static void spawn(World world, LivingEntity caster, Vec3d pos)
    {
        double power = SpellPower.getSpellPower(SpellSchools.ARCANE, caster).randomValue();
        double radius = MathHelper.clamp(power * 0.02, MIN_RADIUS, MAX_RADIUS);
        BlackHoleEntity blackHole = new BlackHoleEntity(world, caster, (float) radius);
        blackHole.setPosition(pos.add(0, -radius, 0));
        world.spawnEntity(blackHole);
    }
}
