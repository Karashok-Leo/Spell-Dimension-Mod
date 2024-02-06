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
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.MagicSchool;

public class RandomEnchantedEssenceFunction extends ConditionalLootFunction
{
    final LootNumberProvider countRange;

    protected RandomEnchantedEssenceFunction(LootCondition[] conditions, LootNumberProvider countRange)
    {
        super(conditions);
        this.countRange = countRange;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context)
    {
        if (stack.getItem() instanceof EnchantedEssenceItem)
        {
            Random random = context.getRandom();
            MagicSchool school = AllLoot.randomSchool(random);
            return AllItems.ENCHANTED_ESSENCE.getStack(
                    new Mage(AllLoot.randomGrade(random), school, null),
                    new ExtraModifier(30 + random.nextInt((int) context.getLuck()), AllLoot.randomSlot(random), school.attributeId())
            );
        } else return stack;
    }

    @Override
    public LootFunctionType getType()
    {
        return AllLoot.RANDOM_ENCHANTED_ESSENCE;
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<RandomEnchantedEssenceFunction>
    {
        @Override
        public RandomEnchantedEssenceFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions)
        {
            LootNumberProvider provider = JsonHelper.deserialize(json, "count", context, LootNumberProvider.class);
            return new RandomEnchantedEssenceFunction(conditions, provider);
        }
    }
}
