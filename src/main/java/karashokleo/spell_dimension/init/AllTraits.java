package karashokleo.spell_dimension.init;

import com.lion.graveyard.init.TGEntities;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.traits.TraitSymbol;
import karashokleo.l2hostility.content.trait.MobTraitBuilder;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.trait.*;
import karashokleo.spell_dimension.data.generic.SDTraitConfigProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public class AllTraits
{
    public static AirborneTrait AIRBORNE;
    public static LeechTrait LEECH;

    // Arcane Tier 1
    public static IntervalSpellTrait SHIFT;
    public static IntervalSpellTrait CONVERGE;
    public static IntervalSpellTrait ARCANE_MISSILE;
    public static IntervalSpellTrait ARCANE_BLAST;
    public static IntervalSpellTrait ARCANE_BLINK;

    // Arcane Tier 2
    public static ChanelIntervalSpellTrait AMETHYST_SLASH;
    public static ChanelIntervalSpellTrait ARCANE_BEAM;

    // Arcane Tier 3
    public static CycloneTrait MAELSTROM;
    public static CycloneTrait ECHO_STORM;

    // Fire Tier 1
    public static IntervalSpellTrait FIREBALL;
    public static IntervalSpellTrait FIRE_WALL;
    public static IntervalSpellTrait FIRE_METEOR;

    // Fire Tier 2
    public static ChanelIntervalSpellTrait FLAME_SLASH;

    // Fire Tier 3
    public static CycloneTrait INFERNO;

    // Frost Tier 1
    public static IntervalSpellTrait FROST_NOVA;
    public static IntervalSpellTrait FROST_SHIELD;
    public static IntervalSpellTrait ICICLE;
    public static IntervalSpellTrait FROST_BLIZZARD;

    // Frost Tier 2
    public static IntervalSpellTrait NUCLEUS;
    public static IntervalSpellTrait FROST_AURA;
    public static IntervalSpellTrait FROST_BLINK;
    public static ChanelIntervalSpellTrait FROST_SLASH;

    // Frost Tier 3
    public static CycloneTrait TEMPEST;

    // Healing Tier 1
    public static IntervalSpellTrait DIVINE_PROTECTION;
    public static ChanelIntervalSpellTrait HOLY_BEAM;

    // Healing Tier 2
    public static IntervalSpellTrait BLESSING;
    public static IntervalSpellTrait MISFORTUNE;
    public static IntervalSpellTrait HEAVENLY_JUSTICE;

    // Lightning Tier 1
    public static ChanelIntervalSpellTrait CHAIN_LIGHTNING;
    public static IntervalSpellTrait BALL_LIGHTNING;
    public static IntervalSpellTrait THUNDERBOLT;
    // Lightning Tier 2
    public static IntervalSpellTrait QUANTUM_FIELD;

    // Boss
    public static SpellTrait BLACK_HOLE;
    public static IntervalSpellTrait INCARCERATE;

    public static void register()
    {
        AIRBORNE = Entry.of(
                "airborne",
                new AirborneTrait(),
                120, 100, 4, 50)
            .addEN()
            .addZH("击飞")
            .addENDesc("Every %s seconds, the next attack will knock the target into the air")
            .addZHDesc("每隔%s秒，下一次攻击将击飞目标")
            .register();
        LEECH = Entry.of(
                "leech",
                new LeechTrait(),
                30, 100, 5, 50)
            .addEN()
            .addZH("蛭吸")
            .addENDesc("%s of damage dealt will heal itself")
            .addZHDesc("造成的%s的伤害将治疗自身")
            .register();

        SHIFT = Entry.of(
                "shift",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.SHIFT, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .addBlacklistOptional(LHTags.SEMIBOSS)
            .register();
        CONVERGE = Entry.of(
                "converge",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.CONVERGE, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        ARCANE_MISSILE = Entry.of(
                "arcane_missile",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.ARCANE_MISSILE, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        ARCANE_BLAST = Entry.of(
                "arcane_blast",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.ARCANE_BLAST, lv -> 10 + lv * 10),
                200, 30, 3, 300)
            .register();
        ARCANE_BLINK = Entry.of(
                "arcane_blink",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.ARCANE_BLINK, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();

        AMETHYST_SLASH = Entry.of(
                "amethyst_slash",
                new ChanelIntervalSpellTrait(lv -> 300 - 40 * lv, lv -> 10, AllSpells.AMETHYST_SLASH, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();
        ARCANE_BEAM = Entry.of(
                "arcane_beam",
                new ChanelIntervalSpellTrait(lv -> 300 - 40 * lv, lv -> 10, AllSpells.ARCANE_BEAM, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();

        MAELSTROM = Entry.of(
                "maelstrom",
                new CycloneTrait(lv -> 300 - 40 * lv, AllSpells.MAELSTROM, lv -> 20 + lv * 20, 2),
                400, 10, 3, 900)
            .register();
        ECHO_STORM = Entry.of(
                "echo_storm",
                new CycloneTrait(lv -> 300 - 40 * lv, AllSpells.ECHO_STORM, lv -> 20 + lv * 20, 5),
                400, 10, 3, 900)
            .register();

        FIREBALL = Entry.of(
                "fireball",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.FIREBALL, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        FIRE_WALL = Entry.of(
                "fire_wall",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.FIRE_WALL, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        FIRE_METEOR = Entry.of(
                "fire_meteor",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.FIRE_METEOR, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();

        FLAME_SLASH = Entry.of(
                "flame_slash",
                new ChanelIntervalSpellTrait(lv -> 300 - 40 * lv, lv -> 10, AllSpells.FLAME_SLASH, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();

        INFERNO = Entry.of(
                "inferno",
                new CycloneTrait(lv -> 300 - 40 * lv, AllSpells.INFERNO, lv -> 20 + lv * 20, 4),
                400, 10, 3, 900)
            .register();

        FROST_NOVA = Entry.of(
                "frost_nova",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.FROST_NOVA, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        FROST_SHIELD = Entry.of(
                "frost_shield",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.FROST_SHIELD, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        ICICLE = Entry.of(
                "icicle",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.ICICLE, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        FROST_BLIZZARD = Entry.of(
                "frost_blizzard",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.FROST_BLIZZARD, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();

        NUCLEUS = Entry.of(
                "icy_nucleus",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.ICY_NUCLEUS, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();
        FROST_AURA = Entry.of(
                "frost_aura",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.FROST_AURA, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();
        FROST_BLINK = Entry.of(
                "frost_blink",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.FROST_BLINK, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();
        FROST_SLASH = Entry.of(
                "frost_slash",
                new ChanelIntervalSpellTrait(lv -> 300 - 40 * lv, lv -> 10, AllSpells.FROST_SLASH, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();

        TEMPEST = Entry.of(
                "tempest",
                new CycloneTrait(lv -> 300 - 40 * lv, AllSpells.TEMPEST, lv -> 20 + lv * 20, 3),
                400, 10, 3, 900)
            .register();

        DIVINE_PROTECTION = Entry.of(
                "divine_protection",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.DIVINE_PROTECTION, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        HOLY_BEAM = Entry.of(
                "holy_beam",
                new ChanelIntervalSpellTrait(lv -> 300 - 40 * lv, lv -> 10, AllSpells.HOLY_BEAM, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();

        BLESSING = Entry.of(
                "blessing",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.BLESSING, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();
        MISFORTUNE = Entry.of(
                "misfortune",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.MISFORTUNE, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();
        HEAVENLY_JUSTICE = Entry.of(
                "heavenly_justice",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.HEAVENLY_JUSTICE, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();

        CHAIN_LIGHTNING = Entry.of(
                "chain_lightning",
                new ChanelIntervalSpellTrait(lv -> 300 - 40 * lv, lv -> 10, AllSpells.CHAIN_LIGHTNING, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        BALL_LIGHTNING = Entry.of(
                "ball_lightning",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.BALL_LIGHTNING, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        THUNDERBOLT = Entry.of(
                "thunderbolt",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.THUNDERBOLT, lv -> 20 + lv * 20),
                200, 30, 3, 300)
            .register();
        QUANTUM_FIELD = Entry.of(
                "quantum_field",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.QUANTUM_FIELD, lv -> 20 + lv * 20),
                300, 20, 3, 600)
            .register();

        BLACK_HOLE = Entry.of(
                "black_hole",
                new SpellTrait(AllSpells.BLACK_HOLE, lv -> 80),
                0, 1, 1, 0)
//                .addENDesc("Fireballs spawns a black hole in place when they explode.")
//                .addZHDesc("发射的火球爆炸时在原地形成一个黑洞")
            .addWhitelist(EntityType.ENDER_DRAGON)
            .register();
        INCARCERATE = Entry.of(
                "incarcerate",
                new IntervalSpellTrait(lv -> 300 - 40 * lv, AllSpells.INCARCERATE, lv -> 40),
                0, 1, 1, 0)
            .addWhitelist(TGEntities.LICH.get())
            .register();
    }

    static class Entry<T extends MobTrait> extends MobTraitBuilder<T>
    {
        public static <T extends MobTrait> Entry<T> of(String name, T trait, int cost, int weight, int maxRank, int minLevel)
        {
            return new Entry<>(name, trait, new TraitConfig.Config(SpellDimension.modLoc(name), cost, weight, maxRank, minLevel));
        }

        protected Entry(String name, T trait, TraitConfig.Config config)
        {
            super(name, trait, config);
        }

        @Override
        public String getNameSpace()
        {
            return SpellDimension.MOD_ID;
        }

        @Override
        public <I extends Item> BiFunction<String, I, ItemBuilder<I>> getItemBuilder()
        {
            return AllItems.Entry::of;
        }

        @Override
        public void generateConfig(MobTrait trait, TraitConfig.Config config)
        {
            SDTraitConfigProvider.add(trait, config);
        }

        @Override
        public T register()
        {
            Identifier id = getId();
            ItemBuilder<Item> itemBuilder = this.getItemBuilder()
                .apply(name, new TraitSymbol(new FabricItemSettings()))
                .addTag(LHTags.TRAIT_ITEM);
            if (content instanceof SpellTrait)
            {
                itemBuilder.addTag(AllTags.SPELL_TRAIT_ITEM);
            }
            itemBuilder.register();
            this.addModel();
            this.generateConfig(content, config);
            return Registry.register(LHTraits.TRAIT, id, content);
        }

        public static final Identifier SYMBOL_BG = L2Hostility.id("item/bg");

        public void addModel()
        {
            Identifier modelId = this.getId().withPrefixedPath("item/");
            // spell texture is not contained in block atlas
//            Identifier trait = content instanceof SpellTrait spellTrait ?
//                    spellTrait.getSpellId().withPrefixedPath("spell/") :
//                    this.getId().withPrefixedPath("item/trait/");
            Identifier trait = this.getId().withPrefixedPath("item/trait/");
            TextureMap textureMap = TextureMap.layered(SYMBOL_BG, trait);
            this.getModelGenerator().addItem(generator ->
                Models.GENERATED_TWO_LAYERS.upload(
                    modelId,
                    textureMap,
                    generator.writer
                ));
        }
    }
}
