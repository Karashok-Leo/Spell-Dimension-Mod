package net.karashokleo.spelldimension.util;

import net.karashokleo.spelldimension.item.mod_item.EnchantedEssenceItem;
import net.karashokleo.spelldimension.item.mod_item.EnlighteningEssenceItem;
import net.karashokleo.spelldimension.item.mod_item.IMageItem;
import net.karashokleo.spelldimension.misc.AttrModifier;
import net.karashokleo.spelldimension.misc.EnchantedModifier;
import net.minecraft.item.ItemStack;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

public class ColorUtil
{
    public static int getItemColor(ItemStack stack)
    {
        MagicSchool school = null;
        if (stack.getItem() instanceof IMageItem item)
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
        return getSchoolColor(school);
    }

    public static int getSchoolColor(@Nullable MagicSchool school)
    {
        return school == null ? 0xFFFFFF : school.color();
    }
}
