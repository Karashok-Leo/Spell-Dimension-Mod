package karashokleo.spell_dimension.content.block.tile;

import karashokleo.enchantment_infusion.api.block.entity.AbstractInfusionTile;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.util.ParticleUtil;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpellInfusionPedestalTile extends AbstractInfusionTile
{
    public static final int CRAFT_TIME = 20 * 10;

    @Nullable
    private ItemStack crafting = null;
    private int craftTime = 0;
    private int craftTimeMax = 0;

    public SpellInfusionPedestalTile(BlockPos pos, BlockState state)
    {
        super(AllBlocks.SPELL_INFUSION_PEDESTAL_TILE, pos, state);
    }

    private void startCrafting(ItemStack stack)
    {
        this.crafting = stack;
        this.craftTime = CRAFT_TIME;
        this.craftTimeMax = CRAFT_TIME;
        this.update();
    }

//    @Override
//    public void onUse(ServerWorld world, BlockPos pos, PlayerEntity player)
//    {
//        if (this.craftTime != 0) return;
//        ItemStack mainHandStack = player.getMainHandStack();
//        ItemStack output = InfusionRecipes.getRecipe(this.getStack().getItem(), mainHandStack.getItem());
//        if (output != null)
//        {
//            this.startCrafting(output.copy());
//            mainHandStack.decrement(1);
//            return;
//        }
//        super.onUse(world, pos, player);
//    }

    @SuppressWarnings("unused")
    public static void clientTick(World world, BlockPos pos, BlockState state, SpellInfusionPedestalTile entity)
    {
        if (entity.craftTime > 0 && entity.craftTime % 5 == 0)
        {
            float progress = (float) entity.craftTime / entity.craftTimeMax;
            float radian = progress * MathHelper.PI * 4;
            float y = 2.5F * (1 - progress);
            Vec3d vec = pos.toCenterPos()
                .add(
                    MathHelper.cos(radian) * progress * 1.5,
                    y > 1.8 ? 1.8 - (y - 1.8) / 0.7 * 1.2 : y,
                    MathHelper.sin(radian) * progress * 1.5
                );
            world.addParticle(
                ParticleTypes.END_ROD,
                vec.getX(), vec.getY(), vec.getZ(),
                0, 0, 0
            );
        }
    }

    @SuppressWarnings("unused")
    public static void serverTick(World world, BlockPos pos, BlockState state, SpellInfusionPedestalTile entity)
    {
        if (entity.craftTime > 0)
        {
            entity.update();
            entity.craftTime--;
            if (entity.craftTime == 0 && entity.crafting != null)
            {
                entity.setStack(entity.crafting);
                if (world instanceof ServerWorld serverWorld)
                {
                    ParticleUtil.sparkParticleEmit(serverWorld, pos.toCenterPos().add(0, 0.6, 0), 24);
                }
                entity.crafting = null;
                entity.craftTimeMax = 0;
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        this.crafting = nbt.contains("Crafting") ? ItemStack.fromNbt(nbt.getCompound("Crafting")) : null;
        this.craftTime = nbt.getInt("CraftTime");
        this.craftTimeMax = nbt.getInt("CraftTimeMax");
    }

    @Override
    protected void writeNbt(NbtCompound nbt)
    {
        super.writeNbt(nbt);
        if (this.crafting != null)
        {
            nbt.put("Crafting", this.crafting.writeNbt(new NbtCompound()));
        }
        nbt.putInt("CraftTime", this.craftTime);
        nbt.putInt("CraftTimeMax", this.craftTimeMax);
    }
}
