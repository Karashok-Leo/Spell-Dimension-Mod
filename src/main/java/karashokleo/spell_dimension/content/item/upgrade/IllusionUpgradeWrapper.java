package karashokleo.spell_dimension.content.item.upgrade;

import karashokleo.spell_dimension.content.item.IllusionContainer;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.settings.memory.MemorySettingsCategory;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ContentsFilterLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IContentsFilteredUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IPickupResponseUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IllusionUpgradeWrapper extends UpgradeWrapperBase<IllusionUpgradeWrapper, IllusionUpgradeItem> implements IPickupResponseUpgrade, IContentsFilteredUpgrade
{
    private final ContentsFilterLogic filterLogic;

    public IllusionUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler)
    {
        super(storageWrapper, upgrade, upgradeSaveHandler);
        this.filterLogic = new ContentsFilterLogic(upgrade, stack -> this.save(), 16, storageWrapper::getInventoryHandler, storageWrapper.getSettingsHandler().getTypeCategory(MemorySettingsCategory.class));
    }

    @SuppressWarnings("UnstableApiUsage")
    @NotNull
    @Override
    public ItemStack pickup(@NotNull World world, @NotNull ItemStack stack, @NotNull TransactionContext ctx)
    {
        if (this.filterLogic.matchesFilter(stack))
        {
            if (!EnchantedModifier.has(stack))
            {
                int materialTier = IllusionContainer.getMaterialTier(stack);
                int enchantmentPoints = IllusionContainer.getEnchantmentPoints(stack);
                if (materialTier >= 0 || enchantmentPoints > 0)
                {
                    IllusionContainer.convert(upgrade, materialTier, enchantmentPoints);
                    stack.setCount(0);
                }
            }

            ItemVariant resource = ItemVariant.of(stack);
            long inserted = this.storageWrapper.getInventoryForUpgradeProcessing().insert(resource, stack.getCount(), ctx);
            return resource.toStack(stack.getCount() - (int) inserted);
        } else
        {
            return stack;
        }
    }

    @NotNull
    @Override
    public ContentsFilterLogic getFilterLogic()
    {
        return filterLogic;
    }
}
