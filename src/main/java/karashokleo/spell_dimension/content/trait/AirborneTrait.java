package karashokleo.spell_dimension.content.trait;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

import java.util.function.IntUnaryOperator;

public class AirborneTrait extends CooldownTrait
{
    private final IntUnaryOperator strength;

    public AirborneTrait()
    {
        super(() -> 0xff9700, lv -> 80);
        this.strength = lv -> lv * 4;
    }

    @Override
    public void onHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        this.trigger(level, entity, event.getEntity());
    }

    @Override
    public void action(int level, Data data, MobEntity mob, LivingEntity target)
    {
        if (ReflectTrinket.canReflect(target, this)) return;
        double knockBackStrength = strength.applyAsInt(level);
        knockBackStrength *= 1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
        Vec3d vec3d = target.getPos().subtract(mob.getPos()).normalize().multiply(knockBackStrength);
        target.addVelocity(vec3d);
    }
}
