package net.karashokleo.spelldimension.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.karashokleo.spelldimension.item.mod_item.EnchantedEssenceItem;
import net.karashokleo.spelldimension.item.ExtraModifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin
{
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    public abstract void drawTexture(Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight);

    @Shadow
    public abstract void drawItem(ItemStack stack, int x, int y, int seed, int z);

    @Inject(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V", at = @At(value = "TAIL"))
    private void drawIcon(LivingEntity entity, World world, ItemStack stack, int x, int y, int seed, int z, CallbackInfo ci)
    {
        if (this.client.player != null && Screen.hasShiftDown() && this.client.currentScreen != null)
        {
            if (stack.getItem() instanceof EnchantedEssenceItem)
            {
                ExtraModifier extraModifier = ExtraModifier.fromNbt(stack.getOrCreateNbt());
                if (extraModifier == null) return;
                drawTexture(extraModifier.getSlotTexture(), x, y, 200, 0, 0, 16, 16, 16, 16);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
}
