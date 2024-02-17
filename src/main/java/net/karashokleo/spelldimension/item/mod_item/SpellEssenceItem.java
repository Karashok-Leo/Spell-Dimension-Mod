package net.karashokleo.spelldimension.item.mod_item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.util.ParticleUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpellEssenceItem extends Item
{
    private static final int COOL_DOWN = 40;

    public SpellEssenceItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (hand == Hand.OFF_HAND ||
                user.getOffHandStack().isEmpty() ||
                MageComponent.get(user).greaterThan(Mage.readFromStack(stack)))
            return TypedActionResult.fail(stack);
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
        if (user instanceof PlayerEntity player)
        {
            if (applyEffect(stack, player))
            {
                Mage mage = Mage.readFromStack(stack);
                ParticleUtil.ringParticleEmit(player, (mage.grade() + 1) * 30, 5, mage.school());
                player.sendMessage(Text.translatable(LangData.TITLE_SUCCESS), true);
                if (!player.isCreative())
                    stack.decrement(1);
            } else player.sendMessage(Text.translatable(LangData.TITLE_FAILURE), true);
            player.getItemCooldownManager().set(this, COOL_DOWN);
        }
        return stack;
    }

    protected abstract boolean applyEffect(ItemStack stack, PlayerEntity player);

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return Mage.readFromStack(stack).grade() > 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(LangData.TOOLTIP_REQUIRE).formatted(Formatting.GRAY));
        tooltip.add(Mage.readFromStack(stack).getMageTitle(Text.translatable(LangData.MAGE)));
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable(LangData.TOOLTIP_ESSENCE_USE));
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable(LangData.TOOLTIP_EFFECT));
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