package karashokleo.spell_dimension.data.generic.recipe;

import com.glisco.things.items.ThingsItems;
import com.kyanite.deeperdarker.content.DDItems;
import com.lion.graveyard.init.TGItems;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.teamremastered.endrem.registry.ERItems;
import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.spell.SpellInfusionRecipeBuilder;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.TagUtil;
import net.adventurez.init.BlockInit;
import net.adventurez.init.ItemInit;
import net.aleganza.plentyofarmors.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.trique.mythicupgrades.block.MUBlocks;
import nourl.mythicmetals.blocks.MythicBlocks;
import nourl.mythicmetals.item.MythicItems;
import nourl.mythicmetals.item.tools.MythicTools;

import java.util.function.Consumer;

public class SDSpellInfusionRecipes
{
    public static void add(Consumer<RecipeJsonProvider> exporter, Identifier spellId, SpellSchool school, String addition)
    {
        add(exporter, spellId, school, Registries.ITEM.get(new Identifier(addition)));
    }

    public static void add(Consumer<RecipeJsonProvider> exporter, Identifier spellId, SpellSchool school, Item... addition)
    {
        Ingredient ingredient;
        if (school == AllSpells.GENERIC)
        {
            ingredient = Ingredient.fromTag(AllTags.ESSENCE_ALL);
        } else
        {
            Item essence = AllItems.BASE_ESSENCES.get(school).get(AllSpells.getSpellTier(spellId));
            ingredient = Ingredient.ofItems(essence);
        }
        Identifier recipeId = SpellDimension.modLoc("spell_infusion/%s/%s".formatted(spellId.getNamespace(), spellId.getPath()));
        SpellInfusionRecipeBuilder builder = new SpellInfusionRecipeBuilder()
            .withTableIngredient(Ingredient.ofItems(Items.PAPER))
            .withPedestalItem(1, ingredient);
        for (Item item : addition)
        {
            builder.withPedestalItem(1, item);
        }
        builder.offerTo(exporter, recipeId, spellId);
    }

    public static void add(Consumer<RecipeJsonProvider> exporter, Identifier spellId, SpellSchool school, Ingredient... addition)
    {
        Ingredient ingredient;
        if (school == AllSpells.GENERIC)
        {
            ingredient = Ingredient.fromTag(AllTags.ESSENCE_ALL);
        } else
        {
            Item essence = AllItems.BASE_ESSENCES.get(school).get(AllSpells.getSpellTier(spellId));
            ingredient = Ingredient.ofItems(essence);
        }
        Identifier recipeId = SpellDimension.modLoc("spell_infusion/%s/%s".formatted(spellId.getNamespace(), spellId.getPath()));
        SpellInfusionRecipeBuilder builder = new SpellInfusionRecipeBuilder()
            .withTableIngredient(Ingredient.ofItems(Items.PAPER))
            .withPedestalItem(1, ingredient);
        for (Ingredient item : addition)
        {
            builder.withPedestalItem(1, item);
        }
        builder.offerTo(exporter, recipeId, spellId);
    }

    public static void add(Consumer<RecipeJsonProvider> exporter)
    {
        add(exporter, AllSpells.LOCATE, AllSpells.GENERIC, Items.COMPASS);
        add(exporter, AllSpells.SUMMON, AllSpells.GENERIC, AllItems.SPAWNER_SOUL);
        add(exporter, AllSpells.PLACE, AllSpells.GENERIC, ModRegistry.ENDERSOUL_HAND_ITEM.get());
        add(exporter, AllSpells.BREAK, AllSpells.GENERIC, ModRegistry.CREEPER_SHARD_ITEM.get());
        add(exporter, AllSpells.LIGHT, AllSpells.GENERIC, Items.GLOWSTONE);
        add(exporter, AllSpells.MOON_SWIM, AllSpells.GENERIC, ModItems.HARDENED_PHANTOM_MEMBRANE);

        add(exporter, AllSpells.PHASE, SpellSchools.ARCANE, DDItems.REINFORCED_ECHO_SHARD);
        add(exporter, AllSpells.CONVERGE, SpellSchools.ARCANE, fuzs.illagerinvasion.init.ModRegistry.PRIMAL_ESSENCE_ITEM.get());
        add(exporter, AllSpells.ELDRITCH_BLAST, SpellSchools.ARCANE, ComplementItems.WARDEN_BONE_SHARD);
        add(exporter, AllSpells.SHIFT, SpellSchools.ARCANE, ModItems.HEART_OF_THE_END);
        add(exporter, AllSpells.FORCE_LANDING, SpellSchools.ARCANE, LHTraits.GRAVITY.asItem());
        add(exporter, AllSpells.ARCANE_BARRIER, SpellSchools.ARCANE, Items.GLASS);
        add(exporter, AllSpells.INCARCERATE, SpellSchools.ARCANE, ComplementItems.BLACKSTONE_CORE);
        add(exporter, AllSpells.BLACK_HOLE, SpellSchools.ARCANE, Items.DRAGON_EGG);
        add(exporter, AllSpells.FINAL_STRIKE, SpellSchools.ARCANE, DDItems.SOUL_CRYSTAL);
        add(exporter, AllSpells.ARCANE_BEAM, SpellSchools.ARCANE, MythicBlocks.KYBER.getStorageBlock().asItem());
        add(exporter, AllSpells.ARCANE_FLOURISH, SpellSchools.ARCANE, MUBlocks.AMETRINE_BLOCK.asItem());
        add(exporter, AllSpells.ARCANE_FLICKER, SpellSchools.ARCANE, MythicBlocks.STAR_PLATINUM.getStorageBlock().asItem());
        add(exporter, AllSpells.ARCANE_OVERDRIVE, SpellSchools.ARCANE, com.spellbladenext.items.Items.arcane_orb.item());
        add(exporter, AllSpells.ECHO_STORM, SpellSchools.ARCANE, MythicItems.Mats.STORMYX_SHELL);
        add(exporter, AllSpells.MAELSTROM, SpellSchools.ARCANE, MythicTools.STORMYX_SHIELD);
        add(exporter, AllSpells.AMETHYST_SLASH, SpellSchools.ARCANE, com.spellbladenext.items.Items.crystal_cutlass.item());

        add(exporter, AllSpells.FIRE_BREATH, SpellSchools.FIRE, MUBlocks.TOPAZ_BLOCK.asItem());
        add(exporter, AllSpells.BLAST, SpellSchools.FIRE, ComplementItems.EXPLOSION_SHARD);
        add(exporter, AllSpells.IGNITE, SpellSchools.FIRE, ItemInit.BLACKSTONE_GOLEM_HEART);
        add(exporter, AllSpells.FIRE_OF_RETRIBUTION, SpellSchools.FIRE, LHTraits.SOUL_BURNER.asItem());
        add(exporter, AllSpells.FIRE_FLOURISH, SpellSchools.FIRE, MythicBlocks.PALLADIUM.getStorageBlock().asItem());
        add(exporter, AllSpells.FLAME_OVERDRIVE, SpellSchools.FIRE, com.spellbladenext.items.Items.fire_orb.item());
        add(exporter, AllSpells.FLICKER_STRIKE, SpellSchools.FIRE, MUBlocks.RUBY_BLOCK.asItem());
        add(exporter, AllSpells.FLAME_SLASH, SpellSchools.FIRE, com.spellbladenext.items.Items.flaming_falchion.item());
        add(exporter, AllSpells.OVER_BLAZE, SpellSchools.FIRE, MythicBlocks.QUADRILLUM.getStorageBlock().asItem());
        add(exporter, AllSpells.PHOENIX_DIVE, SpellSchools.FIRE, "soulsweapons:crimson_ingot");
        add(exporter, AllSpells.PHOENIX_CURSE, SpellSchools.FIRE, "soulsweapons:crimson_obsidian");
        add(exporter, AllSpells.INFERNO, SpellSchools.FIRE, ThingsItems.HADES_CRYSTAL);
        add(exporter, AllSpells.DRAGON_SLAM, SpellSchools.FIRE, net.dragonloot.init.ItemInit.DRAGON_SCALE_ITEM);
        add(exporter, AllSpells.WILDFIRE, SpellSchools.FIRE, MythicItems.Mats.CARMOT_STONE);

        add(exporter, AllSpells.FROST_BLIZZARD, SpellSchools.FROST, MythicBlocks.SILVER.getStorageBlock().asItem());
        add(exporter, AllSpells.ICY_NUCLEUS, SpellSchools.FROST, MythicBlocks.AQUARIUM.getStorageBlock().asItem());
        add(exporter, AllSpells.FROST_AURA, SpellSchools.FROST, AquamiraeItems.MAZE_ROSE);
        add(exporter, AllSpells.ICICLE, SpellSchools.FROST, AllItems.ABYSS_GUARD);
        add(exporter, AllSpells.FROST_BLINK, SpellSchools.FROST, AquamiraeItems.DEAD_SEA_SCROLL);
        add(exporter, AllSpells.FROZEN, SpellSchools.FROST, AquamiraeItems.SHIP_GRAVEYARD_ECHO);
        add(exporter, AllSpells.FROST_FLOURISH, SpellSchools.FROST, MUBlocks.AQUAMARINE_BLOCK.asItem());
        add(exporter, AllSpells.FROST_OVERDRIVE, SpellSchools.FROST, com.spellbladenext.items.Items.frost_orb.item());
        add(exporter, AllSpells.FROST_LOTUS, SpellSchools.FROST, MythicBlocks.RUNITE.getStorageBlock().asItem());
        add(exporter, AllSpells.DEATH_CHILL, SpellSchools.FROST, LHTraits.FREEZING.asItem());
        add(exporter, AllSpells.FROST_SLASH, SpellSchools.FROST, com.spellbladenext.items.Items.glacial_gladius.item());
        add(exporter, AllSpells.MASSACRE, SpellSchools.FROST, AquamiraeItems.POISONED_CHAKRA);
        add(exporter, AllSpells.RIPTIDE, SpellSchools.FROST, MythicItems.Mats.AQUARIUM_PEARL);
        add(exporter, AllSpells.COLD_BUFF, SpellSchools.FROST, ERItems.COLD_EYE);
        add(exporter, AllSpells.TEMPEST, SpellSchools.FROST, AquamiraeItems.ABYSSAL_AMETHYST);

        add(exporter, AllSpells.DIVINE_CURSE_BLAST, SpellSchools.HEALING, ComplementItems.TOTEMIC_GOLD.ingot());
        add(exporter, AllSpells.HOLY_BEAM, SpellSchools.HEALING, "bosses_of_mass_destruction:ancient_anima");
        add(exporter, AllSpells.CIRCLE_OF_HEALING, SpellSchools.HEALING, "soulsweapons:arkenstone");
        add(exporter, AllSpells.BARRIER, SpellSchools.HEALING, TGItems.DARK_IRON_BLOCK.get());
        add(exporter, AllSpells.JUDGEMENT, SpellSchools.HEALING, MUBlocks.JADE_BLOCK.asItem());
        add(exporter, AllSpells.CLEANSE, SpellSchools.HEALING, ComplementItems.LIFE_ESSENCE);
        add(exporter, AllSpells.EXORCISM, SpellSchools.HEALING, LHTraits.DISPELL.asItem());
        add(exporter, AllSpells.CRITICAL_HIT, SpellSchools.HEALING, MiscItems.CHAOS.blockSet().item());
        add(exporter, AllSpells.SPELL_POWER, SpellSchools.HEALING, "soulsweapons:essence_of_luminescence");
        add(exporter, AllSpells.RESIST, SpellSchools.HEALING, "soulsweapons:essence_of_eventide");
        add(exporter, AllSpells.REGEN, SpellSchools.HEALING, "bosses_of_mass_destruction:void_thorn");
        add(exporter, AllSpells.HASTE, SpellSchools.HEALING, "soulsweapons:moonstone_block");
        add(exporter, AllSpells.SPEED, SpellSchools.HEALING, LHTraits.SPEEDY.asItem());
        add(exporter, AllSpells.DIVINE_AURA, SpellSchools.HEALING, LHTraits.KILLER_AURA.asItem());
        add(exporter, AllSpells.BLESSING, SpellSchools.HEALING, ConsumableItems.BOTTLE_SANITY);
        add(exporter, AllSpells.MISFORTUNE, SpellSchools.HEALING, LHTraits.CURSED.asItem());
        add(exporter, AllSpells.HEAVENLY_JUSTICE, SpellSchools.HEALING, ComplementItems.RESONANT_FEATHER);
        add(exporter, AllSpells.BATTLE_BANNER, SpellSchools.HEALING, BlockInit.PIGLIN_FLAG.asItem());

        add(exporter, AllSpells.BALL_LIGHTNING, SpellSchools.LIGHTNING, "fwaystones:local_void");
        add(exporter, AllSpells.RESONANCE, SpellSchools.LIGHTNING, MythicBlocks.MYTHRIL.getStorageBlock().asItem());
        add(exporter, AllSpells.BREAKDOWN, SpellSchools.LIGHTNING, MythicBlocks.MORKITE.getStorageBlock().asItem());
        add(exporter, AllSpells.THUNDERBOLT, SpellSchools.LIGHTNING, MythicBlocks.PLATINUM.getStorageBlock().asItem());
        add(exporter, AllSpells.QUANTUM_FIELD, SpellSchools.LIGHTNING, MythicItems.Mats.UNOBTAINIUM);
        add(exporter, AllSpells.ARCLIGHT, SpellSchools.LIGHTNING, MythicBlocks.CARMOT_NUKE_CORE.asItem());
        add(exporter, AllSpells.CONSTANT_CURRENT, SpellSchools.LIGHTNING, MythicBlocks.QUADRILLUM_NUKE_CORE.asItem());
        add(exporter, AllSpells.CLOSED_LOOP, SpellSchools.LIGHTNING, ComplementItems.VOID_EYE);
        add(exporter, AllSpells.ELECTROCUTION, SpellSchools.LIGHTNING, ComplementItems.GUARDIAN_EYE);
        add(exporter, AllSpells.STORMFLASH, SpellSchools.LIGHTNING, ComplementItems.STORM_CORE, MythicBlocks.STORMYX.getStorageBlock().asItem());
        add(exporter, AllSpells.RAILGUN, SpellSchools.LIGHTNING, "bosses_of_mass_destruction:obsidilith_rune");
        add(exporter, AllSpells.ELECTRIC_BONDAGE, SpellSchools.LIGHTNING, ConsumableItems.ETERNAL_WITCH_CHARGE, ComplementItems.BLACKSTONE_CORE, ComplementItems.SCULKIUM.blockSet().item());

        add(exporter, AllSpells.SOUL_SWAP, SpellSchools.SOUL, TGItems.DARK_IRON_INGOT.get());
        add(exporter, AllSpells.SOUL_STEP, SpellSchools.SOUL, Registries.ITEM.get(new Identifier("soulsweapons:lost_soul")));
        add(exporter, AllSpells.SOUL_MARK, SpellSchools.SOUL, Ingredient.fromTag(TagUtil.itemTag(new Identifier("soulsweapons:lord_soul"))));
        add(exporter, AllSpells.SOUL_DUET, SpellSchools.SOUL, ComplementItems.SOUL_FLAME);
        add(exporter, AllSpells.PHANTOM_SYNDICATE, SpellSchools.SOUL, Items.CALIBRATED_SCULK_SENSOR);
        add(exporter, AllSpells.SOUL_ECHO, SpellSchools.SOUL, DDItems.SOUL_CRYSTAL);
        add(exporter, AllSpells.SOUL_BURST, SpellSchools.SOUL, ComplementItems.EXPLOSION_SHARD, Registries.ITEM.get(new Identifier("soulsweapons:soul_ingot")));
        add(exporter, AllSpells.SOUL_SACRIFICE, SpellSchools.SOUL, MiscItems.HOSTILITY_ESSENCE, ComplementItems.LIFE_ESSENCE);
        add(exporter, AllSpells.ETHEREAL_EVASION, SpellSchools.SOUL, Registries.ITEM.get(new Identifier("soulsweapons:skofnung_stone")));
    }
}
