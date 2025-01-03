package karashokleo.spell_dimension.content.item.trinket;

import dev.emi.trinkets.api.TrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlexBreastplateItem extends TrinketItem
{
    public static final double SPELL_POWER_RATIO = 0.1;
    public static final double ARMOR_RATIO = 0.4;
    public static final double ARMOR_TOUGHNESS_RATIO = 1;

    public FlexBreastplateItem()
    {
        super(
                new FabricItemSettings()
                        .maxCount(1)
                        .fireproof()
                        .rarity(Rarity.EPIC)
        );
    }

    public float getDamageFactor(LivingEntity entity)
    {
        EntityAttributeInstance armorIns = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        EntityAttributeInstance armorToughnessIns = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        double spellPower = SchoolUtil.getEntitySpellPower(entity);
        double armor = armorIns == null ? 0 : armorIns.getValue();
        double armorToughness = armorToughnessIns == null ? 0 : armorToughnessIns.getValue();
        double total = spellPower * SPELL_POWER_RATIO + armor * ARMOR_RATIO + armorToughness * ARMOR_TOUGHNESS_RATIO;
        return MathHelper.clamp(40 / (float) total, 0, 0.5F);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$FLEX_BREASTPLATE.get(
                "%d%%".formatted((int) (SPELL_POWER_RATIO * 100)),
                "%d%%".formatted((int) (ARMOR_RATIO * 100)),
                "%d%%".formatted((int) (ARMOR_TOUGHNESS_RATIO * 100))
        ).formatted(Formatting.DARK_RED));
    }
}
