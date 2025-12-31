package karashokleo.spell_dimension.content.integration;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import vazkii.patchouli.client.book.text.BookTextParser;
import vazkii.patchouli.client.book.text.SpanState;

import java.util.LinkedList;

public class PatchouliIntegration
{
    public static void init()
    {
        BookTextParser.register(PatchouliIntegration::processCopy, "copy");
    }

    private static String processCopy(String parameter, SpanState state)
    {
        state.cluster = new LinkedList<>();

        state.pushStyle(Style.EMPTY.withColor(TextColor.fromRgb(state.book.linkColor)));

        state.tooltip = Text.translatable("chat.copy.click");
        state.onClick = () ->
        {
            MinecraftClient.getInstance().keyboard.setClipboard(parameter);
            return true;
        };

        return "";
    }
}
