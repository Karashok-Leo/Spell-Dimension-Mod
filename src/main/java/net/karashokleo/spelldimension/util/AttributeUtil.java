package net.karashokleo.spelldimension.util;

import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.config.mod_config.ModifiersConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AttributeUtil
{
    public static Map<String, Integer> colorMap = new HashMap<>();

    public static void initColorMap()
    {
        for (ModifiersConfig.AttributeModifier modifier : AllConfigs.modifiers.value.modifiers)
            AttributeUtil.colorMap.put(modifier.attributeId, modifier.color);
    }

    public static int getColorCode(EntityAttribute attribute)
    {
        MagicSchool school = getSchool(attribute, null);
        if (school != null) return school.color();
        Integer color = colorMap.get(getAttributeId(attribute));
        return color == null ? 0xffffff : color;
    }

    @Nullable
    public static MagicSchool getSchool(EntityAttribute attribute, @Nullable MagicSchool defaultSchool)
    {
        if (attribute != null)
            for (MagicSchool school : MagicSchool.values())
                if (school.attributeId().toString().equals(getAttributeId(attribute)))
                    defaultSchool = school;
        return defaultSchool;
    }

    public static String getAttributeId(EntityAttribute attribute)
    {
        Identifier id = Registries.ATTRIBUTE.getId(attribute);
        return id == null ? "" : id.toString();
    }

    public static void addTooltip(List<Text> tooltip, EntityAttribute attribute, double amount, EntityAttributeModifier.Operation operation)
    {
        double e = operation == EntityAttributeModifier.Operation.MULTIPLY_BASE || operation == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? amount * 100.0 : (attribute.equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? amount * 10.0 : amount);
        if (amount > 0.0)
        {
            tooltip.add(Text.translatable("attribute.modifier.plus." + operation.getId(), ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(attribute.getTranslationKey())).formatted(Formatting.BLUE));
        } else if (amount < 0.0)
            tooltip.add(Text.translatable("attribute.modifier.take." + operation.getId(), ItemStack.MODIFIER_FORMAT.format(e * -1.0), Text.translatable(attribute.getTranslationKey())).formatted(Formatting.RED));
    }

    public static void addModifier(LivingEntity entity, EntityAttribute attribute, UUID uuid, String name, double value, EntityAttributeModifier.Operation operation)
    {
        EntityAttributeInstance instance = entity.getAttributeInstance(attribute);
        EntityAttributeModifier modifier = new EntityAttributeModifier(uuid, name, value, operation);
        if (instance == null || instance.hasModifier(modifier)) return;
        instance.addPersistentModifier(modifier);
    }

    public static void removeModifier(LivingEntity entity, EntityAttribute attribute, UUID uuid)
    {
        EntityAttributeInstance instance = entity.getAttributeInstance(attribute);
        if (instance != null)
            instance.removeModifier(uuid);
    }
}
