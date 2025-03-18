package karashokleo.spell_dimension.content.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.UuidUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.spell_power.api.SpellPowerMechanics;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SpellPrismItem extends Item
{
    private static final UUID HASTE_DECAY = UuidUtil.getUUIDFromString("SpellPrism");
    private final Multimap<EntityAttribute, EntityAttributeModifier> modifiers;

    public SpellPrismItem()
    {
        super(
                new FabricItemSettings()
                        .rarity(Rarity.UNCOMMON)
                        .maxCount(1)
                        .maxDamage(4)
        );
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        EntityAttributeModifier modifier = new EntityAttributeModifier(HASTE_DECAY, "Spell Prism", -0.9, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        builder.put(SpellPowerMechanics.HASTE.attribute, modifier);
        this.modifiers = builder.build();
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot)
    {
        return slot == EquipmentSlot.OFFHAND ?
                this.modifiers :
                super.getAttributeModifiers(slot);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable("item.modifiers.offhand").formatted(Formatting.GRAY));
        tooltip.add(SDTexts.TOOLTIP$SPELL_PRISM.get().formatted(Formatting.GRAY));
        tooltip.add(SDTexts.TOOLTIP$SPELL_PRISM_ADVANCED.get().formatted(Formatting.GRAY));
    }
}
