package karashokleo.spell_dimension.data.loot_bag;

import karashokleo.loot_bag.api.common.bag.Bag;
import karashokleo.loot_bag.api.provider.AbstractBagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class BagProvider extends AbstractBagProvider
{
    public BagProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    protected void configure(BiConsumer<Identifier, Bag> provider)
    {
        for (SDBags entry : SDBags.values())
            provider.accept(entry.id, entry.bag);
    }

    @Override
    public String getName()
    {
        return "Spell Dimension Loot Bags";
    }
}
