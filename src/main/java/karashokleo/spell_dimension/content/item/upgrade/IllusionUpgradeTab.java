package karashokleo.spell_dimension.content.item.upgrade;

import net.minecraft.text.Text;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeSettingsTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicControl;

public class IllusionUpgradeTab extends UpgradeSettingsTab<IllusionUpgradeContainer>
{
    public static final String KEY = "gui.spell-dimension.reforge";

    protected FilterLogicControl<FilterLogic, FilterLogicContainer<FilterLogic>> filterLogicControl;

    public IllusionUpgradeTab(IllusionUpgradeContainer upgradeContainer, Position position, StorageScreenBase<?> screen)
    {
        super(upgradeContainer, position, screen, Text.translatable(KEY), Text.translatable(KEY + ".tooltip"));
        filterLogicControl = addHideableChild(new FilterLogicControl.Advanced(screen, new Position(x + 3, y + 24), getContainer().getFilterLogicContainer(), 4));
    }

    @Override
    protected void moveSlotsToTab()
    {
        filterLogicControl.moveSlotsToView();
    }
}
