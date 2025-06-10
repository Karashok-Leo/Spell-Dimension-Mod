package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.client.compat.LocationStack;
import karashokleo.spell_dimension.content.recipe.locate.LocationType;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class LocationEntrySerializer implements EntrySerializer<LocationStack>
{
    @Override
    public boolean supportSaving()
    {
        return true;
    }

    @Override
    public boolean supportReading()
    {
        return true;
    }

    @Override
    public NbtCompound save(EntryStack<LocationStack> entry, LocationStack value)
    {
        NbtCompound tag = new NbtCompound();
        tag.putString("type", value.getType().toString());
        tag.putString("id", value.getId().toString());
        tag.putString("world", value.getWorld().toString());
        return tag;
    }

    @Override
    public LocationStack read(NbtCompound tag)
    {
        LocationType type = LocationType.valueOf(tag.getString("type"));
        Identifier id = new Identifier(tag.getString("id"));
        Identifier world = new Identifier(tag.getString("world"));
        return new LocationStack(type, id, world);
    }
}
