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

public class MiningCriterion extends BaseCriterion<MiningCriterion.Condition, MiningCriterion>
{
    public MiningCriterion(Identifier id)
    {
        super(id, Condition::new, Condition.class);
    }

    public static Condition condition(int count)
    {
        var ans = new Condition(AllCriterions.MINING.getId(), LootContextPredicate.EMPTY);
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
    public static class Condition extends BaseCriterionConditions<MiningCriterion.Condition, MiningCriterion>
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
