package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.data.LangData;
import karashokleo.spell_dimension.content.misc.EnchantedModifier;
import karashokleo.spell_dimension.content.misc.Mage;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.ColorUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantedEssenceItem extends StackClickEssenceItem
{
    private static final String ENCHANTED_ESSENCE = "EnchantedEssence";

    public EnchantedEssenceItem()
    {
        super();
    }

    @Override
    public Text getName()
    {
        return Text.translatable(LangData.ENCHANTED_ESSENCE);
    }

    @Override
    public Text getName(ItemStack stack)
    {
        SpellSchool school = getSchool(stack);
        MutableText name = new Mage(0, school, null).getSchoolText().append(Text.translatable(LangData.ENCHANTED_ESSENCE));
        name.setStyle(Style.EMPTY.withColor(ColorUtil.getItemColor(stack)));
        return name;
    }

    public ItemStack getStack(EnchantedModifier enchantedModifier)
    {
        ItemStack stack = this.getDefaultStack();
        enchantedModifier.toNbt(stack.getOrCreateSubNbt(ENCHANTED_ESSENCE));
        return stack;
    }

    @Nullable
    public EnchantedModifier getModifier(ItemStack stack)
    {
        NbtCompound compound = stack.getSubNbt(ENCHANTED_ESSENCE);
        return EnchantedModifier.fromNbt(compound);
    }

    @Nullable
    @Override
    public SpellSchool getSchool(ItemStack stack)
    {
        EnchantedModifier modifier = getModifier(stack);
        if (modifier == null) return super.getSchool(stack);
        return AttributeUtil.getSchool(modifier.modifier.attribute, super.getSchool(stack));
    }

    @Override
    protected boolean applyEffect(ItemStack essence, ItemStack target)
    {
        EnchantedModifier enchantedModifier = getModifier(essence);
        if (enchantedModifier == null) return false;
        else return enchantedModifier.apply(target);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        EnchantedModifier enchantedModifier = getModifier(stack);
        if (enchantedModifier == null)
        {
            tooltip.add(Text.translatable(LangData.TOOLTIP_INVALID));
            return;
        }
        tooltip.add(Text.translatable(LangData.ENUM_SLOT + enchantedModifier.slot.name()).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable(LangData.TOOLTIP_THRESHOLD, enchantedModifier.threshold).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable(LangData.TOOLTIP_MODIFIER).formatted(Formatting.GRAY));
        AttributeUtil.addTooltip(tooltip, enchantedModifier.modifier.attribute, enchantedModifier.modifier.amount, enchantedModifier.modifier.operation);
    }
}