package karashokleo.spell_dimension.init;

import net.minecraft.world.GameRules;

public class AllMiscInit
{
    public static final GameRules.Key<GameRules.BooleanRule> NOTIFY_SPELL_TRAIT_CASTING = GameRules.register("notifySpellTraitCasting", GameRules.Category.MISC, GameRules.BooleanRule.create(true));

    public static void register()
    {
    }
}
