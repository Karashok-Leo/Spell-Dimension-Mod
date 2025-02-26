package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import karashokleo.spell_dimension.content.object.SummonEntry;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.ParticleUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import net.spell_engine.entity.SpellProjectile;

import java.util.Optional;

public class SummonSpell
{
    public static Optional<SummonEntry> getSummonEntry(ServerWorld world, PlayerEntity player)
    {
        ItemStack stack = player.getOffHandStack();
        if (stack.isOf(AllItems.SPAWNER_SOUL))
            return AllItems.SPAWNER_SOUL.getSummonEntry(stack);

        Optional<SummonRecipe> optional = world.getRecipeManager().getFirstMatch(SummonRecipe.TYPE, player.getInventory(), world);
        if (optional.isEmpty()) return Optional.empty();
        SummonRecipe summonRecipe = optional.get();
        return Optional.of(new SummonEntry(summonRecipe.entityType(), summonRecipe.count()));
    }

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.SUMMON)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        if (!(projectile.getOwner() instanceof PlayerEntity player)) return;
        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SPAWNER) &&
            world.getBlockEntity(blockPos) instanceof MobSpawnerBlockEntity mobSpawnerBlockEntity)
        {
            Optional<SummonEntry> optional = getSummonEntry(world, player);
            if (optional.isEmpty()) return;
            setSummonData(mobSpawnerBlockEntity, optional.get(), world.getRandom());

            mobSpawnerBlockEntity.markDirty();
            world.updateListeners(blockPos, blockState, blockState, 3);
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);

            if (!player.getAbilities().creativeMode)
                player.getOffHandStack().decrement(1);

            ParticleUtil.sparkParticleEmit(world, mobSpawnerBlockEntity.getPos().toCenterPos(), 24);
        }
    }

    private static void setSummonData(MobSpawnerBlockEntity spawnerBlockEntity, SummonEntry summonEntry, Random random)
    {
        spawnerBlockEntity.setEntityType(summonEntry.entityType(), random);
        ((ISpawnerExtension) spawnerBlockEntity.getLogic()).setRemain(summonEntry.count());
    }
}
