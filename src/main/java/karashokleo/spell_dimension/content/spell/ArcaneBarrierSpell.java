package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.block.ProtectiveCoverBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.spell_engine.api.spell.CustomSpellHandler;

public class ArcaneBarrierSpell
{
    public static final int RADIUS = 2;

    public static Boolean handle(CustomSpellHandler.Data data)
    {
        PlayerEntity caster = data.caster();
        ProtectiveCoverBlock.placeAsBarrier(caster.getWorld(), caster.getBlockPos(), RADIUS, 20 * 15);
        return true;
    }
}
