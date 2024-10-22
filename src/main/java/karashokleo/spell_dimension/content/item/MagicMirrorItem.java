package karashokleo.spell_dimension.content.item;

import io.wispforest.owo.ops.WorldOps;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.content.buff.Conscious;
import karashokleo.spell_dimension.content.event.conscious.ConsciousnessEventManager;
import karashokleo.spell_dimension.content.network.S2CFloatingItem;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
            if (destinationWorld != null)
            {
                ServerPlayNetworking.send(player, new S2CFloatingItem(stack.copy()));
                if (broken)
                {
                    stack.decrement(1);
                    Buff.apply(player, Conscious.TYPE, new Conscious(), player);
                }
                BlockPos destinationPos = ConsciousnessEventManager.findTeleportPos(serverWorld, destinationWorld, player.getBlockPos());
                WorldOps.teleportToWorld(player, destinationWorld, destinationPos.toCenterPos());
            }
        }
        return stack;
    }
}
