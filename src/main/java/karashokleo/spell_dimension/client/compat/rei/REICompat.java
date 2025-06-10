package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.client.compat.LocationStack;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import karashokleo.spell_dimension.init.AllItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import me.shedaniel.rei.api.common.entry.type.EntryTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.recipe.RecipeManager;

public class REICompat implements REIClientPlugin
{
    public static final EntryType<LocationStack> LOCATION = EntryType.deferred(SpellDimension.modLoc("location"));
    public static final CategoryIdentifier<REILocateDisplay> LOCATE = CategoryIdentifier.of(SpellDimension.MOD_ID, "locate");
    public static final CategoryIdentifier<REISummonDisplay> SUMMON = CategoryIdentifier.of(SpellDimension.MOD_ID, "summon");

    @Override
    public void registerCategories(CategoryRegistry registry)
    {
        registry.add(new REILocateCategory());
        registry.add(new REISummonCategory());

        registry.addWorkstations(LOCATE, REILocateCategory.WORKSTATION, REILocateCategory.SPELL_SCROLL);
        registry.addWorkstations(SUMMON, REISummonCategory.WORKSTATION, REISummonCategory.SPELL_SCROLL);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
        RecipeManager recipeManager = registry.getRecipeManager();
        for (LocateRecipe locateRecipe : recipeManager.listAllOfType(LocateRecipe.TYPE))
        {
            registry.add(new REILocateDisplay(locateRecipe));
        }
        for (SummonRecipe summonRecipe : recipeManager.listAllOfType(SummonRecipe.TYPE))
        {
            registry.add(new REISummonDisplay(summonRecipe));
        }
    }

    @Override
    public void registerItemComparators(ItemComparatorRegistry registry)
    {
        // see DefaultPlugin
        registry.registerNbt(AllItems.SPELL_SCROLL);
        registry.registerNbt(AllItems.ENCHANTED_ESSENCE);
    }

    @Override
    public void registerEntries(EntryRegistry registry)
    {
        ClientPlayNetworkHandler connection = MinecraftClient.getInstance().getNetworkHandler();
        if (connection == null) return;
        RecipeManager recipeManager = connection.getRecipeManager();
        for (LocateRecipe recipe : recipeManager.listAllOfType(LocateRecipe.TYPE))
        {
            LocationStack stack = LocationStack.fromRecipe(recipe);
            registry.addEntry(EntryStack.of(LOCATION, stack));
        }
        registry.addEntries();
    }

    @Override
    public void registerEntryTypes(EntryTypeRegistry registry)
    {
        registry.register(LOCATION, new LocationEntryDefinition());
    }
}
