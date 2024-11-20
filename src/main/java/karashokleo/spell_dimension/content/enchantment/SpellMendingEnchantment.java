package karashokleo.spell_dimension.content.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SpellMendingEnchantment extends SpellImpactEnchantment
{
    public SpellMendingEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    protected boolean canAccept(Enchantment other)
    {
        return other != Enchantments.MENDING && super.canAccept(other);
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.LIGHT_PURPLE;
    }

    private void doRepair(int level, ItemStack stack)
    {
        int repair = (int) (stack.getMaxDamage() * level * 0.02);
        int toSet = Math.max(0, stack.getDamage() - repair);
        stack.setDamage(toSet);
    }

    @Override
    public void onSpellImpact(World world, LivingEntity caster, Context context, List<Entity> targets, SpellInfo spellInfo)
    {
        int level = context.totalLevel();
        context.stacks().forEach(stack -> doRepair(level, stack));
    }
}
