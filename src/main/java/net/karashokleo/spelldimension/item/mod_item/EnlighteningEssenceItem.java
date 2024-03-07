package net.karashokleo.spelldimension.item.mod_item;

import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.misc.AttrModifier;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.util.AttributeUtil;
import net.karashokleo.spelldimension.util.ColorUtil;
import net.karashokleo.spelldimension.util.ParticleUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnlighteningEssenceItem extends SpellEssenceItem
{
    private static final String ENLIGHTENING_ESSENCE = "EnlighteningEssence";

    public EnlighteningEssenceItem()
    {
        super();
    }

    @Override
    public Text getName()
    {
        return Text.translatable(LangData.ENLIGHTENING_ESSENCE);
    }

    @Override
    public Text getName(ItemStack stack)
    {
        MagicSchool school = getSchool(stack);
        MutableText name = new Mage(0, school, null).getSchoolText().append(Text.translatable(LangData.ENLIGHTENING_ESSENCE));
        name.setStyle(Style.EMPTY.withColor(ColorUtil.getItemColor(stack)));
        return name;
    }

    public ItemStack getStack(AttrModifier modifier)
    {
        ItemStack stack = this.getDefaultStack();
        modifier.writeNbt(stack.getOrCreateSubNbt(ENLIGHTENING_ESSENCE));
        return stack;
    }

    public AttrModifier getModifier(ItemStack stack)
    {
        NbtCompound compound = stack.getSubNbt(ENLIGHTENING_ESSENCE);
        return AttrModifier.fromNbt(compound);
    }

    @Nullable
    @Override
    public MagicSchool getSchool(ItemStack stack)
    {
        AttrModifier attrModifier = getModifier(stack);
        if (attrModifier == null) return super.getSchool(stack);
        return AttributeUtil.getSchool(attrModifier.attribute, super.getSchool(stack));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks)
    {
        if (!world.isClient()) return;
        if (remainingUseTicks % 8 != 0) return;
        ParticleUtil.ringParticle(user, remainingUseTicks, 16, ParticleTypes.END_ROD);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        boolean bl = applyEffect(stack, user);
        if (user instanceof PlayerEntity player)
            if (bl) success(stack, player);
            else fail(stack, player);
        return stack;
    }

    private boolean applyEffect(ItemStack essence, LivingEntity entity)
    {
        AttrModifier attrModifier = getModifier(essence);
        if (attrModifier == null) return false;
        return attrModifier.applyToEntity(entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(LangData.TOOLTIP_ESSENCE_USE_2));
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable(LangData.TOOLTIP_EFFECT));

        AttrModifier attrModifier = getModifier(stack);
        if (attrModifier == null)
        {
            tooltip.add(Text.translatable(LangData.TOOLTIP_INVALID));
            return;
        }
        tooltip.add(Text.translatable(LangData.TOOLTIP_MODIFIER).formatted(Formatting.GRAY));
        AttributeUtil.addTooltip(tooltip, attrModifier.attribute, attrModifier.amount, attrModifier.operation);
    }

    @Override
    public int getMaxUseTime(ItemStack stack)
    {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }
}
