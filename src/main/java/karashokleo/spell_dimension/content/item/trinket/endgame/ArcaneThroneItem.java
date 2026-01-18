package karashokleo.spell_dimension.content.item.trinket.endgame;

import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArcaneThroneItem extends CurseTrinketItem
{
    private static final String ENABLE_KEY = "Enable";
    public static final int LOOT_FACTOR = 3;

    public ArcaneThroneItem()
    {
        super();
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (entity.age % 20 != 0)
        {
            return;
        }

        if (!stack.getOrCreateNbt().getBoolean(ENABLE_KEY))
        {
            return;
        }

        EffectHelper.forceAddEffectWithEvent(
            entity,
            new StatusEffectInstance(AllStatusEffects.PHASE, 40, 0, true, false, true),
            entity
        );
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference)
    {
        if (clickType == ClickType.LEFT)
        {
            return false;
        }
        if (!slot.canTakePartial(player))
        {
            return false;
        }
        NbtCompound nbt = stack.getOrCreateNbt();
        boolean enable = nbt.getBoolean(ENABLE_KEY);
        nbt.putBoolean(ENABLE_KEY, !enable);
        return true;
    }

    @Override
    public double getLootFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return LOOT_FACTOR;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(Text.literal("- ").append(LHTexts.ITEM_CHARM_GREED.get(LOOT_FACTOR)).formatted(Formatting.GOLD));
        tooltip.add(SDTexts.TOOLTIP$ARCANE_THRONE$1.get().formatted(Formatting.LIGHT_PURPLE));
        tooltip.add(SDTexts.TOOLTIP$ARCANE_THRONE$2.get().formatted(Formatting.GRAY));
        boolean enable = stack.getOrCreateNbt().getBoolean(ENABLE_KEY);
        if (enable)
        {
            tooltip.add(SDTexts.TOOLTIP$ARCANE_THRONE$ON.get().formatted(Formatting.GREEN));
        } else
        {
            tooltip.add(SDTexts.TOOLTIP$ARCANE_THRONE$OFF.get().formatted(Formatting.RED));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
