package karashokleo.spell_dimension.render;

import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

public class ConsciousnessFluidRenderHandler extends SimpleFluidRenderHandler
{
    private static final int DEFAULT_COLOR = 0xFFFFFF;
    private static final int STEP = 64;
    private static final int[] COLORS = {
            0xFF0000,
            0xFFFF00,
            0x00FF00,
            0x00FFFF,
            0x0000FF,
            0xFF00FF,
    };

    public ConsciousnessFluidRenderHandler()
    {
        super(WATER_STILL, WATER_FLOWING, WATER_OVERLAY, DEFAULT_COLOR);
    }

    @Override
    public int getFluidColor(@Nullable BlockRenderView view, @Nullable BlockPos pos, FluidState state)
    {
        if (pos == null) return DEFAULT_COLOR;
        return getColor(pos);
//        return getColorGradient(pos);
    }

    private static int getColor(BlockPos pos)
    {
//        int w = pos.getX() + pos.getY() + pos.getZ();
        int w = pos.getX() * pos.getX() + pos.getZ() * pos.getZ();
        w = MathHelper.floor(MathHelper.sqrt(w));
        int current_index = MathHelper.floorDiv(w, STEP);
        float delta = (float) MathHelper.floorMod(w, STEP) / STEP;
        return ColorHelper.Argb.lerp(delta, getColorByIndex(current_index), getColorByIndex(current_index + 1));
    }

    private static int getColorByIndex(int index)
    {
        return COLORS[(index % COLORS.length + COLORS.length) % COLORS.length];
    }

    private static int getColorGradient(BlockPos pos)
    {
        int red = pos.getX() * 32 + pos.getY() * 16;
        if ((red & 256) != 0) red = 255 - (red & 255);
        red &= 255;

        int blue = pos.getY() * 32 + pos.getZ() * 16;
        if ((blue & 256) != 0) blue = 255 - (blue & 255);
        blue ^= 255;

        int green = pos.getX() * 16 + pos.getZ() * 32;
        if ((green & 256) != 0) green = 255 - (green & 255);
        green &= 255;

        return red << 16 | blue << 8 | green;
    }
}
