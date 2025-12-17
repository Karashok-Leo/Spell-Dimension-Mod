package karashokleo.spell_dimension.content.object;

import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

public class EmptyHitResult extends HitResult
{
    public EmptyHitResult(Vec3d pos)
    {
        super(pos);
    }

    @Override
    public Type getType()
    {
        return Type.MISS;
    }
}
