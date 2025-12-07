package karashokleo.spell_dimension.util;

import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import xaero.pac.common.server.api.OpenPACServerAPI;
import xaero.pac.common.server.parties.party.api.IPartyManagerAPI;
import xaero.pac.common.server.parties.party.api.IServerPartyAPI;

public class RelationUtil
{
    /**
     * @return true if entity2 is allied to entity1
     */
    public static boolean isAlly(Entity entity1, Entity entity2)
    {
        Entity superior1 = getUltimateSuperiorEntity(entity1);
        Entity superior2 = getUltimateSuperiorEntity(entity2);
        return superior1 == superior2 ||
            superior1.isTeammate(superior2) ||
            isPartner(superior1, superior2);
    }

    /**
     * @return true if entity1 and entity2 are players in the same party or allied parties in OPAC
     */
    public static boolean isPartner(Entity entity1, Entity entity2)
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

    public static Entity getUltimateSuperiorEntity(Entity entity)
    {
        Entity ans = entity;
        while (ans != null)
        {
            Entity superior = getSuperiorEntity(ans);
            if (superior == null)
            {
                break;
            }
            ans = superior;
        }
        return ans;
    }

    @Nullable
    public static Entity getSuperiorEntity(Entity entity)
    {
        Entity owner = null;
        if (entity instanceof Tameable tameable)
        {
            owner = tameable.getOwner();
        }

        if (owner == null && entity instanceof Ownable ownable)
        {
            owner = ownable.getOwner();
        }

        if (owner == null && entity instanceof MobEntity mob)
        {
            var minionComponent = SoulControl.getSoulMinion(mob);
            owner = minionComponent.getOwner();
        }

        return owner;
    }
}
