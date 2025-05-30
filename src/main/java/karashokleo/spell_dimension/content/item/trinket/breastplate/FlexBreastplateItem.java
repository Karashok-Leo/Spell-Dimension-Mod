package karashokleo.spell_dimension.content.item.trinket.breastplate;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlexBreastplateItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public static final float SPELL_POWER_RATIO = 0.0006F;
    public static final float ARMOR_RATIO = 0.006F;
    public static final float ARMOR_TOUGHNESS_RATIO = 0.02F;
    public static final float MAX_REDUCTION_RATIO = 0.6F;

    public FlexBreastplateItem()
    {
        super();
    }

    public float getDamageReduction(LivingEntity entity)
    {
        EntityAttributeInstance armorIns = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        EntityAttributeInstance armorToughnessIns = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        double spellPower = SchoolUtil.getEntitySpellPower(entity);
        double armor = armorIns == null ? 0 : armorIns.getValue();
        double armorToughness = armorToughnessIns == null ? 0 : armorToughnessIns.getValue();
        double total = spellPower * SPELL_POWER_RATIO + armor * ARMOR_RATIO + armorToughness * ARMOR_TOUGHNESS_RATIO;
        return MathHelper.clamp((float) total, 0, MAX_REDUCTION_RATIO);
    }

    @Override
    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
        float damageFactor = getDamageReduction(entity);
        event.setAmount(event.getAmount() * (1 - damageFactor));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$FLEX_BREASTPLATE.get(
                "%.2f%%".formatted(SPELL_POWER_RATIO * 100),
                "%.2f%%".formatted(ARMOR_RATIO * 100),
                "%.2f%%".formatted(ARMOR_TOUGHNESS_RATIO * 100),
                "%.1f%%".formatted(MAX_REDUCTION_RATIO * 100)
        ).formatted(Formatting.DARK_RED));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
