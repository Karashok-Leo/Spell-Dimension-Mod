package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;
import java.util.Optional;

public class NucleusSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        Optional<Entity> target = targets.stream().findFirst();
        if (target.isEmpty())
        {
            return;
        }
        LivingEntity living = ImpactUtil.castToLiving(target.get());
        if (living == null)
        {
            return;
        }
        if (!living.isAttackable())
        {
            return;
        }
        Optional<Nucleus> nucleus = Buff.get(living, Nucleus.TYPE);
        if (nucleus.isPresent())
        {
            return;
        }
        Buff.apply(living, Nucleus.TYPE, new Nucleus(), caster);
    }
}
