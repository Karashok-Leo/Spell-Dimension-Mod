package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public record REILocateDisplay(EntryIngredient input, Text spot, Text tooltip) implements Display
{
    public REILocateDisplay(Item item, RegistryKey<World> worldKey, RegistryKey<?> spotKey)
    {
        this(
                EntryIngredients.of(item),
                LocateSpellConfig.getSpotName(spotKey),
                Text.translatable(
                        "travelerstitles.%s.%s"
                                .formatted(
                                        worldKey.getValue().getNamespace(),
                                        worldKey.getValue().getPath()
                                )
                ).append(" - ").append(Text.of(spotKey.getValue().toString()))
        );
    }

    public REILocateDisplay(Item item, RegistryKey<World> worldKey, TagKey<?> spotKey)
    {
        this(
                EntryIngredients.of(item),
                LocateSpellConfig.getSpotName(spotKey),
                Text.translatable(
                        "travelerstitles.%s.%s"
                                .formatted(
                                        worldKey.getValue().getNamespace(),
                                        worldKey.getValue().getPath()
                                )
                ).append(" - ").append(Text.of(spotKey.id().toString()))
        );
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return List.of(input);
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return List.of();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return REICompat.LOCATE;
    }
}
