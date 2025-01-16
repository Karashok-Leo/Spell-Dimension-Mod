package karashokleo.spell_dimension.mixin;

import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.common.items.weapon.TerribleSwordItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TerribleSwordItem.class)
public abstract class TerribleSwordItemMixin extends SwordItem
{
    private TerribleSwordItemMixin(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings)
    {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Inject(
            method = "getAttributeModifiers",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_getAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir)
    {
        cir.setReturnValue(super.getAttributeModifiers(slot));
    }
}
