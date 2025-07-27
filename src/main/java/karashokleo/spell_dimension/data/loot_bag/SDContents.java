package karashokleo.spell_dimension.data.loot_bag;

import accieo.midas.hunger.items.MidasItems;
import artifacts.registry.ModItems;
import karashokleo.loot_bag.api.common.content.Content;
import karashokleo.loot_bag.api.common.content.ContentEntry;
import karashokleo.loot_bag.api.common.content.ItemContent;
import karashokleo.loot_bag.api.common.content.LootTableContent;
import karashokleo.loot_bag.api.common.icon.ItemIcon;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.init.AllArmors;
import karashokleo.spell_dimension.init.AllWeapons;
import net.jewelry.items.JewelryItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.runes.api.RuneItems;
import net.trique.mythicupgrades.item.MUItems;
import net.wizards.item.Armors;
import net.wizards.item.Weapons;
import nourl.mythicmetals.item.MythicItems;
import nourl.mythicmetals.item.tools.MythicTools;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

public enum SDContents
{
    START(
            Weapons.arcaneWand::item,
            "Starter Kit",
            "初始装备",
            "Includes random digging tool, 16 bottles of recall potion, bottle of nightmare, bottle of soul binding, ender pouch, broken magic mirror, blank quest scroll, guide book",
            "包含随机挖掘工具、16瓶回忆药水、梦魇之瓶、灵魂羁绊之瓶、末影袋、破碎的魔镜、空白任务卷轴、指南书"
    ),
    ARTIFACT(
            () -> ModItems.CRYSTAL_HEART.get(),
            "Artifact",
            "奇异饰品",
            "Random 1 piece of artifact",
            "随机1件奇异饰品"
    ),
    ARING(
            () -> com.focamacho.ringsofascension.init.ModItems.ringFlight,
            "Rings of Ascension",
            "提升戒指",
            "Random 1 piece of ascension ring",
            "随机1件提升戒指"
    ),
    JEWELRY_RINGS(
            () -> JewelryItems.ruby_ring.item(),
            "Jewelry Ring",
            "珠宝戒指",
            "Random 1 piece of jewelry ring",
            "随机1件珠宝戒指"
    ),
    JEWELRY_NECKLACES(
            () -> JewelryItems.jade_necklace.item(),
            "Jewelry Necklace",
            "珠宝项链",
            "Random 1 piece of jewelry necklace",
            "随机1件珠宝项链"
    ),
    MIDAS(
            () -> MidasItems.COOKED_GOLDEN_BEEF,
            "Golden Food",
            "金色美食",
            "Random 5-piece Golden Food",
            "随机5件金色美食"
    ),
    COMMON_BOOK(
            () -> Items.ENCHANTED_BOOK,
            0,
            0
    ),
    COMMON_GEAR(
            () -> Items.IRON_SWORD,
            0,
            1
    ),
    COMMON_MATERIAL(
            () -> Items.IRON_INGOT,
            0,
            2
    ),
    UNCOMMON_BOOK(
            () -> Items.ENCHANTED_BOOK,
            1,
            0
    ),
    UNCOMMON_GEAR(
            () -> Items.DIAMOND_SWORD,
            1,
            1
    ),
    UNCOMMON_MATERIAL(
            () -> Items.DIAMOND,
            1,
            2
    ),
    RARE_BOOK(
            () -> Items.ENCHANTED_BOOK,
            2,
            0
    ),
    RARE_GEAR(
            () -> MUItems.SAPPHIRE_SWORD,
            2,
            1
    ),
    RARE_MATERIAL(
            () -> MUItems.SAPPHIRE_INGOT,
            2,
            2
    ),
    EPIC_BOOK(
            () -> Items.ENCHANTED_BOOK,
            3,
            0
    ),
    EPIC_GEAR(
            MythicTools.ADAMANTITE::getSword,
            3,
            1
    ),
    EPIC_MATERIAL(
            MythicItems.ADAMANTITE::getIngot,
            3,
            2
    ),
    LEGENDARY_BOOK(
            () -> Items.ENCHANTED_BOOK,
            4,
            0
    ),
    LEGENDARY_GEAR(
            MythicTools.METALLURGIUM::getSword,
            4,
            1
    ),
    LEGENDARY_MATERIAL(
            MythicItems.METALLURGIUM::getIngot,
            4,
            2
    ),
    ROBE_GENERAL(
            () -> Armors.wizardRobeSet.head,
            "General Robe",
            "通用法袍",
            "Includes full set of general robe",
            "包含全套通用法袍"
    ),
    ROBE_ARCANE(
            () -> Armors.arcaneRobeSet.head,
            "Arcane Robe",
            "奥秘法袍",
            "Includes full set of arcane robe",
            "包含全套奥秘法袍"
    ),
    ROBE_FIRE(
            () -> Armors.fireRobeSet.head,
            "Fire Robe",
            "火焰法袍",
            "Includes full set of fire robe",
            "包含全套火焰法袍"
    ),
    ROBE_FROST(
            () -> Armors.frostRobeSet.head,
            "Frost Robe",
            "寒冰法袍",
            "Includes full set of frost robe",
            "包含全套寒冰法袍"
    ),
    ROBE_HEALING(
            () -> net.paladins.item.armor.Armors.priestArmorSet_t1.head,
            "Healing Robe",
            "治愈法袍",
            "Includes full set of healing robe",
            "包含全套治愈法袍"
    ),
    ROBE_LIGHTNING(
            () -> AllArmors.LIGHTNING_ROBE.get(ArmorItem.Type.HELMET),
            "Lightning Robe",
            "雷电法袍",
            "Includes full set of lightning robe",
            "包含全套雷电法袍"
    ),
    WAND_ARCANE(
            () -> Weapons.arcaneWand.item().getDefaultStack(),
            "Arcane Wand",
            "奥秘魔杖"
    ),
    WAND_FIRE(
            () -> Weapons.fireWand.item().getDefaultStack(),
            "Fire Wand",
            "火焰魔杖"
    ),
    WAND_FROST(
            () -> Weapons.frostWand.item().getDefaultStack(),
            "Frost Wand",
            "寒冰魔杖"
    ),
    WAND_HEALING(
            () -> net.paladins.item.Weapons.holy_wand.item().getDefaultStack(),
            "Holy Wand",
            "神圣魔杖"
    ),
    WAND_LIGHTNING(
            () -> AllWeapons.LIGHTNING_WAND.getDefaultStack(),
            "Lightning Wand",
            "雷电魔杖"
    ),
    WAND_SOUL(
            () -> Weapons.arcaneWand.item().getDefaultStack(),
            "Soul Wand",
            "灵魂魔杖"
    ),
    RUNE_ARCANE(
            () -> runeStack(RuneItems.RuneType.ARCANE),
            "Arcane Rune",
            "奥秘符文"
    ),
    RUNE_FIRE(
            () -> runeStack(RuneItems.RuneType.FIRE),
            "Fire Rune",
            "火焰符文"
    ),
    RUNE_FROST(
            () -> runeStack(RuneItems.RuneType.FROST),
            "Frost Rune",
            "寒冰符文"
    ),
    RUNE_HEALING(
            () -> runeStack(RuneItems.RuneType.HEALING),
            "Healing Rune",
            "治愈符文"
    ),
    RUNE_LIGHTNING(
            () -> runeStack(RuneItems.RuneType.LIGHTNING),
            "Lightning Rune",
            "雷电符文"
    ),
    RUNE_SOUL(
            () -> runeStack(RuneItems.RuneType.SOUL),
            "Soul Rune",
            "灵魂符文"
    ),
    ;
    public final Identifier id;
    public final Supplier<ContentEntry> factory;
    public final String nameEn;
    public final String nameZh;
    public final String descEn;
    public final String descZh;

    SDContents(
            Function<String, Content> content,
            String nameEn,
            String nameZh,
            String descEn,
            String descZh
    )
    {
        String path = this.name().toLowerCase(Locale.ROOT).replace('_', '/');
        this.id = SpellDimension.modLoc(path);
        this.factory = () -> new ContentEntry(this.id, content.apply(path));
        this.nameEn = nameEn;
        this.nameZh = nameZh;
        this.descEn = descEn;
        this.descZh = descZh;
    }

    SDContents(
            Supplier<ItemConvertible> icon,
            String nameEn,
            String nameZh,
            String descEn,
            String descZh
    )
    {
        this(
                path -> new LootTableContent(
                        SpellDimension.modLoc("pool/" + path),
                        new ItemIcon(icon.get())
                ),
                nameEn,
                nameZh,
                descEn,
                descZh
        );
    }

    SDContents(
            String icon,
            String nameEn,
            String nameZh,
            String descEn,
            String descZh
    )
    {
        this(
                path -> new LootTableContent(
                        SpellDimension.modLoc("pool/" + path),
                        new ItemIcon(Registries.ITEM.get(new Identifier(icon)))
                ),
                nameEn,
                nameZh,
                descEn,
                descZh
        );
    }

    SDContents(Supplier<ItemConvertible> icon, int rarityIndex, int itemIndex)
    {
        this(
                icon,
                TextConstants.RARITIES_EN[rarityIndex] + " " + TextConstants.ITEMS_EN[itemIndex],
                TextConstants.RARITIES_ZH[rarityIndex] + TextConstants.ITEMS_ZH[itemIndex],
                "Random %s × %s"
                        .formatted(
                                TextConstants.COUNT[rarityIndex][itemIndex],
                                (TextConstants.RARITIES_EN[rarityIndex] + " " + TextConstants.ITEMS_EN[itemIndex]).toLowerCase(Locale.ROOT)
                        ),
                "随机%s件%s"
                        .formatted(
                                TextConstants.COUNT[rarityIndex][itemIndex],
                                (TextConstants.RARITIES_ZH[rarityIndex] + TextConstants.ITEMS_ZH[itemIndex]).toLowerCase(Locale.ROOT)
                        )
        );
    }

    SDContents(Supplier<ItemStack> stack, String nameEn, String nameZh)
    {
        this(
                p -> new ItemContent(stack.get(), new ItemIcon(stack.get())),
                nameEn,
                nameZh,
                "%d × %s".formatted(stack.get().getCount(), nameEn),
                "%d × %s".formatted(stack.get().getCount(), nameZh)
        );
    }

    @SuppressWarnings("all")
    private static ItemStack runeStack(RuneItems.RuneType type)
    {
        return new ItemStack(RuneItems.entries.stream().filter(entry -> entry.type() == type).findFirst().get().item(), 64);
    }
}
