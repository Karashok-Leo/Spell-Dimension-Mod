package karashokleo.spell_dimension.data.generic;

import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.spell_dimension.content.item.essence.BaseEssenceItem;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.TagUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.runes.RunesMod;
import net.runes.api.RuneItems;

import java.util.List;
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
        for (int i = 0; i < AllTags.ESSENCE.size(); i++)
        {
            this.getOrCreateTagBuilder(AllTags.ESSENCE_ALL).addTag(AllTags.ESSENCE.get(i));
            for (List<BaseEssenceItem> items : AllItems.BASE_ESSENCES.values())
                this.getOrCreateTagBuilder(AllTags.ESSENCE.get(i)).add(items.get(i));
        }
        RuneItems.entries.stream().map(RuneItems.Entry::item).forEach(this.getOrCreateTagBuilder(AllTags.RUNE)::add);
        AllItems.BASE_ESSENCES.forEach((school, items) ->
        {
            add(school.id.getPath() + "_small", items.get(0));
            add(school.id.getPath() + "_medium", items.get(1));
        });
        FabricTagProvider<Item>.FabricTagBuilder heartFood = this.getOrCreateTagBuilder(AllTags.HEART_FOOD);
        heartFood.add(Items.ENCHANTED_GOLDEN_APPLE);
        heartFood.add(ComplementItems.LIFE_ESSENCE);
        heartFood.addOptional(new Identifier("midashunger:enchanted_golden_carrot"));
    }

    private void add(String path, Item item)
    {
        this.getOrCreateTagBuilder(TagUtil.itemTag(new Identifier(RunesMod.ID, "rune_crafting/reagent/" + path))).add(item);
    }
}
