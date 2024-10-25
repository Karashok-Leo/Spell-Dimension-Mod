package karashokleo.spell_dimension.content.spell;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.spell_engine.api.spell.CustomSpellHandler;

import java.util.Optional;

public class ExorcismSpell
{
    public static Boolean handle(CustomSpellHandler.Data data)
    {
        Optional<Entity> target = data.targets().stream().findFirst();
        if (target.isPresent() && (target.get() instanceof LivingEntity livingEntity) && livingEntity.isAttackable())
        {
            Optional<MobDifficulty> optional = MobDifficulty.get(livingEntity);
            if (optional.isPresent())
            {
                MobDifficulty difficulty = optional.get();
                int level = difficulty.getLevel();
                if (level > 0)
                {
                    data.caster().damage(livingEntity.getDamageSources().create(DamageTypes.INDIRECT_MAGIC, livingEntity), level);
                    difficulty.reInit(level / 2, false);
                    return true;
                }
            }
        }
        return false;
    }
}
