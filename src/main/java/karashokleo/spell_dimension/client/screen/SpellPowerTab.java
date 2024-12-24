package karashokleo.spell_dimension.client.screen;

import dev.xkmc.l2tabs.tabs.core.TabBase;
import dev.xkmc.l2tabs.tabs.core.TabManager;
import dev.xkmc.l2tabs.tabs.core.TabToken;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class SpellPowerTab extends TabBase<InvTabData, SpellPowerTab>
{
    public SpellPowerTab(int index, TabToken<InvTabData, SpellPowerTab> token, TabManager<InvTabData> manager, ItemStack stack, Text title)
    {
        super(index, token, manager, stack, title);
    }

    @Override
    public void onTabClicked()
    {
        MinecraftClient.getInstance().setScreen(new SpellPowerScreen(this.getMessage()));
    }
}
