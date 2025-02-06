package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.spell_engine.entity.SpellProjectile;

public class BreakSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.BREAK)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        Entity owner = projectile.getOwner();
        if (owner == null) return;
        if (AllSpells.disableInWorld(world))
        {
            owner.sendMessage(SDTexts.TEXT$BANNED_SPELL.get().formatted(Formatting.RED));
            return;
        }
        world.breakBlock(hitResult.getBlockPos(), true, owner);
    }
}
