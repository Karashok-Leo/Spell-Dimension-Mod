package karashokleo.spell_dimension.content.item.trinket.endgame;

import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.content.effect.RebirthEffect;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RebirthSigilItem extends SingleEpicTrinketItem
{
    public RebirthSigilItem()
    {
        super();
    }

    public void rebirth(LivingEntity entity, LivingEntity owner)
    {
        entity.setHealth(0.001f);
        EffectHelper.forceAddEffectWithEvent(entity, new StatusEffectInstance(AllStatusEffects.REBIRTH, RebirthEffect.TOTAL_DURATION, 0, false, false), owner);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$REBIRTH_SIGIL.get().formatted(Formatting.AQUA));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
