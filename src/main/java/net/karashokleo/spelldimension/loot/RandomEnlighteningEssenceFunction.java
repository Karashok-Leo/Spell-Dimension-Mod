package net.karashokleo.spelldimension.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.config.mod_config.ModifiersConfig;
import net.karashokleo.spelldimension.item.mod_item.EnlighteningEssenceItem;
import net.karashokleo.spelldimension.misc.AttrModifier;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.MagicSchool;

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
                el.getStack(AttrModifier.forSelf(new Identifier(modifier.attributeId), modifier.amount, EntityAttributeModifier.Operation.valueOf(modifier.operation))) :
                el.getStack(AttrModifier.forSelf(school.attributeId()));
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
