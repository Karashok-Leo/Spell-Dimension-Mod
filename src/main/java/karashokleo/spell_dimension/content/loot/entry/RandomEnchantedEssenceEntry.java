package karashokleo.spell_dimension.content.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.item.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.item.logic.EnlighteningModifier;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllLoots;
import karashokleo.spell_dimension.util.LootContextUtil;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.math.random.Random;

import java.util.function.Consumer;

public class RandomEnchantedEssenceEntry extends LeafEntry
{
    protected RandomEnchantedEssenceEntry(int weight, int quality, LootCondition[] conditions, LootFunction[] functions)
    {
        super(weight, quality, conditions, functions);
    }

    @Override
    protected void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context)
    {
        Random random = context.getRandom();

        // Determine Slot
        EquipmentSlot slot = RandomUtil.randomSlot(random);

        // Determine Modifier
        AttributeModifier modifier = LootContextUtil.getContextModifier(random, context);

        // Determine Threshold
        PlayerEntity player = LootContextUtil.getContextPlayer(context);
        int maxThreshold = 10;
        if (player != null)
            maxThreshold += PlayerDifficulty.get(player).getLevel().level / 4;

        lootConsumer.accept(AllItems.ENCHANTED_ESSENCE.getStack(
                new EnchantedModifier(
                        context.getRandom().nextInt(maxThreshold),
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

    public static LeafEntry.Builder<?> builder()
    {
        return builder(RandomEnchantedEssenceEntry::new);
    }

    @Override
    public LootPoolEntryType getType()
    {
        return AllLoots.RANDOM_ENCHANTED_ESSENCE_ENTRY;
    }

    public static class Serializer extends LeafEntry.Serializer<RandomEnchantedEssenceEntry>
    {
        @Override
        protected RandomEnchantedEssenceEntry fromJson(JsonObject entryJson, JsonDeserializationContext context, int weight, int quality, LootCondition[] conditions, LootFunction[] functions)
        {
            return new RandomEnchantedEssenceEntry(weight, quality, conditions, functions);
        }
    }
}
