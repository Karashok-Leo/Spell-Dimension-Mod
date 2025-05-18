package karashokleo.spell_dimension.init;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import karashokleo.spell_dimension.api.SpellProjectileOutOfRangeCallback;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.object.ScrollType;
import karashokleo.spell_dimension.content.spell.*;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AllSpells
{
    private static final Set<Identifier> ALL = new HashSet<>();
    private static final HashMap<Identifier, ScrollType> SPELL_TO_SCROLL_TYPE = new HashMap<>();
    private static final HashMap<Identifier, Integer> SPELL_TO_TIER = new HashMap<>();

    public static final SpellSchool GENERIC = SpellSchools.createMagic(SpellDimension.modLoc("generic"), 0xdddddd, false, EntityAttributes.GENERIC_ATTACK_DAMAGE, null);

    /**
     * Primary spells
     */
    public static final Identifier ARCANE_BOLT = fromPrimary("wizards:arcane_bolt");
    public static final Identifier FIRE_SCORCH = fromPrimary("wizards:fire_scorch");
    public static final Identifier FROST_SHARD = fromPrimary("wizards:frost_shard");
    public static final Identifier HEAL = fromPrimary("paladins:heal");

    /**
     * Binding spells
     */
    // Arcane
    public static final Identifier ARCANE_MISSILE = fromBinding("wizards:arcane_missile");
    public static final Identifier ARCANE_BLAST = fromBinding("wizards:arcane_blast");
    public static final Identifier ARCANE_BLINK = fromBinding("wizards:arcane_blink");

    // Fire
    public static final Identifier FIREBALL = fromBinding("wizards:fireball");
    public static final Identifier FIRE_WALL = fromBinding("wizards:fire_wall");
    public static final Identifier FIRE_METEOR = fromBinding("wizards:fire_meteor");

    // Frost
    public static final Identifier FROST_BOLT = fromBinding("wizards:frostbolt");
    public static final Identifier FROST_NOVA = fromBinding("wizards:frost_nova");
    public static final Identifier FROST_SHIELD = fromBinding("wizards:frost_shield");

    // Healing
    public static final Identifier FLASH_HEAL = fromBinding("paladins:flash_heal");
    public static final Identifier HOLY_SHOCK = fromBinding("paladins:holy_shock");
    public static final Identifier DIVINE_PROTECTION = fromBinding("paladins:divine_protection");

    /**
     * Infusion spells
     */
    // Generic
    public static final Identifier LOCATE = fromCrafting("locate").build();
    public static final Identifier SUMMON = fromCrafting("summon").build();
    public static final Identifier PLACE = fromCrafting("place").build();
    public static final Identifier BREAK = fromCrafting("break").build();
    public static final Identifier LIGHT = fromCrafting("light").build();
    public static final Identifier MOON_SWIM = fromCrafting("moon_swim").build();

    // Arcane
    // Tier 1
    public static final Identifier CONVERGE = fromCrafting("converge").build();
    public static final Identifier SHIFT = fromCrafting("shift").build();
    public static final Identifier INCARCERATE = fromCrafting("incarcerate").build();
    public static final Identifier FORCE_LANDING = fromCrafting("force_landing").build();
    public static final Identifier ARCANE_BARRIER = fromCrafting("arcane_barrier").build();
    public static final Identifier FINAL_STRIKE = fromCraftingWithId("spellbladenext:finalstrike").build();
    // Tier 2
    public static final Identifier ARCANE_FLOURISH = fromCraftingWithId("spellbladenext:arcaneflourish").withTier(1).build();
    public static final Identifier AMETHYST_SLASH = fromCraftingWithId("spellbladenext:amethystslash").withTier(1).build();
    public static final Identifier ARCANE_BEAM = fromCraftingWithId("wizards:arcane_beam").withTier(1).build();
    public static final Identifier PHASE = fromCrafting("phase").withTier(1).build();
    public static final Identifier ARCANE_FLICKER = fromCraftingWithId("spellbladenext:frostblink").withTier(1).build();
    public static final Identifier ELDRITCH_BLAST = fromCraftingWithId("spellbladenext:eldritchblast").withTier(1).build();
    // Tier 3
    public static final Identifier ARCANE_OVERDRIVE = fromCraftingWithId("spellbladenext:arcaneoverdrive").withTier(2).build();
    public static final Identifier BLACK_HOLE = fromCrafting("black_hole").withTier(2).build();
    public static final Identifier MAELSTROM = fromCraftingWithId("spellbladenext:maelstrom").withTier(2).build();
    public static final Identifier ECHO_STORM = fromCraftingWithId("spellbladenext:bladestorm").withTier(2).build();

    // Fire
    public static final Identifier FIRE_BREATH = fromCraftingWithId("wizards:fire_breath").build();
    public static final Identifier OVER_BLAZE = fromCraftingWithId("spellbladenext:overblaze").build();
    public static final Identifier WILDFIRE = fromCraftingWithId("spellbladenext:snuffout").build();
    // Tier 2
    public static final Identifier FIRE_FLOURISH = fromCraftingWithId("spellbladenext:fireflourish").withTier(1).build();
    public static final Identifier FLAME_SLASH = fromCraftingWithId("spellbladenext:flameslash").withTier(1).build();
    public static final Identifier BLAST = fromCrafting("blast").withTier(1).build();
    public static final Identifier IGNITE = fromCrafting("ignite").withTier(1).build();
    public static final Identifier PHOENIX_DIVE = fromCraftingWithId("spellbladenext:phoenixdive").withTier(1).build();
    public static final Identifier PHOENIX_CURSE = fromCraftingWithId("spellbladenext:combustion").withTier(1).build();
    public static final Identifier DRAGON_SLAM = fromCraftingWithId("spellbladenext:frostvert").withTier(1).build();
    // Tier 3
    public static final Identifier FLAME_OVERDRIVE = fromCraftingWithId("spellbladenext:fireoverdrive").withTier(2).build();
    public static final Identifier FIRE_OF_RETRIBUTION = fromCrafting("fire_of_retribution").withTier(2).build();
    public static final Identifier INFERNO = fromCraftingWithId("spellbladenext:inferno").withTier(2).build();
    public static final Identifier FLICKER_STRIKE = fromCraftingWithId("spellbladenext:flicker_strike").withTier(2).build();

    // Frost
    public static final Identifier ICICLE = fromCrafting("icicle").build();
    public static final Identifier FROZEN = fromCrafting("frozen").build();
    public static final Identifier FROST_LOTUS = fromCraftingWithId("spellbladenext:frostbloom0").build();
    public static final Identifier DEATH_CHILL = fromCraftingWithId("spellbladenext:deathchill").build();
    public static final Identifier FROST_BLIZZARD = fromCraftingWithId("wizards:frost_blizzard").build();
    // Tier 2
    public static final Identifier FROST_FLOURISH = fromCraftingWithId("spellbladenext:frostflourish").withTier(1).build();
    public static final Identifier FROST_SLASH = fromCraftingWithId("spellbladenext:frostslash").withTier(1).build();
    public static final Identifier FROST_BLINK = fromCrafting("frost_blink").withTier(1).build();
    public static final Identifier ICY_NUCLEUS = fromCrafting("icy_nucleus").withTier(1).build();
    public static final Identifier FROST_AURA = fromCrafting("frost_aura").withTier(1).build();
    public static final Identifier COLD_BUFF = fromCraftingWithId("spellbladenext:coldbuff").withTier(1).build();
    // Tier 3
    public static final Identifier FROST_OVERDRIVE = fromCraftingWithId("spellbladenext:frostoverdrive").withTier(2).build();
    public static final Identifier MASSACRE = fromCraftingWithId("spellbladenext:eviscerate").withTier(2).build();
    public static final Identifier RIPTIDE = fromCraftingWithId("spellbladenext:whirlingblades").withTier(2).build();
    public static final Identifier TEMPEST = fromCraftingWithId("spellbladenext:tempest").withTier(2).build();

    // Healing
    public static final Identifier DIVINE_CURSE_BLAST = fromCrafting("divine_curse_blast").build();
    public static final Identifier HOLY_BEAM = fromCraftingWithId("paladins:holy_beam").build();
    public static final Identifier CIRCLE_OF_HEALING = fromCraftingWithId("paladins:circle_of_healing").build();
    public static final Identifier JUDGEMENT = fromCraftingWithId("paladins:judgement").build();
    public static final Identifier CRITICAL_HIT = fromCrafting("critical_hit").build();
    public static final Identifier SPELL_POWER = fromCrafting("spell_power").build();
    public static final Identifier REGEN = fromCrafting("regen").build();
    public static final Identifier RESIST = fromCrafting("resist").build();
    public static final Identifier HASTE = fromCrafting("haste").build();
    public static final Identifier SPEED = fromCrafting("speed").build();
    // Tier 2
    public static final Identifier CLEANSE = fromCrafting("cleanse").withTier(1).build();
    public static final Identifier BARRIER = fromCraftingWithId("paladins:barrier").withTier(1).build();
    public static final Identifier BATTLE_BANNER = fromCraftingWithId("paladins:battle_banner").withTier(1).build();
    public static final Identifier BLESSING = fromCrafting("blessing").withTier(1).build();
    public static final Identifier MISFORTUNE = fromCrafting("misfortune").withTier(1).build();
    public static final Identifier HEAVENLY_JUSTICE = fromCrafting("heavenly_justice").withTier(1).build();
    // Tier 3
    public static final Identifier DIVINE_AURA = fromCrafting("divine_aura").withTier(2).build();
    public static final Identifier EXORCISM = fromCrafting("exorcism").withTier(2).build();
    public static final Identifier SPELL_POWER_ADVANCED = builderWithName("spell_power_advanced").withScrollType(ScrollType.EVENT_AWARD).withTier(2).build();
    public static final Identifier REGEN_ADVANCED = builderWithName("regen_advanced").withScrollType(ScrollType.EVENT_AWARD).withTier(2).build();
    public static final Identifier RESIST_ADVANCED = builderWithName("resist_advanced").withScrollType(ScrollType.EVENT_AWARD).withTier(2).build();
    public static final Identifier HASTE_ADVANCED = builderWithName("haste_advanced").withScrollType(ScrollType.EVENT_AWARD).withTier(2).build();
    public static final Identifier SPEED_ADVANCED = builderWithName("speed_advanced").withScrollType(ScrollType.EVENT_AWARD).withTier(2).build();

    // Lightning
    // Tier 1
    public static final Identifier CHAIN_LIGHTNING = fromCrafting("chain_lightning").build();
    // Tier 2
    public static final Identifier SURGE = fromCrafting("surge").build();
    public static final Identifier STEADY_CURRENT = fromCrafting("steady_current").build();
    public static final Identifier FISSION = fromCrafting("fission").build();
    public static final Identifier RESONANCE = fromCrafting("resonance").build();
    public static final Identifier BREAKDOWN = fromCrafting("breakdown").build();
    // Tier 3
    public static final Identifier ARCLIGHT = fromCrafting("arclight").build();
    public static final Identifier CONSTANT_CURRENT = fromCrafting("constant_current").build();

    public static void register()
    {
        SpellSchools.register(GENERIC);

        LivingDamageEvent.DAMAGE.register(BlazingMark::mark);

        SpellProjectileHitEntityCallback.EVENT.register(ShiftSpell::handle);

        SpellProjectileHitEntityCallback.EVENT.register(ConvergeSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(ConvergeSpell::handle);

        SpellProjectileHitEntityCallback.EVENT.register(BlackHoleSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(BlackHoleSpell::handle);
        SpellProjectileOutOfRangeCallback.EVENT.register(BlackHoleSpell::handle);

        SpellProjectileHitEntityCallback.EVENT.register(IcicleSpell::handle);

        SpellProjectileHitBlockCallback.EVENT.register(LightSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(LocateSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(SummonSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(PlaceSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(BreakSpell::handle);

        SpellImpactEvents.BEFORE.register(ArcaneBarrierSpell::handle);
        SpellImpactEvents.BEFORE.register(NucleusSpell::handle);
        SpellImpactEvents.BEFORE.register(ExorcismSpell::handle);
        SpellImpactEvents.BEFORE.register(RandomEffectSpell::handleBlessing);
        SpellImpactEvents.BEFORE.register(RandomEffectSpell::handleMisfortune);
        SpellImpactEvents.BEFORE.register(FrostBlinkSpell::handle);
        SpellImpactEvents.BEFORE.register(FireOfRetributionSpell::handle);
        SpellImpactEvents.BEFORE.register(HeavenlyJusticeSpell::handle);
        SpellImpactEvents.BEFORE.register(ChainLightningSpell::handle);
    }

    public static Set<Identifier> getAll()
    {
        return ALL;
    }

    public static Set<Identifier> getSpells(Predicate<ScrollType> predicate)
    {
        return SPELL_TO_SCROLL_TYPE.entrySet()
                .stream()
                .filter(entry -> predicate.test(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static MutableText getSpellObtainText(Identifier spellId)
    {
        ScrollType scrollType = SPELL_TO_SCROLL_TYPE.get(spellId);
        return scrollType == null ?
                SDTexts.SCROLL$UNAVAILABLE.get() :
                scrollType.getTooltip();
    }

    public static int getSpellTier(Identifier spellId)
    {
        return SPELL_TO_TIER.getOrDefault(spellId, 0);
    }

    // local namespace
    private static Entry builderWithName(String name)
    {
        return new Entry(SpellDimension.modLoc(name));
    }

    // other namespace
    private static Identifier fromPrimary(String id)
    {
        return new Entry(new Identifier(id)).withScrollType(ScrollType.PRIMARY).build();
    }

    // other namespace
    private static Identifier fromBinding(String id)
    {
        return new Entry(new Identifier(id)).withScrollType(ScrollType.BINDING).build();
    }

    // local namespace
    private static Entry fromCrafting(String name)
    {
        return new Entry(SpellDimension.modLoc(name)).withScrollType(ScrollType.CRAFTING);
    }

    // other namespace
    private static Entry fromCraftingWithId(String id)
    {
        return new Entry(new Identifier(id)).withScrollType(ScrollType.CRAFTING);
    }

    public static class Entry
    {
        private final Identifier id;
        @Nullable
        private Integer tier;
        @Nullable
        private ScrollType scrollType;

        public Entry(Identifier id)
        {
            this.id = id;
        }

        public Entry withTier(int tier)
        {
            this.tier = tier;
            return this;
        }

        public Entry withScrollType(ScrollType type)
        {
            this.scrollType = type;
            return this;
        }

        public Identifier build()
        {
            ALL.add(id);
            if (tier != null)
                SPELL_TO_TIER.put(id, tier);
            if (scrollType != null)
                SPELL_TO_SCROLL_TYPE.put(id, scrollType);
            return id;
        }
    }
}