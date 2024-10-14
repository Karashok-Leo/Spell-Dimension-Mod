package karashokleo.spell_dimension.init;

import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.util.TagUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.runes.api.RuneItems;
import net.spell_engine.internals.SpellInfinityEnchantment;

import java.util.List;
import java.util.stream.Stream;

public class AllTags
{
    public static final List<TagKey<Item>> ESSENCE = List.of(
            TagUtil.itemTag("essence/0"),
            TagUtil.itemTag("essence/1"),
            TagUtil.itemTag("essence/2")
    );
    public static final TagKey<Item> ESSENCE_ALL = TagUtil.itemTag("essence/all");
    public static final List<TagKey<Item>> BOOK = List.of(
            TagUtil.itemTag("book/0"),
            TagUtil.itemTag("book/1"),
            TagUtil.itemTag("book/2")
    );
    public static final TagKey<Item> BOOK_ALL = TagUtil.itemTag("book/all");

    public static final TagKey<Item> RUNE = TagUtil.itemTag("rune");
    public static final TagKey<Item> HEART_FOOD = TagUtil.itemTag("heart_food");

    public static final TagKey<Item> MELEE_WEAPONS = TagUtil.itemTag(new Identifier("equipment_standard:melee_weapons"));
    public static final TagKey<Item> ARMOR = TagUtil.itemTag(new Identifier("equipment_standard:armor"));

    public static final List<TagKey<Item>> SPELL_POWER_ENCHANTMENT_TAGS = Stream.of(
            "enchant_spell_power_generic",
            "enchant_spell_power_soulfrost",
            "enchant_spell_power_sunfire",
            "enchant_spell_power_energize",
            "enchant_critical_chance",
            "enchant_critical_damage",
            "enchant_haste"
    ).map(s -> TagUtil.itemTag(new Identifier("spell_power", s))).toList();

    public static final TagKey<Item> SPELL_INFINITY = TagUtil.itemTag(SpellInfinityEnchantment.tagId);

    public static final List<TagKey<Item>> REFORGE_CORE_TAGS = Stream.of(1, 2, 3).map(i -> TagUtil.itemTag(new Identifier("equipment_standard:reforge_core/lv" + i))).toList();

    public static final TagKey<Item> SHELL_HORN_REQUIREMENT = TagUtil.itemTag("shell_horn_requirement");

    public static final TagKey<Block> LOCATE_TARGET = TagUtil.blockTag("locate_target");

    public static final TagKey<Item> BACK = TagUtil.itemTag(new Identifier("trinkets", "chest/back"));
    public static final TagKey<Item> CAPE = TagUtil.itemTag(new Identifier("trinkets", "chest/cape"));

    public static void register()
    {
        TagGenerator<Item> generator = SpellDimension.ITEM_TAGS;

        RuneItems.entries.stream().map(RuneItems.Entry::id)
                .forEach(id -> generator.getOrCreateContainer(RUNE).add(id));

        generator.getOrCreateContainer(HEART_FOOD)
                .add(Items.ENCHANTED_GOLDEN_APPLE)
                .addOptional(
                        new Identifier("l2hostility:life_essence"),
                        new Identifier("midashunger:enchanted_golden_carrot")
                );

        generator.getOrCreateContainer(REFORGE_CORE_TAGS.get(0), true)
                .add(AllItems.SPAWNER_SOUL);
        generator.getOrCreateContainer(REFORGE_CORE_TAGS.get(1), true)
                .addOptional(new Identifier("l2hostility:chaos_ingot"));
        generator.getOrCreateContainer(REFORGE_CORE_TAGS.get(2), true)
                .addOptional(new Identifier("l2hostility:miracle_ingot"));

        TagGenerator.Container<Item> essenceAllContainer = generator.getOrCreateContainer(ESSENCE_ALL);
        for (TagKey<Item> key : ESSENCE)
            essenceAllContainer.addTag(key);

        TagGenerator.Container<Item> bookAllContainer = generator.getOrCreateContainer(BOOK_ALL);
        for (TagKey<Item> key : BOOK)
            bookAllContainer.addTag(key);

        SpellDimension.BLOCK_TAGS.getOrCreateContainer(LOCATE_TARGET).add(
                Blocks.LODESTONE
        );

        SpellDimension.BLOCK_TAGS.getOrCreateContainer(BlockTags.REPLACEABLE_BY_TREES).add(
                AllBlocks.CONSCIOUSNESS
        );

        SpellDimension.FLUID_TAGS.getOrCreateContainer(FluidTags.WATER).add(
                AllBlocks.STILL_CONSCIOUSNESS,
                AllBlocks.FLOWING_CONSCIOUSNESS
        );
    }
}
