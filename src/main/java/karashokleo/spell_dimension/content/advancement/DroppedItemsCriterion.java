package karashokleo.spell_dimension.content.advancement;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.advancement.base.BaseCriterion;
import karashokleo.l2hostility.content.advancement.base.BaseCriterionConditions;
import karashokleo.spell_dimension.init.AllCriterions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class DroppedItemsCriterion extends BaseCriterion<DroppedItemsCriterion.Condition, DroppedItemsCriterion>
{
    public DroppedItemsCriterion(Identifier id)
    {
        super(id, Condition::new, Condition.class);
    }

    public static Condition condition(ItemStack item, int count)
    {
        var ans = new Condition(AllCriterions.DROPPED_ITEMS.getId(), LootContextPredicate.EMPTY);
        ans.item = item;
        ans.count = count;
        return ans;
    }

    public void trigger(ServerPlayerEntity player, ItemStack item)
    {
        ServerStatHandler statHandler = player.getStatHandler();
        int drop = statHandler.getStat(Stats.DROPPED.getOrCreateStat(item.getItem()));
        this.trigger(player, e -> e.match(item, drop));
    }

    @SerialClass
    public static class Condition extends BaseCriterionConditions<DroppedItemsCriterion.Condition, DroppedItemsCriterion>
    {
        @SerialClass.SerialField
        public ItemStack item;
        @SerialClass.SerialField
        public int count;

        public Condition(Identifier id, LootContextPredicate player)
        {
            super(id, player);
        }

        public boolean match(ItemStack dropItem, int dropCount)
        {
            return ItemStack.areEqual(dropItem, item) && dropCount >= count;
        }
    }
}
