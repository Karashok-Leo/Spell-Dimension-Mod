package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.spell_engine.entity.SpellProjectile;

public class PlaceSpell
{
    public static final Identifier SPELL_ID = SpellDimension.modLoc("place");

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(SPELL_ID)) return;
        if (!(projectile.getOwner() instanceof PlayerEntity player)) return;
        player.getOffHandStack().useOnBlock(
                new ItemUsageContext(player, Hand.OFF_HAND, hitResult)
        );
    }
}
