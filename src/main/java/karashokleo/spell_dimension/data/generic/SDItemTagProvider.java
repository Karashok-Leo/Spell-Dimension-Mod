package karashokleo.spell_dimension.data.generic;

import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

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
        getOrCreateTagBuilder(AllTags.REFORGE_CORE_TAGS.get(0))
                .setReplace(true)
                .forceAddTag(AllTags.ESSENCE.get(2));

        getOrCreateTagBuilder(AllTags.REFORGE_CORE_TAGS.get(1))
                .setReplace(true)
                .add(MiscItems.CHAOS.ingot());

        getOrCreateTagBuilder(AllTags.REFORGE_CORE_TAGS.get(2))
                .setReplace(true)
                .add(MiscItems.MIRACLE.ingot());
    }
}
