package karashokleo.spell_dimension.content.item.armor;

import com.google.common.collect.ImmutableMultimap;
import karashokleo.spell_dimension.client.render.CustomRobeRenderer;
import mod.azure.azurelibarmor.animatable.client.RenderProvider;
import mod.azure.azurelibarmor.renderer.GeoArmorRenderer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.spell_power.api.SpellPowerMechanics;
import net.wizards.item.WizardArmor;

import java.util.EnumMap;
import java.util.UUID;
import java.util.function.Consumer;

public class ConfiguredArmorItem extends WizardArmor
{
    private static final EnumMap<Type, UUID> MODIFIERS = Util.make(new EnumMap<>(Type.class), (uuidMap) ->
    {
        uuidMap.put(Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        uuidMap.put(Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        uuidMap.put(Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        uuidMap.put(Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

    public ConfiguredArmorItem(
        ConfiguredArmorMaterial material,
        Type type
    )
    {
        super(material, type, new FabricItemSettings());

        UUID uuid = MODIFIERS.get(type);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Armor modifier", material.getProtection(type), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uuid, "Armor modifier", material.getToughness(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uuid, "Armor modifier", material.getKnockbackResistance(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(material.school.attribute, new EntityAttributeModifier(uuid, "Armor modifier", material.spellPower, EntityAttributeModifier.Operation.ADDITION));
        builder.put(SpellPowerMechanics.HASTE.attribute, new EntityAttributeModifier(uuid, "Armor modifier", material.haste, EntityAttributeModifier.Operation.MULTIPLY_BASE));

        setAttributes(builder.build());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void createRenderer(Consumer<Object> consumer)
    {
        consumer.accept(new RenderProvider()
        {
            private GeoArmorRenderer<?> renderer;

            @Override
            public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original)
            {
                if (this.renderer == null)
                {
                    this.renderer = new CustomRobeRenderer();
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
