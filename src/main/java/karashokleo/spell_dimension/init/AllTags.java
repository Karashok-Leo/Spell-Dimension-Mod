package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.util.TagUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public class AllTags
{
    public static final List<TagKey<Item>> ESSENCE = List.of(
            TagUtil.itemTag("essence/0"),
            TagUtil.itemTag("essence/1"),
            TagUtil.itemTag("essence/2")
    );
    public static final TagKey<Item> ESSENCE_ALL = TagUtil.itemTag("essence/all");
    public static final TagKey<Item> RUNE = TagUtil.itemTag("rune");
    public static final TagKey<Item> HEART_FOOD = TagUtil.itemTag("heart_food");
}
