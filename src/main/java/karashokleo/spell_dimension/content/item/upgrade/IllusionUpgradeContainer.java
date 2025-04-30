package karashokleo.spell_dimension.content.item.upgrade;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterLogicContainer;
import org.jetbrains.annotations.NotNull;

public class IllusionUpgradeContainer extends UpgradeContainerBase<IllusionUpgradeWrapper, IllusionUpgradeContainer>
{
    private final FilterLogicContainer<FilterLogic> filterLogicContainer;

    public IllusionUpgradeContainer(PlayerEntity player, int containerId, IllusionUpgradeWrapper upgradeWrapper, UpgradeContainerType<IllusionUpgradeWrapper, IllusionUpgradeContainer> type)
    {
        super(player, containerId, upgradeWrapper, type);
        filterLogicContainer = new FilterLogicContainer<>(upgradeWrapper::getFilterLogic, this, slots::add);
    }

    @Override
    public void handleMessage(@NotNull NbtCompound data)
    {
        filterLogicContainer.handleMessage(data);
    }

    public FilterLogicContainer<FilterLogic> getFilterLogicContainer()
    {
        return filterLogicContainer;
    }
}
