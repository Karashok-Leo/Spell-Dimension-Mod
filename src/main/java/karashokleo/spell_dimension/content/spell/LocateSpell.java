package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.entity.LocatePortalEntity;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.FutureTask;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.TeleportUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class LocateSpell
{
    public static final double BREAK_CHANCE = 0.3;

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
        BlockPos castPos = blockHitResult.getBlockPos();
        if (!isLocateTargetBlock(world, castPos))
        {
            return;
        }

        ItemStack offHandStack = player.getOffHandStack();
        LocateRecipe locateRecipe;

        // if spell prism, locate a random spot
        if (offHandStack.isOf(AllItems.SPELL_PRISM))
        {
            List<LocateRecipe> list = world.getRecipeManager()
                .listAllOfType(LocateRecipe.TYPE)
                .stream()
                .filter(recipe -> world.getRegistryKey().getValue().equals(recipe.getWorldId()))
                .toList();
            if (list.isEmpty())
            {
                // no locate recipes for this dimension
                return;
            }
            locateRecipe = RandomUtil.randomFromList(
                player.getRandom(),
                list
            );
        } else
        {
            Optional<LocateRecipe> recipe = world.getRecipeManager()
                .getFirstMatch(LocateRecipe.TYPE, player.getInventory(), world);

            if (recipe.isEmpty())
            {
                player.sendMessage(SDTexts.TEXT$INVALID_KEY_ITEM.get(), true);
                return;
            }

            locateRecipe = recipe.get();
        }

        Optional<BlockPos> located = locateRecipe.locate(world, castPos, player);
        if (located.isEmpty())
        {
            return;
        }

        BlockPos locatedPos = located.get();
        FutureTask.submit(
            TeleportUtil.getTeleportPosFuture(world, locatedPos),
            optional -> spawnLocatePortal(world, optional.orElse(locatedPos), castPos)
        );

        if (player.getAbilities().creativeMode)
        {
            return;
        }

        if (offHandStack.isOf(AllItems.SPELL_PRISM))
        {
            offHandStack.damage(1, player, e -> e.sendToolBreakStatus(Hand.OFF_HAND));
        } else
        {
            offHandStack.decrement(1);
            if (player.getRandom().nextFloat() < BREAK_CHANCE)
            {
                world.breakBlock(castPos, false);
            }
        }
    }

    public static void spawnLocatePortal(ServerWorld world, BlockPos destination, BlockPos pos)
    {
        world.spawnParticles(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() - 0.5 + 3, pos.getZ() + 0.5, 1000, 0, 0, 0, 2);
        LocatePortalEntity locatePortalEntity = new LocatePortalEntity(world, destination);
        locatePortalEntity.setPosition(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5);
        world.spawnEntity(locatePortalEntity);
    }

    private static boolean isLocateTargetBlock(World world, BlockPos pos)
    {
        return world.getBlockState(pos).isIn(AllTags.LOCATE_TARGET);
    }
}
