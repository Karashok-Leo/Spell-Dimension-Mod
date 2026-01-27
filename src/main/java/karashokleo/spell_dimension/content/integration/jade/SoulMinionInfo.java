package karashokleo.spell_dimension.content.integration.jade;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.item.SoulContainerItem;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.util.CommonProxy;

import java.util.UUID;

public enum SoulMinionInfo implements IEntityComponentProvider, IServerDataProvider<EntityAccessor>
{
    INSTANCE;

    public static final Identifier ID = SpellDimension.modLoc("soul_minion");

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config)
    {
        Entity entity = accessor.getEntity();
        if (!(entity instanceof MobEntity mob))
        {
            return;
        }
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
        if (minionComponent.hasOwner())
        {
            String name = null;
            NbtCompound data = accessor.getServerData();
            if (data.contains("SoulOwnerName"))
            {
                name = data.getString("SoulOwnerName");
            } else
            {
                UUID ownerUuid = minionComponent.getOwnerUuid();
                if (ownerUuid != null)
                {
                    name = CommonProxy.getLastKnownUsername(ownerUuid);
                }
            }
            if (name == null)
            {
                name = "???";
            }
            tooltip.add(SDTexts.TEXT$SOUL_MINION_INFO.get(name));
            return;
        }
        PlayerEntity player = accessor.getPlayer();
        if (!minionComponent.isOwner(player))
        {
            if (player.getMainHandStack().getItem() instanceof SoulContainerItem soulContainer)
            {
                float probability = soulContainer.getCaptureProbability(mob);
                tooltip.add(SDTexts.TOOLTIP$SOUL_MINION$CAPTURE_PROBABILITY.get("%.1f%%".formatted(probability * 100)));
            }
        }
    }

    @Override
    public void appendServerData(NbtCompound data, EntityAccessor accessor)
    {
        MinecraftServer server = accessor.getLevel().getServer();
        if (server != null &&
            server.isHost(accessor.getPlayer().getGameProfile()))
        {
            return;
        }
        Entity entity = accessor.getEntity();
        if (!(entity instanceof MobEntity mob))
        {
            return;
        }
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
        UUID ownerUuid = minionComponent.getOwnerUuid();
        if (ownerUuid == null)
        {
            return;
        }
        String name = CommonProxy.getLastKnownUsername(ownerUuid);
        if (name == null)
        {
            return;
        }
        data.putString("SoulOwnerName", name);
    }

    @Override
    public Identifier getUid()
    {
        return ID;
    }
}
