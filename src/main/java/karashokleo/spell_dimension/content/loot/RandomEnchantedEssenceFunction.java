package karashokleo.spell_dimension.content.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import karashokleo.spell_dimension.content.component.MageComponent;
import karashokleo.spell_dimension.content.item.EnchantedEssenceItem;
import karashokleo.spell_dimension.init.AllConfigs;
import karashokleo.spell_dimension.config.ModifiersConfig;
import karashokleo.spell_dimension.init.AllLoots;
import karashokleo.spell_dimension.content.misc.AttrModifier;
import karashokleo.spell_dimension.content.misc.EnchantedModifier;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.random.Random;
import net.spell_engine.api.item.AttributeResolver;
import net.spell_power.api.SpellSchool;

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

        Random random = context.getRandom();

        // Determine Player
        PlayerEntity player = AllLoots.getContextPlayer(context);

        // Determine Modifier
        ModifiersConfig.AttributeModifier[] modifiers = AllConfigs.modifiers.value.modifiers;
        ModifiersConfig.AttributeModifier modifier = modifiers[random.nextInt(modifiers.length)];

        // Determine School
        SpellSchool school = null;
        if (player != null) school = MageComponent.get(player).school();
        if (school == null) school = RandomUtil.randomSchool(random);

        // Determine Slot
        EquipmentSlot slot = RandomUtil.randomSlot(random);

        return random.nextFloat() < AllConfigs.loot.value.random_essence.generic_chance ?
                ec.getStack(
                        new EnchantedModifier(
                                threshold.nextInt(context),
                                slot,
                                new AttrModifier(
                                        AttributeResolver.get(new Identifier(modifier.attributeId)),
                                        UuidUtil.getEquipUuid(slot, EntityAttributeModifier.Operation.ADDITION),
                                        modifier.amount,
                                        EntityAttributeModifier.Operation.valueOf(modifier.operation)
                                )
                        )
                ) :
                ec.getStack(
                        new EnchantedModifier(
                                threshold.nextInt(context),
                                slot,

                                new AttrModifier(
                                        school.attribute,
                                        UuidUtil.getEquipUuid(slot, EntityAttributeModifier.Operation.ADDITION),
                                        1.0,
                                        EntityAttributeModifier.Operation.ADDITION
                                )
                        )
                );
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
