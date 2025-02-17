package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.spell_engine.entity.SpellProjectile;

public class PlaceSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.PLACE)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        if (!(projectile.getOwner() instanceof PlayerEntity player)) return;
        if (AllWorldGen.disableInWorld(world))
        {
            player.sendMessage(SDTexts.TEXT$BANNED_SPELL.get().formatted(Formatting.RED));
            return;
        }
        player.getOffHandStack().useOnBlock(
                new ItemUsageContext(player, Hand.OFF_HAND, hitResult)
        );
    }
}
