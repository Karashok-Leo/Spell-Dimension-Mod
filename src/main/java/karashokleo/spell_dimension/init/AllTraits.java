package karashokleo.spell_dimension.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.traits.TraitSymbol;
import karashokleo.l2hostility.content.trait.MobTraitBuilder;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.trait.AirborneTrait;
import karashokleo.spell_dimension.content.trait.IntervalSpellTrait;
import karashokleo.spell_dimension.content.trait.LeechTrait;
import karashokleo.spell_dimension.content.trait.SpellTrait;
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
    public static IntervalSpellTrait SHIFT;
    public static SpellTrait BLACK_HOLE;
//    public static SpellTrait ARCANE_MISSILE;
//    public static SpellTrait ARCANE_BLAST;
//    public static SpellTrait ARCANE_BLINK;
//    public static SpellTrait FIREBALL;
//    public static SpellTrait FIRE_WALL;
//    public static SpellTrait FIRE_METEOR;
//    public static SpellTrait FROST_NOVA;
//    public static SpellTrait DIVINE_PROTECTION;
//    public static SpellTrait INCARCERATE;
//    public static SpellTrait CONVERGE;
//    public static SpellTrait ECHO_STORM;
//    public static SpellTrait AMETHYST_SLASH;
//    public static SpellTrait FLAME_SLASH;
//    public static SpellTrait FROST_BLIZZARD;
//    public static SpellTrait NUCLEUS;
//    public static SpellTrait AURA;
//    public static SpellTrait ICICLE;
//    public static SpellTrait FROST_BLINK;
//    public static SpellTrait FROST_SLASH;
//    public static SpellTrait BLESSING;
//    public static SpellTrait MISFORTUNE;
//    public static SpellTrait HEAVENLY_JUSTICE;

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
                .addWhitelist(EntityType.WARDEN)
                .addWhitelistOptional(LHTags.MELEE_WEAPON_TARGET)
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
                        new IntervalSpellTrait(lv -> 200 - 40 * lv, AllSpells.SHIFT),
                        120, 60, 3, 200)
                .addBlacklistOptional(LHTags.SEMIBOSS)
                .register();
        BLACK_HOLE = Entry.of(
                        "black_hole",
                        new SpellTrait(AllSpells.BLACK_HOLE, 0.2f),
                        0, 1, 1, 0)
                .addENDesc("Fireballs spawns a black hole in place when they explode.")
                .addZHDesc("发射的火球爆炸时在原地形成一个黑洞")
                .addWhitelist(EntityType.ENDER_DRAGON)
                .register();
//        ARCANE_MISSILE = Entry.of(
//                        "arcane_missile",
//                        new SpellTrait(lv -> 20, AllSpells.ARCANE_MISSILE),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        ARCANE_BLAST = Entry.of(
//                        "arcane_blast",
//                        new SpellTrait(lv -> 20, AllSpells.ARCANE_BLAST),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        ARCANE_BLINK = Entry.of(
//                        "arcane_blink",
//                        new SpellTrait(lv -> 20, AllSpells.ARCANE_BLINK),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        FIREBALL = Entry.of(
//                        "fireball",
//                        new SpellTrait(lv -> 20, AllSpells.FIREBALL),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        FIRE_WALL = Entry.of(
//                        "fire_wall",
//                        new SpellTrait(lv -> 20, AllSpells.FIRE_WALL),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        FIRE_METEOR = Entry.of(
//                        "fire_meteor",
//                        new SpellTrait(lv -> 20, AllSpells.FIRE_METEOR),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        FROST_NOVA = Entry.of(
//                        "frost_nova",
//                        new SpellTrait(lv -> 20, AllSpells.FROST_NOVA),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        DIVINE_PROTECTION = Entry.of(
//                        "divine_protection",
//                        new SpellTrait(lv -> 20, AllSpells.DIVINE_PROTECTION),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        INCARCERATE = Entry.of(
//                        "incarcerate",
//                        new SpellTrait(lv -> 20, AllSpells.INCARCERATE),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        CONVERGE = Entry.of(
//                        "converge",
//                        new SpellTrait(lv -> 20, AllSpells.CONVERGE),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        ECHO_STORM = Entry.of(
//                        "echo_storm",
//                        new SpellTrait(lv -> 20, AllSpells.ECHO_STORM),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        AMETHYST_SLASH = Entry.of(
//                        "amethyst_slash",
//                        new SpellTrait(lv -> 20, AllSpells.AMETHYST_SLASH),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        FLAME_SLASH = Entry.of(
//                        "flame_slash",
//                        new SpellTrait(lv -> 20, AllSpells.FLAME_SLASH),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        FROST_BLIZZARD = Entry.of(
//                        "frost_blizzard",
//                        new SpellTrait(lv -> 20, AllSpells.FROST_BLIZZARD),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        NUCLEUS = Entry.of(
//                        "nucleus",
//                        new SpellTrait(lv -> 20, AllSpells.NUCLEUS),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        AURA = Entry.of(
//                        "aura",
//                        new SpellTrait(lv -> 20, AllSpells.AURA),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        ICICLE = Entry.of(
//                        "icicle",
//                        new SpellTrait(lv -> 20, AllSpells.ICICLE),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        FROST_BLINK = Entry.of(
//                        "frost_blink",
//                        new SpellTrait(lv -> 20, AllSpells.FROST_BLINK),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        FROST_SLASH = Entry.of(
//                        "frost_slash",
//                        new SpellTrait(lv -> 20, AllSpells.FROST_SLASH),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        BLESSING = Entry.of(
//                        "blessing",
//                        new SpellTrait(lv -> 20, AllSpells.BLESSING),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        MISFORTUNE = Entry.of(
//                        "misfortune",
//                        new SpellTrait(lv -> 20, AllSpells.MISFORTUNE),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
//        HEAVENLY_JUSTICE = Entry.of(
//                        "heavenly_justice",
//                        new SpellTrait(lv -> 20, AllSpells.HEAVENLY_JUSTICE),
//                        0, 1, 1, 0)
//                .addWhitelistOptional(new Identifier("bosses_of_mass_destruction:obsidilith"))
//                .register();
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
            this.getItemBuilder()
                    .apply(name, new TraitSymbol(new FabricItemSettings()))
                    .addTag(LHTags.TRAIT_ITEM)
                    .register();
            this.addModel();
            this.generateConfig(content, config);
            return Registry.register(LHTraits.TRAIT, id, content);
        }

        public static final Identifier SYMBOL_BG = L2Hostility.id("item/bg");

        public void addModel()
        {
            Identifier modelId = this.getId().withPrefixedPath("item/");
            Identifier foreground = content instanceof SpellTrait spellTrait ?
                    spellTrait.getSpellId().withPrefixedPath("spell/") :
                    this.getId().withPrefixedPath("item/trait/");
            TextureMap textureMap = TextureMap.layered(SYMBOL_BG, foreground);
            this.getModelGenerator().addItem(generator ->
                    Models.GENERATED_TWO_LAYERS.upload(
                            modelId,
                            textureMap,
                            generator.writer
                    ));
        }
    }
}
