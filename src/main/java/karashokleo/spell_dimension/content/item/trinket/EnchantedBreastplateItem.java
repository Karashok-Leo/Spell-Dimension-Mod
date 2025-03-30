package karashokleo.spell_dimension.content.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class EnchantedBreastplateItem extends TrinketItem
{
    public static final float MAX_HEALTH_RATIO = 0.1F;
    public static final float ARMOR_RATIO = 0.2F;
    public static final float ARMOR_TOUGHNESS_RATIO = 0.05F;

    public EnchantedBreastplateItem()
    {
        super(
                new FabricItemSettings()
                        .maxCount(1)
                        .fireproof()
                        .rarity(Rarity.EPIC)
        );
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
        double maxHealth = 0;
        double armor = 0;
        double armorToughness = 0;
        if (entity instanceof PlayerEntity player)
        {
            double spellPower = SchoolUtil.getEntitySpellPower(player);
            maxHealth = spellPower * MAX_HEALTH_RATIO;
            armor = spellPower * ARMOR_RATIO;
            armorToughness = spellPower * ARMOR_TOUGHNESS_RATIO;
        }
        if (maxHealth != 0)
            modifiers.put(
                    EntityAttributes.GENERIC_MAX_HEALTH,
                    new EntityAttributeModifier(uuid, "Enchanted Breastplate Max Health", maxHealth, EntityAttributeModifier.Operation.ADDITION)
            );
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
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$ENCHANTED_BREASTPLATE.get(MAX_HEALTH_RATIO, ARMOR_RATIO, ARMOR_TOUGHNESS_RATIO).formatted(Formatting.DARK_PURPLE));
    }
}
