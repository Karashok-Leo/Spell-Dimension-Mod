package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.spell_engine.entity.SpellProjectile;

public class BreakSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.BREAK)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        Entity owner = projectile.getOwner();
        if (owner == null) return;
        if (AllWorldGen.disableInWorld(world))
        {
            owner.sendMessage(SDTexts.TEXT$BANNED_SPELL.get().formatted(Formatting.RED));
            return;
        }

        BlockPos blockPos = hitResult.getBlockPos();
        if (owner instanceof LivingEntity living &&
            living.getOffHandStack().isOf(AllItems.SPELL_PRISM))
        {
            Item blockItem = world.getBlockState(blockPos).getBlock().asItem();
            Block.dropStack(world, blockPos, blockItem.getDefaultStack());
            world.breakBlock(blockPos, false, owner);
        } else
        {
            world.breakBlock(blockPos, true, owner);
        }
    }
}
