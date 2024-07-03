package karashokleo.spell_dimension.util;

import karashokleo.spell_dimension.content.item.EnchantedEssenceItem;
import karashokleo.spell_dimension.content.item.EnlighteningEssenceItem;
import karashokleo.spell_dimension.content.item.IMageRequirement;
import karashokleo.spell_dimension.content.misc.AttrModifier;
import karashokleo.spell_dimension.content.misc.EnchantedModifier;
import net.minecraft.item.ItemStack;
import net.spell_power.api.SpellSchool;

public class ColorUtil
{
    public static int getItemColor(ItemStack stack)
    {
        SpellSchool school = null;
        if (stack.getItem() instanceof IMageRequirement item)
            school = item.getMage(stack).school();
        else if (stack.getItem() instanceof EnlighteningEssenceItem item)
        {
            AttrModifier modifier = item.getModifier(stack);
            if (modifier != null)
                return AttributeUtil.getColorCode(modifier.attribute);
        } else if (stack.getItem() instanceof EnchantedEssenceItem item)
        {
            EnchantedModifier modifier = item.getModifier(stack);
            if (modifier != null)
                return AttributeUtil.getColorCode(modifier.modifier.attribute);
        }
        return SchoolUtil.getSchoolColorOrWhite(school);
    }
}
