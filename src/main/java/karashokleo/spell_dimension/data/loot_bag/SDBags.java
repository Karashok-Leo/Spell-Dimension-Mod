package karashokleo.spell_dimension.data.loot_bag;

import karashokleo.loot_bag.api.common.bag.Bag;
import karashokleo.loot_bag.api.common.bag.BagEntry;
import karashokleo.loot_bag.api.common.bag.OptionalBag;
import karashokleo.loot_bag.api.common.bag.SingleBag;
import karashokleo.loot_bag.internal.item.LootBagItemRegistry;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

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
    JEWELRY$RINGS(
        SDContents.JEWELRY$RINGS,
        Rarity.EPIC,
        16711935,
        16776960
    ),
    JEWELRY$NECKLACES(
        SDContents.JEWELRY$NECKLACES,
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
    COMMON$BOOK(
        SDContents.COMMON$BOOK,
        Rarity.COMMON,
        16316671,
        16775920
    ),
    COMMON$GEAR(
        SDContents.COMMON$GEAR,
        Rarity.COMMON,
        16316671,
        16775920
    ),
    COMMON$MATERIAL(
        SDContents.COMMON$MATERIAL,
        Rarity.COMMON,
        16316671,
        16775920
    ),
    UNCOMMON$BOOK(
        SDContents.UNCOMMON$BOOK,
        Rarity.UNCOMMON,
        16776960,
        16775920
    ),
    UNCOMMON$GEAR(
        SDContents.UNCOMMON$GEAR,
        Rarity.UNCOMMON,
        16776960,
        16775920
    ),
    UNCOMMON$MATERIAL(
        SDContents.UNCOMMON$MATERIAL,
        Rarity.UNCOMMON,
        16776960,
        16775920
    ),
    RARE$BOOK(
        SDContents.RARE$BOOK,
        Rarity.RARE,
        65535,
        16775920
    ),
    RARE$GEAR(
        SDContents.RARE$GEAR,
        Rarity.RARE,
        65535,
        16775920
    ),
    RARE$MATERIAL(
        SDContents.RARE$MATERIAL,
        Rarity.RARE,
        65535,
        16775920
    ),
    EPIC$BOOK(
        SDContents.EPIC$BOOK,
        Rarity.EPIC,
        9055202,
        16775920
    ),
    EPIC$GEAR(
        SDContents.EPIC$GEAR,
        Rarity.EPIC,
        9055202,
        16775920
    ),
    EPIC$MATERIAL(
        SDContents.EPIC$MATERIAL,
        Rarity.EPIC,
        9055202,
        16775920
    ),
    LEGENDARY$BOOK(
        SDContents.LEGENDARY$BOOK,
        Rarity.EPIC,
        3100495,
        16775920
    ),
    LEGENDARY$GEAR(
        SDContents.LEGENDARY$GEAR,
        Rarity.EPIC,
        3100495,
        16775920
    ),
    LEGENDARY$MATERIAL(
        SDContents.LEGENDARY$MATERIAL,
        Rarity.EPIC,
        3100495,
        16775920
    ),
    ROBE(
        List.of(
            SDContents.ROBE$GENERAL,
            SDContents.ROBE$ARCANE,
            SDContents.ROBE$FIRE,
            SDContents.ROBE$FROST,
            SDContents.ROBE$HEALING,
            SDContents.ROBE$LIGHTNING,
            SDContents.ROBE$SOUL
        ),
        Rarity.COMMON,
        10040013,
        10444703,
        "Apprentice Robe Bag",
        "新手法袍袋"
    ),
    WAND(
        List.of(
            SDContents.WAND$ARCANE,
            SDContents.WAND$FIRE,
            SDContents.WAND$FROST,
            SDContents.WAND$HEALING,
            SDContents.WAND$LIGHTNING,
            SDContents.WAND$SOUL
        ),
        Rarity.COMMON,
        10040013,
        10444703,
        "Apprentice Wand Bag",
        "新手法杖袋"
    ),
    RUNE(
        List.of(
            SDContents.RUNE$ARCANE,
            SDContents.RUNE$FIRE,
            SDContents.RUNE$FROST,
            SDContents.RUNE$HEALING,
            SDContents.RUNE$LIGHTNING,
            SDContents.RUNE$SOUL
        ),
        Rarity.COMMON,
        10040013,
        10444703,
        "Magic Rune Bag",
        "魔法符文袋"
    ),
    SPELL_SCROLL(
        SDContents.SPELL_SCROLL,
        Rarity.EPIC,
        9055202,
        16775920,
        "Spell Scroll Bag",
        "法术卷轴袋"
    ),
    ;
    public final Identifier id;
    public final Supplier<BagEntry> factory;
    public final String nameEn;
    public final String nameZh;

    SDBags(Supplier<Bag> bag, String nameEn, String nameZh)
    {
        String path = this.name().toLowerCase(Locale.ROOT).replace('$', '/');
        this.id = SpellDimension.modLoc(path);
        this.factory = () -> new BagEntry(this.id, bag.get());
        this.nameEn = nameEn;
        this.nameZh = nameZh;
    }

    SDBags(SDContents contents, Rarity rarity, int bodyColor, int stringColor, String nameEn, String nameZh)
    {
        this(
            () -> new SingleBag(contents.factory.get(), rarity, new Bag.Color(bodyColor, stringColor)),
            nameEn,
            nameZh
        );
    }

    SDBags(SDContents contents, Rarity rarity, int bodyColor, int stringColor)
    {
        this(
            () -> new SingleBag(contents.factory.get(), rarity, new Bag.Color(bodyColor, stringColor)),
            contents.nameEn + " Loot Bag",
            contents.nameZh + "战利品袋"
        );
    }

    SDBags(List<SDContents> contents, Rarity rarity, int bodyColor, int stringColor, String nameEn, String nameZh)
    {
        this(
            () -> new OptionalBag(contents.stream().map(ins -> ins.factory.get()).toList(), rarity, new Bag.Color(bodyColor, stringColor)),
            nameEn,
            nameZh
        );
    }

    public ItemStack getStack()
    {
        return LootBagItemRegistry.LOOT_BAG.getStack(id);
    }
}
