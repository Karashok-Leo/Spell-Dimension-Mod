package karashokleo.spell_dimension.mixin.modded;

import karashokleo.l2hostility.content.item.consumable.BookEverything;
import karashokleo.spell_dimension.content.component.EndStageComponent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BookEverything.class)
public abstract class BookEverythingMixin
{
    @Inject(
            method = "use",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir)
    {
        if (world.isClient()) return;
        if (EndStageComponent.canEnterEnd(user)) return;
        user.sendMessage(SDTexts.TEXT$END_STAGE$BOOK_EVERYTHING.get(AllItems.CELESTIAL_LUMINARY.getName()), true);
        cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
    }
}
