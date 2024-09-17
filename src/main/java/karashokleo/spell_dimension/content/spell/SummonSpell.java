package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.SummonSpellConfig;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import net.spell_engine.entity.SpellProjectile;

public class SummonSpell
{
    public static final Identifier SPELL_ID = SpellDimension.modLoc("summon");

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(SPELL_ID)) return;
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

            BlockPos pos = mobSpawnerBlockEntity.getPos();
            world.spawnParticles(
                    ParticleTypes.END_ROD,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    16,
                    0.01,
                    0.01,
                    0.01,
                    0.06
            );
        }
    }

    private static void setSummonData(MobSpawnerBlockEntity spawnerBlockEntity, SummonSpellConfig.Entry entry, Random random)
    {
        spawnerBlockEntity.setEntityType(entry.entityType(), random);
        ((ISpawnerExtension) spawnerBlockEntity.getLogic()).setRemain(entry.count());
    }
}
