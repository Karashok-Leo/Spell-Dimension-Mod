package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

@SerialClass
public record C2SSpiritTomeShopBuy(int index) implements SerialPacketC2S
{
    @Override
    public void handle(ServerPlayerEntity player)
    {
        SpiritTomeComponent component = SpiritTomeComponent.get(player);
        component.refreshShop(false);
        int cost = component.getShopCost(index);
        // refresh
        if (index == SpiritTomeComponent.REFRESH_FLAG)
        {
            if (!component.tryConsumeSpirit(cost))
            {
                return;
            }
            component.refreshShop(true);
            return;
        }
        // lottery
        if (index == SpiritTomeComponent.LOTTERY_FLAG)
        {
            if (!component.tryConsumeSpirit(cost))
            {
                return;
            }
            ItemStack stack = RandomUtil.randomItemFromRegistry(player.getRandom(), AllTags.SPIRIT_TOME_SHOP_BLACKLIST).getDefaultStack();
            if (stack.isEmpty())
            {
                return;
            }
            player.getInventory().offerOrDrop(stack.copy());
            return;
        }
        if (index < 0)
        {
            return;
        }
        // already purchased
        if (component.isShopItemPurchased(index))
        {
            return;
        }
        // prepare item
        List<Item> shopItems = component.getShopItems();
        if (index >= shopItems.size())
        {
            return;
        }
        ItemStack stack = new ItemStack(shopItems.get(index));
        // consume spirit
        if (!component.tryConsumeSpirit(cost))
        {
            return;
        }
        component.markShopItemPurchased(index);
        player.getInventory().offerOrDrop(stack.copy());
        component.sync();
    }
}
