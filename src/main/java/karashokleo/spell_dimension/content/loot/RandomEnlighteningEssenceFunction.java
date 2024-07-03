package karashokleo.spell_dimension.content.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import karashokleo.spell_dimension.content.component.MageComponent;
import karashokleo.spell_dimension.init.AllConfigs;
import karashokleo.spell_dimension.config.ModifiersConfig;
import karashokleo.spell_dimension.init.AllLoots;
import karashokleo.spell_dimension.content.item.EnlighteningEssenceItem;
import karashokleo.spell_dimension.content.misc.AttrModifier;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.spell_engine.api.item.AttributeResolver;
import net.spell_power.api.SpellSchool;

public class RandomEnlighteningEssenceFunction extends ConditionalLootFunction
{
    public RandomEnlighteningEssenceFunction(LootCondition[] conditions)
    {
        super(conditions);
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context)
    {
        if (!(stack.getItem() instanceof EnlighteningEssenceItem el)) return stack;

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

        return random.nextFloat() < AllConfigs.loot.value.random_essence.generic_chance ?
                el.getStack(
                        new AttrModifier(
                                AttributeResolver.get(new Identifier(modifier.attributeId)),
                                UuidUtil.getSelfUuid(EntityAttributeModifier.Operation.valueOf(modifier.operation)),
                                modifier.amount,
                                EntityAttributeModifier.Operation.valueOf(modifier.operation)
                        )
                ) :
                el.getStack(
                        new AttrModifier(
                                school.attribute,
                                UuidUtil.getSelfUuid(EntityAttributeModifier.Operation.ADDITION),
                                1.0,
                                EntityAttributeModifier.Operation.ADDITION
                        )
                );
    }

    @Override
    public LootFunctionType getType()
    {
        return AllLoots.RANDOM_ENLIGHTENING_ESSENCE;
    }

    public static RandomEnlighteningEssenceFunction.Builder builder()
    {
        return new RandomEnlighteningEssenceFunction.Builder();
    }

    public static class Builder extends ConditionalLootFunction.Builder<RandomEnlighteningEssenceFunction.Builder>
    {
        public Builder()
        {
        }

        protected RandomEnlighteningEssenceFunction.Builder getThisBuilder()
        {
            return this;
        }

        public LootFunction build()
        {
            return new RandomEnlighteningEssenceFunction(this.getConditions());
        }
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<RandomEnlighteningEssenceFunction>
    {
        @Override
        public RandomEnlighteningEssenceFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions)
        {
            return new RandomEnlighteningEssenceFunction(conditions);
        }
    }
}
