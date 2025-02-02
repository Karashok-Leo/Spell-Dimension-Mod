package karashokleo.spell_dimension.content.trait;

import karashokleo.l2hostility.content.trait.common.IntervalTrait;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.internals.casting.SpellCast;

import java.util.List;
import java.util.function.IntSupplier;

public class SpellTrait extends IntervalTrait
{
    private final Identifier spellId;

    public SpellTrait(Formatting format, IntSupplier interval, Identifier spellId)
    {
        super(format, interval);
        this.spellId = spellId;
    }

    @Override
    public void action(MobEntity mob, int level, Data data)
    {
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive())
        {
            World world = mob.getWorld();

            ImpactUtil.performSpell(world, mob, spellId, List.of(target), SpellCast.Action.RELEASE, 1.0F);

            super.action(mob, level, data);
        }
    }
}
