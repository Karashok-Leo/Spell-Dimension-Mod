package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.config.QuestToEntryConfig;
import karashokleo.spell_dimension.content.component.QuestComponent;
import karashokleo.spell_dimension.content.network.S2COpenQuestScreen;
import karashokleo.spell_dimension.content.network.S2CTitle;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllPackets;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
    public static final Text SPACING_LINE = Text.literal("?????????????????????????????????????????????????????")
            .formatted(Formatting.DARK_PURPLE)
            .formatted(Formatting.OBFUSCATED);

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

    public void setQuest(ItemStack stack, @Nullable Identifier questId)
    {
        if (questId == null) stack.getOrCreateNbt().remove(KEY);
        else stack.getOrCreateNbt().putString(KEY, questId.toString());
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
                    if (creativeMode)
                    {
                        player.sendMessage(SDTexts.TEXT$PROGRESS_ROLLBACK.get(), true);
                        QuestUsage.removeQuestsCompleted(player, quest);
                    } else player.sendMessage(SDTexts.TEXT$QUEST$COMPLETED.get(), true);
                }
                // Do reward
                else if (QuestUsage.allDependenciesCompleted(player, quest))
                {
                    // Requirements met or creative mode
                    if (quest.completeTasks(player) || creativeMode)
                    {
                        quest.reward(player);
                        QuestUsage.addQuestsCompleted(player, quest);
//                        giveDependentQuests(player, quest);
                        Text feedback = quest.getFeedback(world);
                        if (feedback != null)
                        {
                            player.sendMessage(SPACING_LINE);
                            player.sendMessage(feedback);
                            player.sendMessage(SPACING_LINE);
                        }
                        S2CTitle title = new S2CTitle(SDTexts.TEXT$QUEST$COMPLETE.get());
                        AllPackets.toClientPlayer(player, title);
                        S2CUndying packet = new S2CUndying(player);
                        LHNetworking.toClientPlayer(player, packet);
                        LHNetworking.toTracking(player, packet);
                        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, player.getSoundCategory(), 1.0F, 1.0F);
//                        if (!creativeMode) stack.decrement(1);
                        AllItems.QUEST_SCROLL.setQuest(stack, null);
                    }
                    // Requirements not met
                    else player.sendMessage(SDTexts.TEXT$QUEST$REQUIREMENT.get(), true);
                }
                // Dependencies not completed
                else player.sendMessage(SDTexts.TEXT$QUEST$DEPENDENCIES.get(), true);
            }
            // Empty quest
            else
            {
                Set<RegistryEntry<Quest>> currentQuests = QuestUsage.getCurrentQuests(player);
                // All completed
                if (currentQuests.isEmpty())
                {
                    player.sendMessage(SDTexts.TEXT$QUEST$ALL_COMPLETED.get(), true);
                } else
                {
                    AllPackets.toClientPlayer(player, new S2COpenQuestScreen(hand));
//                    currentQuests.stream()
//                            .map(AllItems.QUEST_SCROLL::getStack)
//                            .forEach(itemStack -> player.getInventory().offerOrDrop(itemStack));
//                    if (!player.getAbilities().creativeMode)
//                        stack.decrement(1);
                }
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    private void giveDependentQuests(ServerPlayerEntity player, Quest quest)
    {
        QuestUsage.getDependents(quest)
                .stream()
                .filter(q1 -> QuestUsage.getDependencies(q1.value()).stream().allMatch(q2 -> QuestComponent.isCompleted(player, q2)))
                .map(this::getStack)
                .forEach(itemStack -> player.getInventory().offerOrDrop(itemStack));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        Optional<Quest> quest = this.getQuest(stack);
        if (quest.isPresent())
        {
            quest.get().appendTooltip(world, tooltip);
            tooltip.add(SDTexts.TOOLTIP$QUEST$COMPLETE.get().formatted(Formatting.GOLD));
            tooltip.add(SDTexts.TOOLTIP$QUEST$RESELECT.get().formatted(Formatting.DARK_GREEN));
            if (QuestToEntryConfig.hasEntry(quest.get()))
                tooltip.add(SDTexts.TOOLTIP$QUEST$OPEN_ENTRY.get().formatted(Formatting.DARK_AQUA));
        } else
        {
            tooltip.add(SDTexts.TOOLTIP$QUEST$VIEW_CURRENT.get());
//            tooltip.add(SDTexts.TOOLTIP$QUEST$OBTAIN_CURRENT.get());
        }
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack)
    {
        return this.getQuest(stack).map(Quest::getTooltipData);
    }
}
