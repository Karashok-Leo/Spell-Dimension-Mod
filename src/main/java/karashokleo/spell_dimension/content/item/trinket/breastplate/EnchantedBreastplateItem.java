package karashokleo.spell_dimension.content.item.trinket.breastplate;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class EnchantedBreastplateItem extends SingleEpicTrinketItem
{
    public static final float ARMOR_RATIO = 0.2F;
    public static final float ARMOR_TOUGHNESS_RATIO = 0.05F;

    public EnchantedBreastplateItem()
    {
        super();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
        double spellPower = SchoolUtil.getEntitySpellPower(entity);
        double armor = spellPower * ARMOR_RATIO;
        double armorToughness = spellPower * ARMOR_TOUGHNESS_RATIO;
        if (armor != 0)
            modifiers.put(
                    EntityAttributes.GENERIC_ARMOR,
                    new EntityAttributeModifier(uuid, "Enchanted Breastplate Armor", armor, EntityAttributeModifier.Operation.ADDITION)
            );
        if (armorToughness != 0)
            modifiers.put(
                    EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                    new EntityAttributeModifier(uuid, "Enchanted Breastplate Armor Toughness", armorToughness, EntityAttributeModifier.Operation.ADDITION)
            );
        return modifiers;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$ENCHANTED_BREASTPLATE.get(ARMOR_RATIO, ARMOR_TOUGHNESS_RATIO).formatted(Formatting.DARK_PURPLE));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
