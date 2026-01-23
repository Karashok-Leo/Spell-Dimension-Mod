package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SoulBeamSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        targets.removeIf(
            entity -> RelationUtil.isAlly(caster, entity) &&
                !SoulControl.isSoulMinion(caster, entity)
        );
    }
}
