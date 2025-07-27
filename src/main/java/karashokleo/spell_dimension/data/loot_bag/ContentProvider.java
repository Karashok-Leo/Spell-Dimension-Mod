package karashokleo.spell_dimension.data.loot_bag;

import karashokleo.loot_bag.api.common.content.Content;
import karashokleo.loot_bag.api.common.content.ContentEntry;
import karashokleo.loot_bag.api.data.AbstractContentProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class ContentProvider extends AbstractContentProvider
{
    public ContentProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    protected void configure(BiConsumer<Identifier, Content> provider)
    {
        for (SDContents ins : SDContents.values())
        {
            ContentEntry entry = ins.factory.get();
            provider.accept(entry.id(), entry.content());
        }
    }

    @Override
    public String getName()
    {
        return "Spell Dimension Loot Contents";
    }
}
