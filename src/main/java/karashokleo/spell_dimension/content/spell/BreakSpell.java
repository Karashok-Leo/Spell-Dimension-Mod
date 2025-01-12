package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.spell_engine.entity.SpellProjectile;

public class BreakSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.BREAK)) return;
        projectile.getWorld().breakBlock(hitResult.getBlockPos(), true, projectile.getOwner());
    }
}
