package karashokleo.spell_dimension.util;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagUtil
{
    public static TagKey<Item> itemTag(Identifier id)
    {
        return TagKey.of(RegistryKeys.ITEM, id);
    }

    public static TagKey<Item> itemTag(String path)
    {
        return TagKey.of(RegistryKeys.ITEM, SpellDimension.modLoc(path));
    }
}
