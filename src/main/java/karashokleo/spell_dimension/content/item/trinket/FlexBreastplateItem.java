package karashokleo.spell_dimension.content.item.trinket;

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
    public static final float SPELL_POWER_RATIO = 0.1F;
    public static final float ARMOR_RATIO = 0.4F;
    public static final float ARMOR_TOUGHNESS_RATIO = 1F;
    public static final float MAX_REDUCTION_RATIO = 0.6F;

    public FlexBreastplateItem()
    {
        super();
    }

    public float getDamageFactor(LivingEntity entity)
    {
        EntityAttributeInstance armorIns = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        EntityAttributeInstance armorToughnessIns = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        double spellPower = SchoolUtil.getEntitySpellPower(entity);
        double armor = armorIns == null ? 0 : armorIns.getValue();
        double armorToughness = armorToughnessIns == null ? 0 : armorToughnessIns.getValue();
        double total = spellPower * SPELL_POWER_RATIO + armor * ARMOR_RATIO + armorToughness * ARMOR_TOUGHNESS_RATIO;
        return MathHelper.clamp(40 / (float) total, 1.0F - MAX_REDUCTION_RATIO, 1.0F);
    }

    @Override
    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
        float damageFactor = getDamageFactor(entity);
        event.setAmount(event.getAmount() * damageFactor);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$FLEX_BREASTPLATE.get(
                "%d%%".formatted((int) (SPELL_POWER_RATIO * 100)),
                "%d%%".formatted((int) (ARMOR_RATIO * 100)),
                "%d%%".formatted((int) (ARMOR_TOUGHNESS_RATIO * 100)),
                "%d%%".formatted((int) (MAX_REDUCTION_RATIO * 100))
        ).formatted(Formatting.DARK_RED));
    }
}
