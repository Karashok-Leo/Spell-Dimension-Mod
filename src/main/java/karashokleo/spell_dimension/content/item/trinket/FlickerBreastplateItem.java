package karashokleo.spell_dimension.content.item.trinket;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlickerBreastplateItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public FlickerBreastplateItem()
    {
        super();
    }

    public boolean willFlicker(LivingEntity entity, LivingEntity attacker)
    {
        double entitySpeed = entity.getMovementSpeed();
        double attackerSpeed = attacker.getMovementSpeed();
        double flickerRatio = attackerSpeed / entitySpeed;
        if (!entity.isOnGround()) flickerRatio /= 2;
        return entity.getRandom().nextDouble() > flickerRatio;
    }

    @Override
    public void onAttacked(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
        if (!(event.getSource().getAttacker() instanceof LivingEntity attacker)) return;
        if (AllItems.FLICKER_BREASTPLATE.willFlicker(entity, attacker))
            event.setCanceled(true);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$FLICKER_BREASTPLATE.get().formatted(Formatting.GOLD));
    }
}
