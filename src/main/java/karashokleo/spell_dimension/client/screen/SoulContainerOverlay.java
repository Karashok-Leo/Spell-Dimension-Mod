package karashokleo.spell_dimension.client.screen;

import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.leobrary.gui.api.overlay.InfoSideBar;
import karashokleo.leobrary.gui.api.overlay.SideBar;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.item.SoulContainerItem;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;

import java.util.ArrayList;
import java.util.List;

public class SoulContainerOverlay extends InfoSideBar<SideBar.IntSignature>
{
    private EntityHitResult hitResult;

    public SoulContainerOverlay()
    {
        super(40, 4);
    }

    @Override
    protected List<Text> getText()
    {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null)
        {
            return List.of();
        }
        if (this.hitResult == null)
        {
            return List.of();
        }
        Entity entity = this.hitResult.getEntity();
        if (!(entity instanceof MobEntity mob))
        {
            return List.of();
        }
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
        PlayerEntity owner = minionComponent.getOwner();
        if (owner == player)
        {
            return List.of();
        }
        if (!(player.getMainHandStack().getItem() instanceof SoulContainerItem soulContainer))
        {
            return List.of();
        }
        ArrayList<Text> texts = new ArrayList<>();
        float probability = soulContainer.getCaptureProbability(mob);
        texts.add(SDTexts.TOOLTIP$SOUL_MINION$CAPTURE_PROBABILITY.get("%.1f%%".formatted(probability * 100)));
        return texts;
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
        if (!(player.getMainHandStack().getItem() instanceof SoulContainerItem))
        {
            return false;
        }
        this.hitResult = RayTraceUtil.rayTraceEntity(player, SoulContainerItem.RANGE, entity -> entity instanceof MobEntity);
        return this.hitResult != null && this.hitResult.getEntity() != null;
    }
}
