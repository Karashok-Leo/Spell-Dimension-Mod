package karashokleo.spell_dimension.init;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import karashokleo.spell_dimension.api.SpellProjectileOutOfRangeCallback;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.spell.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

public class AllSpells
{
    public static final SpellSchool GENERIC = SpellSchools.createMagic(SpellDimension.modLoc("generic"), 0xdddddd, false, EntityAttributes.GENERIC_ATTACK_DAMAGE, null);

    /**
     * Primary spells
     */
    public static final Identifier ARCANE_BOLT = new Identifier("wizards:arcane_bolt");
    public static final Identifier FIRE_SCORCH = new Identifier("wizards:fire_scorch");
    public static final Identifier FROST_SHARD = new Identifier("wizards:frost_shard");
    public static final Identifier HEAL = new Identifier("paladins:heal");

    /**
     * Binding spells
     */
    // Arcane
    public static final Identifier ARCANE_MISSILE = new Identifier("wizards:arcane_missile");
    public static final Identifier ARCANE_BLAST = new Identifier("wizards:arcane_blast");
    public static final Identifier ARCANE_BLINK = new Identifier("wizards:arcane_blink");

    // Fire
    public static final Identifier FIREBALL = new Identifier("wizards:fireball");
    public static final Identifier FIRE_WALL = new Identifier("wizards:fire_wall");
    public static final Identifier FIRE_METEOR = new Identifier("wizards:fire_meteor");

    // Frost
    public static final Identifier FROST_BOLT = new Identifier("wizards:frostbolt");
    public static final Identifier FROST_NOVA = new Identifier("wizards:frost_nova");
    public static final Identifier FROST_SHIELD = new Identifier("wizards:frost_shield");

    // Healing
    public static final Identifier FLASH_HEAL = new Identifier("paladins:flash_heal");
    public static final Identifier HOLY_SHOCK = new Identifier("paladins:holy_shock");
    public static final Identifier DIVINE_PROTECTION = new Identifier("paladins:divine_protection");

    /**
     * Infusion spells
     */
    // Generic
    public static final Identifier LOCATE = SpellDimension.modLoc("locate");
    public static final Identifier SUMMON = SpellDimension.modLoc("summon");
    public static final Identifier PLACE = SpellDimension.modLoc("place");
    public static final Identifier BREAK = SpellDimension.modLoc("break");
    public static final Identifier LIGHT = SpellDimension.modLoc("light");
    public static final Identifier MOON_SWIM = SpellDimension.modLoc("moon_swim");
    public static final Identifier INCARCERATE = SpellDimension.modLoc("incarcerate");

    // Arcane
    // Tier 1
    public static final Identifier CONVERGE = SpellDimension.modLoc("converge");
    public static final Identifier SHIFT = SpellDimension.modLoc("shift");
    public static final Identifier FORCE_LANDING = SpellDimension.modLoc("force_landing");
    public static final Identifier ARCANE_BARRIER = SpellDimension.modLoc("arcane_barrier");
    public static final Identifier FINAL_STRIKE = new Identifier("spellbladenext:finalstrike");
    // Tier 2
    public static final Identifier ARCANE_FLOURISH = new Identifier("spellbladenext:arcaneflourish");
    public static final Identifier AMETHYST_SLASH = new Identifier("spellbladenext:amethystslash");
    public static final Identifier ARCANE_BEAM = new Identifier("wizards:arcane_beam");
    public static final Identifier PHASE = SpellDimension.modLoc("phase");
    public static final Identifier ARCANE_FLICKER = new Identifier("spellbladenext:frostblink");
    public static final Identifier ELDRITCH_BLAST = new Identifier("spellbladenext:eldritchblast");
    // Tier 3
    public static final Identifier ARCANE_OVERDRIVE = new Identifier("spellbladenext:arcaneoverdrive");
    public static final Identifier BLACK_HOLE = SpellDimension.modLoc("black_hole");
    public static final Identifier MAELSTROM = new Identifier("spellbladenext:maelstrom");
    public static final Identifier ECHO_STORM = new Identifier("spellbladenext:bladestorm");

    // Fire
    public static final Identifier FIRE_BREATH = new Identifier("wizards:fire_breath");
    public static final Identifier OVER_BLAZE = new Identifier("spellbladenext:overblaze");
    public static final Identifier WILDFIRE = new Identifier("spellbladenext:snuffout");
    // Tier 2
    public static final Identifier FIRE_FLOURISH = new Identifier("spellbladenext:fireflourish");
    public static final Identifier FLAME_SLASH = new Identifier("spellbladenext:flameslash");
    public static final Identifier BLAST = SpellDimension.modLoc("blast");
    public static final Identifier IGNITE = SpellDimension.modLoc("ignite");
    public static final Identifier PHOENIX_DIVE = new Identifier("spellbladenext:phoenixdive");
    public static final Identifier PHOENIX_CURSE = new Identifier("spellbladenext:combustion");
    public static final Identifier DRAGON_SLAM = new Identifier("spellbladenext:frostvert");
    // Tier 3
    public static final Identifier FLAME_OVERDRIVE = new Identifier("spellbladenext:fireoverdrive");
    public static final Identifier FIRE_OF_RETRIBUTION = SpellDimension.modLoc("fire_of_retribution");
    public static final Identifier INFERNO = new Identifier("spellbladenext:inferno");
    public static final Identifier FLICKER_STRIKE = new Identifier("spellbladenext:flicker_strike");

    // Frost
    public static final Identifier ICICLE = SpellDimension.modLoc("icicle");
    public static final Identifier FROZEN = SpellDimension.modLoc("frozen");
    public static final Identifier FROST_LOTUS = new Identifier("spellbladenext:frostbloom0");
    public static final Identifier DEATH_CHILL = new Identifier("spellbladenext:deathchill");
    public static final Identifier FROST_BLIZZARD = new Identifier("wizards:frost_blizzard");
    // Tier 2
    public static final Identifier FROST_FLOURISH = new Identifier("spellbladenext:frostflourish");
    public static final Identifier FROST_SLASH = new Identifier("spellbladenext:frostslash");
    public static final Identifier FROST_BLINK = SpellDimension.modLoc("frost_blink");
    public static final Identifier NUCLEUS = SpellDimension.modLoc("nucleus");
    public static final Identifier AURA = SpellDimension.modLoc("aura");
    public static final Identifier COLD_BUFF = new Identifier("spellbladenext:coldbuff");
    // Tier 3
    public static final Identifier FROST_OVERDRIVE = new Identifier("spellbladenext:frostoverdrive");
    public static final Identifier MASSACRE = new Identifier("spellbladenext:eviscerate");
    public static final Identifier RIPTIDE = new Identifier("spellbladenext:whirlingblades");

    // Healing
    public static final Identifier HOLY_BEAM = new Identifier("paladins:holy_beam");
    public static final Identifier CIRCLE_OF_HEALING = new Identifier("paladins:circle_of_healing");
    public static final Identifier JUDGEMENT = new Identifier("paladins:judgement");
    public static final Identifier CRITICAL_HIT = SpellDimension.modLoc("critical_hit");
    public static final Identifier SPELL_POWER = SpellDimension.modLoc("spell_power");
    public static final Identifier REGEN = SpellDimension.modLoc("regen");
    public static final Identifier RESIST = SpellDimension.modLoc("resist");
    public static final Identifier HASTE = SpellDimension.modLoc("haste");
    public static final Identifier SPEED = SpellDimension.modLoc("speed");
    // Tier 2
    public static final Identifier CLEANSE = SpellDimension.modLoc("cleanse");
    public static final Identifier BARRIER = new Identifier("paladins:barrier");
    public static final Identifier BATTLE_BANNER = new Identifier("paladins:battle_banner");
    public static final Identifier BLESSING = SpellDimension.modLoc("blessing");
    public static final Identifier MISFORTUNE = SpellDimension.modLoc("misfortune");
    public static final Identifier HEAVENLY_JUSTICE = SpellDimension.modLoc("heavenly_justice");
    // Tier 3
    public static final Identifier DIVINE_AURA = SpellDimension.modLoc("divine_aura");
    public static final Identifier EXORCISM = SpellDimension.modLoc("exorcism");
    public static final Identifier SPELL_POWER_ADVANCED = SpellDimension.modLoc("spell_power_advanced");
    public static final Identifier REGEN_ADVANCED = SpellDimension.modLoc("regen_advanced");
    public static final Identifier RESIST_ADVANCED = SpellDimension.modLoc("resist_advanced");
    public static final Identifier HASTE_ADVANCED = SpellDimension.modLoc("haste_advanced");
    public static final Identifier SPEED_ADVANCED = SpellDimension.modLoc("speed_advanced");

    public static void register()
    {
        SpellSchools.register(GENERIC);

        LivingDamageEvent.DAMAGE.register(BlazingMark::mark);

        SpellProjectileHitEntityCallback.EVENT.register(ShiftSpell::handle);

        SpellProjectileHitEntityCallback.EVENT.register(ConvergeSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(ConvergeSpell::handle);

        SpellProjectileHitEntityCallback.EVENT.register((projectile, spellId, hitResult) -> BlackHoleSpell.handle(projectile, spellId));
        SpellProjectileHitBlockCallback.EVENT.register((projectile, spellId, hitResult) -> BlackHoleSpell.handle(projectile, spellId));
        SpellProjectileOutOfRangeCallback.EVENT.register(BlackHoleSpell::handle);

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

        registerIntermediateSpells();
        registerAdvancedSpells();
    }

    public static void registerIntermediateSpells()
    {
        SpellConfig.addIntermediateSpell(ARCANE_FLOURISH);
        SpellConfig.addIntermediateSpell(AMETHYST_SLASH);
        SpellConfig.addIntermediateSpell(ARCANE_BEAM);
        SpellConfig.addIntermediateSpell(PHASE);
        SpellConfig.addIntermediateSpell(ARCANE_FLICKER);
        SpellConfig.addIntermediateSpell(ELDRITCH_BLAST);

        SpellConfig.addIntermediateSpell(FIRE_FLOURISH);
        SpellConfig.addIntermediateSpell(FLAME_SLASH);
        SpellConfig.addIntermediateSpell(BLAST);
        SpellConfig.addIntermediateSpell(IGNITE);
        SpellConfig.addIntermediateSpell(PHOENIX_DIVE);
        SpellConfig.addIntermediateSpell(PHOENIX_CURSE);
        SpellConfig.addIntermediateSpell(DRAGON_SLAM);

        SpellConfig.addIntermediateSpell(FROST_FLOURISH);
        SpellConfig.addIntermediateSpell(FROST_SLASH);
        SpellConfig.addIntermediateSpell(FROST_BLINK);
        SpellConfig.addIntermediateSpell(NUCLEUS);
        SpellConfig.addIntermediateSpell(AURA);
        SpellConfig.addIntermediateSpell(COLD_BUFF);

        SpellConfig.addIntermediateSpell(CLEANSE);
        SpellConfig.addIntermediateSpell(BARRIER);
        SpellConfig.addIntermediateSpell(BATTLE_BANNER);
        SpellConfig.addIntermediateSpell(BLESSING);
        SpellConfig.addIntermediateSpell(MISFORTUNE);
        SpellConfig.addIntermediateSpell(HEAVENLY_JUSTICE);
    }

    public static void registerAdvancedSpells()
    {
        SpellConfig.addAdvancedSpell(ARCANE_OVERDRIVE);
        SpellConfig.addAdvancedSpell(BLACK_HOLE);
        SpellConfig.addAdvancedSpell(MAELSTROM);
        SpellConfig.addAdvancedSpell(ECHO_STORM);

        SpellConfig.addAdvancedSpell(FLAME_OVERDRIVE);
        SpellConfig.addAdvancedSpell(FIRE_OF_RETRIBUTION);
        SpellConfig.addAdvancedSpell(INFERNO);
        SpellConfig.addAdvancedSpell(FLICKER_STRIKE);

        SpellConfig.addAdvancedSpell(FROST_OVERDRIVE);
        SpellConfig.addAdvancedSpell(MASSACRE);
        SpellConfig.addAdvancedSpell(RIPTIDE);

        SpellConfig.addAdvancedSpell(DIVINE_AURA);
        SpellConfig.addAdvancedSpell(EXORCISM);
        SpellConfig.addAdvancedSpell(SPELL_POWER_ADVANCED);
        SpellConfig.addAdvancedSpell(REGEN_ADVANCED);
        SpellConfig.addAdvancedSpell(RESIST_ADVANCED);
        SpellConfig.addAdvancedSpell(HASTE_ADVANCED);
        SpellConfig.addAdvancedSpell(SPEED_ADVANCED);
    }
}