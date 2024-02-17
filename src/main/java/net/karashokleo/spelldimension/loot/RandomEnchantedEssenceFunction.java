package net.karashokleo.spelldimension.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.item.ExtraModifier;
import net.karashokleo.spelldimension.item.mod_item.EnchantedEssenceItem;
import net.karashokleo.spelldimension.misc.Mage;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.MagicSchool;

public class RandomEnchantedEssenceFunction extends ConditionalLootFunction
{
    final LootNumberProvider threshold;

    protected RandomEnchantedEssenceFunction(LootCondition[] conditions, LootNumberProvider threshold)
    {
        super(conditions);
        this.threshold = threshold;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context)
    {
        if (stack.getItem() instanceof EnchantedEssenceItem)
        {
            Random random = context.getRandom();
            MagicSchool school = AllLoot.randomSchool(random);
            return AllItems.ENCHANTED_ESSENCE.getStack(
                    new Mage(AllLoot.randomGrade(random, 3), school, null),
                    new ExtraModifier(threshold.nextInt(context), AllLoot.randomSlot(random), school.attributeId())
            );
        } else return stack;
    }

    @Override
    public LootFunctionType getType()
    {
        return AllLoot.RANDOM_ENCHANTED_ESSENCE;
    }

    public static RandomEnchantedEssenceFunction.Builder builder(LootNumberProvider threshold)
    {
        return new RandomEnchantedEssenceFunction.Builder(threshold);
    }

    public static class Builder extends ConditionalLootFunction.Builder<RandomEnchantedEssenceFunction.Builder>
    {
        private final LootNumberProvider threshold;

        public Builder(LootNumberProvider threshold)
        {
            this.threshold = threshold;
        }

        protected RandomEnchantedEssenceFunction.Builder getThisBuilder()
        {
            return this;
        }

        public LootFunction build()
        {
            return new RandomEnchantedEssenceFunction(this.getConditions(), this.threshold);
        }
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<RandomEnchantedEssenceFunction>
    {
        @Override
        public RandomEnchantedEssenceFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions)
        {
            LootNumberProvider provider = JsonHelper.deserialize(json, "threshold", context, LootNumberProvider.class);
            return new RandomEnchantedEssenceFunction(conditions, provider);
        }
    }
}
