package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class SoulContainer extends Item
{
    public static final String ENTITY_KEY = "Entity";

    public SoulContainer()
    {
        super(
            new FabricItemSettings()
                .fireproof()
                .maxCount(1)
        );
    }

    public boolean tryCaptureEntity(ItemStack stack, PlayerEntity player, MobEntity mob)
    {
        if (player.getWorld().isClient())
        {
            throw new IllegalStateException("tryCaptureEntity should be called on server side only");
        }

        NbtCompound nbt = stack.getSubNbt(ENTITY_KEY);
        if (nbt != null && !nbt.isEmpty())
        {
            return false;
        }

        // fully heal
        mob.setHealth(mob.getMaxHealth());
        // set owner
        SoulMinionComponent component = SoulControl.getSoulMinion(mob);
        if (component != null)
        {
            component.setOwner(player);
        }
        // do saving
        var entityNbt = SoulControl.saveMinionData(mob);
        stack.setSubNbt(ENTITY_KEY, entityNbt);
        mob.discard();
        return true;
    }

    protected boolean trySpawnEntity(ItemStack stack, ServerWorld world, BlockPos pos, PlayerEntity user)
    {
        NbtCompound nbt = stack.getSubNbt(ENTITY_KEY);
        if (nbt == null || nbt.isEmpty())
        {
            return false;
        }

        MobEntity mob = SoulControl.loadMinionFromData(nbt, world);
        // TODO: pos
        mob.setPosition(pos.toCenterPos());
        world.spawnEntity(mob);

        stack.removeSubNbt(ENTITY_KEY);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        world.emitGameEvent(user, GameEvent.ENTITY_PLACE, pos);
        return true;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        if (!(context.getWorld() instanceof ServerWorld serverWorld))
        {
            return ActionResult.SUCCESS;
        }

        ItemStack itemStack = context.getStack();
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockState blockState = serverWorld.getBlockState(blockPos);

        BlockPos offsetPos;
        if (blockState.getCollisionShape(serverWorld, blockPos).isEmpty())
        {
            offsetPos = blockPos;
        } else
        {
            offsetPos = blockPos.offset(direction);
        }

        if (trySpawnEntity(itemStack, serverWorld, offsetPos, context.getPlayer()))
        {
            return ActionResult.CONSUME;
        }

        return ActionResult.FAIL;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK)
        {
            return TypedActionResult.pass(itemStack);
        }
        if (!(world instanceof ServerWorld serverWorld))
        {
            return TypedActionResult.success(itemStack);
        }
        BlockPos blockPos = hitResult.getBlockPos();
        if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock))
        {
            return TypedActionResult.pass(itemStack);
        }
        if (world.canPlayerModifyAt(user, blockPos) &&
            user.canPlaceOn(blockPos, hitResult.getSide(), itemStack) &&
            trySpawnEntity(itemStack, serverWorld, blockPos, user))
        {
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        NbtCompound nbt = stack.getSubNbt(ENTITY_KEY);
        if (nbt == null || nbt.isEmpty())
        {
            tooltip.add(SDTexts.TOOLTIP$CONTAINER_EMPTY.get().formatted(Formatting.DARK_AQUA));
            return;
        }
        Optional<EntityType<?>> entityType = EntityType.fromNbt(nbt);
        if (entityType.isEmpty())
        {
            tooltip.add(SDTexts.TOOLTIP$CONTAINER_EMPTY.get().formatted(Formatting.DARK_AQUA));
            return;
        }
        float health = nbt.getFloat("Health");

        tooltip.add(SDTexts.TOOLTIP$SOUL_TYPE.get(entityType.get().getName()).formatted(Formatting.DARK_AQUA));
        tooltip.add(SDTexts.TOOLTIP$REMAINING_HEALTH.get("%.1f".formatted(health)).formatted(Formatting.DARK_AQUA));
    }
}
