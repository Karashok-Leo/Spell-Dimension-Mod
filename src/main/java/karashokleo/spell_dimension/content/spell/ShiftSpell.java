package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.spell_engine.entity.SpellProjectile;

public class ShiftSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, EntityHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.SHIFT)) return;
        Entity owner = projectile.getOwner();
        if (owner == null || owner.isRemoved() || owner.getWorld().isClient()) return;
        Entity entity = hitResult.getEntity();
        if (owner.getWorld() != entity.getWorld()) return;
        double x = owner.getX();
        double y = owner.getY();
        double z = owner.getZ();
        owner.teleport(entity.getX(), entity.getY(), entity.getZ());
        entity.teleport(x, y, z);
    }
}
