package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.content.buff.Conscious;
import karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager;
import karashokleo.spell_dimension.content.network.S2CFloatingItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllWorldGen;
import karashokleo.spell_dimension.util.TeleportUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicMirrorItem extends Item
{
    private final boolean broken;

    public MagicMirrorItem(boolean broken)
    {
        super(
                new FabricItemSettings()
                        .fireproof()
                        .maxCount(1)
                        .rarity(broken ? Rarity.UNCOMMON : Rarity.EPIC)
        );
        this.broken = broken;
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (world.getRegistryKey().equals(AllWorldGen.OC_WORLD))
            return TypedActionResult.fail(stack);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack)
    {
        return 32;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        if (world instanceof ServerWorld serverWorld &&
            user instanceof ServerPlayerEntity player)
        {
            ServerWorld destinationWorld = world.getServer().getWorld(AllWorldGen.OC_WORLD);
            if (destinationWorld != null && serverWorld != destinationWorld)
            {
                ServerPlayNetworking.send(player, new S2CFloatingItem(stack.copy()));
                if (broken)
                {
                    stack.decrement(1);
                    Buff.apply(player, Conscious.TYPE, new Conscious(), player);
                }
                BlockPos destinationPos = TeleportUtil.findTeleportPos(serverWorld, destinationWorld, player.getBlockPos());
                TeleportUtil.teleportPlayerChangeDimension(player, destinationWorld, destinationPos);
                if (!player.getSpawnPointDimension().equals(AllWorldGen.OC_WORLD))
                    player.setSpawnPoint(AllWorldGen.OC_WORLD, destinationPos, player.getSpawnAngle(), true, true);
            }
        }
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$MAGIC_MIRROR$USAGE.get().formatted(Formatting.YELLOW));
        tooltip.add(SDTexts.TOOLTIP$MAGIC_MIRROR$WARNING.get().formatted(Formatting.RED));
        if (broken)
        {
            tooltip.add(SDTexts.TOOLTIP$MAGIC_MIRROR$BROKEN_1.get().formatted(Formatting.GRAY));
            tooltip.add(SDTexts.TOOLTIP$MAGIC_MIRROR$BROKEN_2.get().formatted(Formatting.GRAY));
        }
    }
}
