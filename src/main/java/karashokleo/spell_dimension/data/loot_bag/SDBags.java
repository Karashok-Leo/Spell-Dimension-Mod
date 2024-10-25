package karashokleo.spell_dimension.data.loot_bag;

import karashokleo.loot_bag.api.common.bag.Bag;
import karashokleo.loot_bag.api.common.bag.BagEntry;
import karashokleo.loot_bag.api.common.bag.OptionalBag;
import karashokleo.loot_bag.api.common.bag.SingleBag;
import karashokleo.loot_bag.internal.item.LootBagItemRegistry;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.Locale;

public enum SDBags
{
    START(
            SDContents.START,
            Rarity.COMMON,
            13467442,
            16775920,
            SDContents.START.nameEn + " Bag",
            SDContents.START.nameZh + "袋"
    ),
    CHARM(
            SDContents.CHARM,
            Rarity.RARE,
            14277107,
            15132922
    ),
    ARTIFACT(
            SDContents.ARTIFACT,
            Rarity.EPIC,
            14972979,
            4353858
    ),
    ARING(
            SDContents.ARING,
            Rarity.EPIC,
            9408445,
            9315179
    ),
    VICTUS(
            SDContents.VICTUS,
            Rarity.EPIC,
            16711935,
            16776960
    ),
    JEWELRY_RINGS(
            SDContents.JEWELRY_RINGS,
            Rarity.EPIC,
            16711935,
            16776960
    ),
    JEWELRY_NECKLACES(
            SDContents.JEWELRY_NECKLACES,
            Rarity.EPIC,
            16711935,
            16776960
    ),
    MIDAS(
            SDContents.MIDAS,
            Rarity.COMMON,
            16776960,
            14408560
    ),
    COMMON_BOOK(
            SDContents.COMMON_BOOK,
            Rarity.COMMON,
            16316671,
            16775920
    ),
    COMMON_GEAR(
            SDContents.COMMON_GEAR,
            Rarity.COMMON,
            16316671,
            16775920
    ),
    COMMON_MATERIAL(
            SDContents.COMMON_MATERIAL,
            Rarity.COMMON,
            16316671,
            16775920
    ),
    UNCOMMON_BOOK(
            SDContents.UNCOMMON_BOOK,
            Rarity.UNCOMMON,
            16776960,
            16775920
    ),
    UNCOMMON_GEAR(
            SDContents.UNCOMMON_GEAR,
            Rarity.UNCOMMON,
            16776960,
            16775920
    ),
    UNCOMMON_MATERIAL(
            SDContents.UNCOMMON_MATERIAL,
            Rarity.UNCOMMON,
            16776960,
            16775920
    ),
    RARE_BOOK(
            SDContents.RARE_BOOK,
            Rarity.RARE,
            65535,
            16775920
    ),
    RARE_GEAR(
            SDContents.RARE_GEAR,
            Rarity.RARE,
            65535,
            16775920
    ),
    RARE_MATERIAL(
            SDContents.RARE_MATERIAL,
            Rarity.RARE,
            65535,
            16775920
    ),
    EPIC_BOOK(
            SDContents.EPIC_BOOK,
            Rarity.EPIC,
            9055202,
            16775920
    ),
    EPIC_GEAR(
            SDContents.EPIC_GEAR,
            Rarity.EPIC,
            9055202,
            16775920
    ),
    EPIC_MATERIAL(
            SDContents.EPIC_MATERIAL,
            Rarity.EPIC,
            9055202,
            16775920
    ),
    LEGENDARY_BOOK(
            SDContents.LEGENDARY_BOOK,
            Rarity.EPIC,
            3100495,
            16775920
    ),
    LEGENDARY_GEAR(
            SDContents.LEGENDARY_GEAR,
            Rarity.EPIC,
            3100495,
            16775920
    ),
    LEGENDARY_MATERIAL(
            SDContents.LEGENDARY_MATERIAL,
            Rarity.EPIC,
            3100495,
            16775920
    ),
    WAND(
            List.of(
                    SDContents.WAND_ARCANE,
                    SDContents.WAND_FIRE,
                    SDContents.WAND_FROST,
                    SDContents.WAND_HEALING
            ),
            Rarity.COMMON,
            10040013,
            10444703,
            "Apprentice Wand Bag",
            "新手法杖袋"
    ),
    RUNE(
            List.of(
                    SDContents.RUNE_ARCANE,
                    SDContents.RUNE_FIRE,
                    SDContents.RUNE_FROST,
                    SDContents.RUNE_HEALING
            ),
            Rarity.COMMON,
            10040013,
            10444703,
            "Magic Rune Bag",
            "魔法符文袋"
    ),
    ;
    public final Identifier id;
    public final Bag bag;
    public final BagEntry entry;
    public final String nameEn;
    public final String nameZh;

    SDBags(Bag bag, String nameEn, String nameZh)
    {
        String path = this.name().toLowerCase(Locale.ROOT).replace('_', '/');
        this.id = SpellDimension.modLoc(path);
        this.bag = bag;
        this.entry = new BagEntry(this.id, this.bag);
        this.nameEn = nameEn;
        this.nameZh = nameZh;
    }

    SDBags(SDContents contents, Rarity rarity, int bodyColor, int stringColor, String nameEn, String nameZh)
    {
        this(
                new SingleBag(contents.entry, rarity, new Bag.Color(bodyColor, stringColor)),
                nameEn,
                nameZh
        );
    }

    SDBags(SDContents contents, Rarity rarity, int bodyColor, int stringColor)
    {
        this(
                new SingleBag(contents.entry, rarity, new Bag.Color(bodyColor, stringColor)),
                contents.nameEn + " Loot Bag",
                contents.nameZh + "战利品袋"
        );
    }

    SDBags(List<SDContents> contents, Rarity rarity, int bodyColor, int stringColor, String nameEn, String nameZh)
    {
        this(
                new OptionalBag(contents.stream().map(ins -> ins.entry).toList(), rarity, new Bag.Color(bodyColor, stringColor)),
                nameEn,
                nameZh
        );
    }

    public ItemStack getStack()
    {
        ItemStack stack = LootBagItemRegistry.LOOT_BAG.getDefaultStack();
        stack.setSubNbt("BagId", NbtString.of(id.toString()));
        return stack;
    }
}
