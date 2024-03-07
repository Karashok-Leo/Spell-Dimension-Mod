package net.karashokleo.spelldimension.mixin;

import net.karashokleo.spelldimension.item.mod_item.EnchantedEssenceItem;
import net.karashokleo.spelldimension.misc.EnchantedModifier;
import net.karashokleo.spelldimension.item.mod_item.IMageItem;
import net.karashokleo.spelldimension.util.ColorUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
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
    @Final
    private MatrixStack matrices;

    @Shadow
    public abstract void drawTexture(Identifier texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight);

    @Shadow
    public abstract int drawText(TextRenderer textRenderer, @Nullable String text, int x, int y, int color, boolean shadow);


    @Inject(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V", at = @At(value = "TAIL"))
    private void drawIcon(LivingEntity entity, World world, ItemStack stack, int x, int y, int seed, int z, CallbackInfo ci)
    {
        if (this.client.player != null && Screen.hasShiftDown() && this.client.currentScreen != null)
        {
            if (stack.getItem() instanceof IMageItem item)
            {
                int grade = item.getMage(stack).grade();
                if (grade > 0)
                {
                    TextRenderer textRenderer = this.client.textRenderer;
                    this.matrices.push();
                    this.matrices.translate(0, 0, 200);
                    drawText(textRenderer, String.valueOf(grade), x, y - 1, ColorUtil.getItemColor(stack), true);
                    this.matrices.pop();
                }
            }
            if (stack.getItem() instanceof EnchantedEssenceItem item && stack.hasNbt())
            {
                EnchantedModifier enchantedModifier = item.getModifier(stack);
                if (enchantedModifier != null)
                {
                    drawTexture(enchantedModifier.getSlotTexture(), x, y, 200, 0, 0, 16, 16, 16, 16);
                    TextRenderer textRenderer = this.client.textRenderer;
                    String threshold = String.valueOf(enchantedModifier.threshold);
                    this.matrices.push();
                    this.matrices.translate(0, 0, 200);
                    drawText(textRenderer, threshold, x + 19 - 2 - textRenderer.getWidth(threshold), y - 1, 0xffffff, true);
                    this.matrices.pop();
                }
            }
        }
    }
}
