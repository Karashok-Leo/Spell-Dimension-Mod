package net.karashokleo.spelldimension.mixin.phase;

import net.karashokleo.spelldimension.SpellDimensionClient;
import net.karashokleo.spelldimension.misc.INoClip;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin
{
    @Final
    @Shadow
    private MinecraftClient client;

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"
            )
    )
    private void injectedRender(DrawContext context, float tickDelta, CallbackInfo ci)
    {
        if (INoClip.noClip(client.player))
            this.renderOverlay(context, SpellDimensionClient.PHASE_LAYER, 0.5F);
    }
}
