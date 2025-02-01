package karashokleo.spell_dimension.content.enchantment;

import com.google.common.collect.Multimap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public abstract class SpellBladeAmplifyEnchantment extends Enchantment
{
    public static final UUID MODIFIER_ID = UUID.fromString("9ed774ce-6bd0-4216-9ca5-1ec2adf3b4ed");

    public SpellBladeAmplifyEnchantment()
    {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    protected boolean canAccept(Enchantment other)
    {
        return !(other instanceof SpellBladeAmplifyEnchantment)
               && super.canAccept(other);
    }

    public abstract void operateModifiers(ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> modifiers);
}
