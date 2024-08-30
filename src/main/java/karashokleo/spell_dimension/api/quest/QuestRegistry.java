package karashokleo.spell_dimension.api.quest;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.mojang.serialization.Codec;
import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

@SuppressWarnings("all")
public class QuestRegistry
{
    public static final RegistryKey<Registry<Quest>> QUEST_REGISTRY_KEY = RegistryKey.ofRegistry(SpellDimension.modLoc("quest"));

    public static final Registry<Quest> QUEST_REGISTRY = FabricRegistryBuilder.createSimple(QUEST_REGISTRY_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static final Codec<RegistryEntry<Quest>> ENTRY_CODEC = QUEST_REGISTRY.createEntryCodec();

    public static final MutableGraph<RegistryEntry<Quest>> QUEST_GRAPH = GraphBuilder.directed().allowsSelfLoops(false).build();

    public static void register(Identifier id, Quest quest)
    {
        Registry.register(QUEST_REGISTRY, id, quest);
    }
}
