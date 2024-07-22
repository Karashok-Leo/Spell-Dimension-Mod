package karashokleo.spell_dimension.content.item.essence;

import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.spell_power.api.SpellSchool;

public class BaseEssenceItem extends Item implements ColorProvider
{
    private final SpellSchool school;
    private final int grade;

    public BaseEssenceItem(SpellSchool school, int grade)
    {
        super(new FabricItemSettings());
        this.school = school;
        this.grade = grade;
    }

    @Override
    public Text getName()
    {
        return SDTexts.ESSENCE_BASE.get();
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return SDTexts.getGradeText(this.grade)
                .append(SDTexts.getSchoolText(this.school))
                .append(SDTexts.ESSENCE_NAME.get())
                .setStyle(Style.EMPTY.withColor(this.getColor(stack)));
    }

    @Override
    public int getColor(ItemStack stack)
    {
        return this.school.color;
    }
}
