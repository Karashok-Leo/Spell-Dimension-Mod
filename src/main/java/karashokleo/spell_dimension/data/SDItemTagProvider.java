package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.init.AllItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
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
    }

    private void add(String path, Item item)
    {
        this.getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier(RunesMod.ID, "rune_crafting/reagent/" + path))).add(item);
    }
}
