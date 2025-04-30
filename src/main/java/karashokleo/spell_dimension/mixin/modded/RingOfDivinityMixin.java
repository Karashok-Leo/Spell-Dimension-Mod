package karashokleo.spell_dimension.mixin.modded;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.content.item.trinket.ring.RingOfDivinity;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(RingOfDivinity.class)
public abstract class RingOfDivinityMixin extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    /**
     * @author Karashok-Leo
     * @reason mechanics overwrite
     */
    @Overwrite
    public void onAttacked(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
    }

    @Override
    public void onHurt(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        if (!source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) &&
            !source.isIn(DamageTypeTags.BYPASSES_EFFECTS) &&
            source.isIn(LHTags.MAGIC))
        {
            float amount = event.getAmount();
            amount *= 0.5F;
            amount = Math.min(amount, entity.getMaxHealth() * 0.8F);
            event.setAmount(amount);
        }
    }

    /**
     * @author Karashok-Leo
     * @reason tooltip overwrite
     */
    @Overwrite
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$RING_DIVINITY.get().formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
