package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.advancement.DroppedItemsCriterion;
import karashokleo.spell_dimension.content.advancement.MinedOresCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class AllCriterions
{
    public static final MinedOresCriterion MINED_ORES = new MinedOresCriterion(SpellDimension.modLoc("mined_ores"));
    public static final DroppedItemsCriterion DROPPED_ITEMS = new DroppedItemsCriterion(SpellDimension.modLoc("dropped_items"));

    public static void register()
    {
        Criteria.register(MINED_ORES);
        Criteria.register(DROPPED_ITEMS);
    }
}
