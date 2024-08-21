package karashokleo.spell_dimension.api.buff;

import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class BuffTypeRegistry
{
    public static final RegistryKey<Registry<BuffType<?>>> BUFF_TYPE_REGISTRY_KEY = RegistryKey.ofRegistry(SpellDimension.modLoc("buff"));

    public static final Registry<BuffType<?>> BUFF_TYPE_REGISTRY = FabricRegistryBuilder.createSimple(BUFF_TYPE_REGISTRY_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static void register(Identifier id, BuffType<?> buffType)
    {
        Registry.register(BUFF_TYPE_REGISTRY, id, buffType);
    }
}
