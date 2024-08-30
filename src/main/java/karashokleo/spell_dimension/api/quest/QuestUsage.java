package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.content.component.QuestComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public final class QuestUsage
{
    private QuestUsage()
    {
    }

    public static void addQuestsCompleted(ServerPlayerEntity player, Quest... quests)
    {
        for (Quest quest : quests)
            QuestComponent.addCompleted(player, entry(quest));
    }

    public static void removeQuestsCompleted(ServerPlayerEntity player, Quest... quests)
    {
        for (Quest quest : quests)
            QuestComponent.removeCompleted(player, entry(quest));
    }

    public static boolean isQuestCompleted(PlayerEntity player, Quest quest)
    {
        return QuestComponent.isCompleted(player, entry(quest));
    }

    public static boolean allDependenciesCompleted(PlayerEntity player, Quest quest)
    {
        return getDependencies(quest).stream().allMatch(entry -> QuestComponent.isCompleted(player, entry));
    }

    public static Set<RegistryEntry<Quest>> getCurrentQuests(PlayerEntity player)
    {
        return QuestRegistry.QUEST_REGISTRY.streamEntries().filter(entry ->
        {
            if (QuestComponent.isCompleted(player, entry))
                return false;
            else if (allDependenciesCompleted(player, entry.value()))
                return true;
            else return false;
        }).collect(Collectors.toSet());
    }

    public static RegistryEntry<Quest> entry(Quest quest)
    {
        Identifier id = QuestRegistry.QUEST_REGISTRY.getId(quest);
        if (id == null)
            throw new UnsupportedOperationException("Unregistered quest: " + quest);
        return entry(id);
    }

    public static RegistryEntry<Quest> entry(Identifier id)
    {
        return QuestRegistry.QUEST_REGISTRY.getEntry(RegistryKey.of(QuestRegistry.QUEST_REGISTRY_KEY, id)).orElseThrow(() -> new IllegalArgumentException("Unknown quest id: " + id));
    }

    public static void configure(RegistryEntry<Quest> dependence, RegistryEntry<Quest> dependent)
    {
        QuestRegistry.QUEST_GRAPH.putEdge(dependence, dependent);
    }

    public static void configure(Quest dependence, Quest dependent)
    {
        configure(entry(dependence), entry(dependent));
    }

    public static void configure(Identifier dependence, Identifier dependent)
    {
        configure(entry(dependence), entry(dependent));
    }

    public static Set<RegistryEntry<Quest>> getDependencies(Quest quest)
    {
        return QuestRegistry.QUEST_GRAPH.predecessors(entry(quest));
    }

    public static Set<RegistryEntry<Quest>> getDependents(Quest quest)
    {
        return QuestRegistry.QUEST_GRAPH.successors(entry(quest));
    }
}
