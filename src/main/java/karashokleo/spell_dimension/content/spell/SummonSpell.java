package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.config.recipe.SummonSpellConfig;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.ParticleUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import net.spell_engine.entity.SpellProjectile;

public class SummonSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.SUMMON)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        if (!(projectile.getOwner() instanceof LivingEntity living)) return;
        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SPAWNER) &&
            world.getBlockEntity(blockPos) instanceof MobSpawnerBlockEntity mobSpawnerBlockEntity)
        {
            ItemStack itemStack = living.getOffHandStack();
            SummonSpellConfig.Entry entry = SummonSpellConfig.getEntry(itemStack);
            if (entry == null) return;

            setSummonData(mobSpawnerBlockEntity, entry, world.getRandom());

            mobSpawnerBlockEntity.markDirty();
            world.updateListeners(blockPos, blockState, blockState, 3);
            world.emitGameEvent(living, GameEvent.BLOCK_CHANGE, blockPos);

            if (!(living instanceof PlayerEntity player &&
                  player.getAbilities().creativeMode))
                itemStack.decrement(1);

            ParticleUtil.sparkParticleEmit(world, mobSpawnerBlockEntity.getPos().toCenterPos(), 24);
        }
    }

    private static void setSummonData(MobSpawnerBlockEntity spawnerBlockEntity, SummonSpellConfig.Entry entry, Random random)
    {
        spawnerBlockEntity.setEntityType(entry.entityType(), random);
        ((ISpawnerExtension) spawnerBlockEntity.getLogic()).setRemain(entry.count());
    }
}
