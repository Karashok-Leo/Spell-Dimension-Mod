package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.block.ProtectiveCoverBlock;
import net.spell_engine.api.spell.CustomSpellHandler;

public class ArcaneBarrierSpell
{
    public static Boolean handle(CustomSpellHandler.Data data)
    {
        float distance = data.impactContext().distance();
        data.targets().forEach(entity ->
        {
            ProtectiveCoverBlock.placeAsBarrier(entity.getWorld(), entity.getBlockPos(), (int) distance, 20 * 15);
        });
        return true;
    }
}
