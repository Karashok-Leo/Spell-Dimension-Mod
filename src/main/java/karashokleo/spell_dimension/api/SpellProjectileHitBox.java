package karashokleo.spell_dimension.api;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class SpellProjectileHitBox
{
    private static final HashMap<Identifier, Vec3d> EXTENSIONS = new HashMap<>();

    public static void registerExtension(Identifier spellId, Vec3d extend)
    {
        EXTENSIONS.put(spellId, extend);
    }

    @Nullable
    public static Vec3d getExtension(Identifier spellId)
    {
        return EXTENSIONS.get(spellId);
    }
}
