package karashokleo.spell_dimension.content.item.trinket;

import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public class ArcaneThroneItem extends SingleEpicTrinketItem
{
    private static final String ENABLE_KEY = "Enable";

    public ArcaneThroneItem()
    {
        super();
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (entity.age % 20 != 0)
            return;

        EffectHelper.forceAddEffectWithEvent(
                entity,
                new StatusEffectInstance(AllStatusEffects.PHASE, 40, 0),
                entity
        );
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference)
    {
        if (clickType == ClickType.LEFT)
            return false;
        if (!slot.canTakePartial(player))
            return false;
        NbtCompound nbt = stack.getOrCreateNbt();
        boolean enable = nbt.getBoolean(ENABLE_KEY);
        nbt.putBoolean(ENABLE_KEY, !enable);
        return true;
    }
}
