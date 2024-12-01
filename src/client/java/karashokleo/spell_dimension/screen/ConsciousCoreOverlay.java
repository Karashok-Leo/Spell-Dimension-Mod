package karashokleo.spell_dimension.screen;

import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.leobrary.gui.api.overlay.InfoSideBar;
import karashokleo.leobrary.gui.api.overlay.SideBar;
import karashokleo.spell_dimension.content.block.tile.ConsciousnessCoreTile;
import karashokleo.spell_dimension.content.event.conscious.EventAward;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConsciousCoreOverlay extends InfoSideBar<SideBar.IntSignature>
{
    private boolean activating = false;
    private boolean triggered = false;
    private double levelFactor;
    @Nullable
    private EventAward award = null;
    @Nullable
    private Integer level = null;

    public ConsciousCoreOverlay()
    {
        super(40, 4);
    }

    @Override
    protected List<Text> getText()
    {
        ArrayList<Text> texts = new ArrayList<>();
        if (this.activating)
            texts.add(SDTexts.TEXT$CONSCIOUSNESS_CORE$ACTIVATING.get().formatted(Formatting.YELLOW));
        else if (this.triggered)
            texts.add(SDTexts.TEXT$CONSCIOUSNESS_CORE$TRIGGERED.get().formatted(Formatting.GREEN));
        else
        {
            texts.add(SDTexts.TEXT$CONSCIOUSNESS_CORE$NOT_TRIGGERED.get().formatted(Formatting.RED));
            texts.add(SDTexts.TEXT$CONSCIOUSNESS_CORE$LEVEL_FACTOR.get("%.2f".formatted(this.levelFactor)).formatted(Formatting.GOLD));
            if (this.award != null)
                texts.add(SDTexts.TEXT$CONSCIOUSNESS_CORE$AWARD.get(this.award.getText()).formatted(Formatting.AQUA));
            if (this.level != null)
                texts.add(SDTexts.TEXT$CONSCIOUSNESS_CORE$LEVEL.get(this.level).formatted(Formatting.LIGHT_PURPLE));
        }
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
            this.activating = tile.isActivating();
            this.triggered = tile.isTriggered();
            this.levelFactor = tile.getLevelFactor();
            this.award = tile.getAward();
            this.level = tile.getLevel();
            return true;
        }
        return false;
    }
}
