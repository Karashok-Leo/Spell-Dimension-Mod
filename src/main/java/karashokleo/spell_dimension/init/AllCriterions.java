package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.advancement.MiningCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class AllCriterions
{
    public static final MiningCriterion MINING = new MiningCriterion(SpellDimension.modLoc("mining"));

    public static void register()
    {
        Criteria.register(MINING);
    }
}
