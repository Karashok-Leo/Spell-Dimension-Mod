package karashokleo.spell_dimension.content.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public interface NoClip
{
    boolean isNoClip();

    static boolean noClip(LivingEntity entity)
    {
        return ((NoClip) entity).isNoClip();
    }

    static boolean noClip(Entity entity)
    {
        if (entity instanceof LivingEntity livingEntity)
        {
            return noClip(livingEntity);
        } else
        {
            return false;
        }
    }
}
