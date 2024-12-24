package karashokleo.spell_dimension.api.quest;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface AdvancementQuest extends Quest
{
    @Nullable
    Identifier getAdvancementId();

    @Nullable
    default Advancement getAdvancement(ServerWorld world)
    {
        Identifier advancementId = getAdvancementId();
        if (advancementId == null) return null;
        MinecraftServer server = world.getServer();
        return server.getAdvancementLoader().get(advancementId);
    }

    @Override
    default boolean completeTasks(ServerPlayerEntity player)
    {
        Advancement advancement = this.getAdvancement(player.getServerWorld());
        if (advancement == null) return false;
        return player.getAdvancementTracker().getProgress(advancement).isDone();
    }
}
