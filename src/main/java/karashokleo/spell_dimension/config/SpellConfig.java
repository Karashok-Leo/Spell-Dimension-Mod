package karashokleo.spell_dimension.config;

public class SpellConfig
{
    public static final float CONVERGE_FACTOR = 0.6F;
    public static final float BLACK_HOLE_FACTOR = 0.2F;
    public static final float NUCLEUS_FACTOR = 1.5F;
    public static final float FROSTED_FACTOR = 0.4F;
    public static final float DIVINE_AURA_FACTOR = 0.4F;
    public static final BlazingMarkConfig BLAZING_MARK_CONFIG = new BlazingMarkConfig(200, 100, 0.5f, 0.5F);
    public static final ChainLightningConfig CHAIN_LIGHTNING_CONFIG = new ChainLightningConfig(0.2F, 20, 1, 3F);
    public static final PowerPassiveConfig POWER_PASSIVE_CONFIG = new PowerPassiveConfig(2, 2);
    public static final ChainLightningPassiveConfig CHAIN_LIGHTNING_PASSIVE_CONFIG = new ChainLightningPassiveConfig(2, 4, 2, 3);
    public static final BallLightningConfig BALL_LIGHTNING_CONFIG = new BallLightningConfig(0.8F, 200, 100);
    public static final RailgunConfig RAILGUN_CONFIG = new RailgunConfig(32, 2, 10F);
    public static final ElectrocutionConfig ELECTROCUTION_CONFIG = new ElectrocutionConfig(20, 3, 1.5F);

    public record BlazingMarkConfig(
        int totalDuration,
        int triggerDuration,
        float maxDamageRatio,
        float reDamageRatio
    )
    {
    }

    public record ChainLightningConfig(
        float damageFactor,
        int lifespan,
        int chainStep,
        float range
    )
    {
    }

    public record PowerPassiveConfig(
        int surge,
        int arclight
    )
    {
    }

    public record ChainLightningPassiveConfig(
        int steadyCurrentLifespan,
        int constantCurrentLifespan,
        int fissionChainStep,
        int resonanceRange
    )
    {
    }

    public record BallLightningConfig(
        float damageFactor,
        int lifespan,
        int lifespanIncrement
    )
    {
    }

    public record RailgunConfig(
        int length,
        int radius,
        float damageFactor
    )
    {
    }

    public record ElectrocutionConfig(
        int maxDuration,
        int maxStacks,
        float damageFactor
    )
    {
    }
}
