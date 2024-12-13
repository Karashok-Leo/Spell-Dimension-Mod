package karashokleo.spell_dimension.content.item.trinket;

import dev.emi.trinkets.api.TrinketItem;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class RejuvenatingBlossomItem extends TrinketItem
{
    public RejuvenatingBlossomItem()
    {
        super(
                new FabricItemSettings()
                        .fireproof()
                        .maxCount(1)
                        .rarity(Rarity.EPIC)
        );
    }

    public void rejuvenate(LivingEntity entity, int duration)
    {
        StatusEffectInstance instance = entity.getStatusEffect(StatusEffects.REGENERATION);
        if ((instance == null ? 0 : instance.getDuration()) < duration)
        {
            var effects = new HashSet<>(entity.getActiveStatusEffects().keySet());
            for (StatusEffect effect : effects)
                if (effect.getCategory() == StatusEffectCategory.HARMFUL)
                    entity.removeStatusEffect(effect);
        }
        EffectHelper.forceAddEffectWithEvent(entity, new StatusEffectInstance(StatusEffects.REGENERATION, duration, 9, false, false), entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$REJUVENATING_BLOSSOM$USAGE_1.get().formatted(Formatting.YELLOW));
        tooltip.add(SDTexts.TOOLTIP$REJUVENATING_BLOSSOM$USAGE_2.get().formatted(Formatting.GREEN));
    }
}
