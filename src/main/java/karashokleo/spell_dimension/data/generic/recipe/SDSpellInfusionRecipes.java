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
    public static void add(Consumer<RecipeJsonProvider> exporter, String addition, Identifier spellId, SpellSchool school)
    {
        add(exporter, Registries.ITEM.get(new Identifier(addition)), spellId, school);
    }

    public static void add(Consumer<RecipeJsonProvider> exporter, Item addition, Identifier spellId, SpellSchool school)
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
        new SpellInfusionRecipeBuilder()
                .withTableIngredient(Ingredient.ofItems(Items.PAPER))
                .withPedestalItem(1, addition)
                .withPedestalItem(1, ingredient)
                .offerTo(exporter, recipeId, spellId);
    }

    public static void add(Consumer<RecipeJsonProvider> exporter)
    {
        /// pending:
        /// "bosses_of_mass_destruction:blazing_eye"
        /// MythicBlocks.PLATINUM.getStorageBlock().asItem()
        /// AquamiraeItems.ABYSSAL_AMETHYST

        add(exporter, Items.COMPASS, AllSpells.LOCATE, AllSpells.GENERIC);
        add(exporter, AllItems.SPAWNER_SOUL, AllSpells.SUMMON, AllSpells.GENERIC);
        add(exporter, ModRegistry.ENDERSOUL_HAND_ITEM.get(), AllSpells.PLACE, AllSpells.GENERIC);
        add(exporter, ModRegistry.CREEPER_SHARD_ITEM.get(), AllSpells.BREAK, AllSpells.GENERIC);
        add(exporter, Items.GLOWSTONE, AllSpells.LIGHT, AllSpells.GENERIC);
        add(exporter, ModItems.HARDENED_PHANTOM_MEMBRANE, AllSpells.MOON_SWIM, AllSpells.GENERIC);

        add(exporter, DDItems.REINFORCED_ECHO_SHARD, AllSpells.PHASE, SpellSchools.ARCANE);
        add(exporter, fuzs.illagerinvasion.init.ModRegistry.PRIMAL_ESSENCE_ITEM.get(), AllSpells.CONVERGE, SpellSchools.ARCANE);
        add(exporter, ComplementItems.WARDEN_BONE_SHARD, AllSpells.ELDRITCH_BLAST, SpellSchools.ARCANE);
        add(exporter, ModItems.HEART_OF_THE_END, AllSpells.SHIFT, SpellSchools.ARCANE);
        add(exporter, LHTraits.GRAVITY.asItem(), AllSpells.FORCE_LANDING, SpellSchools.ARCANE);
        add(exporter, Items.GLASS, AllSpells.ARCANE_BARRIER, SpellSchools.ARCANE);
        add(exporter, ComplementItems.BLACKSTONE_CORE, AllSpells.INCARCERATE, SpellSchools.ARCANE);
        add(exporter, Items.DRAGON_EGG, AllSpells.BLACK_HOLE, SpellSchools.ARCANE);
        add(exporter, DDItems.SOUL_CRYSTAL, AllSpells.FINAL_STRIKE, SpellSchools.ARCANE);
        add(exporter, MythicBlocks.KYBER.getStorageBlock().asItem(), AllSpells.ARCANE_BEAM, SpellSchools.ARCANE);
        add(exporter, MUBlocks.AMETRINE_BLOCK.asItem(), AllSpells.ARCANE_FLOURISH, SpellSchools.ARCANE);
        add(exporter, MythicBlocks.STAR_PLATINUM.getStorageBlock().asItem(), AllSpells.ARCANE_FLICKER, SpellSchools.ARCANE);
        add(exporter, com.spellbladenext.items.Items.arcane_orb.item(), AllSpells.ARCANE_OVERDRIVE, SpellSchools.ARCANE);
        add(exporter, MythicItems.Mats.STORMYX_SHELL, AllSpells.ECHO_STORM, SpellSchools.ARCANE);
        add(exporter, MythicTools.STORMYX_SHIELD, AllSpells.MAELSTROM, SpellSchools.ARCANE);
        add(exporter, com.spellbladenext.items.Items.crystal_cutlass.item(), AllSpells.AMETHYST_SLASH, SpellSchools.ARCANE);

        add(exporter, MUBlocks.TOPAZ_BLOCK.asItem(), AllSpells.FIRE_BREATH, SpellSchools.FIRE);
        add(exporter, ComplementItems.EXPLOSION_SHARD, AllSpells.BLAST, SpellSchools.FIRE);
        add(exporter, ItemInit.BLACKSTONE_GOLEM_HEART, AllSpells.IGNITE, SpellSchools.FIRE);
        add(exporter, LHTraits.SOUL_BURNER.asItem(), AllSpells.FIRE_OF_RETRIBUTION, SpellSchools.FIRE);
        add(exporter, MythicBlocks.PALLADIUM.getStorageBlock().asItem(), AllSpells.FIRE_FLOURISH, SpellSchools.FIRE);
        add(exporter, com.spellbladenext.items.Items.fire_orb.item(), AllSpells.FLAME_OVERDRIVE, SpellSchools.FIRE);
        add(exporter, MUBlocks.RUBY_BLOCK.asItem(), AllSpells.FLICKER_STRIKE, SpellSchools.FIRE);
        add(exporter, com.spellbladenext.items.Items.flaming_falchion.item(), AllSpells.FLAME_SLASH, SpellSchools.FIRE);
        add(exporter, MythicBlocks.QUADRILLUM.getStorageBlock().asItem(), AllSpells.OVER_BLAZE, SpellSchools.FIRE);
        add(exporter, "soulsweapons:crimson_ingot", AllSpells.PHOENIX_DIVE, SpellSchools.FIRE);
        add(exporter, "soulsweapons:crimson_obsidian", AllSpells.PHOENIX_CURSE, SpellSchools.FIRE);
        add(exporter, ThingsItems.HADES_CRYSTAL, AllSpells.INFERNO, SpellSchools.FIRE);
        add(exporter, net.dragonloot.init.ItemInit.DRAGON_SCALE_ITEM, AllSpells.DRAGON_SLAM, SpellSchools.FIRE);
        add(exporter, MythicItems.Mats.CARMOT_STONE, AllSpells.WILDFIRE, SpellSchools.FIRE);

        add(exporter, MythicBlocks.SILVER.getStorageBlock().asItem(), AllSpells.FROST_BLIZZARD, SpellSchools.FROST);
        add(exporter, MythicBlocks.AQUARIUM.getStorageBlock().asItem(), AllSpells.ICY_NUCLEUS, SpellSchools.FROST);
        add(exporter, AquamiraeItems.MAZE_ROSE, AllSpells.FROST_AURA, SpellSchools.FROST);
        add(exporter, AllItems.ABYSS_GUARD, AllSpells.ICICLE, SpellSchools.FROST);
        add(exporter, AquamiraeItems.DEAD_SEA_SCROLL, AllSpells.FROST_BLINK, SpellSchools.FROST);
        add(exporter, AquamiraeItems.SHIP_GRAVEYARD_ECHO, AllSpells.FROZEN, SpellSchools.FROST);
        add(exporter, MUBlocks.AQUAMARINE_BLOCK.asItem(), AllSpells.FROST_FLOURISH, SpellSchools.FROST);
        add(exporter, com.spellbladenext.items.Items.frost_orb.item(), AllSpells.FROST_OVERDRIVE, SpellSchools.FROST);
        add(exporter, MythicBlocks.RUNITE.getStorageBlock().asItem(), AllSpells.FROST_LOTUS, SpellSchools.FROST);
        add(exporter, LHTraits.FREEZING.asItem(), AllSpells.DEATH_CHILL, SpellSchools.FROST);
        add(exporter, com.spellbladenext.items.Items.glacial_gladius.item(), AllSpells.FROST_SLASH, SpellSchools.FROST);
        add(exporter, AquamiraeItems.POISONED_CHAKRA, AllSpells.MASSACRE, SpellSchools.FROST);
        add(exporter, MythicItems.Mats.AQUARIUM_PEARL, AllSpells.RIPTIDE, SpellSchools.FROST);
        add(exporter, ERItems.COLD_EYE, AllSpells.COLD_BUFF, SpellSchools.FROST);

        add(exporter, "bosses_of_mass_destruction:ancient_anima", AllSpells.HOLY_BEAM, SpellSchools.HEALING);
        add(exporter, "soulsweapons:arkenstone", AllSpells.CIRCLE_OF_HEALING, SpellSchools.HEALING);
        add(exporter, TGItems.DARK_IRON_BLOCK.get(), AllSpells.BARRIER, SpellSchools.HEALING);
        add(exporter, MUBlocks.JADE_BLOCK.asItem(), AllSpells.JUDGEMENT, SpellSchools.HEALING);
        add(exporter, ComplementItems.LIFE_ESSENCE, AllSpells.CLEANSE, SpellSchools.HEALING);
        add(exporter, LHTraits.DISPELL.asItem(), AllSpells.EXORCISM, SpellSchools.HEALING);
        add(exporter, MiscItems.CHAOS.blockSet().item(), AllSpells.CRITICAL_HIT, SpellSchools.HEALING);
        add(exporter, "soulsweapons:essence_of_luminescence", AllSpells.SPELL_POWER, SpellSchools.HEALING);
        add(exporter, "soulsweapons:essence_of_eventide", AllSpells.RESIST, SpellSchools.HEALING);
        add(exporter, "bosses_of_mass_destruction:void_thorn", AllSpells.REGEN, SpellSchools.HEALING);
        add(exporter, "soulsweapons:moonstone_block", AllSpells.HASTE, SpellSchools.HEALING);
        add(exporter, LHTraits.SPEEDY.asItem(), AllSpells.SPEED, SpellSchools.HEALING);
        add(exporter, LHTraits.KILLER_AURA.asItem(), AllSpells.DIVINE_AURA, SpellSchools.HEALING);
        add(exporter, ConsumableItems.BOTTLE_SANITY, AllSpells.BLESSING, SpellSchools.HEALING);
        add(exporter, LHTraits.CURSED.asItem(), AllSpells.MISFORTUNE, SpellSchools.HEALING);
        add(exporter, ComplementItems.RESONANT_FEATHER, AllSpells.HEAVENLY_JUSTICE, SpellSchools.HEALING);
        add(exporter, BlockInit.PIGLIN_FLAG.asItem(), AllSpells.BATTLE_BANNER, SpellSchools.HEALING);
    }
}
