package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
    @Shadow
    public abstract Item getItem();

    @Inject(
        method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;decrement(I)V"
        )
    )
    public <T extends LivingEntity> void inject_damage_safeguard(int amount, T entity, Consumer<T> breakCallback, CallbackInfo ci)
    {
        if (!(entity instanceof PlayerEntity player))
        {
            return;
        }
        Item item = this.getItem();
        if (item instanceof Equipment equipment)
        {
            ItemStack broken = AllItems.BROKEN_ITEM.saveItem((ItemStack) (Object) this);
            EquipmentSlot slotType = equipment.getSlotType();
            player.equipStack(slotType, broken);
        } else if (item instanceof ToolItem)
        {
            ItemStack broken = AllItems.BROKEN_ITEM.saveItem((ItemStack) (Object) this);
            player.getInventory().offerOrDrop(broken);
        }
    }
}
