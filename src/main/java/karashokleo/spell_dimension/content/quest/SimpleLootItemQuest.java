package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.api.quest.ItemTaskQuest;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public record SimpleLootItemQuest(
        List<Supplier<EntityType<?>>> entities,
        List<Supplier<ItemConvertible>> tasks,
        List<ItemStack> rewards
) implements ItemTaskQuest, ItemRewardQuest
{
    public SimpleLootItemQuest(Supplier<EntityType<?>> entity, Supplier<ItemConvertible> task, ItemStack reward)
    {
        this(List.of(entity), List.of(task), List.of(reward));
    }

    public SimpleLootItemQuest(Identifier entity, Identifier task, ItemStack reward)
    {
        this(() -> Registries.ENTITY_TYPE.get(entity), () -> Registries.ITEM.get(task), reward);
    }

    public SimpleLootItemQuest(String entity, String task, ItemStack reward)
    {
        this(new Identifier(entity), new Identifier(task), reward);
    }

    @Override
    public List<ItemConvertible> getTaskItems()
    {
        return this.tasks.stream().map(Supplier::get).toList();
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return this.rewards;
    }

    @Override
    public void appendTaskDesc(World world, List<Text> desc)
    {
        List<? extends EntityType<?>> entities = this.entities.stream().map(Supplier::get).toList();
        MutableText entity = this.entities.isEmpty() ? Text.empty() : Text.empty().append(entities.get(0).getName());
        for (int i = 1; i < this.entities.size(); i++)
            entity.append(SDTexts.TOOLTIP$QUEST$AND.get()).append(entities.get(i).getName());

        List<Item> tasks = getTaskItems().stream().map(ItemConvertible::asItem).toList();
        MutableText task = tasks.isEmpty() ? Text.empty() : Text.empty().append(tasks.get(0).getName());
        for (int i = 1; i < tasks.size(); i++)
            task.append(SDTexts.TOOLTIP$QUEST$AND.get()).append(tasks.get(i).getName());

        desc.add(SDTexts.TOOLTIP$QUEST$LOOT_ITEM.get(entity, task));
    }
}
