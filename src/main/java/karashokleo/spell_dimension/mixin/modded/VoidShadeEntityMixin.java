package karashokleo.spell_dimension.mixin.modded;

import net.adventurez.entity.VoidShadeEntity;
import net.adventurez.entity.VoidShadowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(VoidShadeEntity.class)
public abstract class VoidShadeEntityMixin extends FlyingEntity
{
    private VoidShadeEntityMixin(EntityType<? extends FlyingEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Override
    public void onDeath(DamageSource damageSource)
    {
        super.onDeath(damageSource);
        if (!(damageSource.getAttacker() instanceof LivingEntity living)) return;
        World world = this.getWorld();
        if (world.isClient()) return;
        List<VoidShadowEntity> list = world.getEntitiesByClass(VoidShadowEntity.class, this.getBoundingBox().expand(128), EntityPredicates.EXCEPT_SPECTATOR);
        if (list.isEmpty()) return;
        VoidShadowEntity shadow = list.get(0);
        float shadowMaxHealth = shadow.getMaxHealth();
        if (shadow.getHealth() < shadowMaxHealth * 0.5f) return;
        float damage = Math.min(shadowMaxHealth * 0.01f, this.getMaxHealth());
        shadow.damage(living.getDamageSources().indirectMagic(this, living), damage);
    }
}
