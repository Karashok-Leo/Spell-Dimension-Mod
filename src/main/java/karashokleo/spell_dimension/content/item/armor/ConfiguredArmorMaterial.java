package karashokleo.spell_dimension.content.item.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.recipe.Ingredient;
import net.spell_engine.api.item.armor.Armor;
import net.spell_power.api.SpellSchool;
import net.wizards.item.WizardArmor;

import java.util.EnumMap;
import java.util.function.Supplier;

public class ConfiguredArmorMaterial extends Armor.CustomMaterial
{
    protected final EnumMap<ArmorItem.Type, Integer> armors;
    protected final SpellSchool school;
    protected final float spellPower;
    protected final float haste;

    public ConfiguredArmorMaterial(
        String name,
        int durabilityMultiplier,
        int enchantability,
        Supplier<Ingredient> repairIngredientSupplier,
        int helmetArmor,
        int chestplateArmor,
        int leggingsArmor,
        int bootsArmor,
        SpellSchool school,
        float spellPower,
        float haste
    )
    {
        super(name, durabilityMultiplier, enchantability, WizardArmor.equipSound, repairIngredientSupplier);
        this.armors = new EnumMap<>(ArmorItem.Type.class);
        this.armors.put(ArmorItem.Type.HELMET, helmetArmor);
        this.armors.put(ArmorItem.Type.CHESTPLATE, chestplateArmor);
        this.armors.put(ArmorItem.Type.LEGGINGS, leggingsArmor);
        this.armors.put(ArmorItem.Type.BOOTS, bootsArmor);
        this.school = school;
        this.spellPower = spellPower;
        this.haste = haste;
    }

    @Override
    public int getProtection(ArmorItem.Type type)
    {
        return this.armors.getOrDefault(type, 0);
    }
}
