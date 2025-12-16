package karashokleo.spell_dimension.content.item;

import com.google.common.collect.ImmutableMultimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.spell_engine.api.item.weapon.StaffItem;
import net.spell_engine.api.item.weapon.Weapon;
import net.spell_power.api.SpellSchool;

import java.util.UUID;

public class ConfiguredStaffItem extends StaffItem
{
    private static final UUID MISC_UUID = UUID.fromString("c102cb57-a7b8-4a98-8c6e-2cd7b70b74c1");

    public ConfiguredStaffItem(
        ToolMaterials material,
        Item repairItem,
        float attackDamage,
        float attackSpeed,
        SpellSchool school,
        float spellPower
    )
    {
        super(
            Weapon.CustomMaterial.matching(
                material,
                () -> Ingredient.ofItems(repairItem)
            ),
            new FabricItemSettings()
        );

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(school.attribute, new EntityAttributeModifier(MISC_UUID, "Weapon modifier", spellPower, EntityAttributeModifier.Operation.ADDITION));
        setAttributes(builder.build());
    }
}
