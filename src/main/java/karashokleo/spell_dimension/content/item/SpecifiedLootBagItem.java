package karashokleo.spell_dimension.content.item;

import karashokleo.loot_bag.api.LootBagManager;
import karashokleo.loot_bag.api.common.bag.BagEntry;
import karashokleo.loot_bag.internal.item.LootBagItem;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.content.item.logic.Tier;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class SpecifiedLootBagItem extends LootBagItem implements ColorProvider
{
    public final Identifier id;
    public final Tier tier;

    public SpecifiedLootBagItem(Identifier id, Tier tier)
    {
        super(new FabricItemSettings().maxCount(16));
        this.id = id;
        this.tier = tier;
    }

    @Override
    public Optional<BagEntry> getBagEntry(ItemStack stack)
    {
        return Optional.ofNullable(LootBagManager.getInstance().getBagEntry(id));
    }

    @Override
    public ItemStack getStack(Identifier bagId)
    {
        return this.getDefaultStack();
    }

    @Override
    public ItemStack getStack(BagEntry entry)
    {
        return this.getDefaultStack();
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return this.getName();
    }

    @Override
    public Text getName()
    {
        return SDTexts.TEXT$RANDOM_LOOT_NAME.get(Text.translatable(id.toTranslationKey("content")));
    }

    @Override
    public int getColor(ItemStack stack)
    {
        return tier.color;
    }
}
