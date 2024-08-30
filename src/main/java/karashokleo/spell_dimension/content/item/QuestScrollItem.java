package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.content.component.QuestComponent;
import karashokleo.spell_dimension.content.network.S2CTitle;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class QuestScrollItem extends Item
{
    private static final String KEY = "Quest";

    public QuestScrollItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    public ItemStack getStack(Quest quest)
    {
        return getStack(QuestUsage.entry(quest));
    }

    public ItemStack getStack(Identifier id)
    {
        return getStack(QuestUsage.entry(id));
    }

    public ItemStack getStack(RegistryEntry<Quest> entry)
    {
        ItemStack stack = this.getDefaultStack();
        QuestRegistry.ENTRY_CODEC.encodeStart(NbtOps.INSTANCE, entry).result().ifPresent(e -> stack.getOrCreateNbt().put(KEY, e));
        return stack;
    }

    public Optional<Identifier> getQuestId(ItemStack stack)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return Optional.empty();
        return Identifier.validate(nbt.getString(KEY)).result();
    }

    public Optional<Quest> getQuest(ItemStack stack)
    {
        return this.getQuestId(stack).map(QuestRegistry.QUEST_REGISTRY::get);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        Optional<Quest> optional = this.getQuest(stack);
        if (optional.isPresent() && user instanceof ServerPlayerEntity player)
        {
            Quest quest = optional.get();
            boolean creativeMode = player.getAbilities().creativeMode;
            if (QuestUsage.isQuestCompleted(player, quest))
            {
                if (creativeMode && player.isSneaking())
                {
                    player.sendMessage(SDTexts.TEXT$PROGRESS_ROLLBACK.get(), true);
                    QuestUsage.removeQuestsCompleted(player, quest);
                }
            } else if (QuestUsage.allDependenciesCompleted(player, quest) &&
                    quest.completeTasks(player))
            {
                quest.reward(player);
                QuestUsage.addQuestsCompleted(player, quest);
                QuestUsage.getDependents(quest)
                        .stream()
                        .filter(q1 -> QuestUsage.getDependencies(q1.value()).stream().allMatch(q2 -> QuestComponent.isCompleted(player, q2)))
                        .map(this::getStack)
                        .forEach(itemStack -> player.getInventory().offerOrDrop(itemStack));
                ServerPlayNetworking.send(player, new S2CTitle(SDTexts.TEXT$QUEST_COMPLETE.get()));
                S2CUndying packet = new S2CUndying(player);
                LHNetworking.toClientPlayer(player, packet);
                LHNetworking.toTracking(player, packet);
                if (!creativeMode) stack.decrement(1);
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        Optional<Quest> quest = this.getQuest(stack);
        if (quest.isPresent()) tooltip.addAll(quest.get().getDesc());
        else tooltip.add(SDTexts.TOOLTIP$INVALID.get());
    }
}
