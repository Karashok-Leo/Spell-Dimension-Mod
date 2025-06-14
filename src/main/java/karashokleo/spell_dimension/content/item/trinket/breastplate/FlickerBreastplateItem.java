package karashokleo.spell_dimension.content.item.trinket.breastplate;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlickerBreastplateItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    private static final float INITIAL_DODGE_CHANCE = 0.8f;
    private static final float DODGE_CHANCE_DECREMENT = 0.01f;
    private static final float MIN_DODGE_CHANCE = 0.1f;

    public FlickerBreastplateItem()
    {
        super();
    }

    private int getTotalEnchantmentLevels(LivingEntity entity)
    {
        int total = 0;
        for (var slot : EquipmentSlot.values())
        {
            ItemStack stack = entity.getEquippedStack(slot);
            var enchantments = EnchantmentHelper.get(stack);
            for (var lv : enchantments.values())
            {
                total += lv;
            }
        }
        return total;
    }

    public float getDodgeChance(LivingEntity entity)
    {
        int totalLevels = getTotalEnchantmentLevels(entity);
        float dodgeChance = INITIAL_DODGE_CHANCE - totalLevels * DODGE_CHANCE_DECREMENT;
        return Math.max(dodgeChance, MIN_DODGE_CHANCE);
    }

    @Override
    public void onAttacked(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
        float dodgeChance = getDodgeChance(entity);
        if (entity.getRandom().nextFloat() < dodgeChance)
        {
            event.setCanceled(true);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$FLICKER_BREASTPLATE.get(
                Math.round(INITIAL_DODGE_CHANCE * 100),
                Math.round(DODGE_CHANCE_DECREMENT * 100),
                Math.round(MIN_DODGE_CHANCE * 100)
        ).formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
