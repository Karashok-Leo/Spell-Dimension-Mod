package karashokleo.spell_dimension.content.item.essence.base;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.ParticleUtil;
import karashokleo.spell_dimension.util.SoundUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class RightPressEssenceItem extends SpellEssenceItem
{
    protected RightPressEssenceItem(Settings settings)
    {
        super(settings);
    }

    protected abstract boolean applyEffect(ItemStack essence, LivingEntity entity);

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        boolean bl = applyEffect(stack, user);
        if (user instanceof PlayerEntity player)
            if (bl) success(stack, player);
            else fail(stack, player);
        return stack;
    }

    @Override
    protected void success(ItemStack essence, PlayerEntity player)
    {
        super.success(essence, player);
        SoundUtil.playSound(player, SoundUtil.LEVEL_UP);
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
        if (remainingUseTicks % 4 != 0) return;
        ParticleUtil.ringParticle(user, remainingUseTicks, 16, ParticleTypes.END_ROD);
    }

    @Override
    public int getMaxUseTime(ItemStack stack)
    {
        return 16;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$USE$PRESS.get());
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(SDTexts.TOOLTIP$EFFECT.get());
    }
}
