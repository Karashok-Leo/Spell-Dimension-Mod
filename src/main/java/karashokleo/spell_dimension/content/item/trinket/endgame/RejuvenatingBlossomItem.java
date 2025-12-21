package karashokleo.spell_dimension.content.item.trinket.endgame;

import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RejuvenatingBlossomItem extends SingleEpicTrinketItem
{
    private static final float HEALING_FACTOR = 0.01f;

    public RejuvenatingBlossomItem()
    {
        super();
    }

    public float getHealingAmountScale(LivingEntity entity)
    {
        float power = (float) SpellPower.getSpellPower(SpellSchools.HEALING, entity).baseValue();
        return power * HEALING_FACTOR;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(
            SDTexts.TOOLTIP$REJUVENATING_BLOSSOM$1.get(
                "%.1f%%".formatted(HEALING_FACTOR * 100)
            ).formatted(Formatting.YELLOW)
        );
    }
}
