package karashokleo.spell_dimension.compat;

import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.spell_dimension.config.recipe.SummonSpellConfig;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

public record EntityWrapper(
        @Nullable LivingEntity entity,
        int scale,
        Text text
)
{
    public static EntityWrapper of(SummonSpellConfig.Entry entry)
    {
        LivingEntity entity = entry.entityType().create(L2HostilityClient.getClientWorld());
        if (entity != null)
        {
            Box box = entity.getBoundingBox();
            double len = box.getAverageSideLength();
            if (len > 1.05)
                len = (len + Math.sqrt(len)) / 2.0;

            if (entity instanceof SlimeEntity)
                ((SlimeEntity) entity).setSize(5, false);
            int scale = (int) (1.05 / len * 8.0);

            return new EntityWrapper(entity, scale * 3, SDTexts.TOOLTIP$QUEST$MUL.get(entity.getName(), entry.count()));
        } else
            return new EntityWrapper(null, 0, SDTexts.TEXT$INVALID_KEY_ITEM.get());
    }

    public void render(DrawContext ctx, int x, int y, int mouseX, int mouseY)
    {
        if (this.entity != null)
        {
            float yOffset = this.entity.getStandingEyeHeight() / this.entity.getHeight() * 40;
            InventoryScreen.drawEntity(ctx, x, y, scale, x - mouseX, -yOffset + y - mouseY, this.entity);
        }
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int width = textRenderer.getWidth(text);
        ctx.drawText(textRenderer, text, x - width / 2, y + 8, -1, true);
    }
}
