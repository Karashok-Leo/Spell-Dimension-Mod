package karashokleo.spell_dimension.content.item.trinket.endgame;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrostbiteDomeItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public static final int STONE_CAGE_DURATION = 100;
    public static final float DAMAGE_FACTOR = 0.01F;

    public FrostbiteDomeItem()
    {
        super();
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        if (!event.getSource().isOf(SpellSchools.FROST.damageType))
        {
            return;
        }
        LivingEntity target = event.getEntity();
        StatusEffectInstance effect = target.getStatusEffect(LHEffects.STONE_CAGE);
        if (effect == null)
        {
            EffectHelper.forceAddEffectWithEvent(
                target,
                new StatusEffectInstance(LHEffects.STONE_CAGE, STONE_CAGE_DURATION, 0, false, false, true),
                entity
            );
        } else
        {
            target.removeStatusEffect(LHEffects.STONE_CAGE);
            int duration = effect.getDuration();
            event.setAmount(event.getAmount() * (1 + duration * DAMAGE_FACTOR));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$FROSTBITE_DOME$1.get(STONE_CAGE_DURATION / 20).formatted(Formatting.BLUE));
        tooltip.add(SDTexts.TOOLTIP$FROSTBITE_DOME$2.get().formatted(Formatting.BLUE));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
