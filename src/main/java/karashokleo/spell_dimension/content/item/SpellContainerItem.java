package karashokleo.spell_dimension.content.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class SpellContainerItem extends Item
{
    public SpellContainerItem()
    {
        super(
                new FabricItemSettings()
                        .fireproof()
                        .maxCount(1)
        );
    }
}
