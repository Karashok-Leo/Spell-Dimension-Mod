package karashokleo.spell_dimension.content.item.trinket;

import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulfireMaskItem extends SingleEpicTrinketItem
{
    public static final int INTERNAL = 20;
    public static final int SPELL_POWER_BONUS = 200;
    public static final int DURATION = 40;
    public static final int RANGE = 16;

    public SoulfireMaskItem()
    {
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (!(entity instanceof PlayerEntity player))
            return;
        if (entity.getWorld().isClient())
        {
            RayTraceUtil.clientUpdateTarget(player, RANGE);
            return;
        }
        if (entity.age % INTERNAL == 0)
            return;
        LivingEntity target = RayTraceUtil.serverGetTarget(player);
        if (target == null)
            return;
        if (ImpactUtil.isAlly(player, target))
            return;
        int amplifier = (int) (SpellPower.getSpellPower(SpellSchools.FIRE, player).baseValue() / SPELL_POWER_BONUS);
        EffectUtil.forceAddEffect(target, new StatusEffectInstance(LHEffects.FLAME, DURATION, amplifier), entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$SOULFIRE_MASK$1.get(INTERNAL / 20, RANGE).formatted(Formatting.RED));
        tooltip.add(SDTexts.TOOLTIP$SOULFIRE_MASK$2.get(SPELL_POWER_BONUS).formatted(Formatting.RED));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
