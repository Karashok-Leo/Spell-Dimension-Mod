package karashokleo.spell_dimension.client.quest;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

/**
 * Vanilla copy
 *
 * @param stacks
 * @see net.minecraft.client.gui.tooltip.BundleTooltipComponent
 */
public record QuestItemTooltipComponent(DefaultedList<ItemStack> stacks) implements TooltipComponent
{
    public static final Identifier TEXTURE = new Identifier("textures/gui/container/bundle.png");
    private static final int TEXTURE_SIZE = 128;
    private static final int SLOT_SIZE = 18;

    public QuestItemTooltipComponent(QuestItemTooltipData data)
    {
        this(data.stacks());
    }

    @Override
    public int getHeight()
    {
        return this.getRows() * 20 + 2 + 4;
    }

    @Override
    public int getWidth(TextRenderer textRenderer)
    {
        return this.getColumns() * 18 + 2;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context)
    {
        int columns = this.getColumns();
        int rows = this.getRows();
        int index = 0;

        for (int row = 0; row < rows; row++)
        {
            for (int column = 0; column < columns; column++)
            {
                if (index >= this.stacks.size()) continue;
                int slotX = x + column * SLOT_SIZE + 2;
                int slotY = y + row * SLOT_SIZE + 2;
                ItemStack itemStack = this.stacks.get(index);
                context.drawItem(itemStack, slotX, slotY, index);
                index++;
            }
        }

        this.drawOutline(x, y, columns, rows, context);
    }

    private void drawOutline(int x, int y, int columns, int rows, DrawContext context)
    {
        for (int i = 0; i < columns; i++)
        {
            context.drawTexture(
                    TEXTURE,
                    x + 1 + i * SLOT_SIZE, y, 0,
                    0, 20, 18, 1,
                    TEXTURE_SIZE, TEXTURE_SIZE
            );
            context.drawTexture(
                    TEXTURE,
                    x + 1 + i * SLOT_SIZE, y + rows * SLOT_SIZE + 1, 0,
                    0, 60, 18, 1,
                    TEXTURE_SIZE, TEXTURE_SIZE
            );
        }
        for (int i = 0; i < rows; i++)
        {
            context.drawTexture(
                    TEXTURE,
                    x, y + i * SLOT_SIZE + 1, 0,
                    0, 18, 1, 18,
                    TEXTURE_SIZE, TEXTURE_SIZE
            );
            context.drawTexture(
                    TEXTURE,
                    x + columns * SLOT_SIZE + 1, y + i * SLOT_SIZE + 1, 0,
                    0, 18, 1, 18,
                    TEXTURE_SIZE, TEXTURE_SIZE
            );
        }
    }

    private int getColumns()
    {
        return Math.min(this.stacks.size(), 9);
    }

    private int getRows()
    {
        return this.stacks.size() / 9 + (this.stacks.size() % 9 == 0 ? 0 : 1);
    }
}
