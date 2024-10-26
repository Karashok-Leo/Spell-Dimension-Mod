package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.spell_dimension.content.block.ProtectiveCoverBlock;
import karashokleo.spell_dimension.content.item.logic.KillSpellGoal;
import karashokleo.spell_dimension.content.item.logic.SpellGoal;
import karashokleo.spell_dimension.init.AllItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SpellContainerItem extends Item
{
    public static final KillSpellGoal KILL_MOB = new KillSpellGoal();
    public static final SpellGoal BREAK_SPAWNER = new SpellGoal("BreakSpawner");
    public static final SpellGoal OPEN_CHEST = new SpellGoal("OpenChest");

    public SpellContainerItem()
    {
        super(
                new FabricItemSettings()
                        .fireproof()
                        .maxCount(1)
        );
    }

    public void addKillCredit(PlayerInventory inventory, LivingEntity killed)
    {
        for (int i = 0; i < inventory.size(); i++)
        {
            ItemStack stack = inventory.getStack(i);
            if (stack.isOf(AllItems.SPELL_CONTAINER) &&
                MobDifficulty.get(killed)
                        .map(MobDifficulty::getLevel)
                        .map(level -> KILL_MOB.addKillCredit(stack.getOrCreateNbt(), level))
                        .orElse(false))
                break;
        }
    }

    public void addBreakSpawnerCredit(PlayerInventory inventory)
    {
        for (int i = 0; i < inventory.size(); i++)
        {
            ItemStack stack = inventory.getStack(i);
            if (stack.isOf(this) &&
                BREAK_SPAWNER.increaseProgress(stack.getOrCreateNbt()))
                break;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ProtectiveCoverBlock.placeAsBarrier(world, user.getBlockPos(), 16, 200);
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        return super.useOnBlock(context);
    }
}
