package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.content.component.MageComponent;
import karashokleo.spell_dimension.content.misc.Mage;
import karashokleo.spell_dimension.data.LangData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public interface IMageRequirement extends IMageItem
{
    default boolean meetRequirement(ItemStack stack, LivingEntity entity)
    {
        if (entity instanceof PlayerEntity player)
        {
            Mage requirement = getMage(stack);
            Mage playerMage = MageComponent.get(player);
            return requirement.grade() <= playerMage.grade() &&
                    (requirement.school() == playerMage.school() || requirement.school() == null) &&
                    (requirement.major() == playerMage.major() || requirement.major() == null);
        }
        return false;
    }

    default void addRequirementTooltip(ItemStack stack, List<Text> tooltip)
    {
        Mage mage = getMage(stack);
        tooltip.add(Text.translatable(LangData.TOOLTIP_REQUIRE).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable(LangData.TOOLTIP_REQUIRE_GRADE, mage.grade()));
        if (mage.school() != null)
            tooltip.add(Text.translatable(LangData.TOOLTIP_REQUIRE_SCHOOL, mage.school()));
        if (mage.major() != null)
            tooltip.add(Text.translatable(LangData.TOOLTIP_REQUIRE_MAJOR, mage.major()));
    }
}
