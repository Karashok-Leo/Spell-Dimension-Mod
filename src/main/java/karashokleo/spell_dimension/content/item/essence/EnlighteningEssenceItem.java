package karashokleo.spell_dimension.content.item.essence;

import karashokleo.spell_dimension.config.AttributeColorConfig;
import karashokleo.spell_dimension.content.item.essence.base.RightPressEssenceItem;
import karashokleo.spell_dimension.content.object.EnlighteningModifier;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.ParticleUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class EnlighteningEssenceItem extends RightPressEssenceItem
{
    public EnlighteningEssenceItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public int getColor(ItemStack stack)
    {
        EnlighteningModifier modifier = this.getModifier(stack);
        return AttributeColorConfig.get(modifier == null ? null : modifier.attribute());
    }

    @Override
    public ParticleEffect getParticle(ItemStack stack)
    {
        EnlighteningModifier modifier = this.getModifier(stack);
        SpellSchool school = modifier == null ? null : SchoolUtil.getAttributeSchool(modifier.attribute());
        return ParticleUtil.getParticle(school);
    }

    @Override
    public Text getName()
    {
        return SDTexts.TEXT$ESSENCE$ENLIGHTENING.get();
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return SDTexts.TEXT$ESSENCE$ENLIGHTENING.get().setStyle(Style.EMPTY.withColor(this.getColor(stack)));
    }

    public ItemStack getStack(EnlighteningModifier modifier)
    {
        ItemStack stack = this.getDefaultStack();
        modifier.writeNbt(stack.getOrCreateSubNbt(EnlighteningModifier.NBT_KEY));
        return stack;
    }

    @Nullable
    public EnlighteningModifier getModifier(ItemStack stack)
    {
        NbtCompound compound = stack.getSubNbt(EnlighteningModifier.NBT_KEY);
        return EnlighteningModifier.fromNbt(compound);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        boolean bl = applyEffect(stack, user);
        if (user instanceof PlayerEntity player)
        {
            if (bl)
            {
                success(stack, player);
            } else
            {
                fail(stack, player);
            }
        }
        return stack;
    }

    protected boolean applyEffect(ItemStack essence, LivingEntity entity)
    {
        EnlighteningModifier enlighteningModifier = getModifier(essence);
        if (enlighteningModifier == null)
        {
            return false;
        }
        return enlighteningModifier.applyToEntityOrPlayer(entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);

        EnlighteningModifier enlighteningModifier = getModifier(stack);
        if (enlighteningModifier == null)
        {
            tooltip.add(SDTexts.TOOLTIP$INVALID.get().formatted(Formatting.RED));
            return;
        }
        tooltip.add(SDTexts.TOOLTIP$MODIFIER.get().formatted(Formatting.GRAY));
        AttributeUtil.addTooltip(tooltip, enlighteningModifier.attribute(), enlighteningModifier.amount(), enlighteningModifier.operation());
    }
}
