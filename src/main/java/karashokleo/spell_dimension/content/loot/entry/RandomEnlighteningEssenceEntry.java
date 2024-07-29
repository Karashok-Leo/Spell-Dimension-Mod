package karashokleo.spell_dimension.content.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.item.logic.EnlighteningModifier;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllLoots;
import karashokleo.spell_dimension.util.LootContextUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.SpellSchool;

import java.util.function.Consumer;

public class RandomEnlighteningEssenceEntry extends LeafEntry
{
    protected RandomEnlighteningEssenceEntry(int weight, int quality, LootCondition[] conditions, LootFunction[] functions)
    {
        super(weight, quality, conditions, functions);
    }

    @Override
    protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context)
    {
        Random random = context.getRandom();

        // Determine School
        SpellSchool school = LootContextUtil.getContextSchool(context);

        // Determine Modifier
        AttributeModifier modifier = AttributeModifier.getRandom(random, school);

        lootConsumer.accept(AllItems.ENLIGHTENING_ESSENCE.getStack(
                new EnlighteningModifier(
                        modifier.attribute(),
                        UuidUtil.getSelfUuid(modifier.operation()),
                        modifier.amount(),
                        modifier.operation()
                )
        ));
    }

    @Override
    public LootPoolEntryType getType()
    {
        return AllLoots.RANDOM_ENLIGHTENING_ESSENCE_ENTRY;
    }

    public static LeafEntry.Builder<?> builder()
    {
        return builder(RandomEnlighteningEssenceEntry::new);
    }

    public static class Serializer extends LeafEntry.Serializer<RandomEnlighteningEssenceEntry>
    {
        @Override
        protected RandomEnlighteningEssenceEntry fromJson(JsonObject entryJson, JsonDeserializationContext context, int weight, int quality, LootCondition[] conditions, LootFunction[] functions)
        {
            return new RandomEnlighteningEssenceEntry(weight, quality, conditions, functions);
        }
    }
}
