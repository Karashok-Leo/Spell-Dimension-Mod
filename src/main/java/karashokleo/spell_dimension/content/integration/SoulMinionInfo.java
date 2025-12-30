package karashokleo.spell_dimension.content.integration;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.item.SoulContainer;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class SoulMinionInfo implements IEntityComponentProvider
{
    public static final Identifier ID = SpellDimension.modLoc("soul_minion");

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig)
    {
        Entity entity = entityAccessor.getEntity();
        if (!(entity instanceof MobEntity mob))
        {
            return;
        }
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
        PlayerEntity owner = minionComponent.getOwner();
        if (owner == null)
        {
            PlayerEntity player = entityAccessor.getPlayer();
            if (player.getMainHandStack().getItem() instanceof SoulContainer soulContainer)
            {
                float probability = soulContainer.getCaptureProbability(mob);
                iTooltip.add(SDTexts.TOOLTIP$SOUL_MINION$CAPTURE_PROBABILITY.get("%.1f%%".formatted(probability * 100)));
            }
        } else
        {
            iTooltip.add(SDTexts.TEXT$SOUL_MINION_INFO.get(owner.getName()));
        }
    }

    @Override
    public Identifier getUid()
    {
        return ID;
    }
}
