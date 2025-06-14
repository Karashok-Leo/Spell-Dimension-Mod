package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.config.QuestToEntryConfig;
import karashokleo.spell_dimension.content.component.QuestComponent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public interface QuestUsage
{
    static void addQuestsCompleted(ServerPlayerEntity player, Quest... quests)
    {
        for (Quest quest : quests)
            QuestComponent.addCompleted(player, entry(quest));
    }

    static void removeQuestsCompleted(ServerPlayerEntity player, Quest... quests)
    {
        for (Quest quest : quests)
            QuestComponent.removeCompleted(player, entry(quest));
    }

    static boolean isQuestCompleted(PlayerEntity player, Quest quest)
    {
        return QuestComponent.isCompleted(player, entry(quest));
    }

    static boolean allDependenciesCompleted(PlayerEntity player, Quest quest)
    {
        return getDependencies(quest).stream().allMatch(entry -> QuestComponent.isCompleted(player, entry));
    }

    static Set<RegistryEntry<Quest>> getCurrentQuests(PlayerEntity player)
    {
        return QuestRegistry.QUEST_REGISTRY.streamEntries().filter(entry ->
        {
            if (QuestComponent.isCompleted(player, entry))
                return false;
            else
                return allDependenciesCompleted(player, entry.value());
        }).collect(Collectors.toSet());
    }

    static RegistryEntry<Quest> entry(Quest quest)
    {
        Identifier id = QuestRegistry.QUEST_REGISTRY.getId(quest);
        if (id == null)
            throw new UnsupportedOperationException("Unregistered quest: " + quest);
        return entry(id);
    }

    static RegistryEntry<Quest> entry(Identifier id)
    {
        return QuestRegistry.QUEST_REGISTRY.getEntry(RegistryKey.of(QuestRegistry.QUEST_REGISTRY_KEY, id)).orElseThrow(() -> new IllegalArgumentException("Unknown quest id: " + id));
    }

    static void configure(RegistryEntry<Quest> dependence, RegistryEntry<Quest> dependent)
    {
        QuestRegistry.QUEST_GRAPH.putEdge(dependence, dependent);
    }

    static void configure(Quest dependence, Quest dependent)
    {
        configure(entry(dependence), entry(dependent));
    }

    static void configure(Identifier dependence, Identifier dependent)
    {
        configure(entry(dependence), entry(dependent));
    }

    static Set<RegistryEntry<Quest>> getDependencies(Quest quest)
    {
        return QuestRegistry.QUEST_GRAPH.predecessors(entry(quest));
    }

    static Set<RegistryEntry<Quest>> getDependents(Quest quest)
    {
        return QuestRegistry.QUEST_GRAPH.successors(entry(quest));
    }

    static void appendQuestOperationTooltip(List<Text> tooltip, World world, Quest quest)
    {
        quest.appendTooltip(world, tooltip);
        if (quest.isIn(AllTags.SKIPPABLE))
        {
            tooltip.add(SDTexts.TOOLTIP$QUEST$SUBMIT_OR_SKIP.get().formatted(Formatting.GOLD));
        } else
        {
            tooltip.add(SDTexts.TOOLTIP$QUEST$SUBMIT.get().formatted(Formatting.GOLD));
        }
        tooltip.add(SDTexts.TOOLTIP$QUEST$RESELECT.get().formatted(Formatting.DARK_GREEN));
        if (QuestToEntryConfig.hasEntry(quest))
        {
            tooltip.add(SDTexts.TOOLTIP$QUEST$OPEN_ENTRY.get().formatted(Formatting.DARK_AQUA));
        }
    }
}
