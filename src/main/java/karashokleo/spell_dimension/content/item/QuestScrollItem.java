package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.config.QuestToEntryConfig;
import karashokleo.spell_dimension.content.component.QuestComponent;
import karashokleo.spell_dimension.content.network.S2CTitle;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
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
import java.util.Set;

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
        if (world.isClient() && user.isSneaking())
        {
            Optional<Quest> optional = this.getQuest(stack);
            optional.ifPresent(QuestToEntryConfig::openEntry);
        } else if (user instanceof ServerPlayerEntity player &&
                   !player.isSneaking())
        {
            Optional<Quest> optional = this.getQuest(stack);
            // Valid quest
            if (optional.isPresent())
            {
                Quest quest = optional.get();
                boolean creativeMode = player.getAbilities().creativeMode;
                // Already completed
                if (QuestUsage.isQuestCompleted(player, quest))
                {
                    if (creativeMode && player.isSneaking())
                    {
                        player.sendMessage(SDTexts.TEXT$PROGRESS_ROLLBACK.get(), true);
                        QuestUsage.removeQuestsCompleted(player, quest);
                    } else player.sendMessage(SDTexts.TEXT$QUEST_COMPLETED.get(), true);
                }
                // Do reward
                else if (QuestUsage.allDependenciesCompleted(player, quest) &&
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
                // Requirements not met
                else player.sendMessage(SDTexts.TEXT$QUEST_REQUIREMENT.get(), true);
            }
            // Empty quest
            else
            {
                Set<RegistryEntry<Quest>> currentQuests = QuestUsage.getCurrentQuests(player);
                // All completed
                if (currentQuests.isEmpty())
                    player.sendMessage(SDTexts.TEXT$QUEST_ALL_COMPLETED.get(), true);
                    // Offer all current quests
                else
                {
                    currentQuests.stream()
                            .map(AllItems.QUEST_SCROLL::getStack)
                            .forEach(itemStack -> player.getInventory().offerOrDrop(itemStack));
                    if (!player.getAbilities().creativeMode)
                        stack.decrement(1);
                }
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        Optional<Quest> quest = this.getQuest(stack);
        if (quest.isPresent())
        {
            Text title = quest.get().getTitle(world);
            if (title != null) tooltip.add(title);
            tooltip.addAll(quest.get().getDesc(world));
        } else tooltip.add(SDTexts.TOOLTIP$QUEST$OBTAIN_CURRENT.get());
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack)
    {
        return this.getQuest(stack).map(Quest::getTooltipData);
    }
}
