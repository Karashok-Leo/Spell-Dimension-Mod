package net.karashokleo.spelldimension.config.mod_config;

public class ModifiersConfig
{
    public AttributeModifier[] modifiers = {
            new AttributeModifier("minecraft:generic.armor", 1, "ADDITION", 0x888888),
            new AttributeModifier("minecraft:generic.armor_toughness", 1, "ADDITION", 0xbdeeff),
            new AttributeModifier("minecraft:generic.attack_speed", 0.05, "ADDITION", 0xffb06b),
            new AttributeModifier("minecraft:generic.knockback_resistance", 0.1, "ADDITION", 0xffce6b),
            new AttributeModifier("minecraft:generic.luck", 1, "ADDITION", 0xb8ff6b),
            new AttributeModifier("minecraft:generic.max_health", 1, "ADDITION", 0xff6b6b),
            new AttributeModifier("minecraft:generic.movement_speed", 0.02, "ADDITION", 0x6bbcff),
            new AttributeModifier("spell_power:haste", 0.01, "MULTIPLY_BASE", 0xdf94ff),
            new AttributeModifier("spell_power:critical_damage", 0.01, "MULTIPLY_BASE", 0xfaff94),
            new AttributeModifier("spell_power:critical_chance", 0.01, "MULTIPLY_BASE", 0x94ffda)
    };

    public static class AttributeModifier
    {
        public String attributeId;
        public double amount;
        public String operation;
        public int color;

        public AttributeModifier(String attributeId, double amount, String operation, int color)
        {
            this.attributeId = attributeId;
            this.amount = amount;
            this.operation = operation;
            this.color = color;
        }
    }
}
