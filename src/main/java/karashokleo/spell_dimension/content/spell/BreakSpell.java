package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import org.jetbrains.annotations.Nullable;

public class BreakSpell
{
    public static void handle(SpellProjectile projectile, SpellInfo spellInfo, @Nullable Entity owner, HitResult hitResult)
    {
        if (!(projectile.getWorld() instanceof ServerWorld world))
        {
            return;
        }
        if (owner == null)
        {
            return;
        }
        if (!(hitResult instanceof BlockHitResult blockHitResult))
        {
            return;
        }
        if (AllWorldGen.disableInWorld(world))
        {
            owner.sendMessage(SDTexts.TEXT$BANNED_SPELL.get().formatted(Formatting.RED));
            return;
        }

        BlockPos blockPos = blockHitResult.getBlockPos();
        if (owner instanceof LivingEntity living)
        {
            ItemStack offHandStack = living.getOffHandStack();
            if (offHandStack.isOf(AllItems.SPELL_PRISM))
            {
                offHandStack.damage(1, living, e -> e.sendToolBreakStatus(Hand.OFF_HAND));

                Item blockItem = world.getBlockState(blockPos).getBlock().asItem();
                Block.dropStack(world, blockPos, blockItem.getDefaultStack());
                world.breakBlock(blockPos, false, owner);
                return;
            }
        }

        world.breakBlock(blockPos, true, owner);
    }
}
