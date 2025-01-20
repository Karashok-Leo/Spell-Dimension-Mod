package karashokleo.spell_dimension.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import xaero.pac.common.server.api.OpenPACServerAPI;
import xaero.pac.common.server.parties.party.api.IPartyManagerAPI;
import xaero.pac.common.server.parties.party.api.IServerPartyAPI;

public class PartyUtil
{
    public static boolean isPartner(LivingEntity origin, LivingEntity target)
    {
        if (!(origin instanceof ServerPlayerEntity sp1)) return false;
        if (!(target instanceof ServerPlayerEntity sp2)) return false;
        MinecraftServer server = sp1.getServer();
        if (server == null) return false;
        IPartyManagerAPI manager = OpenPACServerAPI.get(server).getPartyManager();
        IServerPartyAPI party1 = manager.getPartyByMember(sp1.getUuid());
        if (party1 == null) return false;
        IServerPartyAPI party2 = manager.getPartyByMember(sp2.getUuid());
        if (party2 == null) return false;
        return party1.isAlly(party2.getId());
    }
}
