package karashokleo.spell_dimension.content.integration.jade;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.misc.SpawnerExtension;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum SpawnerInfo implements IBlockComponentProvider
{
    INSTANCE;

    public static final Identifier ID = SpellDimension.modLoc("spawner_info");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig)
    {
        if (!(blockAccessor.getBlockEntity() instanceof MobSpawnerBlockEntity spawner))
        {
            return;
        }

        int remain = ((SpawnerExtension) spawner.getLogic()).getRemain();
        iTooltip.add(SDTexts.TEXT$SPAWNER$REMAIN.get(remain));
    }

    @Override
    public Identifier getUid()
    {
        return ID;
    }
}
