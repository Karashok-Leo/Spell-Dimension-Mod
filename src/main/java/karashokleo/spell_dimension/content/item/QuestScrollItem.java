package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.NetworkUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
        Identifier id = QuestRegistry.QUEST_REGISTRY.getId(quest);
        return id == null ?
                this.getDefaultStack() :
                this.getStack(id);
    }

    public ItemStack getStack(Identifier id)
    {
        ItemStack stack = this.getDefaultStack();
        stack.getOrCreateNbt().putString(KEY, id.toString());
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
        Optional<Quest> quest = this.getQuest(stack);
        if (quest.isPresent() &&
                user instanceof ServerPlayerEntity player &&
                quest.get().completeTasks(player))
        {
            quest.get().reward(player);
            ServerPlayNetworking.send(player, NetworkUtil.QUEST_PACKET, PacketByteBufs.create());
            if (!user.getAbilities().creativeMode)
                stack.decrement(1);
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        Optional<Quest> quest = this.getQuest(stack);
        if (quest.isPresent()) tooltip.addAll(quest.get().getDesc());
        else tooltip.add(SDTexts.TOOLTIP_INVALID.get());
    }
}
