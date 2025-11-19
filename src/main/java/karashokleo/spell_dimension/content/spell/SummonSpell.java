package karashokleo.spell_dimension.content.spell;

import com.kyanite.deeperdarker.content.DDEntities;
import karashokleo.spell_dimension.content.misc.SpawnerExtension;
import karashokleo.spell_dimension.content.object.SummonEntry;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.ParticleUtil;
import karashokleo.spell_dimension.util.RandomUtil;
import net.adventurez.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.spell_engine.entity.SpellProjectile;

import java.util.HashMap;
import java.util.Optional;

public class SummonSpell
{
    public static Optional<SummonEntry> getSummonEntry(ServerWorld world, PlayerEntity player)
    {
        ItemStack stack = player.getOffHandStack();

        // if spawner soul, summon the entity in the soul
        if (stack.isOf(AllItems.SPAWNER_SOUL))
            return AllItems.SPAWNER_SOUL.getSummonEntry(stack);

        SummonRecipe summonRecipe;
        // if spell prism, summon a random entity
        if (stack.isOf(AllItems.SPELL_PRISM))
        {
            summonRecipe = RandomUtil.randomFromList(
                    player.getRandom(),
                    world.getRecipeManager().listAllOfType(SummonRecipe.TYPE)
            );
        } else
        {
            Optional<SummonRecipe> optional = world.getRecipeManager().getFirstMatch(SummonRecipe.TYPE, player.getInventory(), world);
            if (optional.isEmpty()) return Optional.empty();

            summonRecipe = optional.get();
        }

        return Optional.of(new SummonEntry(summonRecipe.entityType(), summonRecipe.count()));
    }

    public static void handle(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult)
    {
        if (!spellId.equals(AllSpells.SUMMON)) return;
        if (!(projectile.getWorld() instanceof ServerWorld world)) return;
        if (!(projectile.getOwner() instanceof PlayerEntity player)) return;
        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (!blockState.isOf(Blocks.SPAWNER) ||
            !(world.getBlockEntity(blockPos) instanceof MobSpawnerBlockEntity mobSpawnerBlockEntity)) return;

        Optional<SummonEntry> optional = getSummonEntry(world, player);
        if (optional.isEmpty()) return;
        if (!allowSummon(mobSpawnerBlockEntity, optional.get(), player)) return;
        setSummonData(mobSpawnerBlockEntity, optional.get(), world.getRandom());

        mobSpawnerBlockEntity.markDirty();
        world.updateListeners(blockPos, blockState, blockState, 3);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);

        ParticleUtil.sparkParticleEmit(world, mobSpawnerBlockEntity.getPos().toCenterPos(), 24);

        if (player.getAbilities().creativeMode) return;

        ItemStack offHandStack = player.getOffHandStack();
        if (offHandStack.isOf(AllItems.SPAWNER_SOUL))
        {
            offHandStack.damage(1, player, e -> e.sendToolBreakStatus(Hand.OFF_HAND));
        } else
        {
            offHandStack.decrement(1);
        }
    }

    private static void setSummonData(MobSpawnerBlockEntity spawnerBlockEntity, SummonEntry summonEntry, Random random)
    {
        spawnerBlockEntity.setEntityType(summonEntry.entityType(), random);
        ((SpawnerExtension) spawnerBlockEntity.getLogic()).setRemain(summonEntry.count());
    }

    private static final HashMap<EntityType<?>, Identifier> WORLD_RESTRICTIONS = new HashMap<>();

    static
    {
        WORLD_RESTRICTIONS.put(EntityInit.VOID_SHADOW, new Identifier("voidz:void"));
        WORLD_RESTRICTIONS.put(DDEntities.STALKER, new Identifier("deeperdarker:otherside"));
    }

    private static boolean allowSummon(MobSpawnerBlockEntity spawnerBlockEntity, SummonEntry summonEntry, PlayerEntity player)
    {
        World world = spawnerBlockEntity.getWorld();
        if (world == null) return false;
        Identifier worldId = world.getRegistryKey().getValue();
        EntityType<?> entityType = summonEntry.entityType();
        Identifier requireId = WORLD_RESTRICTIONS.get(entityType);
        if (requireId != null && !worldId.equals(requireId))
        {
            player.sendMessage(SDTexts.TEXT$SUMMON$DISALLOW.get(entityType.getName()).formatted(Formatting.RED), false);
            return false;
        }
        return true;
    }
}
