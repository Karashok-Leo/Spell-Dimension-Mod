package karashokleo.spell_dimension.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.Ownable;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class OwnerGetter
{
    private static final ConcurrentHashMap<Class<?>, Optional<Method>> CACHED_GET_OWNER = new ConcurrentHashMap<>();

    private OwnerGetter()
    {
    }

    @Nullable
    public static Entity getOwner(Entity entity)
    {
        if (entity instanceof Ownable ownable)
        {
            return ownable.getOwner();
        }

        Optional<Method> method = CACHED_GET_OWNER.computeIfAbsent(entity.getClass(), OwnerGetter::findGetOwner);
        if (method.isEmpty())
        {
            return null;
        }

        try
        {
            return (Entity) method.get().invoke(entity);
        } catch (Throwable ignored)
        {
            return null;
        }
    }

    private static Optional<Method> findGetOwner(Class<?> clazz)
    {
        try
        {
            Method method = clazz.getMethod("getOwner");
            if (method.getParameterCount() == 0 &&
                Entity.class.isAssignableFrom(method.getReturnType()))
            {
                method.setAccessible(true);
                return Optional.of(method);
            }
        } catch (NoSuchMethodException ignored)
        {
        }
        return Optional.empty();
    }
}
