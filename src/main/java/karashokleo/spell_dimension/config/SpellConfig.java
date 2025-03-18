package karashokleo.spell_dimension.config;

public class SpellConfig
{
    public static final float CONVERGE_FACTOR = 0.6F;
    public static final float BLACK_HOLE_FACTOR = 0.2F;
    public static final float NUCLEUS_FACTOR = 1.5F;
    public static final float FROSTED_FACTOR = 0.4F;
    public static final float DIVINE_AURA_FACTOR = 0.4F;
    public static final BlazingMarkConfig BLAZING_MARK_CONFIG = new BlazingMarkConfig(200, 100, 0.5f, 0.5F);

    public record BlazingMarkConfig(int totalDuration, int triggerDuration, float maxDamageRatio, float reDamageRatio)
    {
    }
}
