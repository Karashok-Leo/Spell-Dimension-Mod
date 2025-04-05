package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.SoundUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrokenItem extends Item
{
    private static final String SAVE_ITEM_KEY = "SaveItem";
    private static final String REQUIRE_MENDING_KEY = "RequireMending";

    public BrokenItem()
    {
        super(
                new FabricItemSettings()
                        .fireproof()
                        .maxCount(1)
        );
    }

    public ItemStack saveItem(ItemStack stack)
    {
        int requireMending = stack.getMaxDamage() / 100 + 1;
        if (requireMending <= 0) requireMending = 1;
        return saveItem(stack, requireMending);
    }

    public ItemStack saveItem(ItemStack stack, int requireMending)
    {
        ItemStack ans = getDefaultStack();
        NbtCompound nbt = ans.getOrCreateNbt();
        nbt.putInt(REQUIRE_MENDING_KEY, requireMending);
        nbt.put(SAVE_ITEM_KEY, stack.writeNbt(new NbtCompound()));
        if (EnchantmentHelper.getLevel(LHEnchantments.VANISH, stack) > 0)
            ans.addEnchantment(LHEnchantments.VANISH, 1);
        return ans;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference)
    {
        if (clickType == ClickType.LEFT)
            return false;
        if (!slot.canTakePartial(player))
            return false;
        if (!otherStack.isOf(AllItems.MENDING_ESSENCE))
            return false;
        otherStack.decrement(1);
        SoundUtil.playSound(player, SoundUtil.ANVIL);
        NbtCompound nbt = stack.getOrCreateNbt();
        int requireMending = nbt.getInt(REQUIRE_MENDING_KEY);
        if (requireMending > 0)
        {
            requireMending--;
            nbt.putInt(REQUIRE_MENDING_KEY, requireMending);
        }
        if (requireMending <= 0)
        {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound(SAVE_ITEM_KEY));
            itemStack.setDamage(0);
            slot.setStack(itemStack);
            stack.decrement(1);
        }
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        NbtCompound nbt = stack.getOrCreateNbt();
        tooltip.add(SDTexts.TOOLTIP$BROKEN_ITEM.get(nbt.getInt(REQUIRE_MENDING_KEY)).formatted(Formatting.GRAY));
        tooltip.add(ItemStack.fromNbt(nbt.getCompound(SAVE_ITEM_KEY)).getName());
    }
}
