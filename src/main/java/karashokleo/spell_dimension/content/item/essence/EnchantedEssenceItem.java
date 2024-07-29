package karashokleo.spell_dimension.content.item.essence;

import karashokleo.spell_dimension.config.AttributeColorConfig;
import karashokleo.spell_dimension.content.item.essence.base.StackClickEssenceItem;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.ParticleUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantedEssenceItem extends StackClickEssenceItem
{
    private static final String ENCHANTED_MODIFIERS = "EnchantedModifiers";

    public EnchantedEssenceItem()
    {
        super();
    }

    @Override
    public int getColor(ItemStack stack)
    {
        EnchantedModifier modifier = this.getModifier(stack);
        return AttributeColorConfig.get(modifier == null ? null : modifier.modifier().attribute());
    }

    @Override
    public ParticleEffect getParticle(ItemStack stack)
    {
        EnchantedModifier modifier = this.getModifier(stack);
        SpellSchool school = modifier == null ? null : SchoolUtil.getAttributeSchool(modifier.modifier().attribute());
        return ParticleUtil.getParticle(school);
    }

    @Override
    public Text getName()
    {
        return SDTexts.TEXT_ESSENCE_ENCHANTED.get();
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return SDTexts.TEXT_ESSENCE_ENCHANTED.get().setStyle(Style.EMPTY.withColor(this.getColor(stack)));
    }

    public ItemStack getStack(EnchantedModifier enchantedModifier)
    {
        ItemStack stack = this.getDefaultStack();
        enchantedModifier.toNbt(stack.getOrCreateSubNbt(ENCHANTED_MODIFIERS));
        return stack;
    }

    @Nullable
    public EnchantedModifier getModifier(ItemStack stack)
    {
        NbtCompound compound = stack.getSubNbt(ENCHANTED_MODIFIERS);
        return EnchantedModifier.fromNbt(compound);
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
            tooltip.add(SDTexts.TOOLTIP_INVALID.get());
            return;
        }
        tooltip.add(SDTexts.getSlotText(enchantedModifier.slot()).formatted(Formatting.GRAY));
        tooltip.add(SDTexts.TOOLTIP_THRESHOLD.get(enchantedModifier.threshold()).formatted(Formatting.GRAY));
        tooltip.add(SDTexts.TOOLTIP_MODIFIER.get().formatted(Formatting.GRAY));
        AttributeUtil.addTooltip(tooltip, enchantedModifier.modifier().attribute(), enchantedModifier.modifier().amount(), enchantedModifier.modifier().operation());
    }
}