package karashokleo.spell_dimension.config;

public class SpellConfig
{
    public static final Damage CONVERGE = new Damage(4, 0.6, 1.2);
    public static final Damage NUCLEUS = new Damage(8, 0.8, 1.1);
    public static final Damage FROSTED = new Damage(2, 0.4, 1.2);
    public static final Damage DIVINE_AURA = new Damage(4, 0.4, 1.2);
    public static final BlazingMark BLAZING_MARK = new BlazingMark(200, 100, 30, 0.5F);

    public record Damage(double addition, double multiplier, double base)
    {
    }

    public record BlazingMark(int totalDuration, int triggerDuration, int maxDamage, float proportion)
    {
    }
}
