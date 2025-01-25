package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.entity.BlackHoleEntity;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.spell_engine.entity.SpellProjectile;

public class BlackHoleSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, HitResult hitResult)
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
        BlackHoleEntity blackHole = AllEntities.BLACK_HOLE.spawn(serverWorld, projectile.getBlockPos(), SpawnReason.CONVERSION);
        if (blackHole == null)
            return;
        blackHole.setOwner(player);
        blackHole.setRadius(4.0f);
        blackHole.setPosition(projectile.getPos().add(0, -4, 0));
    }
}
