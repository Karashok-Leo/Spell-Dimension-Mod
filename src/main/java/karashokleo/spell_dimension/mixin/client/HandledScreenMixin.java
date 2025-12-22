package karashokleo.spell_dimension.mixin.client;

import karashokleo.spell_dimension.client.misc.FixedTooltipHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fix from <a href="https://github.com/VazkiiMods/Patchouli/pull/789">pull request</a>
 *
 * @param <T>
 */
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler>
{
    @Shadow
    @Nullable
    protected Slot focusedSlot;

    @Shadow
    public abstract T getScreenHandler();

    @Inject(at = @At("HEAD"), method = "drawMouseoverTooltip")
    public void patchouli_onRenderTooltip(DrawContext guiGraphics, int x, int y, CallbackInfo info)
    {
        if (getScreenHandler().getCursorStack().isEmpty() &&
            focusedSlot != null &&
            focusedSlot.hasStack())
        {
            FixedTooltipHandler.onTooltip(guiGraphics, this.focusedSlot.getStack(), x, y);
        }
    }
}
