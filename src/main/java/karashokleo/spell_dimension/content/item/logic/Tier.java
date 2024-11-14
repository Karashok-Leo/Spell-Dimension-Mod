package karashokleo.spell_dimension.content.item.logic;

import karashokleo.spell_dimension.util.TagUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.Locale;

public enum Tier
{
    COMMON(16316671),
    UNCOMMON(16776960),
    RARE(65535),
    EPIC(9055202),
    LEGENDARY(3100495),
    ;
    public final String name;
    public final int color;

    Tier(int color)
    {
        this.name = this.name().toLowerCase(Locale.ROOT);
        this.color = color;
    }

    public TagKey<Item> getTag(String suffix)
    {
        return TagUtil.itemTag(name + "/" + suffix);
    }
}
