package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

public abstract class SpellImpactEnchantment extends UnobtainableEnchantment
{
    public SpellImpactEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    public abstract void onSpellImpact(World world, LivingEntity caster, Context context, List<Entity> targets, SpellInfo spellInfo);

    public record Context(MutableInt level, ArrayList<ItemStack> stacks)
    {
        public int totalLevel()
        {
            return level.getValue();
        }
    }
}
