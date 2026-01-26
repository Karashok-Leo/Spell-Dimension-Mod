package karashokleo.spell_dimension.client.screen;

import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.leobrary.gui.api.overlay.InfoSideBar;
import karashokleo.leobrary.gui.api.overlay.SideBar;
import karashokleo.spell_dimension.content.item.AbstractSoulContainerItem;
import karashokleo.spell_dimension.content.item.SoulContainerItem;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SoulContainerOverlay extends InfoSideBar<SideBar.IntSignature>
{
    private final List<Text> texts = new ArrayList<>();

    public SoulContainerOverlay()
    {
        super(40, 4);
    }

    @Override
    protected List<Text> getText()
    {
        this.texts.clear();
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null)
        {
            return this.texts;
        }
        ItemStack stack = player.getMainHandStack();
        if (!(stack.getItem() instanceof AbstractSoulContainerItem container))
        {
            return this.texts;
        }
        if (container instanceof SoulContainerItem soulContainer)
        {
            var hitResult = RayTraceUtil.rayTraceEntity(player, AbstractSoulContainerItem.RANGE, e -> !SoulControl.isSoulMinion(player, e));
            if (hitResult != null &&
                hitResult.getEntity() instanceof MobEntity mob)
            {
                float probability = soulContainer.getCaptureProbability(mob);
                this.texts.add(SDTexts.TOOLTIP$SOUL_MINION$CAPTURE_PROBABILITY.get("%.1f%%".formatted(probability * 100)));
                return this.texts;
            }
        }
        container.appendTooltip(stack, player.getWorld(), this.texts, TooltipContext.BASIC);
        return this.texts;
    }

    @Override
    protected boolean isOnHold()
    {
        return true;
    }

    @Override
    public IntSignature getSignature()
    {
        return new IntSignature(0);
    }

    @Override
    public boolean isScreenOn()
    {
        var client = MinecraftClient.getInstance();
        if (client.currentScreen != null)
        {
            return false;
        }
        ClientPlayerEntity player = client.player;
        if (player == null)
        {
            return false;
        }
        return (player.getMainHandStack().getItem() instanceof AbstractSoulContainerItem);
    }
}
