package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.spell_engine.entity.SpellProjectile;

public class PlaceSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.PLACE)) return;
        if (!(projectile.getOwner() instanceof PlayerEntity player)) return;
        player.getOffHandStack().useOnBlock(
                new ItemUsageContext(player, Hand.OFF_HAND, hitResult)
        );
    }
}
