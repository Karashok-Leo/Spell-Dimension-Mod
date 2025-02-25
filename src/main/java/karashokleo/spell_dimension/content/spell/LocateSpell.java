package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.entity.LocatePortalEntity;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.FutureTask;
import karashokleo.spell_dimension.util.TeleportUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.spell_engine.entity.SpellProjectile;

import java.util.Optional;

public class LocateSpell
{
    public static final double BREAK_CHANCE = 0.3;

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.LOCATE)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        if (!(projectile.getOwner() instanceof PlayerEntity player)) return;
        BlockPos castPos = hitResult.getBlockPos();
        if (!isLocateTargetBlock(world, castPos)) return;

        ItemStack offHandStack = player.getOffHandStack();

        Optional<LocateRecipe> recipe = world.getRecipeManager()
                .getFirstMatch(LocateRecipe.TYPE, player.getInventory(), world);

        if (recipe.isEmpty())
        {
            player.sendMessage(SDTexts.TEXT$INVALID_KEY_ITEM.get(), true);
            return;
        }

        LocateRecipe locateRecipe = recipe.get();

        Optional<BlockPos> located = locateRecipe.locate(world, castPos, player);
        if (located.isEmpty()) return;

        BlockPos locatedPos = located.get();
        FutureTask.submit(
                TeleportUtil.getTeleportPosFuture(world, locatedPos),
                optional -> spawnLocatePortal(world, optional.orElse(locatedPos), castPos)
        );

        if (player.getAbilities().creativeMode) return;
        offHandStack.decrement(1);
        if (player.getRandom().nextFloat() < BREAK_CHANCE)
            world.breakBlock(castPos, false);
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
