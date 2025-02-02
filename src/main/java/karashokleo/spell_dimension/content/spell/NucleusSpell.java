package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.init.AllSpells;
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
        if (!spellInfo.id().equals(AllSpells.NUCLEUS)) return;
        Optional<Entity> target = targets.stream().findFirst();
        if (target.isPresent() &&
            (target.get() instanceof LivingEntity livingEntity) &&
            livingEntity.isAttackable())
            Buff.apply(livingEntity, Nucleus.TYPE, new Nucleus(), caster);
    }
}
