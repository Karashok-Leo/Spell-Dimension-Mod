package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import org.jetbrains.annotations.Nullable;

public class PlaceSpell
{
    public static void handle(SpellProjectile projectile, SpellInfo spellInfo, @Nullable Entity owner, HitResult hitResult)
    {
        if (!(projectile.getWorld() instanceof ServerWorld world))
        {
            return;
        }
        if (!(projectile.getOwner() instanceof PlayerEntity player))
        {
            return;
        }
        if (!(hitResult instanceof BlockHitResult blockHitResult))
        {
            return;
        }
        if (AllWorldGen.disableInWorld(world))
        {
            player.sendMessage(SDTexts.TEXT$BANNED_SPELL.get().formatted(Formatting.RED));
            return;
        }
        player.getOffHandStack().useOnBlock(
            new ItemUsageContext(player, Hand.OFF_HAND, blockHitResult)
        );
    }
}
