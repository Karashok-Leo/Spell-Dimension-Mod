package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.client.compat.LocationStack;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.stream.Stream;

public class LocationEntryDefinition implements EntryDefinition<LocationStack>
{
    private final EntryRenderer<LocationStack> renderer = new LocationEntryRenderer();
    private final EntrySerializer<LocationStack> serializer = new LocationEntrySerializer();

    @Override
    public Class<LocationStack> getValueType()
    {
        return LocationStack.class;
    }

    @Override
    public EntryType<LocationStack> getType()
    {
        return REICompat.LOCATION;
    }

    @Override
    public EntryRenderer<LocationStack> getRenderer()
    {
        return renderer;
    }

    @Override
    public Identifier getIdentifier(EntryStack<LocationStack> entry, LocationStack value)
    {
        return value.getId();
    }

    @Override
    public boolean isEmpty(EntryStack<LocationStack> entry, LocationStack value)
    {
        return false;
    }

    @Override
    public LocationStack copy(EntryStack<LocationStack> entry, LocationStack value)
    {
        return value.copy();
    }

    @Override
    public LocationStack normalize(EntryStack<LocationStack> entry, LocationStack value)
    {
        return value.copy();
    }

    @Override
    public LocationStack wildcard(EntryStack<LocationStack> entry, LocationStack value)
    {
        return value.copy();
    }

    @Override
    public long hash(EntryStack<LocationStack> entry, LocationStack value, ComparisonContext context)
    {
        return value.hashCode();
    }

    @Override
    public boolean equals(LocationStack o1, LocationStack o2, ComparisonContext context)
    {
        return o1.equals(o2);
    }

    @Override
    public EntrySerializer<LocationStack> getSerializer()
    {
        return serializer;
    }

    @Override
    public Text asFormattedText(EntryStack<LocationStack> entry, LocationStack value)
    {
        return value.getName();
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<LocationStack> entry, LocationStack value)
    {
        return Stream.empty();
    }
}
