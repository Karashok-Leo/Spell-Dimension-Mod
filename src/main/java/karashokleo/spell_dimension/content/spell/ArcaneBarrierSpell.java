package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.block.ProtectiveCoverBlock;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class ArcaneBarrierSpell
{
    public static final int RADIUS = 2;

    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!spellInfo.id().equals(AllSpells.ARCANE_BARRIER)) return;
        ProtectiveCoverBlock.placeAsCube(caster.getWorld(), caster.getBlockPos(), RADIUS, 20 * 15);
    }
}
