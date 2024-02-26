package net.karashokleo.spelldimension.item.mod_item;

import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.misc.ExtraModifier;
import net.karashokleo.spelldimension.misc.Mage;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantedEssenceItem extends SpellEssenceItem
{
    public EnchantedEssenceItem()
    {
        super();
    }

    public ItemStack getStack(ExtraModifier extraModifier)
    {
        ItemStack stack = this.getDefaultStack();
        extraModifier.toNbt(stack.getOrCreateNbt());
        return stack;
    }

    @Override
    protected boolean applyEffect(ItemStack essence, ItemStack target)
    {
        ExtraModifier extraModifier = ExtraModifier.fromNbt(essence.getOrCreateNbt());
        if (extraModifier == null) return false;
        else return extraModifier.apply(target);
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
        MagicSchool school = getSchool(stack);
        MutableText name = new Mage(0, school, null).getSchoolText().append(Text.translatable(LangData.ENCHANTED_ESSENCE));
        if (school != null)
            name.setStyle(Style.EMPTY.withColor(school.color()));
        return name;
    }

    @Override
    public MagicSchool getSchool(ItemStack stack)
    {
        MagicSchool school = super.getSchool(stack);
        ExtraModifier extraModifier = ExtraModifier.fromNbt(stack.getNbt());
        if (extraModifier != null)
            for (MagicSchool school_ : MagicSchool.values())
                if (school_.attributeId().toString().equals(extraModifier.getAttributeId()))
                    school = school_;
        return school;
    }
}