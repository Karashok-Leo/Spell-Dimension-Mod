package karashokleo.spell_dimension.content.item.upgrade;

import karashokleo.spell_dimension.content.item.IllusionContainer;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeCountLimitConfig;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeGroup;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IllusionUpgradeItem extends UpgradeItemBase<IllusionUpgradeWrapper>
{
    public static final UpgradeType<IllusionUpgradeWrapper> TYPE = new UpgradeType<>(IllusionUpgradeWrapper::new);

    public IllusionUpgradeItem()
    {
        super(new IUpgradeCountLimitConfig()
        {
            @Override
            public int getMaxUpgradesPerStorage(@NotNull String s, Identifier identifier)
            {
                return 1;
            }

            @Override
            public int getMaxUpgradesInGroupPerStorage(@NotNull String s, @NotNull UpgradeGroup upgradeGroup)
            {
                return 1;
            }
        });
    }

    @NotNull
    @Override
    public UpgradeType<IllusionUpgradeWrapper> getType()
    {
        return TYPE;
    }

    @NotNull
    @Override
    public List<UpgradeConflictDefinition> getUpgradeConflicts()
    {
        return List.of();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient())
        {
            IllusionContainer.retrieve(stack, user.getInventory());
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack, World worldIn, @NotNull List<Text> tooltip, @NotNull TooltipContext flagIn)
    {
        tooltip.add(SDTexts.TOOLTIP$CONTAINER$REFORGE_UPGRADE.get().formatted(Formatting.GRAY));
        IllusionContainer.appendTooltip(stack, tooltip);
    }
}
