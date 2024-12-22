package karashokleo.spell_dimension.config.recipe;

import artifacts.registry.ModItems;
import com.glisco.victus.item.VictusItems;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.klikli_dev.modonomicon.api.ModonomiconConstants;
import com.klikli_dev.modonomicon.registry.ItemRegistry;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.paladins.item.Shields;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class InfusionRecipes
{
    private static final Table<Item, Item, ItemStack> RECIPES = HashBasedTable.create();

    static
    {
        ItemStack guide_book = ItemRegistry.MODONOMICON_PURPLE.get().getDefaultStack();
        guide_book.getOrCreateNbt().putString(ModonomiconConstants.Nbt.ITEM_BOOK_ID_TAG, MagicGuidanceProvider.BOOK_ID.toString());
        register(Items.BOOK, AllItems.ENCHANTED_ESSENCE, guide_book);
        register(MiscItems.DETECTOR_GLASSES, Items.GOLDEN_CARROT, ModItems.NIGHT_VISION_GOGGLES.get().getDefaultStack());
        register(Items.NETHERITE_PICKAXE, VictusItems.BLAZING_HEART_ASPECT, ModItems.PICKAXE_HEATER.get().getDefaultStack());
        register(Items.GOLDEN_HOE, VictusItems.GOLDEN_HEART_ASPECT, ModItems.GOLDEN_HOOK.get().getDefaultStack());
        register(Items.HEART_OF_THE_SEA, VictusItems.OCEAN_HEART_ASPECT, Registries.ITEM.get(new Identifier("ringsofascension:ring_water_breathing")).getDefaultStack());
        register(net.aleganza.plentyofarmors.item.ModItems.HARDENED_PHANTOM_MEMBRANE, ComplementItems.WIND_BOTTLE, ModItems.CLOUD_IN_A_BOTTLE.get().getDefaultStack());
        register(net.aleganza.plentyofarmors.item.ModItems.HARDENED_PHANTOM_MEMBRANE, ComplementItems.SUN_MEMBRANE, ModItems.HELIUM_FLAMINGO.get().getDefaultStack());
        register(Registries.ITEM.get(new Identifier("midashunger:cooked_golden_beef")), ComplementItems.LIFE_ESSENCE, ModItems.EVERLASTING_BEEF.get().getDefaultStack());
        register(Registries.ITEM.get(Shields.iron_kite_shield.id()), Items.RED_WOOL, ModItems.UMBRELLA.get().getDefaultStack());
        register(Items.LEATHER_HELMET, Items.SLIME_BALL, ModItems.SUPERSTITIOUS_HAT.get().getDefaultStack());
        register(Items.LEATHER_HELMET, Items.EMERALD, ModItems.VILLAGER_HAT.get().getDefaultStack());
        register(Items.GREEN_WOOL, Items.EMERALD_BLOCK, ModItems.LUCKY_SCARF.get().getDefaultStack());
        register(Items.LIGHT_BLUE_WOOL, ComplementItems.FORCE_FIELD, ModItems.SCARF_OF_INVISIBILITY.get().getDefaultStack());
        register(VictusItems.SWEET_HEART_ASPECT, Items.RABBIT_FOOT, ModItems.PANIC_NECKLACE.get().getDefaultStack());
        register(Items.WITHER_SKELETON_SKULL, Items.OBSIDIAN, ModItems.OBSIDIAN_SKULL.get().getDefaultStack());
        register(ConsumableItems.BOOSTER_POTION, ComplementItems.TOTEMIC_GOLD.ingot(), ModItems.ANTIDOTE_VESSEL.get().getDefaultStack());
        register(Items.REDSTONE_BLOCK, Items.GOLD_INGOT, ModItems.UNIVERSAL_ATTRACTOR.get().getDefaultStack());
        register(VictusItems.BUNDLE_HEART_ASPECT, ComplementItems.TOTEMIC_GOLD.ingot(), ModItems.CRYSTAL_HEART.get().getDefaultStack());
        register(Items.TOTEM_OF_UNDYING, Items.CHORUS_FRUIT, ModItems.CHORUS_TOTEM.get().getDefaultStack());
        register(Items.PISTON, Items.PHANTOM_MEMBRANE, ModItems.POCKET_PISTON.get().getDefaultStack());
        register(Items.LEATHER_BOOTS, Items.PRISMARINE_SHARD, ModItems.AQUA_DASHERS.get().getDefaultStack());
        register(Items.LEATHER_BOOTS, Items.RABBIT_FOOT, ModItems.BUNNY_HOPPERS.get().getDefaultStack());
        register(Items.LEATHER_BOOTS, Items.REDSTONE, ModItems.RUNNING_SHOES.get().getDefaultStack());
        register(Items.LEATHER_BOOTS, Items.POWDER_SNOW_BUCKET, ModItems.SNOWSHOES.get().getDefaultStack());
        register(Items.LEATHER_BOOTS, ComplementItems.POSEIDITE.ingot(), ModItems.FLIPPERS.get().getDefaultStack());
        register(Items.LEATHER_BOOTS, Items.GRASS_BLOCK, ModItems.ROOTED_BOOTS.get().getDefaultStack());
    }

    public static void register(Item base, Item addition, ItemStack output)
    {
        RECIPES.put(base, addition, output);
    }

    @Nullable
    public static ItemStack getRecipe(Item base, Item addition)
    {
        return RECIPES.get(base, addition);
    }

    public static Collection<Table.Cell<Item, Item, ItemStack>> getAll()
    {
        return RECIPES.cellSet();
    }
}
