package karashokleo.spell_dimension.api.quest;

import karashokleo.l2hostility.L2Hostility;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public interface AdvancementQuest extends Quest
{
    @Nullable
    Identifier getAdvancementId();

    default Advancement getAdvancement()
    {
        MinecraftServer server = Objects.requireNonNull(L2Hostility.getServer());
        Identifier advancementId = Objects.requireNonNull(getAdvancementId());
        return server.getAdvancementLoader().get(advancementId);
    }

    @Override
    default boolean completeTasks(ServerPlayerEntity player)
    {
        return player.getAdvancementTracker().getProgress(this.getAdvancement()).isDone();
    }

    @Override
    default void appendTaskDesc(List<Text> desc)
    {
        AdvancementDisplay display = getAdvancement().getDisplay();
        if (display == null) return;
        desc.add(display.getDescription());
    }
}
