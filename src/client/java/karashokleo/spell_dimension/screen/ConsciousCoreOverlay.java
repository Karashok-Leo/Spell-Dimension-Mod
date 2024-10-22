package karashokleo.spell_dimension.screen;

import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.leobrary.gui.api.overlay.InfoSideBar;
import karashokleo.leobrary.gui.api.overlay.SideBar;
import karashokleo.spell_dimension.content.block.tile.ConsciousnessCoreTile;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConsciousCoreOverlay extends InfoSideBar<SideBar.IntSignature>
{
    private double levelFactor;
    @Nullable
    private Integer level = null;

    public ConsciousCoreOverlay()
    {
        super(40, 4);
    }

    @Override
    protected List<Text> getText()
    {
        String factorStr = String.format("%.2f", this.levelFactor);
        ArrayList<Text> texts = new ArrayList<>();
        texts.add(SDTexts.TEXT$CONSCIOUSNESS_CORE$LEVEL_FACTOR.get(factorStr));
        if (this.level != null)
            texts.add(SDTexts.TEXT$CONSCIOUSNESS_CORE$LEVEL.get(this.level));
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
        if (L2HostilityClient.getClient().currentScreen != null) return false;
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null) return false;
        BlockPos pos = RayTraceUtil.rayTraceBlock(player.getWorld(), player, 5).getBlockPos();
        if (player.getWorld().getBlockEntity(pos) instanceof ConsciousnessCoreTile tile)
        {
            this.levelFactor = tile.getLevelFactor();
            this.level = tile.getLevel();
            return true;
        }
        return false;
    }
}
