package karashokleo.spell_dimension.data.generic;

import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.Constants;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.leobrary.data.AbstractDataProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import java.util.HashMap;
import java.util.Map;

public class SDTraitConfigProvider extends AbstractDataProvider
{
    private static final Map<MobTrait, TraitConfig.Config> configs = new HashMap<>();

    public static void add(MobTrait trait, TraitConfig.Config config)
    {
        configs.put(trait, config);
    }

    public SDTraitConfigProvider(FabricDataOutput output)
    {
        super(output, Constants.PARENT_CONFIG_PATH + "/" + Constants.TRAIT_CONFIG_PATH);
    }

    @Override
    public String getName()
    {
        return "Spell Dimension Trait Config";
    }

    @Override
    public void addAll()
    {
        for (Map.Entry<MobTrait, TraitConfig.Config> entry : configs.entrySet())
        {
            add(entry.getKey().getNonNullId(), entry.getValue());
        }
    }
}
