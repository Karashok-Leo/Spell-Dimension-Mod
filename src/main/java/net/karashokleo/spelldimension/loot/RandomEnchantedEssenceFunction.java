package net.karashokleo.spelldimension.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.config.mod_config.ModifiersConfig;
import net.karashokleo.spelldimension.misc.EnchantedModifier;
import net.karashokleo.spelldimension.item.mod_item.EnchantedEssenceItem;
import net.karashokleo.spelldimension.util.DamageUtil;
import net.karashokleo.spelldimension.util.EnumUtil;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
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
        if (!(stack.getItem() instanceof EnchantedEssenceItem ec)) return stack;

        // Determine Player
        PlayerEntity player = null;
        if (context.get(LootContextParameters.THIS_ENTITY) instanceof PlayerEntity thisEntity)
            player = thisEntity;
        if (context.get(LootContextParameters.KILLER_ENTITY) instanceof PlayerEntity killerEntity)
            player = killerEntity;

        if (player == null) return stack;
        Random random = context.getRandom();

        // Determine Modifier
        ModifiersConfig.AttributeModifier[] modifiers = AllConfigs.modifiers.value.modifiers;
        ModifiersConfig.AttributeModifier modifier = modifiers[random.nextInt(modifiers.length)];

        // Determine School
        MagicSchool school = MageComponent.get(player).school();
        DamageSource source = context.get(LootContextParameters.DAMAGE_SOURCE);
        if (school == null && source != null) school = DamageUtil.getDamageSchool(source);
        if (school == null) school = EnumUtil.randomSchool(random);

        return random.nextFloat() < AllConfigs.loot.value.random_essence.generic_chance ?
                ec.getStack(new EnchantedModifier(threshold.nextInt(context), EnumUtil.randomSlot(random), new Identifier(modifier.attributeId), modifier.amount, EntityAttributeModifier.Operation.valueOf(modifier.operation))) :
                ec.getStack(new EnchantedModifier(threshold.nextInt(context), EnumUtil.randomSlot(random), school));
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
