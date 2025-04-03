package karashokleo.spell_dimension.content.item.trinket;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RejuvenatingBlossomItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public RejuvenatingBlossomItem()
    {
        super();
    }

    @Override
    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
        int count = TrinketCompat.getTrinketItems(entity, e -> e.isOf(this)).size();
        int amount = (int) event.getAmount() * count;
        StatusEffectInstance instance = entity.getStatusEffect(StatusEffects.REGENERATION);
        if (instance != null && instance.getDuration() < amount)
        {
            EffectUtil.streamEffects(entity, StatusEffectCategory.HARMFUL)
                    .forEach(entity::removeStatusEffect);
            event.setAmount(event.getAmount() / 2);
        }
        EffectHelper.forceAddEffectWithEvent(entity, new StatusEffectInstance(StatusEffects.REGENERATION, amount, 9, false, false), entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$REJUVENATING_BLOSSOM$USAGE_1.get().formatted(Formatting.YELLOW));
        tooltip.add(SDTexts.TOOLTIP$REJUVENATING_BLOSSOM$USAGE_2.get().formatted(Formatting.GREEN));
    }
}
