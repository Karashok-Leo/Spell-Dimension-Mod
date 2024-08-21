package karashokleo.spell_dimension.content.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public abstract class SpellImpactEnchantment extends Enchantment
{
    public SpellImpactEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    public abstract void onSpellImpact(World world, LivingEntity caster, int totalLevel, List<Entity> targets, SpellInfo spellInfo);
}
