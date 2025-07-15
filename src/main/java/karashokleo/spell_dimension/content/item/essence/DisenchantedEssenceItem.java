package karashokleo.spell_dimension.content.item.essence;

import karashokleo.spell_dimension.content.item.essence.base.StackClickEssenceItem;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.ParticleUtil;
import karashokleo.spell_dimension.util.SoundUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisenchantedEssenceItem extends StackClickEssenceItem
{
    private static final String STORAGE_KEY = "Storage";
    private static final String LEVEL_KEY = "Level";

    public DisenchantedEssenceItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    protected boolean applyEffect(ItemStack essence, ItemStack target, PlayerEntity player)
    {
        NbtCompound stored = essence.getSubNbt(STORAGE_KEY);
        if (stored == null)
        {
            NbtCompound removed = EnchantedModifier.remove(target);
            if (removed == null)
            {
                return false;
            }
            essence.getOrCreateNbt().put(STORAGE_KEY, removed);
        } else
        {
            EnchantedModifier.overwrite(target, stored);
            if (!player.getAbilities().creativeMode)
            {
                essence.decrement(1);
            }
        }
        return true;
    }

    @Override
    protected void success(ItemStack essence, PlayerEntity player)
    {
        ParticleUtil.ringParticleEmit(player, 4 * 30, 5, getParticle(essence));
        player.sendMessage(SDTexts.TEXT$ESSENCE$SUCCESS.get(), true);
        SoundUtil.playSound(player, SoundUtil.ANVIL);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$DISENCHANT.get().formatted(Formatting.GRAY));
        NbtCompound extraModifiers = stack.getSubNbt(STORAGE_KEY);
        if (extraModifiers == null ||
            !extraModifiers.contains(LEVEL_KEY))
        {
            return;
        }
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(SDTexts.TOOLTIP$STORED_ENCHANTED_LEVEL.get(extraModifiers.getInt(LEVEL_KEY)).formatted(Formatting.BOLD));
    }
}
