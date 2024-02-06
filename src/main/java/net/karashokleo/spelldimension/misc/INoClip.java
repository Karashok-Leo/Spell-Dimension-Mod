package net.karashokleo.spelldimension.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public interface INoClip
{
    boolean isNoClip();

    static boolean noClip(LivingEntity entity)
    {
        return ((INoClip) entity).isNoClip();
    }

    static boolean noClip(Entity entity)
    {
        if (entity instanceof LivingEntity livingEntity)
            return noClip(livingEntity);
        else return false;
    }
}
