package net.karashokleo.spelldimension.item.mod_item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.karashokleo.spelldimension.config.AllConfig;
import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.util.ParticleUtil;
import net.karashokleo.spelldimension.util.SoundUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpellEssenceItem extends Item
{
    public SpellEssenceItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (hand == Hand.MAIN_HAND &&
                !user.getOffHandStack().isEmpty() &&
                canUse(user, stack))
        {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        } else return TypedActionResult.fail(stack);
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
            if (!player.getOffHandStack().isEmpty() &&
                    applyEffect(stack, player.getOffHandStack()))
                success(stack, player);
            else player.sendMessage(Text.translatable(LangData.TITLE_FAILURE), true);
        return stack;
    }

    protected abstract boolean applyEffect(ItemStack essence, ItemStack target);

    protected boolean canUse(PlayerEntity player, ItemStack essence)
    {
        return true;
    }

    protected MagicSchool getSchool(ItemStack stack)
    {
        return MagicSchool.PHYSICAL_MELEE;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player)
    {
        if (clickType == ClickType.RIGHT &&
                !player.getItemCooldownManager().isCoolingDown(stack.getItem()) &&
                !slot.getStack().isEmpty() &&
                canUse(player, stack) &&
                applyEffect(stack, slot.getStack()))
        {
            success(stack, player);
            return true;
        } else return false;
    }

    public void success(ItemStack essence, PlayerEntity player)
    {
        player.getItemCooldownManager().set(this, AllConfig.INSTANCE.spell_essence_cool_down);
        ParticleUtil.ringParticleEmit(player, 4 * 30, 5, getSchool(essence));
        SoundUtil.playSound(player, getSchool(essence));
        player.sendMessage(Text.translatable(LangData.TITLE_SUCCESS), true);
        if (!player.isCreative()) essence.decrement(1);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
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