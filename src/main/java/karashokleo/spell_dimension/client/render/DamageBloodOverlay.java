package karashokleo.spell_dimension.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;

public class DamageBloodOverlay
{
    private static float time = -1;

    public static void show()
    {
        time = 0;
    }

    public static float getOpacity(MinecraftClient client, ClientPlayerEntity player, DrawContext context, float tickDelta)
    {
        if (time < 0)
        {
            return -1;
        }

        final float fadeInDuration = 6;
        final float fadeOutDuration = 40;

        time += tickDelta;

        if (time >= fadeInDuration + fadeOutDuration)
        {
            time = -1;
            return -1;
        }

        // fade in 0 -> 1
        if (time < fadeInDuration)
        {
            return smoothStep(time / fadeInDuration);
        }

        // fade out 1 -> 0
        return 1f - smoothStep((time - fadeInDuration) / fadeOutDuration);
    }

    private static float smoothStep(float t)
    {
        return t * t * (3f - 2f * t);
    }
}
