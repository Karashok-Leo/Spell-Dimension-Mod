package karashokleo.spell_dimension.util;

import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.Tameable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import xaero.pac.common.server.api.OpenPACServerAPI;
import xaero.pac.common.server.parties.party.api.IPartyManagerAPI;
import xaero.pac.common.server.parties.party.api.IServerPartyAPI;

public class RelationUtil
{
    /**
     * @return true if entity2 is allied to entity1
     */
    public static boolean isAlly(LivingEntity entity1, LivingEntity entity2)
    {
        return entity1 == entity2 ||
            entity1.isTeammate(entity2) ||
            isOwner(entity1, entity2) ||
            isPartner(entity1, entity2);
    }

    /**
     * @return true if entity1 is the owner of entity2
     */
    public static boolean isOwner(LivingEntity entity1, LivingEntity entity2)
    {
        return SoulControl.isSoulMinion(entity1, entity2) ||
            (entity2 instanceof Tameable tameable && tameable.getOwner() == entity1) ||
            (entity2 instanceof Ownable ownable && ownable.getOwner() == entity1);
    }

    /**
     * @return true if entity1 and entity2 are players in the same party or allied parties in OPAC
     */
    public static boolean isPartner(LivingEntity entity1, LivingEntity entity2)
    {
        if (!(entity1 instanceof ServerPlayerEntity sp1))
        {
            return false;
        }
        if (!(entity2 instanceof ServerPlayerEntity sp2))
        {
            return false;
        }
        MinecraftServer server = sp1.getServer();
        if (server == null)
        {
            return false;
        }
        IPartyManagerAPI manager = OpenPACServerAPI.get(server).getPartyManager();
        IServerPartyAPI party1 = manager.getPartyByMember(sp1.getUuid());
        if (party1 == null)
        {
            return false;
        }
        IServerPartyAPI party2 = manager.getPartyByMember(sp2.getUuid());
        if (party2 == null)
        {
            return false;
        }
        return party1.isAlly(party2.getId());
    }
}
