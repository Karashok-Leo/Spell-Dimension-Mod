package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class DashResistanceEnchantment extends UnobtainableEnchantment
{
    public DashResistanceEnchantment()
    {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_LEGS, new EquipmentSlot[]{EquipmentSlot.LEGS});
    }

    public void onDash(PlayerEntity player, Vec3d vec3d)
    {
        int level = EnchantmentHelper.getEquipmentLevel(this, player);
        level = Math.min(level, getMaxLevel());
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, level - 1));
    }

    @Override
    public int getMaxLevel()
    {
        return 4;
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.AQUA;
    }
}
