package karashokleo.spell_dimension.client.screen;

import com.google.common.collect.Lists;
import karashokleo.l2hostility.client.MobTraitRenderer;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.spell_dimension.content.item.SoulAlbumItem;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.C2SSelectSoulAlbum;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class SoulAlbumScreen extends Screen
{
    private final Hand hand;
    private final List<MobEntity> mobs;
    private final List<Text> tooltip;
    private int currentIndex;

    public SoulAlbumScreen(Hand hand)
    {
        super(Text.empty());
        this.hand = hand;
        this.mobs = collectMobsInAlbum();
        this.tooltip = Lists.newArrayList();
        this.currentIndex = -1;
    }

    private List<MobEntity> collectMobsInAlbum()
    {
        var client = MinecraftClient.getInstance();
        if (client.player == null)
        {
            return List.of();
        }
        ItemStack album = client.player.getStackInHand(hand);
        if (!(album.getItem() instanceof SoulAlbumItem))
        {
            return List.of();
        }
        NbtCompound albumNbt = album.getNbt();
        if (albumNbt == null)
        {
            return List.of();
        }
        NbtList list = SoulAlbumItem.getStorageList(albumNbt);
        if (list == null)
        {
            return List.of();
        }
        World world = client.world;
        return list.stream()
            .map(e ->
            {
                MobEntity mob = SoulControl.loadMinionFromData((NbtCompound) e, world);
                mob.getCommandTags().add(MobTraitRenderer.FLAG);
                return mob;
            })
            .toList();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (this.currentIndex == -1 || button != GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        AllPackets.toServer(new C2SSelectSoulAlbum(this.currentIndex, this.hand));
        this.close();
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (super.keyPressed(keyCode, scanCode, modifiers))
        {
            return true;
        }
        if (this.client != null &&
            this.client.options.inventoryKey.matchesKey(keyCode, scanCode))
        {
            this.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldPause()
    {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        if (this.client == null || this.client.player == null)
        {
            return;
        }
        super.render(context, mouseX, mouseY, delta);

        var window = client.getWindow();
        int n = mobs.size();
        int w = (int) Math.ceil(Math.sqrt(n));
        int h = (int) Math.ceil(n * 1d / w);
        int margin = 4;
        int padding = 12;
        int mobSize = 36;
        int cellSize = 48;
        int slotSize = cellSize + 2 * padding;
        int startX = window.getScaledWidth() / 2 - w * slotSize / 2;
        int startY = window.getScaledHeight() / 2 - h * slotSize / 2;

        context.fill(
            startX - margin,
            startY - margin,
            startX + slotSize * w + margin,
            startY + slotSize * h + margin,
            0x66000000
        );

        this.currentIndex = -1;
        int index = 0;

        for (MobEntity mob : this.mobs)
        {
            int iy = index / w;
            int ix = index - iy * w;
            int slotX = startX + ix * slotSize;
            int slotY = startY + iy * slotSize;
            int cellX = slotX + padding;
            int cellY = slotY + padding;

            float maxDim = Math.max(mob.getWidth(), mob.getHeight());
            int size = Math.round(mobSize / maxDim);

            if (mouseX > slotX &&
                mouseY > slotY &&
                mouseX <= (slotX + slotSize) &&
                mouseY <= (slotY + slotSize))
            {
                size = Math.round(cellSize / maxDim);
                this.currentIndex = index;
                context.fill(
                    slotX,
                    slotY,
                    slotX + slotSize,
                    slotY + slotSize,
                    0x88000000
                );
            }

            int centerX = cellX + cellSize / 2;
            int centerY = cellY + cellSize / 2;
            int bottomY = (int) (centerY + mob.getHeight() * size / 2f);
            int eyeY = (int) (bottomY - mob.getStandingEyeHeight() * size);
            InventoryScreen.drawEntity(
                context,
                centerX,
                bottomY,
                size,
                centerX - mouseX,
                eyeY - mouseY,
                mob
            );

            index++;
        }

        if (this.currentIndex != -1)
        {
            this.tooltip.clear();
            appendMobTooltip(this.mobs.get(this.currentIndex));
            context.drawTooltip(this.textRenderer, tooltip, mouseX, mouseY);
        }
    }

    private void appendMobTooltip(MobEntity mob)
    {
        tooltip.add(mob.getName());
        MobDifficulty.get(mob).ifPresent(difficulty ->
            tooltip.addAll(difficulty.getTitleWrap(true, true))
        );
        tooltip.add(SDTexts.TOOLTIP$SOUL_MINION$HEALTH.get(
            "%.1f / %.1f".formatted(
                mob.getHealth(),
                mob.getMaxHealth()
            )
        ).formatted(Formatting.RED));
    }
}
