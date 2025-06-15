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
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(RingOfDivinity.class)
public abstract class RingOfDivinityMixin extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    @Unique
    private static final float DAMAGE_REDUCTION = 0.6F;
    @Unique
    private static final float MAX_DAMAGE_RATIO = 0.4F;

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
            amount *= (1 - DAMAGE_REDUCTION);
            amount = Math.min(amount, entity.getMaxHealth() * MAX_DAMAGE_RATIO);
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
        tooltip.add(SDTexts.TOOLTIP$RING_DIVINITY.get(
                Math.round(DAMAGE_REDUCTION * 100),
                Math.round(MAX_DAMAGE_RATIO * 100)
        ).formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
