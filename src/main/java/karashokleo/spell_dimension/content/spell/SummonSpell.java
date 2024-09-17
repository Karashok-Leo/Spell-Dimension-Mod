package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import net.spell_engine.entity.SpellProjectile;

import java.util.HashMap;
import java.util.Map;

public class SummonSpell
{
    private static final Map<Item, EntityType<?>> CONFIG = new HashMap<>();

    public static final Identifier SPELL_ID = SpellDimension.modLoc("summon");

    static
    {
        register(Items.GUNPOWDER, EntityType.CREEPER);
    }

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
            EntityType<?> entityType = getEntityType(itemStack);
            if (entityType == null) return;

            mobSpawnerBlockEntity.setEntityType(entityType, world.getRandom());
            BlockPos pos = mobSpawnerBlockEntity.getPos();
//            mobSpawnerBlockEntity.getLogic().setEntityId(id, world.getRandom(), pos);

            mobSpawnerBlockEntity.markDirty();
            world.updateListeners(blockPos, blockState, blockState, 3);
            world.emitGameEvent(living, GameEvent.BLOCK_CHANGE, blockPos);

            if (!(living instanceof PlayerEntity player &&
                  player.getAbilities().creativeMode))
                itemStack.decrement(1);

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

    public static void register(Item item, EntityType<?> entityType)
    {
        CONFIG.put(item, entityType);
    }

    private static EntityType<?> getEntityType(ItemStack itemStack)
    {
        return CONFIG.get(itemStack.getItem());
    }
}
