package karashokleo.spell_dimension.data;

import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.spell_dimension.content.event.PlayerHealthEvent;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.TagUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.runes.RunesMod;

import java.util.concurrent.CompletableFuture;

public class SDItemTagProvider extends FabricTagProvider.ItemTagProvider
{
    public SDItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        AllItems.BASE_ESSENCES.forEach((school, items) ->
        {
            add(school.id.getPath() + "_small", items.get(0));
            add(school.id.getPath() + "_medium", items.get(1));
        });
        FabricTagProvider<Item>.FabricTagBuilder heartFood = this.getOrCreateTagBuilder(PlayerHealthEvent.HEART_FOOD);
        heartFood.add(Items.ENCHANTED_GOLDEN_APPLE);
        heartFood.add(ComplementItems.LIFE_ESSENCE);
        heartFood.addOptional(new Identifier("midashunger:enchanted_golden_carrot"));
    }

    private void add(String path, Item item)
    {
        this.getOrCreateTagBuilder(TagUtil.itemTag(new Identifier(RunesMod.ID, "rune_crafting/reagent/" + path))).add(item);
    }
}
