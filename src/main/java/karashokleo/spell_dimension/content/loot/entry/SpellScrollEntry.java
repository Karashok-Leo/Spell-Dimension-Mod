package karashokleo.spell_dimension.content.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllLoots;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.function.Consumer;

public class SpellScrollEntry extends LeafEntry
{
    private final Identifier spell;

    protected SpellScrollEntry(int weight, int quality, LootCondition[] conditions, LootFunction[] functions, Identifier spell)
    {
        super(weight, quality, conditions, functions);
        this.spell = spell;
    }

    @Override
    protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context)
    {
        lootConsumer.accept(AllItems.SPELL_SCROLL.getStack(spell));
    }

    public static LeafEntry.Builder<?> builder(Identifier spell)
    {
        return builder((weight, quality, conditions, functions) -> new SpellScrollEntry(weight, quality, conditions, functions, spell));
    }

    @Override
    public LootPoolEntryType getType()
    {
        return AllLoots.SPELL_SCROLL_ENTRY;
    }

    public static class Serializer extends LeafEntry.Serializer<SpellScrollEntry>
    {
        private static final String KEY = "spell";

        @Override
        public void addEntryFields(JsonObject jsonObject, SpellScrollEntry entry, JsonSerializationContext jsonSerializationContext)
        {
            super.addEntryFields(jsonObject, entry, jsonSerializationContext);
            jsonObject.addProperty(KEY, entry.spell.toString());
        }

        @Override
        protected SpellScrollEntry fromJson(JsonObject entryJson, JsonDeserializationContext context, int weight, int quality, LootCondition[] conditions, LootFunction[] functions)
        {
            return new SpellScrollEntry(weight, quality, conditions, functions, new Identifier(JsonHelper.getString(entryJson, KEY)));
        }
    }
}
