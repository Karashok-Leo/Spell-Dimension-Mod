package karashokleo.spell_dimension.client.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.api.PatchouliLookupCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import vazkii.patchouli.client.base.ClientTicker;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.ClientBookRegistry;
import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.common.base.PatchouliConfig;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.util.ItemStackUtil;

public class FixedTooltipHandler
{
    private static float lexiconLookupTime = 0;

    public static void onTooltip(DrawContext graphics, ItemStack stack, int mouseX, int mouseY)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        int tooltipX = mouseX;
        int tooltipY = mouseY - 4;

        if (mc.player != null && !(mc.currentScreen instanceof GuiBook))
        {
            int lexSlot = -1;
            ItemStack lexiconStack = ItemStack.EMPTY;
            Pair<BookEntry, Integer> lexiconEntry = null;

            for (int i = 0; i < PlayerInventory.getHotbarSize(); i++)
            {
                ItemStack stackAt = mc.player.getInventory().getStack(i);
                if (!stackAt.isEmpty())
                {
                    Book book = ItemStackUtil.getBookFromStack(stackAt);
                    if (book != null)
                    {
                        Pair<Identifier, Integer> pair = PatchouliLookupCallback.EVENT.invoker().lookupEntry(stack);
                        if (pair != null)
                        {
                            BookEntry bookEntry = book.getContents().entries.get(pair.getFirst());
                            if (bookEntry != null && !bookEntry.isLocked())
                            {
                                lexiconStack = stackAt;
                                lexSlot = i;
                                lexiconEntry = Pair.of(bookEntry, pair.getSecond());
                                break;
                            }
                        }

                        Pair<BookEntry, Integer> entry = book.getContents().getEntryForStack(stack);

                        if (entry != null && !entry.getFirst().isLocked())
                        {
                            lexiconStack = stackAt;
                            lexSlot = i;
                            lexiconEntry = entry;
                            break;
                        }
                    }
                }
            }

            if (lexSlot > -1)
            {
                int x = tooltipX - 34;
                RenderSystem.disableDepthTest();

                graphics.fill(RenderLayer.getGuiOverlay(), x - 4, tooltipY - 4, x + 20, tooltipY + 26, 0x44000000);
                graphics.fill(RenderLayer.getGuiOverlay(), x - 6, tooltipY - 6, x + 22, tooltipY + 28, 0x44000000);

                if (PatchouliConfig.get().useShiftForQuickLookup() ? Screen.hasShiftDown() : Screen.hasControlDown())
                {
                    lexiconLookupTime += ClientTicker.delta;

                    int cx = x + 8;
                    int cy = tooltipY + 8;
                    float r = 12;
                    float requiredTime = Math.max(PatchouliConfig.get().quickLookupTime(), 0);
                    float angles = Math.min(lexiconLookupTime / (requiredTime > 1F ? requiredTime - 1F : requiredTime), 1F) * 360F;

                    RenderSystem.enableBlend();
                    RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                    final float a = 0.5F + 0.2F * ((float) Math.cos(ClientTicker.total / 10) * 0.5F + 0.5F);

                    VertexConsumer buf = graphics.getVertexConsumers().getBuffer(RenderLayer.getGuiOverlay());
                    final double PI_mul = Math.PI / 180F;
                    for (float i = 1; i < angles; i += 2)
                    { //to render in guiOverlay buffer [has DrawMode.QUAD]
                        float ti = i - 90;
                        buf.vertex(cx, cy, 0).color(0F, 0.5F, 0F, a).next();//base vertex
                        double rad = ti-- * PI_mul;
                        buf.vertex(cx + Math.cos(rad) * r, cy + Math.sin(rad) * r, 0).color(0F, 1F, 0F, 1F).next();
                        rad = ti-- * PI_mul;
                        buf.vertex(cx + Math.cos(rad) * r, cy + Math.sin(rad) * r, 0).color(0F, 1F, 0F, 1F).next();
                        rad = ti * PI_mul;
                        buf.vertex(cx + Math.cos(rad) * r, cy + Math.sin(rad) * r, 0).color(0F, 1F, 0F, 1F).next();
                    }
                    graphics.draw();

					/*BufferBuilder buf = Tesselator.getInstance().getBuilder();
					buf.begin(Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
					buf.vertex(cx, cy, 0).color(0F, 0.5F, 0F, a).endVertex();
					for (float i = angles; i > 0; i--) {
						double rad = (i - 90) / 180F * Math.PI;
						buf.vertex(cx + Math.cos(rad) * r, cy + Math.sin(rad) * r, 0).color(0F, 1F, 0F, 1F).endVertex();
					}

					buf.vertex(cx, cy, 0).color(0F, 1F, 0F, 0F).endVertex();
					Tesselator.getInstance().end();*/


                    RenderSystem.disableBlend();

                    if (lexiconLookupTime >= requiredTime)
                    {
                        mc.player.getInventory().selectedSlot = lexSlot;
                        int spread = lexiconEntry.getSecond();
                        ClientBookRegistry.INSTANCE.displayBookGui(lexiconEntry.getFirst().getBook().id, lexiconEntry.getFirst().getId(), spread * 2);
                    }
                } else
                {
                    lexiconLookupTime = 0F;
                }

                graphics.getMatrices().push();
                graphics.getMatrices().translate(0, 0, 300);
                graphics.drawItem(lexiconStack, x, tooltipY);
                graphics.drawItemInSlot(mc.textRenderer, lexiconStack, x, tooltipY);
                graphics.getMatrices().pop();

                graphics.getMatrices().push();
                graphics.getMatrices().translate(0, 0, 500);
                graphics.drawText(mc.textRenderer, "?", x + 10, tooltipY + 8, 0xFFFFFFFF, true);

                graphics.getMatrices().scale(0.5F, 0.5F, 1F);
                boolean mac = MinecraftClient.IS_SYSTEM_MAC;
                Text key = Text.literal(PatchouliConfig.get().useShiftForQuickLookup() ? "Shift" : mac ? "Cmd" : "Ctrl")
                    .formatted(Formatting.BOLD);
                graphics.drawText(mc.textRenderer, key, (x + 10) * 2 - 16, (tooltipY + 8) * 2 + 20, 0xFFFFFFFF, true);
                graphics.getMatrices().pop();

                RenderSystem.enableDepthTest();
            } else
            {
                lexiconLookupTime = 0F;
            }
        } else
        {
            lexiconLookupTime = 0F;
        }
    }
}
