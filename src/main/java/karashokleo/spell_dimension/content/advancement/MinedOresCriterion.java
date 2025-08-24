package karashokleo.spell_dimension.content.advancement;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.advancement.base.BaseCriterion;
import karashokleo.l2hostility.content.advancement.base.BaseCriterionConditions;
import karashokleo.spell_dimension.init.AllCriterions;
import karashokleo.spell_dimension.init.AllStats;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class MinedOresCriterion extends BaseCriterion<MinedOresCriterion.Condition, MinedOresCriterion>
{
    public MinedOresCriterion(Identifier id)
    {
        super(id, Condition::new, Condition.class);
    }

    public static Condition condition(int count)
    {
        var ans = new Condition(AllCriterions.MINED_ORES.getId(), LootContextPredicate.EMPTY);
        ans.count = count;
        return ans;
    }

    public void trigger(ServerPlayerEntity player)
    {
        ServerStatHandler statHandler = player.getStatHandler();
        Stat<Identifier> stat = Stats.CUSTOM.getOrCreateStat(AllStats.MINED_ORES);
        int statValue = statHandler.getStat(stat);
        this.trigger(player, e -> e.match(statValue));
    }

    @SerialClass
    public static class Condition extends BaseCriterionConditions<MinedOresCriterion.Condition, MinedOresCriterion>
    {
        @SerialClass.SerialField
        public int count;

        public Condition(Identifier id, LootContextPredicate player)
        {
            super(id, player);
        }

        public boolean match(int miningCount)
        {
            return miningCount >= count;
        }
    }
}
