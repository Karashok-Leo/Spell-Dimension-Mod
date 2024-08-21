package karashokleo.spell_dimension.data.loot_bag;

import karashokleo.loot_bag.api.common.content.Content;
import karashokleo.loot_bag.api.common.content.ContentEntry;
import karashokleo.loot_bag.api.common.content.ItemContent;
import karashokleo.loot_bag.api.common.content.LootTableContent;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.runes.api.RuneItems;
import net.wizards.item.Weapons;

import java.util.Locale;
import java.util.function.Function;

public enum SDContents
{
    START(
            "wizards:textures/item/wand_arcane.png",
            "Starter Kit",
            "初始装备",
            "Includes random digging tool, 16 bottles of recall potion, ender pouch",
            "包含随机挖掘工具、16瓶回忆药水、末影袋"
    ),
    CHARM(
            "mythic_charms:textures/item/fragile_charm_of_gaze_serenity.png",
            "Charm",
            "护符",
            "Random 1 piece of charm",
            "随机1件护符"
    ),
    ARTIFACT(
            "artifacts:textures/item/crystal_heart.png",
            "Artifact",
            "奇异饰品",
            "Random 1 piece of artifact",
            "随机1件奇异饰品"
    ),
    ARING(
            "ringsofascension:textures/item/ring_flight.png",
            "Rings of Ascension",
            "提升戒指",
            "Random 1 piece of ascension ring",
            "随机1件提升戒指"
    ),
    JEWELRY_RINGS(
            "jewelry:textures/item/ruby_ring.png",
            "Jewelry Ring",
            "珠宝戒指",
            "Random 1 piece of jewelry ring",
            "随机1件珠宝戒指"
    ),
    JEWELRY_NECKLACES(
            "jewelry:textures/item/jade_necklace.png",
            "Jewelry Necklace",
            "珠宝项链",
            "Random 1 piece of jewelry necklace",
            "随机1件珠宝项链"
    ),
    MIDAS(
            "midashunger:textures/item/cooked_golden_beef.png",
            "Golden Food",
            "金色美食",
            "Random 5-piece Golden Food",
            "随机5件金色美食"
    ),
    COMMON_BOOK(
            "minecraft:textures/item/enchanted_book.png",
            0,
            0
    ),
    COMMON_GEAR(
            "minecraft:textures/item/iron_sword.png",
            0,
            1
    ),
    COMMON_MATERIAL(
            "minecraft:textures/item/iron_ingot.png",
            0,
            2
    ),
    UNCOMMON_BOOK(
            "minecraft:textures/item/enchanted_book.png",
            1,
            0
    ),
    UNCOMMON_GEAR(
            "minecraft:textures/item/diamond_sword.png",
            1,
            1
    ),
    UNCOMMON_MATERIAL(
            "minecraft:textures/item/diamond.png",
            1,
            2
    ),
    RARE_BOOK(
            "minecraft:textures/item/enchanted_book.png",
            2,
            0
    ),
    RARE_GEAR(
            "mythicupgrades:textures/item/sapphire_sword.png",
            2,
            1
    ),
    RARE_MATERIAL(
            "mythicupgrades:textures/item/sapphire_ingot.png",
            2,
            2
    ),
    EPIC_BOOK(
            "minecraft:textures/item/enchanted_book.png",
            3,
            0
    ),
    EPIC_GEAR(
            "mythicmetals:textures/item/tools/adamantite_sword.png",
            3,
            1
    ),
    EPIC_MATERIAL(
            "mythicmetals:textures/item/adamantite_ingot.png",
            3,
            2
    ),
    LEGENDARY_BOOK(
            "minecraft:textures/item/enchanted_book.png",
            4,
            0
    ),
    LEGENDARY_GEAR(
            "mythicmetals:textures/item/tools/metallurgium_sword.png",
            4,
            1
    ),
    LEGENDARY_MATERIAL(
            "mythicmetals:textures/item/metallurgium_ingot.png",
            4,
            2
    ),
    WAND_ARCANE(
            Weapons.arcaneWand.item().getDefaultStack(),
            "Arcane Wand",
            "奥秘魔杖"
    ),
    WAND_FIRE(
            Weapons.fireWand.item().getDefaultStack(),
            "Fire Wand",
            "火焰魔杖"
    ),
    WAND_FROST(
            Weapons.frostWand.item().getDefaultStack(),
            "Frost Wand",
            "寒冰魔杖"
    ),
    WAND_HEALING(
            net.paladins.item.Weapons.holy_wand.item().getDefaultStack(),
            "Holy Wand",
            "神圣魔杖"
    ),
    WAND_LIGHTNING(
            Weapons.arcaneWand.item().getDefaultStack(),
            "Lightning Wand",
            "雷电魔杖"
    ),
    WAND_SOUL(
            Weapons.arcaneWand.item().getDefaultStack(),
            "Soul Wand",
            "灵魂魔杖"
    ),
    RUNE_ARCANE(
            runeStack(RuneItems.RuneType.ARCANE),
            "Arcane Rune",
            "奥秘符文"
    ),
    RUNE_FIRE(
            runeStack(RuneItems.RuneType.FIRE),
            "Fire Rune",
            "火焰符文"
    ),
    RUNE_FROST(
            runeStack(RuneItems.RuneType.FROST),
            "Frost Rune",
            "寒冰符文"
    ),
    RUNE_HEALING(
            runeStack(RuneItems.RuneType.HEALING),
            "Healing Rune",
            "治愈符文"
    ),
    RUNE_LIGHTNING(
            runeStack(RuneItems.RuneType.LIGHTNING),
            "Lightning Rune",
            "雷电符文"
    ),
    RUNE_SOUL(
            runeStack(RuneItems.RuneType.SOUL),
            "Soul Rune",
            "灵魂符文"
    ),
    ;
    public final Identifier id;
    public final Content content;
    public final ContentEntry entry;
    public final String nameEn;
    public final String nameZh;
    public final String descEn;
    public final String descZh;

    SDContents(Function<String, Content> content, String nameEn, String nameZh, String descEn, String descZh)
    {
        String path = this.name().toLowerCase(Locale.ROOT).replace('_', '/');
        this.id = SpellDimension.modLoc(path);
        this.content = content.apply(path);
        this.entry = new ContentEntry(this.id, this.content);
        this.nameEn = nameEn;
        this.nameZh = nameZh;
        this.descEn = descEn;
        this.descZh = descZh;
    }

    SDContents(String texture, String nameEn, String nameZh, String descEn, String descZh)
    {
        this(
                path -> new LootTableContent(
                        SpellDimension.modLoc("pool/" + path),
                        new Content.Icon(new Identifier(texture))
                ),
                nameEn,
                nameZh,
                descEn,
                descZh
        );
    }

    SDContents(Content content, String nameEn, String nameZh, String descEn, String descZh)
    {
        this(
                path -> content,
                nameEn,
                nameZh,
                descEn,
                descZh
        );
    }

    SDContents(String texture, int rarityIndex, int itemIndex)
    {
        this(
                texture,
                ConstantStr.RARITIES_EN[rarityIndex] + " " + ConstantStr.ITEMS_EN[itemIndex],
                ConstantStr.RARITIES_ZH[rarityIndex] + ConstantStr.ITEMS_ZH[itemIndex],
                "Random %s × %s"
                        .formatted(
                                ConstantStr.COUNT[rarityIndex][itemIndex],
                                (ConstantStr.RARITIES_EN[rarityIndex] + " " + ConstantStr.ITEMS_EN[itemIndex]).toLowerCase(Locale.ROOT)
                        ),
                "随机%s件%s"
                        .formatted(
                                ConstantStr.COUNT[rarityIndex][itemIndex],
                                (ConstantStr.RARITIES_ZH[rarityIndex] + ConstantStr.ITEMS_ZH[itemIndex]).toLowerCase(Locale.ROOT)
                        )
        );
    }

    SDContents(ItemStack stack, String nameEn, String nameZh)
    {
        this(
                new ItemContent(stack),
                nameEn,
                nameZh,
                "%d × %s".formatted(stack.getCount(), nameEn),
                "%d × %s".formatted(stack.getCount(), nameZh)
        );
    }

    private static ItemStack runeStack(RuneItems.RuneType type)
    {
        ItemStack stack = RuneItems.get(type).getDefaultStack();
        stack.setCount(stack.getMaxCount());
        return stack;
    }
}