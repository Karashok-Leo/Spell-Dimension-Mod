package karashokleo.spell_dimension.util;

import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Collection;

public class NetworkUtil
{
    public static final Identifier SPELL_DASH_PACKET = SpellDimension.modLoc("spell_dash");
    public static final Identifier QUEST_PACKET = SpellDimension.modLoc("quest");
    public static final Identifier DUST_PACKET = SpellDimension.modLoc("dust");

    public static void sendToTrackers(Entity trackedEntity, Identifier id, PacketByteBuf buf)
    {
        sendToTrackers(trackedEntity, id, buf, PlayerLookup.tracking(trackedEntity));
    }

    public static void sendToTrackers(Entity trackedEntity, Identifier id, PacketByteBuf buf, Collection<ServerPlayerEntity> trackers)
    {
        if (trackedEntity instanceof ServerPlayerEntity player)
            ServerPlayNetworking.send(player, id, buf);
        trackers.forEach(player -> ServerPlayNetworking.send(player, id, buf));
    }
}
