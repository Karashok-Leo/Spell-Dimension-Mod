package karashokleo.spell_dimension.init;

import net.minecraft.world.GameRules;

public class AllMiscInit
{
    public static final GameRules.Key<GameRules.BooleanRule> NOTIFY_SPELL_TRAIT_CASTING = createRule("notifySpellTraitCasting", true);

    public static final GameRules.Key<GameRules.BooleanRule> ENABLE_DAMAGE_TRACKER = createRule("enableDamageTracker", false);

    public static final GameRules.Key<GameRules.BooleanRule> IMMUNE_TRACKED_DAMAGE = createRule("immuneTrackedDamage", false);

    public static final GameRules.Key<GameRules.BooleanRule> NOTIFY_DAMAGE_TRACKER = createRule("notifyDamageTracker", false);

    public static final GameRules.Key<GameRules.IntRule> DAMAGE_TRACKER_THRESHOLD = createRule("damageTrackerThreshold", 50);

    public static void register()
    {
    }

    private static GameRules.Key<GameRules.BooleanRule> createRule(String name, boolean defaultValue)
    {
        return GameRules.register(name, GameRules.Category.MISC, GameRules.BooleanRule.create(defaultValue));
    }

    private static GameRules.Key<GameRules.IntRule> createRule(String name, int defaultValue)
    {
        return GameRules.register(name, GameRules.Category.MISC, GameRules.IntRule.create(defaultValue));
    }
}
