package karashokleo.spell_dimension.init;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.leobrary.datagen.builder.NamedEntryBuilder;
import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.item.DynamicSpellBookItem;
import karashokleo.spell_dimension.content.quest.*;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.spell_power.api.SpellPowerMechanics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllQuests
{
    public static FirstDayQuest FIRST_DAY;

    public static HealthQuest HEALTH;
    public static SimpleAdvancementQuest CHOOSE_PATH;
    public static SimpleAdvancementQuest KILL_TRAIT;

    public static SimpleTagIngredientQuest BASE_ESSENCE;
    public static SimpleTagIngredientQuest RUNE;
    public static SimpleItemQuest MORE_ESSENCE;
    public static SimpleTagIngredientQuest SPELL_BOOK_0;
    public static SimpleTagIngredientQuest SPELL_BOOK_1;
    public static SimpleTagIngredientQuest SPELL_BOOK_2;
    public static SpellPowerQuest SPELL_POWER_0;
    public static SpellPowerQuest SPELL_POWER_1;
    public static SpellPowerQuest SPELL_POWER_2;

    public static SimpleLootItemQuest KILL_MUTANT_ZOMBIE;
    public static SimpleLootItemQuest KILL_MUTANT_SKELETON;
    public static SimpleLootItemQuest KILL_MUTANT_CREEPER;
    public static SimpleLootItemQuest KILL_MUTANT_ENDERMAN;

    public static SimpleLootItemQuest KILL_OLD_CHAMPION;
    public static SimpleLootItemQuest KILL_DECAYING_KING;
    public static SimpleLootItemQuest KILL_ELDER_GUARDIAN;
    public static SimpleLootItemQuest KILL_WITHER;
    public static SimpleLootItemQuest KILL_INVOKER;
    public static SimpleLootItemQuest KILL_WARDEN;

    public static SimpleLootItemQuest KILL_MOON_KNIGHT;
    public static SimpleLootItemQuest KILL_BLACKSTONE_GOLEM;
    public static SimpleLootItemQuest KILL_CAPTAIN_CORNELIA;
    public static SimpleLootItemQuest KILL_CHAOS_MONARCH;
    public static SimpleLootItemQuest KILL_RETURNING_KNIGHT;
    public static SimpleLootItemQuest KILL_STALKER;

    public static SimpleLootItemQuest KILL_GRAVEYARD_LICH;
    public static SimpleLootItemQuest KILL_BOMD_LICH;
    public static SimpleLootItemQuest KILL_VOID_BLOSSOM;
    public static SimpleLootItemQuest KILL_GAUNTLET;

    public static SimpleLootItemQuest KILL_DAY_NIGHT;
    public static EnderDragonAdvancementQuest KILL_ENDER_DRAGON;
    public static SimpleLootItemQuest KILL_THE_EYE;

    public static SimpleLootItemQuest KILL_OBSIDLITH;
    public static SimpleLootItemQuest KILL_VOID_SHADOW;

    public static void register()
    {
        registerQuestsBase();
        registerQuestsMage();
        registerQuestsKillMutant();
        registerQuestsKillT4();
        registerQuestsKillT3();
        registerQuestsKillT2();
        registerQuestsKillT1();
        registerQuestsKillT0();
        Entry.buildRelations();
    }

    public static void registerQuestsBase()
    {
        FIRST_DAY = Entry.of("first_day", new FirstDayQuest(
                        SDBags.MIDAS::getStack
                ))
                .addEnDesc("Survive for one day")
                .addZhDesc("存活一天")
                .register();
        HEALTH = Entry.of("health", new HealthQuest(20,
                        Items.ENCHANTED_GOLDEN_APPLE::getDefaultStack
                ))
                .addDependencies(FIRST_DAY)
                .register();
        CHOOSE_PATH = Entry.of("choose_path", new SimpleAdvancementQuest(
                        new Identifier("rpg_series:classes"),
                        () -> AllItems.ENLIGHTENING_ESSENCE.getStack(
                                new AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.attribute, 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE).toELM()
                        ),
                        "advancements.rpg_series.classes.description"
                ))
                .addDependencies(FIRST_DAY)
                .register();
        KILL_TRAIT = Entry.of("kill_trait", new SimpleAdvancementQuest(
                        L2Hostility.id("hostility/kill_first"),
                        () -> AllItems.ENLIGHTENING_ESSENCE.getStack(
                                new AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.attribute, 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE).toELM()
                        ),
                        "advancements.l2hostility.kill_first.description"
                ))
                .addDependencies(FIRST_DAY)
                .register();
    }

    public static void registerQuestsMage()
    {
        BASE_ESSENCE = Entry.of(
                        "base_essence",
                        new SimpleTagIngredientQuest(
                                AllTags.ESSENCE_ALL,
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addDependencies(CHOOSE_PATH)
                .addEnDesc("Obtain any basic magic essence")
                .addZhDesc("获取任意基础魔法精华")
                .register();
        RUNE = Entry.of(
                        "rune",
                        new SimpleTagIngredientQuest(
                                AllTags.RUNE,
                                SDBags.ARTIFACT::getStack
                        )
                )
                .addDependencies(BASE_ESSENCE)
                .addEnDesc("Use basic magic essence to craft runes at the rune altar")
                .addZhDesc("使用基础魔法精华在符文祭坛上合成符文")
                .register();
        MORE_ESSENCE = Entry.of(
                        "more_essence",
                        new SimpleItemQuest(
                                List.of(
                                        () -> AllItems.ENLIGHTENING_ESSENCE,
                                        () -> AllItems.ENCHANTED_ESSENCE,
                                        () -> AllItems.MENDING_ESSENCE
                                ),
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(BASE_ESSENCE)
                .register();
        SPELL_BOOK_0 = Entry.of(
                        "spell_book_0",
                        new SimpleTagIngredientQuest(
                                AllTags.BOOK.get(0),
                                SDBags.UNCOMMON_GEAR::getStack
                        )
                )
                .addDependencies(CHOOSE_PATH)
                .addEnDesc("Craft your Apprentice Spell Book into a Primary Spell Book")
                .addZhDesc("将学徒法术书合成为初级法术书")
                .register();
        SPELL_BOOK_1 = Entry.of(
                        "spell_book_1",
                        new SimpleTagIngredientQuest(
                                AllTags.BOOK.get(1),
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .addDependencies(SPELL_BOOK_0)
                .addEnDesc("Craft your Primary Spell Book into a Intermediate Spell Book")
                .addZhDesc("将初级法术书合成为中级法术书")
                .register();
        SPELL_BOOK_2 = Entry.of(
                        "spell_book_2",
                        new SimpleTagIngredientQuest(
                                AllTags.BOOK.get(2),
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(SPELL_BOOK_1)
                .addEnDesc("Craft your Intermediate Spell Book into a Advanced Spell Book")
                .addZhDesc("将中级法术书合成为高级法术书")
                .register();
        SPELL_POWER_0 = Entry.of(
                        "spell_power_0",
                        new SpellPowerQuest(
                                DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE,
                                SDBags.UNCOMMON_MATERIAL::getStack
                        )
                )
                .addDependencies(CHOOSE_PATH)
                .register();
        SPELL_POWER_1 = Entry.of(
                        "spell_power_1",
                        new SpellPowerQuest(
                                DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE * 2,
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(SPELL_POWER_0)
                .register();
        SPELL_POWER_2 = Entry.of(
                        "spell_power_2",
                        new SpellPowerQuest(
                                DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE * 3,
                                SDBags.EPIC_MATERIAL::getStack
                        )
                )
                .addDependencies(SPELL_POWER_1)
                .register();
    }

    public static void registerQuestsKillMutant()
    {
        KILL_MUTANT_ZOMBIE = Entry.of(
                        "kill_mutant_zombie",
                        new SimpleLootItemQuest(
                                ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE::get,
                                ModRegistry.HULK_HAMMER_ITEM::get,
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(KILL_TRAIT)
                .register();
        KILL_MUTANT_SKELETON = Entry.of(
                        "kill_mutant_skeleton",
                        new SimpleLootItemQuest(
                                ModRegistry.MUTANT_SKELETON_ENTITY_TYPE::get,
                                ModRegistry.MUTANT_SKELETON_SKULL_ITEM::get,
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(KILL_TRAIT)
                .register();
        KILL_MUTANT_CREEPER = Entry.of(
                        "kill_mutant_creeper",
                        new SimpleLootItemQuest(
                                ModRegistry.MUTANT_CREEPER_ENTITY_TYPE::get,
                                ModRegistry.CREEPER_SHARD_ITEM::get,
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .addDependencies(KILL_TRAIT)
                .register();
        KILL_MUTANT_ENDERMAN = Entry.of(
                        "kill_mutant_enderman",
                        new SimpleLootItemQuest(
                                ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE::get,
                                ModRegistry.ENDERSOUL_HAND_ITEM::get,
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .addDependencies(KILL_TRAIT)
                .register();
    }

    public static void registerQuestsKillT4()
    {
        KILL_OLD_CHAMPION = Entry.of(
                        "kill_old_champion",
                        new SimpleLootItemQuest(
                                List.of(
                                        () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:draugr_boss")),
                                        () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:night_shade"))
                                ),
                                List.of(() -> Registries.ITEM.get(new Identifier("soulsweapons:essence_of_eventide"))),
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(KILL_MUTANT_ZOMBIE)
                .register();
        KILL_DECAYING_KING = Entry.of(
                        "kill_decaying_king",
                        new SimpleLootItemQuest(
                                () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:accursed_lord_boss")),
                                () -> AllItems.ACCURSED_BLACKSTONE,
                                TrinketItems.LOOT_1::getDefaultStack
                        )
                )
                .addDependencies(KILL_MUTANT_ZOMBIE)
                .register();
        KILL_ELDER_GUARDIAN = Entry.of(
                        "kill_elder_guardian",
                        new SimpleLootItemQuest(
                                () -> EntityType.ELDER_GUARDIAN,
                                () -> AllItems.ABYSS_GUARD,
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(KILL_MUTANT_SKELETON)
                .register();
        KILL_WITHER = Entry.of(
                        "kill_wither",
                        new SimpleLootItemQuest(
                                () -> EntityType.WITHER,
                                () -> Items.NETHER_STAR,
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addDependencies(KILL_MUTANT_SKELETON)
                .register();
        KILL_INVOKER = Entry.of(
                        "kill_invoker",
                        new SimpleLootItemQuest(
                                "illagerinvasion:invoker",
                                "illagerinvasion:primal_essence",
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .addDependencies(KILL_MUTANT_CREEPER)
                .register();
        KILL_WARDEN = Entry.of(
                        "kill_warden",
                        new SimpleLootItemQuest(
                                () -> EntityType.WARDEN,
                                () -> Registries.ITEM.get(new Identifier("deeperdarker:heart_of_the_deep")),
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(KILL_MUTANT_CREEPER)
                .register();
    }

    public static void registerQuestsKillT3()
    {
        KILL_MOON_KNIGHT = Entry.of(
                        "kill_moon_knight",
                        new SimpleLootItemQuest(
                                "soulsweapons:moonknight",
                                "soulsweapons:essence_of_luminescence",
                                SDBags.RARE_BOOK::getStack
                        )
                )
                .addDependencies(KILL_OLD_CHAMPION)
                .register();
        KILL_BLACKSTONE_GOLEM = Entry.of(
                        "kill_blackstone_golem",
                        new SimpleLootItemQuest(
                                () -> EntityInit.BLACKSTONE_GOLEM,
                                () -> ItemInit.BLACKSTONE_GOLEM_HEART,
                                SDBags.RARE_BOOK::getStack
                        )
                )
                .addDependencies(KILL_DECAYING_KING)
                .register();
        KILL_CAPTAIN_CORNELIA = Entry.of(
                        "kill_captain_cornelia",
                        new SimpleLootItemQuest(
                                () -> AquamiraeEntities.CAPTAIN_CORNELIA,
                                () -> AquamiraeItems.SHIP_GRAVEYARD_ECHO,
                                SDBags.ARING::getStack
                        )
                )
                .addDependencies(KILL_ELDER_GUARDIAN)
                .register();
        KILL_CHAOS_MONARCH = Entry.of(
                        "kill_chaos_monarch",
                        new SimpleLootItemQuest(
                                "soulsweapons:chaos_monarch",
                                "soulsweapons:chaos_crown",
                                TrinketItems.LOOT_2::getDefaultStack
                        )
                )
                .addDependencies(KILL_WITHER)
                .register();
        KILL_RETURNING_KNIGHT = Entry.of(
                        "kill_returning_knight",
                        new SimpleLootItemQuest(
                                "soulsweapons:returning_knight",
                                "soulsweapons:arkenstone",
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(KILL_INVOKER)
                .register();
        KILL_STALKER = Entry.of(
                        "kill_stalker",
                        new SimpleLootItemQuest(
                                "deeperdarker:stalker",
                                "deeperdarker:soul_crystal",
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .addDependencies(KILL_WARDEN)
                .register();
    }

    public static void registerQuestsKillT2()
    {
        KILL_GRAVEYARD_LICH = Entry.of(
                        "kill_graveyard_lich",
                        new SimpleLootItemQuest(
                                "graveyard:lich",
                                "endrem:undead_soul",
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(KILL_MUTANT_ENDERMAN)
                .register();
        KILL_BOMD_LICH = Entry.of(
                        "kill_bomd_lich",
                        new SimpleLootItemQuest(
                                "bosses_of_mass_destruction:lich",
                                "bosses_of_mass_destruction:ancient_anima",
                                SDBags.EPIC_BOOK::getStack
                        )
                )
                .addDependencies(KILL_MUTANT_ENDERMAN)
                .register();
        KILL_VOID_BLOSSOM = Entry.of(
                        "kill_void_blossom",
                        new SimpleLootItemQuest(
                                "bosses_of_mass_destruction:void_blossom",
                                "bosses_of_mass_destruction:void_thorn",
                                TrinketItems.LOOT_3::getDefaultStack
                        )
                )
                .addDependencies(KILL_MUTANT_ENDERMAN)
                .register();
        KILL_GAUNTLET = Entry.of(
                        "kill_gauntlet",
                        new SimpleLootItemQuest(
                                "bosses_of_mass_destruction:gauntlet",
                                "bosses_of_mass_destruction:blazing_eye",
                                SDBags.EPIC_MATERIAL::getStack
                        )
                )
                .addDependencies(KILL_MUTANT_ENDERMAN)
                .register();
    }

    public static void registerQuestsKillT1()
    {
        KILL_DAY_NIGHT = Entry.of(
                        "kill_day_night",
                        new SimpleLootItemQuest(
                                List.of(
                                        () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:day_stalker")),
                                        () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:night_prowler"))
                                ),
                                List.of(() -> AllItems.CELESTIAL_LUMINARY),
                                SDBags.ARING::getStack
                        )
                )
                .addDependencies(
                        KILL_BOMD_LICH,
                        KILL_VOID_BLOSSOM,
                        KILL_CHAOS_MONARCH,
                        KILL_MOON_KNIGHT,
                        KILL_RETURNING_KNIGHT,
                        KILL_DECAYING_KING,
                        KILL_CAPTAIN_CORNELIA,
                        KILL_BLACKSTONE_GOLEM
                )
                .register();
        KILL_ENDER_DRAGON = Entry.of(
                        "kill_ender_dragon",
                        new EnderDragonAdvancementQuest(
                                TrinketItems.LOOT_4::getDefaultStack
                        )
                )
                .addDependencies(KILL_DAY_NIGHT)
                .addEnDesc("Defeat Ender Dragon and free the end")
                .addZhDesc("击杀末影龙，解放末地")
                .register();
        KILL_THE_EYE = Entry.of(
                        "kill_the_eye",
                        new SimpleLootItemQuest(
                                () -> EntityInit.THE_EYE,
                                () -> ItemInit.PRIME_EYE,
                                SDBags.LEGENDARY_GEAR::getStack
                        )
                )
                .addDependencies(KILL_ENDER_DRAGON)
                .register();
    }

    public static void registerQuestsKillT0()
    {
        KILL_OBSIDLITH = Entry.of(
                        "kill_obsidilith",
                        new SimpleLootItemQuest(
                                "bosses_of_mass_destruction:obsidilith",
                                "bosses_of_mass_destruction:obsidian_heart",
                                SDBags.LEGENDARY_BOOK::getStack
                        )
                )
                .addDependencies(KILL_ENDER_DRAGON)
                .register();
        KILL_VOID_SHADOW = Entry.of(
                        "kill_void_shadow",
                        new SimpleLootItemQuest(
                                () -> EntityInit.VOID_SHADOW,
                                () -> ItemInit.SOURCE_STONE,
                                AllItems.MEDAL::getDefaultStack
                        )
                )
                .addDependencies(KILL_THE_EYE)
                .register();
    }

    public static class Entry<Q extends Quest> extends NamedEntryBuilder<Q> implements DefaultLanguageGeneratorProvider
    {
        private static final Set<Pair<Quest, Quest>> RELATIONS = new HashSet<>();

        public static <Q extends Quest> Entry<Q> of(String name, Q quest)
        {
            return new Entry<>(name, quest);
        }

        public static void buildRelations()
        {
            for (Pair<Quest, Quest> relation : RELATIONS)
                QuestUsage.configure(relation.getLeft(), relation.getRight());
            RELATIONS.clear();
        }

        public Entry(String name, Q content)
        {
            super(name, content);
        }

        public Q register()
        {
            QuestRegistry.register(getId(), content);
            return content;
        }

        public Entry<Q> addDependencies(Quest... dependencies)
        {
            for (Quest quest : dependencies)
                RELATIONS.add(new Pair<>(quest, content));
            return this;
        }

        public Entry<Q> addDependents(Quest... dependents)
        {
            for (Quest quest : dependents)
                RELATIONS.add(new Pair<>(content, quest));
            return this;
        }

        public Entry<Q> addEnDesc(String desc)
        {
            getEnglishGenerator().addText(getTranslationKey(), desc);
            return this;
        }

        public Entry<Q> addZhDesc(String desc)
        {
            getChineseGenerator().addText(getTranslationKey(), desc);
            return this;
        }

        public String getTranslationKey()
        {
            return super.getTranslationKey("quest") + ".description";
        }

        @Override
        public String getNameSpace()
        {
            return SpellDimension.MOD_ID;
        }
    }
}
