package karashokleo.spell_dimension.content.item.armor;

import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.leobrary.datagen.util.StringUtil;
import karashokleo.spell_dimension.init.AllGroups;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.BiFunction;

public class RobeBuilder
{
    protected EnumMap<ArmorItem.Type, String> suffixID = new EnumMap<>(ArmorItem.Type.class);
    protected EnumMap<ArmorItem.Type, String> suffixEN = new EnumMap<>(ArmorItem.Type.class);
    protected EnumMap<ArmorItem.Type, String> suffixZH = new EnumMap<>(ArmorItem.Type.class);
    protected EnumMap<ArmorItem.Type, ArmorItem> items = new EnumMap<>(ArmorItem.Type.class);

    protected final String name;

    @Nullable
    protected String nameEN;
    @Nullable
    protected String nameZH;

    @Nullable
    protected ConfiguredArmorMaterial material;

    protected ArrayList<TagKey<Item>> tags = new ArrayList<>();

    public RobeBuilder(String name)
    {
        this.name = name;
        suffixID.put(ArmorItem.Type.HELMET, "_robe_head");
        suffixID.put(ArmorItem.Type.CHESTPLATE, "_robe_chest");
        suffixID.put(ArmorItem.Type.LEGGINGS, "_robe_legs");
        suffixID.put(ArmorItem.Type.BOOTS, "_robe_feet");
        suffixEN.put(ArmorItem.Type.HELMET, " Hat");
        suffixEN.put(ArmorItem.Type.CHESTPLATE, " Robe Top");
        suffixEN.put(ArmorItem.Type.LEGGINGS, " Robe Bottom");
        suffixEN.put(ArmorItem.Type.BOOTS, " Boots");
        suffixZH.put(ArmorItem.Type.HELMET, "魔法师帽");
        suffixZH.put(ArmorItem.Type.CHESTPLATE, "魔法师长袍");
        suffixZH.put(ArmorItem.Type.LEGGINGS, "魔法师长袍（下半身）");
        suffixZH.put(ArmorItem.Type.BOOTS, "魔法师靴");
    }

    public RobeBuilder addEN()
    {
        this.nameEN = StringUtil.defaultName(name);
        return this;
    }

    public RobeBuilder addEN(String nameEN)
    {
        this.nameEN = nameEN;
        return this;
    }

    public RobeBuilder addZH(String nameZH)
    {
        this.nameZH = nameZH;
        return this;
    }

    @SafeVarargs
    public final RobeBuilder addTag(TagKey<Item>... keys)
    {
        this.tags.addAll(Arrays.asList(keys));
        return this;
    }

    public RobeBuilder material(ConfiguredArmorMaterial material)
    {
        this.material = material;
        return this;
    }

    public ArmorSet register()
    {
        if (material == null)
        {
            throw new IllegalStateException("Armor material must be set before registering the armor set.");
        }

        BiFunction<String, ArmorItem, ItemBuilder<ArmorItem>> itemBuilder = getItemBuilder();

        for (ArmorItem.Type type : ArmorItem.Type.values())
        {
            ItemBuilder<ArmorItem> builder = itemBuilder.apply(
                            name + suffixID.get(type),
                            new ConfiguredArmorItem(material, type)
                    )
                    .addEN(nameEN + suffixEN.get(type))
                    .addZH(nameZH + suffixZH.get(type))
                    .addTag(AllTags.WIZARD_ROBES);
            for (TagKey<Item> tag : tags)
            {
                builder.addTag(tag);
            }
            ArmorItem item = builder
                    .addModel()
                    .setTab(AllGroups.EQUIPMENTS)
                    .register();
            items.put(type, item);
        }

        return new ArmorSet(
                items.get(ArmorItem.Type.HELMET),
                items.get(ArmorItem.Type.CHESTPLATE),
                items.get(ArmorItem.Type.LEGGINGS),
                items.get(ArmorItem.Type.BOOTS)
        );
    }

    protected <T extends Item> BiFunction<String, T, ItemBuilder<T>> getItemBuilder()
    {
        return AllItems.Entry::of;
    }
}
