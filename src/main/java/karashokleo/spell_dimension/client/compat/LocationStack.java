package karashokleo.spell_dimension.client.compat;

import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.content.recipe.locate.LocationType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;

public class LocationStack
{
    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static final Identifier COMPASS = new Identifier("minecraft:textures/item/compass_16.png");
    public static final int COLOR_START = 0x80000000;

    protected final LocationType type;
    protected final Identifier id;
    protected final Identifier world;
    protected final Text name;
    protected final List<Text> tooltip;

    public static LocationStack fromRecipe(LocateRecipe recipe)
    {
        return new LocationStack(
            recipe.getLocationType(),
            recipe.getTargetId(),
            recipe.getWorldId()
        );
    }

    public LocationStack(LocationType type, Identifier id, Identifier world)
    {
        this.type = type;
        this.id = id;
        this.world = world;
        this.name = type.getName(id);
        MutableText worldName = Text.translatable(
            world.toTranslationKey("travelerstitles")
        ).formatted(Formatting.GRAY);
        MutableText idString = Text.literal(type.toString(id)).formatted(Formatting.DARK_GRAY);
        this.tooltip = List.of(name, worldName, idString);
    }

    public LocationType getType()
    {
        return type;
    }

    public Identifier getId()
    {
        return id;
    }

    public Identifier getWorld()
    {
        return world;
    }

    public Text getName()
    {
        return name;
    }

    public List<Text> getTooltip()
    {
        return tooltip;
    }

    public LocationStack copy()
    {
        return new LocationStack(type, id, world);
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof LocationStack that))
        {
            return false;
        }
        return type == that.type &&
            id.equals(that.id) &&
            world.equals(that.world);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(type, id, world);
    }

    public void render(DrawContext draw, int x, int y)
    {
        draw.drawTexture(COMPASS, x, y, 0, 0, 16, 16, 16, 16);
        String string = name.getString();
        if (string.isBlank())
        {
            return;
        }
        char firstChar = string.charAt(0);
        DyeColor dyeColor = DyeColor.byId(firstChar % 16);

        draw.fillGradient(x, y, x + 16, y + 16, COLOR_START, COLOR_START + dyeColor.getSignColor());
        draw.drawCenteredTextWithShadow(client.textRenderer, firstChar + "", x + 8, y + 4, 0xFFFFFF);
    }
}
