package karashokleo.spell_dimension.client.screen;

import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.leobrary.gui.api.overlay.InfoSideBar;
import karashokleo.leobrary.gui.api.overlay.SideBar;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.content.item.QuestScrollItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

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
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null) return List.of();
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof QuestScrollItem scroll)
        {
            Optional<Quest> quest = scroll.getQuest(stack);
            if (quest.isPresent())
            {
                World world = player.getWorld();
                ArrayList<Text> texts = new ArrayList<>();
                Text title = quest.get().getTitle(world);
                if (title != null) texts.add(title);
                texts.addAll(quest.get().getDesc(world));
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
        if (L2HostilityClient.getClient().currentScreen != null) return false;
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null) return false;
        return player.getMainHandStack().isOf(AllItems.QUEST_SCROLL);
    }
}
