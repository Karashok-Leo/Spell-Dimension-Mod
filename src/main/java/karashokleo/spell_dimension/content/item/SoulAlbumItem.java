package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.S2COpenSoulAlbumScreen;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class SoulAlbumItem extends Item
{
    public static final String STORAGE_KEY = "Storage";
    public static final String SELECTED_KEY = "Selected";

    public SoulAlbumItem()
    {
        super(
            new FabricItemSettings()
                .fireproof()
                .maxCount(1)
        );
    }

    // use album to click container
    @Override
    public boolean onStackClicked(ItemStack stack, net.minecraft.screen.slot.Slot slot, ClickType clickType, PlayerEntity player)
    {
        if (clickType != ClickType.RIGHT)
        {
            return false;
        }
        ItemStack clicked = slot.getStack();
        if (clicked.isEmpty())
        {
            ItemStack removed = removeSelectedOrLast(stack);
            if (removed.isEmpty())
            {
                return false;
            }
            ItemStack remain = slot.insertStack(removed);
            player.getInventory().offerOrDrop(remain);
            playSound(player);
            return true;
        }
        if (cannotStore(clicked))
        {
            return false;
        }
        addContainer(stack, clicked.copy());
        clicked.setCount(0);
        playSound(player);
        return true;
    }

    // use container to click album
    @Override
    public boolean onClicked(ItemStack clicking, ItemStack holding, net.minecraft.screen.slot.Slot slot, ClickType clickType, PlayerEntity player, net.minecraft.inventory.StackReference cursorStackReference)
    {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player))
        {
            if (holding.isEmpty())
            {
                ItemStack removed = removeSelectedOrLast(clicking);
                if (removed.isEmpty())
                {
                    return false;
                }
                if (!cursorStackReference.set(removed))
                {
                    player.getInventory().offerOrDrop(removed);
                }
            } else
            {
                if (cannotStore(holding))
                {
                    return false;
                }
                addContainer(clicking, holding.copy());
                holding.setCount(0);
            }
            playSound(player);
            return true;
        }
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        ItemStack selected = getSelectedContainer(stack);
        if (selected.isEmpty())
        {
            if (!world.isClient() && user instanceof ServerPlayerEntity player)
            {
                AllPackets.toClientPlayer(player, new S2COpenSoulAlbumScreen(hand));
            }
            return TypedActionResult.success(stack, world.isClient());
        }

        if (!(selected.getItem() instanceof SoulContainerItem soulContainer))
        {
            return TypedActionResult.fail(stack);
        }
        if (user.getItemCooldownManager().isCoolingDown(soulContainer))
        {
            return TypedActionResult.fail(stack);
        }
        NbtCompound nbt = selected.getOrCreateNbt();
        if (isBoundToOther(nbt, user))
        {
            return TypedActionResult.fail(stack);
        }
        if (hand != Hand.MAIN_HAND ||
            nbt.contains(SoulContainerItem.ENTITY_KEY, NbtElement.COMPOUND_TYPE))
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
            RayTraceUtil.clientUpdateTarget(player, SoulContainerItem.RANGE);
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        if (!(user instanceof ServerPlayerEntity player))
        {
            return stack;
        }
        ItemStack selected = getSelectedContainer(stack);
        if (selected.isEmpty())
        {
            return stack;
        }
        if (!(selected.getItem() instanceof SoulContainerItem soulContainer))
        {
            return stack;
        }
        LivingEntity target = RayTraceUtil.serverGetTarget(player);
        if (target != null)
        {
            soulContainer.tryCapture(selected, player, target);
            updateSelectedContainer(stack, selected);
        }
        return stack;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        ItemStack stack = context.getStack();
        ItemStack selected = getSelectedContainer(stack);
        if (selected.isEmpty())
        {
            return ActionResult.FAIL;
        }
        if (!(selected.getItem() instanceof SoulContainerItem))
        {
            return ActionResult.FAIL;
        }
        if (!(context.getWorld() instanceof ServerWorld serverWorld))
        {
            return ActionResult.CONSUME;
        }

        PlayerEntity player = context.getPlayer();
        if (player == null)
        {
            return ActionResult.FAIL;
        }

        NbtCompound nbt = selected.getOrCreateNbt();
        if (isBoundToOther(nbt, player))
        {
            return ActionResult.FAIL;
        }
        if (nbt.contains(SoulContainerItem.ENTITY_KEY, NbtElement.COMPOUND_TYPE) ||
            nbt.containsUuid(SoulContainerItem.ENTITY_KEY))
        {
            bindOwner(nbt, player);
        }
        BlockPos blockPos = context.getBlockPos();
        BlockPos offsetPos = serverWorld.getBlockState(blockPos)
            .getCollisionShape(serverWorld, blockPos).isEmpty() ?
            blockPos :
            blockPos.offset(context.getSide());
        Vec3d pos = Vec3d.ofBottomCenter(offsetPos);

        if (nbt.contains(SoulContainerItem.ENTITY_KEY, NbtElement.COMPOUND_TYPE))
        {
            NbtCompound data = nbt.getCompound(SoulContainerItem.ENTITY_KEY);
            MobEntity mob = SoulControl.loadMinionFromData(data, serverWorld);
            mob.setPosition(pos);
            serverWorld.spawnEntity(mob);

            nbt.putUuid(SoulContainerItem.ENTITY_KEY, mob.getUuid());
            NbtCompound tooltipData = saveSoulMinionTooltipData(mob);
            nbt.put(SoulContainerItem.TOOLTIP_DATA_KEY, tooltipData);

            player.incrementStat(Stats.USED.getOrCreateStat(this));
            serverWorld.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos);
            serverWorld.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.PLAYERS, 0.5F, 0.4F / (serverWorld.getRandom().nextFloat() * 0.4F + 0.8F));

            updateSelectedContainer(stack, selected);
            return ActionResult.SUCCESS;
        } else if (nbt.containsUuid(SoulContainerItem.ENTITY_KEY))
        {
            UUID lastUuid = nbt.getUuid(SoulContainerItem.ENTITY_KEY);
            Entity entity = serverWorld.getEntity(lastUuid);

            if (entity == null)
            {
                player.sendMessage(SDTexts.TOOLTIP$SOUL_CONTAINER$WARNING.get().formatted(Formatting.RED), true);
            } else
            {
                entity.setPosition(pos);
                updateSelectedContainer(stack, selected);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
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

    @Override
    public void onItemEntityDestroyed(ItemEntity entity)
    {
        ItemUsage.spawnItemContents(entity, getAllContainers(entity.getStack()));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$USE$CLICK.get().formatted(Formatting.GRAY));
        tooltip.add(SDTexts.TOOLTIP$USE$PRESS.get().formatted(Formatting.GRAY));
    }

    public static List<ItemStack> getStoredContainers(ItemStack stack)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(STORAGE_KEY, NbtElement.LIST_TYPE))
        {
            return new ArrayList<>();
        }
        NbtList list = nbt.getList(STORAGE_KEY, NbtElement.COMPOUND_TYPE);
        List<ItemStack> result = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++)
        {
            ItemStack stored = ItemStack.fromNbt(list.getCompound(i));
            if (!stored.isEmpty())
            {
                result.add(stored);
            }
        }
        return result;
    }

    public static void selectContainer(ItemStack stack, int index)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(STORAGE_KEY, NbtElement.LIST_TYPE))
        {
            setSelectedIndex(stack, -1);
            return;
        }
        NbtList list = nbt.getList(STORAGE_KEY, NbtElement.COMPOUND_TYPE);
        if (index < 0 || index >= list.size())
        {
            setSelectedIndex(stack, -1);
            return;
        }
        setSelectedIndex(stack, index);
    }

    public static int getSelectedIndex(ItemStack stack)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(SELECTED_KEY, NbtElement.INT_TYPE))
        {
            return -1;
        }
        return nbt.getInt(SELECTED_KEY);
    }

    private static ItemStack getSelectedContainer(ItemStack stack)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(STORAGE_KEY, NbtElement.LIST_TYPE))
        {
            return ItemStack.EMPTY;
        }
        NbtList list = nbt.getList(STORAGE_KEY, NbtElement.COMPOUND_TYPE);
        int selected = getSelectedIndex(stack);
        if (selected < 0 || selected >= list.size())
        {
            return ItemStack.EMPTY;
        }
        ItemStack container = ItemStack.fromNbt(list.getCompound(selected));
        return container;
    }

    private static boolean cannotStore(ItemStack stack)
    {
        if (!(stack.getItem() instanceof SoulContainerItem))
        {
            return true;
        }
        NbtCompound nbt = stack.getNbt();
        if (nbt == null)
        {
            return true;
        }
        return !nbt.contains(SoulContainerItem.ENTITY_KEY, NbtElement.COMPOUND_TYPE) &&
            !nbt.containsUuid(SoulContainerItem.ENTITY_KEY);
    }

    private static void addContainer(ItemStack album, ItemStack container)
    {
        NbtCompound nbt = album.getOrCreateNbt();
        NbtList list = nbt.getList(STORAGE_KEY, NbtElement.COMPOUND_TYPE);
        list.add(container.writeNbt(new NbtCompound()));
        nbt.put(STORAGE_KEY, list);
    }

    private static void updateSelectedContainer(ItemStack album, ItemStack selected)
    {
        NbtCompound nbt = album.getNbt();
        if (nbt == null || !nbt.contains(STORAGE_KEY, NbtElement.LIST_TYPE))
        {
            return;
        }
        NbtList list = nbt.getList(STORAGE_KEY, NbtElement.COMPOUND_TYPE);
        int index = getSelectedIndex(album);
        if (index < 0 || index >= list.size())
        {
            return;
        }
        list.set(index, selected.writeNbt(new NbtCompound()));
        nbt.put(STORAGE_KEY, list);
    }

    private static ItemStack removeSelectedOrLast(ItemStack album)
    {
        NbtCompound nbt = album.getNbt();
        if (nbt == null || !nbt.contains(STORAGE_KEY, NbtElement.LIST_TYPE))
        {
            return ItemStack.EMPTY;
        }
        NbtList list = nbt.getList(STORAGE_KEY, NbtElement.COMPOUND_TYPE);
        if (list.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        int selected = getSelectedIndex(album);
        int index = selected >= 0 && selected < list.size() ? selected : list.size() - 1;
        NbtCompound removedNbt = list.getCompound(index);
        list.remove(index);
        int nextSelected = selected;
        if (selected == index)
        {
            nextSelected = -1;
        } else if (selected > index)
        {
            nextSelected = selected - 1;
        }
        if (list.isEmpty())
        {
            nbt.remove(STORAGE_KEY);
        } else
        {
            nbt.put(STORAGE_KEY, list);
        }
        setSelectedIndex(album, nextSelected);
        return ItemStack.fromNbt(removedNbt);
    }

    private static void setSelectedIndex(ItemStack stack, int selected)
    {
        if (selected < 0)
        {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null)
            {
                nbt.remove(SELECTED_KEY);
            }
        } else
        {
            stack.getOrCreateNbt().putInt(SELECTED_KEY, selected);
        }
    }

    private static Stream<ItemStack> getAllContainers(ItemStack stack)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(STORAGE_KEY, NbtElement.LIST_TYPE))
        {
            return Stream.empty();
        }
        NbtList list = nbt.getList(STORAGE_KEY, NbtElement.COMPOUND_TYPE);
        return list.stream()
            .map(NbtCompound.class::cast)
            .map(ItemStack::fromNbt)
            .filter(itemStack -> !itemStack.isEmpty());
    }

    private static NbtCompound saveSoulMinionTooltipData(Entity entity)
    {
        NbtCompound nbt = new NbtCompound();

        Text customName = entity.getCustomName();
        if (customName != null)
        {
            String json = Text.Serializer.toJson(customName);
            nbt.putString(SoulContainerItem.CUSTOM_NAME_KEY, json);
        }

        nbt.putString(SoulContainerItem.ENTITY_TYPE_KEY, net.minecraft.registry.Registries.ENTITY_TYPE.getId(entity.getType()).toString());

        MobDifficulty.get(entity).ifPresent(difficulty ->
            nbt.putInt(SoulContainerItem.LEVEL_KEY, difficulty.getLevel())
        );

        if (entity instanceof LivingEntity living)
        {
            nbt.putFloat(SoulContainerItem.HEALTH_KEY, living.getHealth());
            nbt.putFloat(SoulContainerItem.MAX_HEALTH_KEY, living.getMaxHealth());
        }

        return nbt;
    }

    private static boolean isBoundToOther(NbtCompound nbt, PlayerEntity user)
    {
        return nbt.containsUuid(SoulContainerItem.OWNER_KEY) &&
            !nbt.getUuid(SoulContainerItem.OWNER_KEY).equals(user.getUuid());
    }

    private static void bindOwner(NbtCompound nbt, PlayerEntity user)
    {
        if (nbt.containsUuid(SoulContainerItem.OWNER_KEY))
        {
            return;
        }
        nbt.putUuid(SoulContainerItem.OWNER_KEY, user.getUuid());
    }

    private void playSound(Entity entity)
    {
        entity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.6F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }
}
