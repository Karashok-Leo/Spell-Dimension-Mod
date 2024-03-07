package net.karashokleo.spelldimension.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.config.mod_config.ModifiersConfig;
import net.karashokleo.spelldimension.item.mod_item.EnlighteningEssenceItem;
import net.karashokleo.spelldimension.misc.AttrModifier;
import net.karashokleo.spelldimension.misc.EnchantedModifier;
import net.karashokleo.spelldimension.item.mod_item.EnchantedEssenceItem;
import net.karashokleo.spelldimension.util.EnumUtil;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.MagicSchool;

public class RandomEssenceFunction extends ConditionalLootFunction
{
    final LootNumberProvider threshold;

    protected RandomEssenceFunction(LootCondition[] conditions, LootNumberProvider threshold)
    {
        super(conditions);
        this.threshold = threshold;
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context)
    {
        PlayerEntity player = null;
        if (context.get(LootContextParameters.THIS_ENTITY) instanceof PlayerEntity thisEntity)
            player = thisEntity;
        if (context.get(LootContextParameters.KILLER_ENTITY) instanceof PlayerEntity killerEntity)
            player = killerEntity;
        if (player == null) return stack;
        Random random = context.getRandom();
        MagicSchool school = MageComponent.get(player).school();
        boolean bl = school == null || random.nextFloat() < AllConfigs.loot.value.function.generic_chance;
        ModifiersConfig.AttributeModifier[] modifiers = AllConfigs.modifiers.value.modifiers;
        ModifiersConfig.AttributeModifier modifier = modifiers[random.nextInt(modifiers.length)];
        Item item = stack.getItem();
        if (item instanceof EnchantedEssenceItem ec)
            return bl ?
                    ec.getStack(new EnchantedModifier(threshold.nextInt(context), EnumUtil.randomSlot(random), new Identifier(modifier.attributeId), modifier.amount, EntityAttributeModifier.Operation.valueOf(modifier.operation))) :
                    ec.getStack(new EnchantedModifier(threshold.nextInt(context), EnumUtil.randomSlot(random), school));
        else if (item instanceof EnlighteningEssenceItem el)
            return bl ?
                    el.getStack(AttrModifier.forSelf(new Identifier(modifier.attributeId), modifier.amount, EntityAttributeModifier.Operation.valueOf(modifier.operation))) :
                    el.getStack(AttrModifier.forSelf(MagicSchool.FIRE.attributeId()));
        else return stack;
    }

    @Override
    public LootFunctionType getType()
    {
        return AllLoots.RANDOM_ENCHANTED_ESSENCE;
    }

    public static Builder builder(LootNumberProvider threshold)
    {
        return new Builder(threshold);
    }

    public static class Builder extends ConditionalLootFunction.Builder<Builder>
    {
        private final LootNumberProvider threshold;

        public Builder(LootNumberProvider threshold)
        {
            this.threshold = threshold;
        }

        protected Builder getThisBuilder()
        {
            return this;
        }

        public LootFunction build()
        {
            return new RandomEssenceFunction(this.getConditions(), this.threshold);
        }
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<RandomEssenceFunction>
    {
        @Override
        public RandomEssenceFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions)
        {
            LootNumberProvider provider = JsonHelper.deserialize(json, "threshold", context, LootNumberProvider.class);
            return new RandomEssenceFunction(conditions, provider);
        }
    }
}
