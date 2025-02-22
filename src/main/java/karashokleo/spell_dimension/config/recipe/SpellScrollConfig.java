package karashokleo.spell_dimension.config.recipe;

import com.kyanite.deeperdarker.content.DDItems;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import net.adventurez.init.BlockInit;
import net.adventurez.init.ItemInit;
import net.aleganza.plentyofarmors.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellInfo;
import net.trique.mythicupgrades.block.MUBlocks;
import nourl.mythicmetals.blocks.MythicBlocks;
import nourl.mythicmetals.item.MythicItems;
import nourl.mythicmetals.item.tools.MythicTools;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpellScrollConfig
{
    public static Set<Identifier> getAllSpells()
    {
        return SPELL_TEXTS.keySet();
    }

    public static Set<Identifier> getCraftSpells()
    {
        return CRAFT_SPELLS;
    }

    public static Set<Identifier> getEventLootSpells()
    {
        Set<Identifier> spells = new HashSet<>(EVENT_LOOT_SPELLS);
        spells.addAll(CRAFT_SPELLS);
        return spells;
    }

    public static Text getSpellScrollText(@Nullable SpellInfo spellInfo)
    {
        return spellInfo == null ?
                SDTexts.SCROLL$UNAVAILABLE
                        .get()
                        .formatted(Formatting.GRAY) :
                SPELL_TEXTS
                        .get(spellInfo.id())
                        .setStyle(Style.EMPTY.withColor(spellInfo.spell().school.color));
    }

    private static final Map<Identifier, MutableText> SPELL_TEXTS = new HashMap<>();
    private static final Set<Identifier> CRAFT_SPELLS = new HashSet<>();
    private static final Set<Identifier> EVENT_LOOT_SPELLS = new HashSet<>();

    static
    {
        // Primary
        {
            fromPrimary(AllSpells.ARCANE_BOLT);
            fromPrimary(AllSpells.FIRE_SCORCH);
            fromPrimary(AllSpells.FROST_SHARD);
            fromPrimary(AllSpells.HEAL);
        }

        // Binding
        {
            fromBinding(AllSpells.ARCANE_MISSILE);
            fromBinding(AllSpells.ARCANE_BLAST);
            fromBinding(AllSpells.ARCANE_BLINK);

            fromBinding(AllSpells.FIREBALL);
            fromBinding(AllSpells.FIRE_WALL);
            fromBinding(AllSpells.FIRE_METEOR);

            fromBinding(AllSpells.FROST_BOLT);
            fromBinding(AllSpells.FROST_NOVA);
            fromBinding(AllSpells.FROST_SHIELD);

            fromBinding(AllSpells.FLASH_HEAL);
            fromBinding(AllSpells.HOLY_SHOCK);
            fromBinding(AllSpells.DIVINE_PROTECTION);
        }

        // Infusion
        {
            /// pending:
            /// "bosses_of_mass_destruction:blazing_eye"
            /// MythicBlocks.PLATINUM.getStorageBlock().asItem()
            /// AquamiraeItems.ABYSSAL_AMETHYST

            fromCrafting(AllSpells.LOCATE, Items.COMPASS);
            fromCrafting(AllSpells.SUMMON, AllItems.SPAWNER_SOUL);
            fromCrafting(AllSpells.PLACE, ModRegistry.ENDERSOUL_HAND_ITEM.get());
            fromCrafting(AllSpells.BREAK, ModRegistry.CREEPER_SHARD_ITEM.get());
            fromCrafting(AllSpells.LIGHT, Items.GLOWSTONE);
            fromCrafting(AllSpells.MOON_SWIM, ModItems.HARDENED_PHANTOM_MEMBRANE);

            fromCrafting(AllSpells.PHASE, DDItems.REINFORCED_ECHO_SHARD);
            fromCrafting(AllSpells.CONVERGE, fuzs.illagerinvasion.init.ModRegistry.PRIMAL_ESSENCE_ITEM.get());
            fromCrafting(AllSpells.ELDRITCH_BLAST, ComplementItems.WARDEN_BONE_SHARD);
            fromCrafting(AllSpells.SHIFT, ModItems.HEART_OF_THE_END);
            fromCrafting(AllSpells.FORCE_LANDING, LHTraits.GRAVITY.asItem());
            fromCrafting(AllSpells.ARCANE_BARRIER, Items.GLASS);
            fromCrafting(AllSpells.INCARCERATE, ComplementItems.BLACKSTONE_CORE);
            fromCrafting(AllSpells.BLACK_HOLE, Items.DRAGON_EGG);
            fromCrafting(AllSpells.FINAL_STRIKE, DDItems.SOUL_CRYSTAL);
            fromCrafting(AllSpells.ARCANE_BEAM, MythicBlocks.KYBER.getStorageBlock().asItem());
            fromCrafting(AllSpells.ARCANE_FLOURISH, MUBlocks.AMETRINE_BLOCK.asItem());
            fromCrafting(AllSpells.ARCANE_FLICKER, MythicBlocks.STAR_PLATINUM.getStorageBlock().asItem());
            fromCrafting(AllSpells.ARCANE_OVERDRIVE, com.spellbladenext.items.Items.arcane_orb.item());
            fromCrafting(AllSpells.ECHO_STORM, MythicItems.Mats.STORMYX_SHELL);
            fromCrafting(AllSpells.MAELSTROM, MythicTools.STORMYX_SHIELD);
            fromCrafting(AllSpells.AMETHYST_SLASH, com.spellbladenext.items.Items.crystal_cutlass.item());

            fromCrafting(AllSpells.FIRE_BREATH, MUBlocks.TOPAZ_BLOCK.asItem());
            fromCrafting(AllSpells.BLAST, ComplementItems.EXPLOSION_SHARD);
            fromCrafting(AllSpells.IGNITE, ItemInit.BLACKSTONE_GOLEM_HEART);
            fromCrafting(AllSpells.FIRE_OF_RETRIBUTION, LHTraits.SOUL_BURNER.asItem());
            fromCrafting(AllSpells.FIRE_FLOURISH, MythicBlocks.PALLADIUM.getStorageBlock().asItem());
            fromCrafting(AllSpells.FLAME_OVERDRIVE, com.spellbladenext.items.Items.fire_orb.item());
            fromCrafting(AllSpells.FLICKER_STRIKE, MUBlocks.RUBY_BLOCK.asItem());
            fromCrafting(AllSpells.FLAME_SLASH, com.spellbladenext.items.Items.flaming_falchion.item());
            fromCrafting(AllSpells.OVER_BLAZE, MythicBlocks.QUADRILLUM.getStorageBlock().asItem());
            fromCrafting(AllSpells.PHOENIX_DIVE, "soulsweapons:crimson_ingot");
            fromCrafting(AllSpells.PHOENIX_CURSE, "soulsweapons:crimson_obsidian");
            fromCrafting(AllSpells.INFERNO, "things:hades_crystal");
            fromCrafting(AllSpells.DRAGON_SLAM, "dragonloot:dragon_scale");
            fromCrafting(AllSpells.WILDFIRE, MythicItems.Mats.CARMOT_STONE);

            fromCrafting(AllSpells.FROST_BLIZZARD, MythicBlocks.SILVER.getStorageBlock().asItem());
            fromCrafting(AllSpells.NUCLEUS, MythicBlocks.AQUARIUM.getStorageBlock().asItem());
            fromCrafting(AllSpells.AURA, AquamiraeItems.MAZE_ROSE);
            fromCrafting(AllSpells.ICICLE, AllItems.ABYSS_GUARD);
            fromCrafting(AllSpells.FROST_BLINK, AquamiraeItems.DEAD_SEA_SCROLL);
            fromCrafting(AllSpells.FROZEN, AquamiraeItems.SHIP_GRAVEYARD_ECHO);
            fromCrafting(AllSpells.FROST_FLOURISH, MUBlocks.AQUAMARINE_BLOCK.asItem());
            fromCrafting(AllSpells.FROST_OVERDRIVE, com.spellbladenext.items.Items.frost_orb.item());
            fromCrafting(AllSpells.FROST_LOTUS, MythicBlocks.RUNITE.getStorageBlock().asItem());
            fromCrafting(AllSpells.DEATH_CHILL, LHTraits.FREEZING.asItem());
            fromCrafting(AllSpells.FROST_SLASH, com.spellbladenext.items.Items.glacial_gladius.item());
            fromCrafting(AllSpells.MASSACRE, AquamiraeItems.POISONED_CHAKRA);
            fromCrafting(AllSpells.RIPTIDE, MythicItems.Mats.AQUARIUM_PEARL);
            fromCrafting(AllSpells.COLD_BUFF, "endrem:cold_eye");

            fromCrafting(AllSpells.HOLY_BEAM, "bosses_of_mass_destruction:ancient_anima");
            fromCrafting(AllSpells.CIRCLE_OF_HEALING, "soulsweapons:arkenstone");
            fromCrafting(AllSpells.BARRIER, "graveyard:dark_iron_block");
            fromCrafting(AllSpells.JUDGEMENT, MUBlocks.JADE_BLOCK.asItem());
            fromCrafting(AllSpells.CLEANSE, ComplementItems.LIFE_ESSENCE);
            fromCrafting(AllSpells.EXORCISM, LHTraits.DISPELL.asItem());
            fromCrafting(AllSpells.CRITICAL_HIT, MiscItems.CHAOS.blockSet().item());
            fromCrafting(AllSpells.SPELL_POWER, "soulsweapons:essence_of_luminescence");
            fromCrafting(AllSpells.RESIST, "soulsweapons:essence_of_eventide");
            fromCrafting(AllSpells.REGEN, "bosses_of_mass_destruction:void_thorn");
            fromCrafting(AllSpells.HASTE, "soulsweapons:moonstone_block");
            fromCrafting(AllSpells.SPEED, LHTraits.SPEEDY.asItem());
            fromCrafting(AllSpells.DIVINE_AURA, LHTraits.KILLER_AURA.asItem());
            fromCrafting(AllSpells.BLESSING, ConsumableItems.BOTTLE_SANITY);
            fromCrafting(AllSpells.MISFORTUNE, LHTraits.CURSED.asItem());
            fromCrafting(AllSpells.HEAVENLY_JUSTICE, ComplementItems.RESONANT_FEATHER);
            fromCrafting(AllSpells.BATTLE_BANNER, BlockInit.PIGLIN_FLAG.asItem());
        }

        // Event loot
        {
            fromEventLoot(AllSpells.SPELL_POWER_ADVANCED);
            fromEventLoot(AllSpells.RESIST_ADVANCED);
            fromEventLoot(AllSpells.REGEN_ADVANCED);
            fromEventLoot(AllSpells.HASTE_ADVANCED);
            fromEventLoot(AllSpells.SPEED_ADVANCED);
        }
    }

    private static void fromPrimary(Identifier spellId)
    {
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$PRIMARY.get());
    }

    private static void fromBinding(Identifier spellId)
    {
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$BINDING.get());
    }

    private static void fromCrafting(Identifier spellId, String itemId)
    {
        fromCrafting(spellId, new Identifier(itemId));
    }

    private static void fromCrafting(Identifier spellId, Identifier itemId)
    {
        fromCrafting(spellId, Registries.ITEM.get(itemId));
    }

    private static void fromCrafting(Identifier spellId, Item item)
    {
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$CRAFT.get(item.getName().copy().formatted(Formatting.BOLD)));
        CRAFT_SPELLS.add(spellId);
        InfusionRecipes.register(Items.PAPER, item, AllItems.SPELL_SCROLL.getStack(spellId));
    }

    private static void fromEventLoot(Identifier spellId)
    {
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$EVENT.get());
        EVENT_LOOT_SPELLS.add(spellId);
    }
}
