package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public abstract class AbstractSoulContainerItem extends Item
{
    public static final int RANGE = 12;
    public static final int COOLDOWN = 10;
    private static final String OWNER_KEY = "Owner";
    private static final String TOOLTIP_DATA_KEY = "TooltipData";
    private static final String CUSTOM_NAME_KEY = "CustomName";
    private static final String ENTITY_TYPE_KEY = "EntityType";
    private static final String LEVEL_KEY = "Level";
    private static final String HEALTH_KEY = "Health";
    private static final String MAX_HEALTH_KEY = "MaxHealth";

    protected AbstractSoulContainerItem(Settings settings)
    {
        super(settings);
    }

    public abstract int getContainMobMaxLevel(ItemStack stack, World world);

    protected abstract boolean isFull(NbtCompound itemNbt);

    protected void saveMobToNbt(NbtCompound itemNbt, MobEntity mob)
    {
        NbtCompound mobNbt = SoulControl.saveMinionData(mob);
        putMobDataToNbt(itemNbt, mobNbt);
    }

    protected abstract void putMobDataToNbt(NbtCompound itemNbt, NbtCompound mobNbt);

    @Nullable
    protected abstract NbtCompound getMobDataFromNbt(NbtCompound itemNbt, boolean removeData);

    protected int getCooldown(boolean success)
    {
        return COOLDOWN;
    }

    protected boolean cannotCapture(ItemStack stack, PlayerEntity user)
    {
        // cooldown check
        if (user.getItemCooldownManager().isCoolingDown(this))
        {
            return true;
        }
        // container owner check
        NbtCompound nbt = stack.getOrCreateNbt();
        if (isBoundToOther(nbt, user))
        {
            notifyOwnerBound(user);
            return true;
        }
        // content check
        boolean full = isFull(nbt);
        if (full)
        {
            notifyFull(user);
            return true;
        }
        return false;
    }

    protected boolean cannotCapture(ItemStack stack, PlayerEntity user, MobEntity mob)
    {
        if (cannotCapture(stack, user))
        {
            return true;
        }
        SoulMinionComponent component = SoulControl.getSoulMinion(mob);
        PlayerEntity owner = component.getOwner();
        // not owned
        return owner != user;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        // capture check
        if (hand != Hand.MAIN_HAND || cannotCapture(stack, user))
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
            if (RayTraceUtil.serverGetTarget(player) instanceof MobEntity mob)
            {
                tryCapture(stack, player, mob);
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

    protected void tryCapture(ItemStack stack, PlayerEntity user, MobEntity mob)
    {
        if (cannotCapture(stack, user, mob))
        {
            // fail
            user.getItemCooldownManager().set(this, getCooldown(false));
            return;
        }

        SoulMinionComponent component = SoulControl.getSoulMinion(mob);

        // first capture
        if (component.getOwner() != user)
        {
            mob.setHealth(mob.getMaxHealth());
            component.setOwner(user);
        }

        // do saving
        NbtCompound nbt = stack.getOrCreateNbt();
        saveMobToNbt(nbt, mob);
        mob.discard();

        bindOwner(nbt, user);
        user.getItemCooldownManager().set(this, getCooldown(true));
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
        // TODO: do not create nbt?
        NbtCompound nbt = itemStack.getOrCreateNbt();

        // container owner check
        if (isBoundToOther(nbt, player))
        {
            notifyOwnerBound(player);
            return ActionResult.FAIL;
        }

        BlockPos blockPos = context.getBlockPos();
        BlockPos offsetPos = serverWorld.getBlockState(blockPos)
            .getCollisionShape(serverWorld, blockPos).isEmpty() ?
            blockPos :
            blockPos.offset(context.getSide());
        Vec3d pos = Vec3d.ofBottomCenter(offsetPos);

        MobEntity mob = tryAccessMob(serverWorld, pos, nbt, player);
        if (mob != null)
        {
            bindOwner(nbt, player);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Nullable
    protected MobEntity tryAccessMob(ServerWorld world, Vec3d pos, NbtCompound itemNbt, PlayerEntity player)
    {
        // spawn entity
        NbtCompound data = getMobDataFromNbt(itemNbt, true);
        if (data == null)
        {
            notifyEmpty(player);
            return null;
        }

        MobEntity mob = SoulControl.loadMinionFromData(data, world);
        // TODO: spawn pos
        mob.setPosition(pos);
        world.spawnEntity(mob);

        // feedback
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        return mob;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null)
        {
            return;
        }

        // container owner tooltips
        if (nbt.containsUuid(OWNER_KEY))
        {
            UUID ownerId = nbt.getUuid(OWNER_KEY);
            Text ownerName = null;
            if (world != null)
            {
                PlayerEntity owner = world.getPlayerByUuid(ownerId);
                if (owner != null)
                {
                    ownerName = owner.getName();
                }
            }
            if (ownerName == null)
            {
                ownerName = Text.literal(ownerId.toString());
            }
            tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$OWNER.get(ownerName).formatted(Formatting.GRAY));
            tooltip.add(ScreenTexts.SPACE);
        }
    }

    protected static void saveSoulMinionTooltipData(NbtCompound itemNbt, @Nullable MobEntity mob)
    {
        if (mob == null)
        {
            itemNbt.remove(TOOLTIP_DATA_KEY);
            return;
        }
        NbtCompound nbt = new NbtCompound();
        {
            Text customName = mob.getCustomName();
            if (customName != null)
            {
                String json = Text.Serializer.toJson(customName);
                nbt.putString(CUSTOM_NAME_KEY, json);
            }

            Identifier typeId = Registries.ENTITY_TYPE.getId(mob.getType());
            nbt.putString(ENTITY_TYPE_KEY, typeId.toString());

            MobDifficulty.get(mob).ifPresent(difficulty ->
                nbt.putInt(LEVEL_KEY, difficulty.getLevel())
            );

            nbt.putFloat(HEALTH_KEY, mob.getHealth());
            nbt.putFloat(MAX_HEALTH_KEY, mob.getMaxHealth());
        }
        itemNbt.put(TOOLTIP_DATA_KEY, nbt);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected static boolean appendSoulMinionDetailTooltip(NbtCompound itemNbt, List<Text> tooltip)
    {
        if (!itemNbt.contains(TOOLTIP_DATA_KEY, NbtElement.COMPOUND_TYPE))
        {
            return false;
        }
        NbtCompound nbt = itemNbt.getCompound(TOOLTIP_DATA_KEY);
        if (nbt.contains(CUSTOM_NAME_KEY, NbtElement.STRING_TYPE))
        {
            String rawName = nbt.getString(CUSTOM_NAME_KEY);
            MutableText text = null;
            try
            {
                text = Text.Serializer.fromJson(rawName);
            } catch (RuntimeException ignored)
            {
            }
            if (text == null)
            {
                text = Text.literal(rawName);
            }
            tooltip.add(
                SDTexts.TOOLTIP$SOUL_MINION$NAME
                    .get(text)
                    .formatted(Formatting.DARK_AQUA)
            );
        }

        String typeRaw = nbt.getString(ENTITY_TYPE_KEY);
        Identifier typeId = Identifier.tryParse(typeRaw);
        Text typeName = typeId != null &&
            Registries.ENTITY_TYPE.containsId(typeId) ?
            Registries.ENTITY_TYPE.get(typeId).getName() :
            Text.literal(typeRaw);
        tooltip.add(
            SDTexts.TOOLTIP$SOUL_MINION$TYPE
                .get(typeName)
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
        return true;
    }

    protected static boolean isBoundToOther(NbtCompound itemNbt, PlayerEntity user)
    {
        return itemNbt.containsUuid(OWNER_KEY) &&
            !itemNbt.getUuid(OWNER_KEY).equals(user.getUuid());
    }

    protected static void bindOwner(NbtCompound itemNbt, PlayerEntity user)
    {
        if (itemNbt.containsUuid(OWNER_KEY))
        {
            return;
        }
        itemNbt.putUuid(OWNER_KEY, user.getUuid());
    }

    protected static void notifyOwnerBound(PlayerEntity player)
    {
        if (player.getWorld().isClient())
        {
            return;
        }
        player.sendMessage(SDTexts.TEXT$SOUL_CONTAINER_NOT_OWNER.get().formatted(Formatting.RED), true);
    }

    protected static void notifyEmpty(PlayerEntity player)
    {
        if (player.getWorld().isClient())
        {
            return;
        }
        player.sendMessage(SDTexts.TEXT$SOUL_CONTAINER_EMPTY.get().formatted(Formatting.RED), true);
    }

    protected static void notifyFull(PlayerEntity player)
    {
        if (player.getWorld().isClient())
        {
            return;
        }
        player.sendMessage(SDTexts.TEXT$SOUL_CONTAINER_FULL.get().formatted(Formatting.RED), true);
    }
}
