package karashokleo.spell_dimension.content.item;

import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SoulContainerItem extends Item
{
    public static final int RANGE = 8;
    public static final int COOLDOWN = 10;
    public static final String ENTITY_KEY = "Entity";
    public static final String TOOLTIP_DATA_KEY = "TooltipData";
    public static final String CUSTOM_NAME_KEY = "CustomName";
    public static final String ENTITY_TYPE_KEY = "EntityType";
    public static final String LEVEL_KEY = "Level";
    public static final String HEALTH_KEY = "Health";
    public static final String MAX_HEALTH_KEY = "MaxHealth";

    private final FloatUnaryOperator probabilityFunction;
    private final float healthThresholdRatio;
    private final int cooldownOnFail;

    public SoulContainerItem(FloatUnaryOperator probabilityFunction, float healthThresholdRatio, int cooldownOnFail)
    {
        super(
            new FabricItemSettings()
                .fireproof()
                .maxCount(1)
        );
        this.probabilityFunction = probabilityFunction;
        this.healthThresholdRatio = healthThresholdRatio;
        this.cooldownOnFail = cooldownOnFail;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (user.getItemCooldownManager().isCoolingDown(this))
        {
            return TypedActionResult.fail(stack);
        }
        // already stored
        if (hand != Hand.MAIN_HAND ||
            stack.getOrCreateNbt().contains(ENTITY_KEY, NbtElement.COMPOUND_TYPE))
        {
            return TypedActionResult.fail(stack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks)
    {
        if (world.isClient() && user instanceof PlayerEntity player)
        {
            RayTraceUtil.clientUpdateTarget(player, RANGE);
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        if (user instanceof ServerPlayerEntity player)
        {
            LivingEntity target = RayTraceUtil.serverGetTarget(player);
            if (target != null)
            {
                tryCapture(stack, player, target);
            }
        }
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack)
    {
        return 10;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    public float getCaptureProbability(LivingEntity entity)
    {
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        float probability = probabilityFunction.apply(health / maxHealth);
        return MathHelper.clamp(probability, 0f, 1f);
    }

    protected void tryCapture(ItemStack stack, PlayerEntity user, LivingEntity entity)
    {
        NbtCompound nbt = stack.getOrCreateNbt();
        // already stored
        if (nbt.contains(ENTITY_KEY, NbtElement.COMPOUND_TYPE))
        {
            return;
        }

        if (!(entity instanceof MobEntity mob))
        {
            return;
        }

        SoulMinionComponent component = SoulControl.getSoulMinion(mob);
        boolean notOwned = component.getOwner() != user;
        // user is not the owner, and failed to capture
        if (notOwned &&
            user.getRandom().nextFloat() > getCaptureProbability(mob))
        {
            user.getItemCooldownManager().set(this, cooldownOnFail);
            return;
        }

        // first capture
        if (notOwned)
        {
            mob.setHealth(mob.getMaxHealth());
            component.setOwner(user);
        }

        // do saving
        NbtCompound entityNbt = SoulControl.saveMinionData(mob);
        nbt.put(ENTITY_KEY, entityNbt);
        NbtCompound tooltipData = saveSoulMinionTooltipData(mob);
        nbt.put(TOOLTIP_DATA_KEY, tooltipData);
        mob.discard();

        user.getItemCooldownManager().set(this, COOLDOWN);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        if (!(context.getWorld() instanceof ServerWorld serverWorld))
        {
            return ActionResult.CONSUME;
        }

        PlayerEntity player = context.getPlayer();
        if (player == null)
        {
            return ActionResult.FAIL;
        }

        ItemStack itemStack = context.getStack();
        BlockPos blockPos = context.getBlockPos();
        BlockPos offsetPos = serverWorld.getBlockState(blockPos)
            .getCollisionShape(serverWorld, blockPos).isEmpty() ?
            blockPos :
            blockPos.offset(context.getSide());
        Vec3d pos = Vec3d.ofBottomCenter(offsetPos);

        NbtCompound nbt = itemStack.getOrCreateNbt();
        // if stored, try to spawn it
        if (nbt.contains(ENTITY_KEY, NbtElement.COMPOUND_TYPE))
        {
            // spawn entity
            NbtCompound data = nbt.getCompound(ENTITY_KEY);
            MobEntity mob = SoulControl.loadMinionFromData(data, serverWorld);
            // TODO: pos
            mob.setPosition(pos);
            serverWorld.spawnEntity(mob);

            // data changed
            nbt.putUuid(ENTITY_KEY, mob.getUuid());
            NbtCompound tooltipData = saveSoulMinionTooltipData(mob);
            nbt.put(TOOLTIP_DATA_KEY, tooltipData);

            // feedback
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            serverWorld.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
            serverWorld.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.PLAYERS, 0.5F, 0.4F / (serverWorld.getRandom().nextFloat() * 0.4F + 0.8F));

            return ActionResult.SUCCESS;
        }

        // else if last stored, try to teleport it back here
        else if (nbt.containsUuid(ENTITY_KEY))
        {
            UUID lastUuid = nbt.getUuid(ENTITY_KEY);
            Entity entity = serverWorld.getEntity(lastUuid);

            // not found
            if (entity == null)
            {
                player.sendMessage(SDTexts.TOOLTIP$SOUL_CONTAINER$WARNING.get().formatted(Formatting.RED), true);
            } else
            {
                entity.setPosition(pos);
                return ActionResult.SUCCESS;
            }
        }

        // else do nothing
        return ActionResult.FAIL;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        NbtCompound nbt = stack.getNbt();

        boolean stored = nbt != null && nbt.contains(ENTITY_KEY, NbtElement.COMPOUND_TYPE);
        boolean lastStored = nbt != null && nbt.containsUuid(ENTITY_KEY);

        // stored
        if (stored)
        {
            tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$STORED.get().formatted(Formatting.GRAY));
        }
        // nothing inside
        else
        {
            tooltip.add(SDTexts.TOOLTIP$CONTAINER_EMPTY.get().formatted(Formatting.DARK_AQUA).formatted(Formatting.GRAY));
            // last stored
            if (lastStored)
            {
                tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$LAST_STORED.get().formatted(Formatting.GRAY));
            }
        }

        if (nbt != null && nbt.contains(TOOLTIP_DATA_KEY, NbtElement.COMPOUND_TYPE))
        {
            appendSoulMinionDetailTooltip(tooltip, nbt.getCompound(TOOLTIP_DATA_KEY));
            tooltip.add(Text.empty());
        } else if (stored || lastStored)
        {
            tooltip.add(SDTexts.TOOLTIP$INVALID.get().formatted(Formatting.RED));
            tooltip.add(Text.empty());
        }

        if (stored)
        {
            tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$USAGE_2.get().formatted(Formatting.GRAY));
        } else
        {
            if (lastStored)
            {
                tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$USAGE_3.get().formatted(Formatting.GRAY));
                tooltip.add(Text.empty());
            }
            tooltip.add(
                SDTexts.TOOLTIP$SOUL_CONTAINER$USAGE_1.get(
                    "%d%%".formatted(Math.round(healthThresholdRatio * 100))
                ).formatted(Formatting.GRAY)
            );
        }
    }

    private static NbtCompound saveSoulMinionTooltipData(Entity entity)
    {
        NbtCompound nbt = new NbtCompound();

        Text customName = entity.getCustomName();
        if (customName != null)
        {
            nbt.putString(CUSTOM_NAME_KEY, customName.getString());
        }

        nbt.putString(ENTITY_TYPE_KEY, entity.getType().getName().getString());

        MobDifficulty.get(entity).ifPresent(difficulty ->
            nbt.putInt(LEVEL_KEY, difficulty.getLevel())
        );

        if (entity instanceof LivingEntity living)
        {
            nbt.putFloat(HEALTH_KEY, living.getHealth());
            nbt.putFloat(MAX_HEALTH_KEY, living.getMaxHealth());
        }

        return nbt;
    }

    private static void appendSoulMinionDetailTooltip(List<Text> tooltip, NbtCompound nbt)
    {
        if (nbt.contains(CUSTOM_NAME_KEY, NbtElement.STRING_TYPE))
        {
            tooltip.add(
                SDTexts.TOOLTIP$SOUL_MINION$NAME
                    .get(nbt.getString(CUSTOM_NAME_KEY))
                    .formatted(Formatting.DARK_AQUA)
            );
        }

        tooltip.add(
            SDTexts.TOOLTIP$SOUL_MINION$TYPE
                .get(nbt.getString(ENTITY_TYPE_KEY))
                .formatted(Formatting.DARK_AQUA)
        );

        if (nbt.contains(LEVEL_KEY, NbtElement.INT_TYPE))
        {
            tooltip.add(
                SDTexts.TOOLTIP$SOUL_MINION$LEVEL
                    .get(nbt.getInt(LEVEL_KEY))
                    .formatted(Formatting.DARK_AQUA)
            );
        }

        tooltip.add(SDTexts.TOOLTIP$SOUL_MINION$HEALTH.get(
            "%.1f / %.1f".formatted(
                nbt.getFloat(HEALTH_KEY),
                nbt.getFloat(MAX_HEALTH_KEY)
            )
        ).formatted(Formatting.DARK_AQUA));
    }

    public static int getContainMobLevel(ItemStack stack)
    {
        if (stack.getItem() instanceof SoulContainerItem)
        {
            var nbt = stack.getNbt();
            if (nbt != null &&
                nbt.contains(ENTITY_KEY, NbtElement.COMPOUND_TYPE) &&
                nbt.contains(TOOLTIP_DATA_KEY, NbtElement.COMPOUND_TYPE))
            {
                NbtCompound tempData = nbt.getCompound(TOOLTIP_DATA_KEY);
                return tempData.getInt(LEVEL_KEY);
            }
        }
        return 0;
    }
}
