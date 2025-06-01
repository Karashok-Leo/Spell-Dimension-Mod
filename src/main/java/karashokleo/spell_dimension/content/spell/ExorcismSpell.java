package karashokleo.spell_dimension.content.spell;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.init.LHTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;
import java.util.Optional;

public class ExorcismSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        Optional<Entity> target = targets.stream().findFirst();
        if (target.isEmpty()) return;
        if (!(target.get() instanceof LivingEntity livingEntity)) return;
        if (!livingEntity.isAttackable()) return;
        if (livingEntity.getType().isIn(LHTags.SEMIBOSS)) return;
        Optional<MobDifficulty> optional = MobDifficulty.get(livingEntity);
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        int level = difficulty.getLevel();
        if (level <= 0) return;
        caster.damage(livingEntity.getDamageSources().create(DamageTypes.INDIRECT_MAGIC, livingEntity), level);
        difficulty.reInit(level / 2, false);
    }
}
