package karashokleo.spell_dimension.client.screen;

import com.google.common.collect.Lists;
import karashokleo.spell_dimension.content.item.SoulAlbumItem;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.C2SSelectSoulAlbum;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class SoulAlbumScreen extends Screen
{
    private static final int BASE_ENTITY_SIZE = 20;
    private static final int CELL_SIZE = 48;

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
        if (client == null)
        {
            return List.of();
        }
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
            .map(e -> SoulControl.loadMinionFromData((NbtCompound) e, world))
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
        var iconSize = 18;
        var startX = window.getScaledWidth() / 2 - w * iconSize / 2;
        var startY = window.getScaledHeight() / 2 - h * iconSize / 2;
        var padding = 4;

        context.fill(
            startX - padding,
            startY - padding,
            startX + iconSize * w + padding,
            startY + iconSize * h + padding,
            0x66000000
        );

        int index = 0;

        for (MobEntity mob : this.mobs)
        {
            int iy = index / w;
            int ix = index - iy * w;
            int mobX = startX + ix * iconSize;
            int mobY = startY + iy * iconSize;
            int centerX = mobX + CELL_SIZE / 2;
            int centerY = mobY + CELL_SIZE / 2;

            float scale = 1f;

            if (mouseX > mobX &&
                mouseY > mobY &&
                mouseX <= (mobX + 16) &&
                mouseY <= (mobY + 16))
            {
                scale = 1.3f;
                this.currentIndex = index;
            }

            int size = Math.round(BASE_ENTITY_SIZE * scale);
            InventoryScreen.drawEntity(context, centerX, centerY, size, centerX - mouseX, centerY - mouseY, mob);
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
    }
}
