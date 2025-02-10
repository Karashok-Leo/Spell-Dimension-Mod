package karashokleo.spell_dimension.config;

import net.minecraft.util.Identifier;

import java.util.HashSet;

public class SpellConfig
{
    private static final HashSet<Identifier> INTERMEDIATE_SPELLS = new HashSet<>();
    private static final HashSet<Identifier> ADVANCED_SPELLS = new HashSet<>();

    public static boolean enableSpellTier()
    {
        return true;
    }

    public static int getSpellTier(Identifier spellId)
    {
        if (INTERMEDIATE_SPELLS.contains(spellId))
            return 1;
        else if (ADVANCED_SPELLS.contains(spellId))
            return 2;
        else
            return 0;
    }

    public static void addIntermediateSpell(Identifier spellId)
    {
        INTERMEDIATE_SPELLS.add(spellId);
    }

    public static void addAdvancedSpell(Identifier spellId)
    {
        ADVANCED_SPELLS.add(spellId);
    }

    public static final float CONVERGE_FACTOR = 0.6F;
    public static final float BLACK_HOLE_FACTOR = 0.2F;
    public static final float NUCLEUS_FACTOR = 1.5F;
    public static final float FROSTED_FACTOR = 0.4F;
    public static final float DIVINE_AURA_FACTOR = 0.4F;
    public static final BlazingMarkConfig BLAZING_MARK_CONFIG = new BlazingMarkConfig(200, 100, 30, 0.5F);

    public record BlazingMarkConfig(int totalDuration, int triggerDuration, int maxDamage, float proportion)
    {
    }
}
