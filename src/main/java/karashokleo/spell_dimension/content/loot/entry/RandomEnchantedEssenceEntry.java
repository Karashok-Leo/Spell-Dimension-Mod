package karashokleo.spell_dimension.content.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.item.logic.EnlighteningModifier;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllLoots;
import karashokleo.spell_dimension.util.LootContextUtil;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;

import java.util.function.Consumer;

public class RandomEnchantedEssenceEntry extends LeafEntry
{
    private final LootNumberProvider threshold;

    protected RandomEnchantedEssenceEntry(LootNumberProvider threshold, int weight, int quality, LootCondition[] conditions, LootFunction[] functions)
    {
        super(weight, quality, conditions, functions);
        this.threshold = threshold;
    }

    @Override
    protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context)
    {
        Random random = context.getRandom();

        // Determine Slot
        EquipmentSlot slot = RandomUtil.randomSlot(random);

        // Determine Modifier
        AttributeModifier modifier = LootContextUtil.getContextModifier(random, context);

        lootConsumer.accept(AllItems.ENCHANTED_ESSENCE.getStack(
                new EnchantedModifier(
                        threshold.nextInt(context),
                        slot,
                        new EnlighteningModifier(
                                modifier.attribute(),
                                UuidUtil.getEquipmentUuid(slot, modifier.operation()),
                                modifier.amount(),
                                modifier.operation()
                        )
                )
        ));
    }

    public static LeafEntry.Builder<?> builder(LootNumberProvider threshold)
    {
        return builder((weight, quality, conditions, functions) -> new RandomEnchantedEssenceEntry(threshold, weight, quality, conditions, functions));
    }

    @Override
    public LootPoolEntryType getType()
    {
        return AllLoots.RANDOM_ENCHANTED_ESSENCE_ENTRY;
    }

    public static class Serializer extends LeafEntry.Serializer<RandomEnchantedEssenceEntry>
    {
        @Override
        public void addEntryFields(JsonObject jsonObject, RandomEnchantedEssenceEntry entry, JsonSerializationContext jsonSerializationContext)
        {
            super.addEntryFields(jsonObject, entry, jsonSerializationContext);
            JsonElement jsonElement = jsonSerializationContext.serialize(entry.threshold);
            jsonObject.add("threshold", jsonElement);
        }

        @Override
        protected RandomEnchantedEssenceEntry fromJson(JsonObject entryJson, JsonDeserializationContext context, int weight, int quality, LootCondition[] conditions, LootFunction[] functions)
        {
            LootNumberProvider threshold = JsonHelper.deserialize(entryJson, "threshold", context, LootNumberProvider.class);
            return new RandomEnchantedEssenceEntry(threshold, weight, quality, conditions, functions);
        }
    }
}
