package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.S2COpenSoulAlbumScreen;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulAlbumItem extends AbstractSoulContainerItem
{
    public static final int CAPACITY = 10;
    private static final String STORAGE_KEY = "Storage";
    private static final String SELECTED_KEY = "Selected";

    public SoulAlbumItem()
    {
        super(
            new FabricItemSettings()
                .fireproof()
                .maxCount(1)
        );
    }

    @Override
    protected boolean isFull(NbtCompound itemNbt)
    {
        return getStorageSize(itemNbt) >= CAPACITY;
    }

    @Override
    protected void putMobDataToNbt(NbtCompound itemNbt, NbtCompound mobNbt)
    {
        NbtList list = getStorageList(itemNbt);
        if (list == null)
        {
            list = new NbtList();
        }
        list.add(mobNbt);
        itemNbt.put(STORAGE_KEY, list);
    }

    @Override
    protected NbtCompound getMobDataFromNbt(NbtCompound itemNbt, boolean removeData)
    {
        int selected = getSelectedIndex(itemNbt);
        if (selected < 0)
        {
            return null;
        }
        NbtList list = getStorageList(itemNbt);
        if (list == null)
        {
            return null;
        }
        if (selected >= list.size())
        {
            return null;
        }
        NbtCompound data = list.getCompound(selected);
        if (removeData)
        {
            list.remove(selected);
            if (list.isEmpty())
            {
                itemNbt.remove(STORAGE_KEY);
            } else
            {
                itemNbt.put(STORAGE_KEY, list);
            }
            itemNbt.remove(SELECTED_KEY);
        }
        return data;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (getSelectedIndex(stack) < 0)
        {
            if (!shouldStartCapture(user, stack))
            {
                if (!world.isClient() && user instanceof ServerPlayerEntity player)
                {
                    AllPackets.toClientPlayer(player, new S2COpenSoulAlbumScreen(hand));
                }
                return TypedActionResult.success(stack, world.isClient());
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        ItemStack stack = context.getStack();
        if (getSelectedIndex(stack) < 0)
        {
            if (!context.getWorld().isClient() &&
                context.getPlayer() instanceof ServerPlayerEntity player)
            {
                AllPackets.toClientPlayer(player, new S2COpenSoulAlbumScreen(context.getHand()));
            }
            return ActionResult.CONSUME;
        }
        return super.useOnBlock(context);
    }

    // use album to click container
    @Override
    public boolean onStackClicked(ItemStack stack, net.minecraft.screen.slot.Slot slot, ClickType clickType, PlayerEntity player)
    {
        if (clickType != ClickType.RIGHT)
        {
            return false;
        }
        ItemStack clicking = slot.getStack();
        if (!(clicking.getItem() instanceof SoulContainerItem container))
        {
            return false;
        }
        boolean changed = moveBetweenAlbumAndContainer(stack, clicking, container, player);
        if (changed)
        {
            playSound(player);
        }
        return changed;
    }

    // use container to click album
    @Override
    public boolean onClicked(ItemStack clicking, ItemStack holding, net.minecraft.screen.slot.Slot slot, ClickType clickType, PlayerEntity player, net.minecraft.inventory.StackReference cursorStackReference)
    {
        if (clickType != ClickType.RIGHT || !slot.canTakePartial(player))
        {
            return false;
        }
        if (!(holding.getItem() instanceof SoulContainerItem container))
        {
            return false;
        }
        boolean changed = moveBetweenAlbumAndContainer(clicking, holding, container, player);
        if (changed)
        {
            playSound(player);
        }
        return changed;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$USE$CLICK.get().formatted(Formatting.GRAY));
        tooltip.add(SDTexts.TOOLTIP$USE$PRESS.get().formatted(Formatting.GRAY));
    }

    public static void select(ItemStack stack, int index)
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
        if (nbt == null)
        {
            return -1;
        }
        return getSelectedIndex(nbt);
    }

    private static int getSelectedIndex(NbtCompound itemNbt)
    {
        if (!itemNbt.contains(SELECTED_KEY, NbtElement.INT_TYPE))
        {
            return -1;
        }
        return itemNbt.getInt(SELECTED_KEY);
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

    @Nullable
    public static NbtList getStorageList(NbtCompound itemNbt)
    {
        if (!itemNbt.contains(STORAGE_KEY, NbtElement.LIST_TYPE))
        {
            return null;
        }
        return itemNbt.getList(STORAGE_KEY, NbtElement.COMPOUND_TYPE);
    }

    private static int getStorageSize(NbtCompound itemNbt)
    {
        NbtList list = getStorageList(itemNbt);
        if (list == null)
        {
            return 0;
        }
        return list.size();
    }

    private boolean moveBetweenAlbumAndContainer(ItemStack album, ItemStack containerStack, SoulContainerItem containerItem, PlayerEntity player)
    {
        NbtCompound albumNbt = album.getOrCreateNbt();
        NbtCompound containerNbt = containerStack.getOrCreateNbt();
        if (isBoundToOther(albumNbt, player) ||
            isBoundToOther(containerNbt, player))
        {
            return false;
        }

        NbtCompound containerData = containerItem.getMobDataFromNbt(containerNbt, false);
        // move from album to container
        if (containerData == null)
        {
            NbtCompound mobData = getMobDataFromNbt(albumNbt, true);
            if (mobData == null)
            {
                return false;
            }
            containerItem.putMobDataToNbt(containerNbt, mobData);
            bindOwner(albumNbt, player);
            bindOwner(containerNbt, player);
            return true;
        }

        if (isFull(albumNbt))
        {
            return false;
        }
        // move from container to album
        NbtCompound mobData = containerItem.getMobDataFromNbt(containerNbt, true);
        putMobDataToNbt(albumNbt, mobData);
        bindOwner(albumNbt, player);
        bindOwner(containerNbt, player);
        return true;
    }

    private boolean shouldStartCapture(PlayerEntity user, ItemStack stack)
    {
        if (cannotCapture(stack, user))
        {
            return false;
        }
        EntityHitResult hit = RayTraceUtil.rayTraceEntity(
            user,
            RANGE,
            entity -> ImpactUtil.castToLiving(entity) instanceof MobEntity mob && SoulControl.isSoulMinion(user, mob)
        );
        return hit != null;
    }

    private static void playSound(Entity entity)
    {
        entity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.6F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public int getContainMobMaxLevel(ItemStack stack, World world)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null)
        {
            return 0;
        }
        NbtList list = getStorageList(nbt);
        if (list == null)
        {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < list.size(); i++)
        {
            NbtCompound data = list.getCompound(i);
            MobEntity mob = SoulControl.loadMinionFromData(data, world);
            max = Math.max(max, DifficultyLevel.ofAny(mob));
        }
        return max;
    }
}
