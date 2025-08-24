package karashokleo.spell_dimension.content.item.trinket;

import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZhuziMinerHelmet extends SingleEpicTrinketItem
{
    private static final int POWER_FACTOR = 500;

    public ZhuziMinerHelmet()
    {
        super();
    }

    public int getFortuneBonus(LivingEntity entity)
    {
        double spellPower = SchoolUtil.getEntitySpellPower(entity);
        int bonus = (int) (spellPower / POWER_FACTOR);
        if (bonus < 0)
        {
            bonus = 0;
        }

        return bonus;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$ZHUZI_MINER_HELMET$1.get(POWER_FACTOR).formatted(Formatting.LIGHT_PURPLE));
        tooltip.add(SDTexts.TOOLTIP$ZHUZI_MINER_HELMET$2.get().formatted(Formatting.GREEN, Formatting.BOLD));
        tooltip.add(SDTexts.TOOLTIP$CONTRIBUTOR_ITEM.get("zhuziou").formatted(Formatting.AQUA, Formatting.ITALIC));
    }
}
