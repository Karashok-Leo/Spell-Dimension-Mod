package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.content.network.C2SSelectQuest;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Set;

public class QuestScreen extends Screen
{
    private final Hand hand;
    private final List<ItemStack> scrolls;
    private ItemStack current;

    public QuestScreen(Hand hand, Set<RegistryEntry<Quest>> quests)
    {
        super(Text.empty());
        this.hand = hand;
        this.scrolls = quests.stream().map(AllItems.QUEST_SCROLL::getStack).toList();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (this.current.isEmpty() || button != GLFW.GLFW_MOUSE_BUTTON_LEFT)
            return super.mouseClicked(mouseX, mouseY, button);
        Identifier questId = AllItems.QUEST_SCROLL.getQuestId(this.current).orElse(null);
        AllPackets.toServer(new C2SSelectQuest(questId, this.hand));
        this.close();
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (super.keyPressed(keyCode, scanCode, modifiers))
        {
            return true;
        } else if (this.client != null &&
                   this.client.options.inventoryKey.matchesKey(keyCode, scanCode))
        {
            this.close();
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public boolean shouldPause()
    {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        var client = this.client;
        if (client == null) return;
        var player = client.player;
        if (player == null) return;

        super.render(context, mouseX, mouseY, delta);

        var window = client.getWindow();
        int n = scrolls.size();
        int w = (int) Math.ceil(Math.sqrt(n));
        int h = (int) Math.ceil(n * 1d / w);
        var iconSize = 18;
        var startX = window.getScaledWidth() / 2 - w * iconSize / 2;
        var startY = window.getScaledHeight() / 2 - h * iconSize / 2;
        var padding = 4;

        context.fill(
                startX - padding,
                startY - padding,
                startX + iconSize * w + padding,
                startY + iconSize * h + padding,
                0x22000000
        );

        this.current = ItemStack.EMPTY;
        var index = 0;

        for (var stack : this.scrolls)
        {
            int iy = index / w;
            int ix = index - iy * w;
            var stackX = startX + ix * iconSize;
            var stackY = startY + iy * iconSize;

            var scale = 1f;

            if (mouseX > stackX && mouseY > stackY && mouseX <= (stackX + 16) && mouseY <= (stackY + 16))
            {
                scale = 1.3f;
                this.current = stack;
            }

            drawItem(context, client.getItemRenderer(), player, client.world, stack, stackX, stackY, scale);

            index++;
        }

        if (!this.current.isEmpty())
            context.drawTooltip(this.textRenderer, getTooltipFromItem(client, this.current), mouseX, mouseY);
    }

    private void drawItem(DrawContext context, ItemRenderer itemRenderer, @Nullable LivingEntity entity, @Nullable World world, ItemStack stack, int x, int y, float scale)
    {
        if (stack.isEmpty()) return;

        BakedModel bakedModel = itemRenderer.getModel(stack, world, entity, 0);
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate((float) (x + 8), (float) (y + 8), 150f);

        try
        {
            matrices.multiplyPositionMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
            matrices.scale(16.0F, 16.0F, 16.0F);
            matrices.scale(scale, scale, scale);
            boolean bl = !bakedModel.isSideLit();
            if (bl)
            {
                DiffuseLighting.disableGuiDepthLighting();
            }

            itemRenderer.renderItem(stack, ModelTransformationMode.GUI, false, matrices, context.getVertexConsumers(), 15728880, OverlayTexture.DEFAULT_UV, bakedModel);
            context.draw();
            if (bl)
            {
                DiffuseLighting.enableGuiDepthLighting();
            }
        } catch (Throwable throwable)
        {
            CrashReport crashReport = CrashReport.create(throwable, "Rendering item");
            CrashReportSection crashReportSection = crashReport.addElement("Item being rendered");
            crashReportSection.add("Item Type", () -> String.valueOf(stack.getItem()));
            crashReportSection.add("Item Damage", () -> String.valueOf(stack.getDamage()));
            crashReportSection.add("Item NBT", () -> String.valueOf(stack.getNbt()));
            crashReportSection.add("Item Foil", () -> String.valueOf(stack.hasGlint()));
            throw new CrashException(crashReport);
        }

        matrices.pop();
    }

}
