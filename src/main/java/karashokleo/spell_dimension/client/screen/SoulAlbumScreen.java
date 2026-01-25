package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.content.item.SoulAlbumItem;
import karashokleo.spell_dimension.content.item.SoulContainerItem;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.C2SSelectSoulAlbum;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class SoulAlbumScreen extends Screen
{
    private static final int BASE_ENTITY_SIZE = 20;
    private static final int CELL_SIZE = 48;
    private static final int OUTLINE_COLOR = 0x2dd4da;

    private final Hand hand;
    private final List<SoulEntry> entries;
    private final int selectedIndex;
    @Nullable
    private SoulEntry hovered;

    public SoulAlbumScreen(Hand hand)
    {
        super(Text.empty());
        this.hand = hand;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null)
        {
            this.entries = List.of();
            this.selectedIndex = -1;
            return;
        }
        ItemStack album = client.player.getStackInHand(hand);
        this.entries = collectEntries(client.world, album);
        this.selectedIndex = SoulAlbumItem.getSelectedIndex(album);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        if (this.hovered != null)
        {
            AllPackets.toServer(new C2SSelectSoulAlbum(this.hovered.index(), this.hand));
            this.close();
            return true;
        }
        AllPackets.toServer(new C2SSelectSoulAlbum(-1, this.hand));
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

        context.fill(0, 0, this.width, this.height, 0x66000000);

        if (this.entries.isEmpty())
        {
            return;
        }

        int n = this.entries.size();
        int columns = (int) Math.ceil(Math.sqrt(n));
        int rows = (int) Math.ceil(n * 1d / columns);
        int startX = this.width / 2 - columns * CELL_SIZE / 2;
        int startY = this.height / 2 - rows * CELL_SIZE / 2;

        this.hovered = null;
        int index = 0;

        for (SoulEntry entry : this.entries)
        {
            int row = index / columns;
            int column = index - row * columns;
            int cellX = startX + column * CELL_SIZE;
            int cellY = startY + row * CELL_SIZE;
            int centerX = cellX + CELL_SIZE / 2;
            int centerY = cellY + CELL_SIZE / 2 + 6;
            boolean isHover = mouseX >= cellX && mouseY >= cellY && mouseX < cellX + CELL_SIZE && mouseY < cellY + CELL_SIZE;
            float scale = isHover ? 1.3f : 1f;

            if (entry.index() == this.selectedIndex)
            {
                context.fill(cellX - 2, cellY - 2, cellX + CELL_SIZE + 2, cellY + CELL_SIZE + 2, 0x33ffffff);
            }

            if (entry.entity() != null)
            {
                int size = Math.round(BASE_ENTITY_SIZE * scale);
                if (entry.hasSoul())
                {
                    InventoryScreen.drawEntity(context, centerX, centerY, size, centerX - mouseX, centerY - mouseY, entry.entity());
                } else
                {
                    drawOutlineEntity(context, centerX, centerY, size, centerX - mouseX, centerY - mouseY, entry.entity(), OUTLINE_COLOR);
                }
            } else
            {
                context.drawItem(entry.container(), centerX - 8, centerY - 8);
            }

            if (isHover)
            {
                this.hovered = entry;
            }
            index++;
        }

        if (this.hovered != null)
        {
            TooltipContext tooltipContext = this.client.options.advancedItemTooltips ? TooltipContext.ADVANCED : TooltipContext.BASIC;
            if (tooltipContext instanceof TooltipContext.Default defaultContext &&
                this.client.player.getAbilities().creativeMode)
            {
                tooltipContext = defaultContext.withCreative();
            }
            List<Text> tooltip = this.hovered.container().getTooltip(this.client.player, tooltipContext);
            context.drawTooltip(this.textRenderer, tooltip, mouseX, mouseY);
        }
    }

    private static List<SoulEntry> collectEntries(@Nullable World world, ItemStack album)
    {
        if (world == null)
        {
            return List.of();
        }
        List<ItemStack> containers = SoulAlbumItem.getStoredContainers(album);
        List<SoulEntry> entries = new ArrayList<>();
        for (int i = 0; i < containers.size(); i++)
        {
            ItemStack container = containers.get(i);
            NbtCompound nbt = container.getNbt();
            if (nbt == null)
            {
                continue;
            }
            boolean stored = nbt.contains(SoulContainerItem.ENTITY_KEY, NbtElement.COMPOUND_TYPE);
            boolean lastStored = nbt.containsUuid(SoulContainerItem.ENTITY_KEY);
            if (!stored && !lastStored)
            {
                continue;
            }
            LivingEntity entity = stored ?
                createEntityFromData(world, nbt.getCompound(SoulContainerItem.ENTITY_KEY)) :
                createEntityFromTooltip(world, nbt.getCompound(SoulContainerItem.TOOLTIP_DATA_KEY));
            entries.add(new SoulEntry(i, container, entity, stored));
        }
        return entries;
    }

    @Nullable
    private static LivingEntity createEntityFromData(World world, NbtCompound data)
    {
        try
        {
            LivingEntity entity = SoulControl.loadMinionFromData(data, world);
            entity.setNoGravity(true);
            return entity;
        } catch (RuntimeException ignored)
        {
            return null;
        }
    }

    @Nullable
    private static LivingEntity createEntityFromTooltip(World world, NbtCompound tooltip)
    {
        if (tooltip == null || !tooltip.contains(SoulContainerItem.ENTITY_TYPE_KEY, NbtElement.STRING_TYPE))
        {
            return null;
        }
        Identifier typeId = Identifier.tryParse(tooltip.getString(SoulContainerItem.ENTITY_TYPE_KEY));
        if (typeId == null || !Registries.ENTITY_TYPE.containsId(typeId))
        {
            return null;
        }
        EntityType<?> type = Registries.ENTITY_TYPE.get(typeId);
        LivingEntity entity = null;
        if (type.create(world) instanceof LivingEntity living)
        {
            entity = living;
        }
        if (entity == null)
        {
            return null;
        }
        if (tooltip.contains(SoulContainerItem.CUSTOM_NAME_KEY, NbtElement.STRING_TYPE))
        {
            String rawName = tooltip.getString(SoulContainerItem.CUSTOM_NAME_KEY);
            Text name = null;
            try
            {
                name = Text.Serializer.fromJson(rawName);
            } catch (RuntimeException ignored)
            {
            }
            if (name != null)
            {
                entity.setCustomName(name);
            }
        }
        entity.setNoGravity(true);
        return entity;
    }

    private static void drawOutlineEntity(DrawContext context, int x, int y, int size, float mouseX, float mouseY, LivingEntity entity, int color)
    {
        float yaw = (float) Math.atan(mouseX / 40.0F);
        float pitch = (float) Math.atan(mouseY / 40.0F);
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(x, y, 50.0D);
        matrices.scale((float) size, (float) size, (float) -size);
        Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf quaternionf2 = new Quaternionf().rotateX(pitch * 20.0F * ((float) Math.PI / 180F));
        quaternionf.mul(quaternionf2);
        matrices.multiply(quaternionf);

        float bodyYaw = entity.bodyYaw;
        float headYaw = entity.headYaw;
        float prevHeadYaw = entity.prevHeadYaw;
        float entityYaw = entity.getYaw();
        float entityPitch = entity.getPitch();

        entity.bodyYaw = 180.0F + yaw * 20.0F;
        entity.setYaw(180.0F + yaw * 40.0F);
        entity.setPitch(-pitch * 20.0F);
        entity.headYaw = entity.getYaw();
        entity.prevHeadYaw = entity.getYaw();

        EntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternionf2.conjugate();
        dispatcher.setRotation(quaternionf2);
        dispatcher.setRenderShadows(false);

        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        OutlineVertexConsumerProvider outlineProvider = new OutlineVertexConsumerProvider(immediate);
        outlineProvider.setColor((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, 255);

        try
        {
            dispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrices, outlineProvider, 15728880);
            outlineProvider.draw();
        } catch (Throwable throwable)
        {
            CrashReport crashReport = CrashReport.create(throwable, "Rendering entity");
            CrashReportSection crashReportSection = crashReport.addElement("Entity being rendered");
            crashReportSection.add("Entity Type", () -> String.valueOf(entity.getType()));
            crashReportSection.add("Entity NBT", () -> String.valueOf(entity.writeNbt(new NbtCompound())));
            throw new CrashException(crashReport);
        }

        dispatcher.setRenderShadows(true);
        entity.bodyYaw = bodyYaw;
        entity.headYaw = headYaw;
        entity.prevHeadYaw = prevHeadYaw;
        entity.setYaw(entityYaw);
        entity.setPitch(entityPitch);

        matrices.pop();
        DiffuseLighting.enableGuiDepthLighting();
    }

    private record SoulEntry(int index, ItemStack container, @Nullable LivingEntity entity, boolean hasSoul)
    {
    }
}
