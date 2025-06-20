package karashokleo.spell_dimension.content.item.trinket.endgame;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperconductorItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public SuperconductorItem()
    {
        super();
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        if (!event.getSource().isOf(SpellSchools.LIGHTNING.damageType))
        {
            return;
        }
        double baseValue = SpellPower.getSpellPower(SpellSchools.LIGHTNING, entity).baseValue();
        int level = (int) (baseValue / 100);
        if (level <= 0)
        {
            return;
        }
        int ticks = 2 * level;
        EffectHelper.forceAddEffectWithEvent(
                event.getEntity(),
                new StatusEffectInstance(AllStatusEffects.STUN, ticks, 0, false, false),
                entity
        );
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$SUPERCONDUCTOR.get().formatted(Formatting.AQUA));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
