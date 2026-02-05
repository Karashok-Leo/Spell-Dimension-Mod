package karashokleo.spell_dimension.util;

import com.mojang.datafixers.util.Either;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import xaero.pac.common.server.api.OpenPACServerAPI;
import xaero.pac.common.server.parties.party.api.IPartyManagerAPI;
import xaero.pac.common.server.parties.party.api.IServerPartyAPI;

import java.util.UUID;

public class RelationUtil
{
    /**
     * @return true if entity2 is allied to entity1
     */
    public static boolean isAlly(Entity entity1, Entity entity2)
    {
        Either<Entity, UUID> superior1 = getUltimateSuperiorEntity(entity1);
        Either<Entity, UUID> superior2 = getUltimateSuperiorEntity(entity2);

        Entity superiorEntity1 = superior1.left().orElse(null);
        Entity superiorEntity2 = superior2.left().orElse(null);

        if (superiorEntity1 != null && superiorEntity2 != null)
        {
            return superiorEntity1 == superiorEntity2 ||
                superiorEntity1.isTeammate(superiorEntity2) ||
                isPartner(superiorEntity1, superiorEntity2);
        }

        UUID superiorUUID1 = superior1.map(Entity::getUuid, uuid -> uuid);
        UUID superiorUUID2 = superior2.map(Entity::getUuid, uuid -> uuid);

        if (superiorUUID1.equals(superiorUUID2))
        {
            return true;
        }

        MinecraftServer server = entity1.getServer();
        return server != null && isPartner(server, superiorUUID1, superiorUUID2);
    }

    /**
     * @return true if entity1 and entity2 are players in the same party or allied parties in OPAC
     */
    public static boolean isPartner(MinecraftServer server, UUID entity1, UUID entity2)
    {
        IPartyManagerAPI manager = OpenPACServerAPI.get(server).getPartyManager();
        IServerPartyAPI party1 = manager.getPartyByMember(entity1);
        if (party1 == null)
        {
            return false;
        }
        IServerPartyAPI party2 = manager.getPartyByMember(entity2);
        if (party2 == null)
        {
            return false;
        }
        if (party1 == party2)
        {
            return true;
        }
        if (party1.getId().equals(party2.getId()))
        {
            return true;
        }
        return party1.isAlly(party2.getId());
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
        return isPartner(server, sp1.getUuid(), sp2.getUuid());
    }

    private static Either<Entity, UUID> getUltimateSuperiorEntity(Entity entity)
    {
        Either<Entity, UUID> ans = Either.left(entity);
        while (true)
        {
            // TODO: prevent circular reference?
            Entity cur = ans.left().orElse(null);
            if (cur == null)
            {
                break;
            }
            var superior = getSuperiorEntity(cur);
            if (superior == null)
            {
                break;
            }
            // TODO: check using uuid?
            if (cur == superior.left().orElse(null))
            {
                break;
            }
            ans = superior;
        }
        return ans;
    }

    @Nullable
    private static Either<Entity, UUID> getSuperiorEntity(Entity entity)
    {
        if (entity instanceof Tameable tameable)
        {
            var owner = tameable.getOwner();
            if (owner != null)
            {
                return Either.left(owner);
            }
            UUID ownerUUID = tameable.getOwnerUuid();
            if (ownerUUID != null)
            {
                return Either.right(ownerUUID);
            }
        }

        // TODO: use OwnerGetter?
        if (entity instanceof Ownable ownable)
        {
            var owner = ownable.getOwner();
            if (owner != null)
            {
                return Either.left(owner);
            }
        }

        if (entity instanceof MobEntity mob)
        {
            var minionComponent = SoulControl.getSoulMinion(mob);
            if (minionComponent.hasOwner())
            {
                var owner = minionComponent.getOwner();
                if (owner != null)
                {
                    return Either.left(owner);
                }
                return Either.right(minionComponent.getOwnerUuid());
            }
        }

        if (entity instanceof FakePlayerEntity fakePlayer)
        {
            var owner = fakePlayer.getPlayer();
            if (owner != null)
            {
                return Either.left(owner);
            }
            UUID ownerUUID = fakePlayer.getPlayerUUID();
            if (ownerUUID != null)
            {
                return Either.right(ownerUUID);
            }
        }

        if (entity instanceof EnderDragonPart part)
        {
            var owner = part.owner;
            if (owner != null)
            {
                return Either.left(owner);
            }
        }

        return null;
    }
}
