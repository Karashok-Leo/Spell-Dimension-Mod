package karashokleo.spell_dimension.mixin.client;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.content.item.QuestScrollItem;
import karashokleo.spell_dimension.content.item.essence.EnchantedEssenceItem;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
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


    @Shadow
    public abstract void drawItem(ItemStack stack, int x, int y, int seed, int z);

    @Inject(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V", at = @At(value = "TAIL"))
    private void drawIcon(LivingEntity entity, World world, ItemStack stack, int x, int y, int seed, int z, CallbackInfo ci)
    {
        if (this.client.player != null && Screen.hasShiftDown() && this.client.currentScreen != null)
        {
            if (stack.getItem() instanceof EnchantedEssenceItem item && stack.hasNbt())
            {
                EnchantedModifier enchantedModifier = item.getModifier(stack);
                if (enchantedModifier != null)
                {
                    Identifier texture = SpellDimension.modLoc("textures/slot/" + enchantedModifier.slot().getName() + ".png");
                    drawTexture(texture, x, y, z + 200, 0, 0, 16, 16, 16, 16);
                    TextRenderer textRenderer = this.client.textRenderer;
                    String threshold = String.valueOf(enchantedModifier.threshold());
                    this.matrices.push();
                    this.matrices.translate(0, 0, 200);
                    drawText(textRenderer, threshold, x + 19 - 2 - textRenderer.getWidth(threshold), y - 1, 0xffffff, true);
                    this.matrices.pop();
                }
            } else if (stack.getItem() instanceof QuestScrollItem scroll)
            {
                scroll.getQuest(stack)
                    .map(Quest::getIcon)
                    .ifPresent(itemStack -> drawItem(itemStack, x, y, seed, z + (itemStack.getItem() instanceof BlockItem ? 5 : 200)));
            }
        }
    }
}
