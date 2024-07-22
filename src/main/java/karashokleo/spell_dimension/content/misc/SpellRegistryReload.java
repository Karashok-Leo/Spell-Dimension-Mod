package karashokleo.spell_dimension.content.misc;

import karashokleo.spell_dimension.mixin.SpellRegistryAccessor;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.spell_engine.SpellEngineMod;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.network.Packets;
import net.spell_engine.utils.WeaponCompatibility;

public class SpellRegistryReload
{
    public static void init()
    {
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) ->
        {
            SpellRegistry.loadSpells(server.getResourceManager());
            SpellRegistry.loadPools(server.getResourceManager());
            SpellRegistry.loadContainers(server.getResourceManager());
            WeaponCompatibility.initialize();
            SpellRegistryAccessor.invokeEncodedContent();

            PacketByteBuf configSerialized = Packets.ConfigSync.write(SpellEngineMod.config);
            for (ServerPlayerEntity player : PlayerLookup.all(server))
            {
                PacketSender sender = ServerPlayNetworking.getSender(player);
                sender.sendPacket(Packets.SpellRegistrySync.ID, SpellRegistry.encoded);
                sender.sendPacket(Packets.ConfigSync.ID, configSerialized);
            }
        });
    }
}
