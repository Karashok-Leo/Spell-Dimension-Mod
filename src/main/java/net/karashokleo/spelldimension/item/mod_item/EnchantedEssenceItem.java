package net.karashokleo.spelldimension.item.mod_item;

import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.item.ExtraModifier;
import net.karashokleo.spelldimension.misc.Mage;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantedEssenceItem extends SpellEssenceItem
{
    public EnchantedEssenceItem()
    {
        super();
    }

    public ItemStack getStack(Mage mage, ExtraModifier extraModifier)
    {
        ItemStack stack = this.getDefaultStack();
        mage.writeToStack(stack);
        extraModifier.toNbt(stack.getOrCreateNbt());
        return stack;
    }

    @Override
    protected boolean applyEffect(ItemStack stack, PlayerEntity player)
    {
        ExtraModifier extraModifier = ExtraModifier.fromNbt(stack.getOrCreateNbt());
        if (extraModifier == null) return false;
        else return extraModifier.apply(player.getOffHandStack());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        ExtraModifier extraModifier = ExtraModifier.fromNbt(stack.getOrCreateNbt());
        if (extraModifier == null)
        {
            tooltip.add(Text.translatable(LangData.TOOLTIP_INVALID));
            return;
        }
        tooltip.add(Text.translatable(LangData.ENUM_SLOT + extraModifier.slot.name()).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable(LangData.TOOLTIP_THRESHOLD, extraModifier.threshold).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable(LangData.TOOLTIP_MODIFIER).formatted(Formatting.GRAY));

        double d = extraModifier.value;
        double e = extraModifier.operation == EntityAttributeModifier.Operation.MULTIPLY_BASE || extraModifier.operation == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? d * 100.0 : (extraModifier.attribute.equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? d * 10.0 : d);
        if (d > 0.0)
        {
            tooltip.add(Text.translatable("attribute.modifier.plus." + extraModifier.operation.getId(), ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(extraModifier.attribute.getTranslationKey())).formatted(Formatting.BLUE));
        } else if (d < 0.0)
            tooltip.add(Text.translatable("attribute.modifier.take." + extraModifier.operation.getId(), ItemStack.MODIFIER_FORMAT.format(e * -1.0), Text.translatable(extraModifier.attribute.getTranslationKey())).formatted(Formatting.RED));
    }

    @Override
    public Text getName()
    {
        return Text.translatable(LangData.ENCHANTED_ESSENCE);
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return Mage.readFromStack(stack).getMageTitle(Text.translatable(LangData.ENCHANTED_ESSENCE));
    }
}