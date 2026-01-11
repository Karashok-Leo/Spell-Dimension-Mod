package karashokleo.spell_dimension.client.screen;

import karashokleo.leobrary.gui.api.overlay.InfoSideBar;
import karashokleo.leobrary.gui.api.overlay.SideBar;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.content.item.QuestScrollItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestOverlay extends InfoSideBar<SideBar.IntSignature>
{
    public QuestOverlay()
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
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof QuestScrollItem scroll)
        {
            Optional<Quest> quest = scroll.getQuest(stack);
            if (quest.isPresent())
            {
                ArrayList<Text> texts = new ArrayList<>();
                QuestUsage.appendQuestOperationTooltip(texts, player.getWorld(), quest.get());
                return texts;
            }
        }
        return List.of(SDTexts.TOOLTIP$QUEST$VIEW_CURRENT.get());
//        return List.of(SDTexts.TOOLTIP$QUEST$OBTAIN_CURRENT.get());
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
        return player.getMainHandStack().isOf(AllItems.QUEST_SCROLL);
    }
}
