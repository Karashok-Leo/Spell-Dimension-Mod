package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.entity.BlackHoleEntity;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.entity.SpellProjectile;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import static karashokleo.spell_dimension.content.entity.BlackHoleEntity.MAX_RADIUS;
import static karashokleo.spell_dimension.content.entity.BlackHoleEntity.MIN_RADIUS;

public class BlackHoleSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId)
    {
        handle(projectile, spellId, null);
    }

    public static void handle(SpellProjectile projectile, Identifier spellId, @Nullable HitResult hitResult)
    {
        if (!spellId.equals(AllSpells.BLACK_HOLE)) return;
        handle(projectile, hitResult);
    }

    public static float getRadius(LivingEntity caster)
    {
        double power = SpellPower.getSpellPower(SpellSchools.ARCANE, caster).randomValue();
        return (float) MathHelper.clamp(power * 0.02, MIN_RADIUS, MAX_RADIUS);
    }

    public static <T extends Entity & Ownable> void handle(T entity, @Nullable HitResult hitResult)
    {
        Entity owner = entity.getOwner();
        if (owner == null ||
            owner.isRemoved() ||
            (!(owner instanceof LivingEntity caster)))
            return;
        if (!(entity.getWorld() instanceof ServerWorld serverWorld))
            return;

        float radius = getRadius(caster);

        Vec3d center = hitResult == null ?
                entity.getPos() :
                adjustCenterPosition(hitResult, radius / 4);

        spawn(serverWorld, caster, center, radius);
    }

    public static void spawn(World world, LivingEntity caster, Vec3d center, float radius)
    {
//        level.playSound(null, center.x, center.y, center.z, SoundRegistry.BLACK_HOLE_CAST.get(), SoundSource.AMBIENT, 4, 1);

        BlackHoleEntity blackHole = new BlackHoleEntity(world, caster, radius);
        blackHole.refreshPositionAfterTeleport(center);
        world.spawnEntity(blackHole);
    }

    private static Vec3d adjustCenterPosition(HitResult hitResult, float offset)
    {
        Vec3d center = hitResult.getPos();
        if (hitResult instanceof BlockHitResult blockHitResult)
        {
            Direction side = blockHitResult.getSide();
            center = center.offset(side, offset);
        }
        return center;
    }
}
