package karashokleo.spell_dimension.config;

public class DamageConfig
{
    public Damage damage;

    public DamageConfig(double addition, double multiplier, double base)
    {
        this.damage = new Damage(addition, multiplier, base);
    }

    public static class Damage
    {
        public double addition;
        public double multiplier;
        public double base;

        public Damage(double addition, double multiplier, double base)
        {
            this.addition = addition;
            this.multiplier = multiplier;
            this.base = base;
        }
    }
}
