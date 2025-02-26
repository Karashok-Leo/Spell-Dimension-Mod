package karashokleo.spell_dimension.data.generic.recipe;

import accieo.midas.hunger.items.MidasItems;
import artifacts.registry.ModItems;
import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.registry.ItemRegistry;
import karashokleo.enchantment_infusion.content.data.SimpleInfusionRecipeBuilder;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.paladins.item.Shields;
import net.spell_power.api.SpellSchools;
import nourl.mythicmetals.blocks.MythicBlocks;
import nourl.mythicmetals.item.MythicItems;

import java.util.function.Consumer;

public class SDSimpleInfusionRecipes
{
    public static void add(Consumer<RecipeJsonProvider> exporter, Item base, Item addition, ItemStack output)
    {
        Identifier itemId = Registries.ITEM.getId(output.getItem());
        Identifier recipeId = SpellDimension.modLoc("simple_infusion/%s/%s".formatted(itemId.getNamespace(), itemId.getPath()));
        new SimpleInfusionRecipeBuilder()
                .withTableIngredient(Ingredient.ofItems(base))
                .withPedestalItem(1, addition)
                .withPedestalItem(1, Ingredient.fromTag(AllTags.ESSENCE.get(0)))
                .copyNbt(false)
                .offerTo(exporter, recipeId, output);
    }

    public static void add(Consumer<RecipeJsonProvider> exporter)
    {
        ItemStack guideBook = ItemRegistry.MODONOMICON_PURPLE.get().getDefaultStack();
        guideBook.getOrCreateNbt().putString(ModonomiconConstants.Nbt.ITEM_BOOK_ID_TAG, MagicGuidanceProvider.BOOK_ID.toString());
        add(exporter, Items.BOOK, AllItems.ENCHANTED_ESSENCE, guideBook);

        add(exporter, MiscItems.DETECTOR_GLASSES, Items.GOLDEN_CARROT, ModItems.NIGHT_VISION_GOGGLES.get().getDefaultStack());
        add(exporter, Items.NETHERITE_PICKAXE, ComplementItems.SOUL_FLAME, ModItems.PICKAXE_HEATER.get().getDefaultStack());
        add(exporter, Items.GOLDEN_HOE, Items.EXPERIENCE_BOTTLE, ModItems.GOLDEN_HOOK.get().getDefaultStack());
        add(exporter, Items.HEART_OF_THE_SEA, net.aleganza.plentyofarmors.item.ModItems.MARINE_DIAMOND, com.focamacho.ringsofascension.init.ModItems.ringWaterBreathing.getDefaultStack());
        add(exporter, net.aleganza.plentyofarmors.item.ModItems.HARDENED_PHANTOM_MEMBRANE, ComplementItems.WIND_BOTTLE, ModItems.CLOUD_IN_A_BOTTLE.get().getDefaultStack());
        add(exporter, net.aleganza.plentyofarmors.item.ModItems.HARDENED_PHANTOM_MEMBRANE, ComplementItems.SUN_MEMBRANE, ModItems.HELIUM_FLAMINGO.get().getDefaultStack());
        add(exporter, MidasItems.COOKED_GOLDEN_BEEF, ComplementItems.LIFE_ESSENCE, ModItems.EVERLASTING_BEEF.get().getDefaultStack());
        add(exporter, Registries.ITEM.get(Shields.iron_kite_shield.id()), Items.RED_WOOL, ModItems.UMBRELLA.get().getDefaultStack());
        add(exporter, Items.LEATHER_HELMET, Items.SLIME_BALL, ModItems.SUPERSTITIOUS_HAT.get().getDefaultStack());
        add(exporter, Items.LEATHER_HELMET, Items.EMERALD, ModItems.VILLAGER_HAT.get().getDefaultStack());
        add(exporter, Items.GREEN_WOOL, Items.EMERALD_BLOCK, ModItems.LUCKY_SCARF.get().getDefaultStack());
        add(exporter, Items.LIGHT_BLUE_WOOL, ComplementItems.FORCE_FIELD, ModItems.SCARF_OF_INVISIBILITY.get().getDefaultStack());
        add(exporter, Items.SUGAR, Items.RABBIT_FOOT, ModItems.PANIC_NECKLACE.get().getDefaultStack());
        add(exporter, Items.WITHER_SKELETON_SKULL, Items.OBSIDIAN, ModItems.OBSIDIAN_SKULL.get().getDefaultStack());
        add(exporter, ConsumableItems.BOOSTER_POTION, ComplementItems.TOTEMIC_GOLD.ingot(), ModItems.ANTIDOTE_VESSEL.get().getDefaultStack());
        add(exporter, Items.REDSTONE_BLOCK, Items.GOLD_INGOT, ModItems.UNIVERSAL_ATTRACTOR.get().getDefaultStack());
        add(exporter, ComplementItems.LIFE_ESSENCE, ComplementItems.TOTEMIC_GOLD.ingot(), ModItems.CRYSTAL_HEART.get().getDefaultStack());
        add(exporter, Items.TOTEM_OF_UNDYING, Items.CHORUS_FRUIT, ModItems.CHORUS_TOTEM.get().getDefaultStack());
        add(exporter, Items.PISTON, Items.PHANTOM_MEMBRANE, ModItems.POCKET_PISTON.get().getDefaultStack());
        add(exporter, Items.LEATHER_BOOTS, Items.PRISMARINE_SHARD, ModItems.AQUA_DASHERS.get().getDefaultStack());
        add(exporter, Items.LEATHER_BOOTS, Items.RABBIT_FOOT, ModItems.BUNNY_HOPPERS.get().getDefaultStack());
        add(exporter, Items.LEATHER_BOOTS, Items.REDSTONE, ModItems.RUNNING_SHOES.get().getDefaultStack());
        add(exporter, Items.LEATHER_BOOTS, Items.POWDER_SNOW_BUCKET, ModItems.SNOWSHOES.get().getDefaultStack());
        add(exporter, Items.LEATHER_BOOTS, ComplementItems.POSEIDITE.ingot(), ModItems.FLIPPERS.get().getDefaultStack());
        add(exporter, Items.LEATHER_BOOTS, Items.GRASS_BLOCK, ModItems.ROOTED_BOOTS.get().getDefaultStack());
        add(exporter, Items.LEATHER, Items.BLAZE_POWDER, ModItems.FIRE_GAUNTLET.get().getDefaultStack());
        add(exporter, MythicBlocks.STORMYX.getStorageBlock().asItem(), AllItems.BASE_ESSENCES.get(SpellSchools.ARCANE).get(2), MythicItems.Mats.STORMYX_SHELL.getDefaultStack());
        add(exporter, MythicBlocks.CARMOT.getStorageBlock().asItem(), AllItems.BASE_ESSENCES.get(SpellSchools.FIRE).get(2), MythicItems.Mats.CARMOT_STONE.getDefaultStack());
        add(exporter, MythicBlocks.AQUARIUM.getStorageBlock().asItem(), AllItems.BASE_ESSENCES.get(SpellSchools.FROST).get(2), MythicItems.Mats.AQUARIUM_PEARL.getDefaultStack());
    }
}
