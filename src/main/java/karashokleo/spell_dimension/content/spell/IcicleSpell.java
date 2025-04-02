package karashokleo.spell_dimension.content.spell;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;
import java.util.Optional;

public class IcicleSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!TrinketCompat.hasItemInTrinket(caster, AllItems.GLACIAL_NUCLEAR_ERA))
            return;

        for (Entity target : targets)
        {
            if (!(target instanceof LivingEntity living))
                continue;
            if (!living.isAttackable()) continue;
            Optional<Nucleus> nucleus = Buff.get(living, Nucleus.TYPE);
            if (nucleus.isPresent()) continue;
            Buff.apply(living, Nucleus.TYPE, new Nucleus(), caster);
        }
    }
}
