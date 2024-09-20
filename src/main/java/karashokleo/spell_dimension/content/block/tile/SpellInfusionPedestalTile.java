package karashokleo.spell_dimension.content.block.tile;

import karashokleo.enchantment_infusion.api.block.entity.AbstractInfusionTile;
import karashokleo.spell_dimension.config.recipe.ScrollLootConfig;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.ParticleUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpellInfusionPedestalTile extends AbstractInfusionTile
{
    private int craftTime = 0;
    @Nullable
    private Identifier craftSpellId = null;

    public SpellInfusionPedestalTile(BlockPos pos, BlockState state)
    {
        super(AllBlocks.SPELL_INFUSION_PEDESTAL_TILE, pos, state);
    }

    @Override
    public void onUse(ServerWorld world, BlockPos pos, PlayerEntity player)
    {
        if (this.craftTime != 0) return;
        if (this.getStack().isOf(Items.PAPER))
        {
            this.craftSpellId = ScrollLootConfig.getCraftSpellId(player.getMainHandStack());
            if (this.craftSpellId != null)
            {
                this.craftTime = 20 * 10;
                return;
            }
        }
        super.onUse(world, pos, player);
    }

    @SuppressWarnings("unused")
    public static void serverTick(World world, BlockPos pos, BlockState state, SpellInfusionPedestalTile entity)
    {
        if (entity.craftTime > 0)
        {
            entity.craftTime--;
            if (entity.craftTime == 0 && entity.craftSpellId != null)
            {
                entity.setStack(AllItems.SPELL_SCROLL.getStack(entity.craftSpellId));
                if (world instanceof ServerWorld serverWorld)
                    ParticleUtil.sparkParticleEmit(serverWorld, pos.toCenterPos().add(0, 0.8, 0), 24);
                entity.craftSpellId = null;
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        this.craftTime = nbt.getInt("CraftTime");
        this.craftSpellId = nbt.contains("CraftSpellId") ? new Identifier(nbt.getString("CraftSpellId")) : null;
    }

    @Override
    protected void writeNbt(NbtCompound nbt)
    {
        super.writeNbt(nbt);
        nbt.putInt("CraftTime", this.craftTime);
        if (this.craftSpellId != null)
            nbt.putString("CraftSpellId", this.craftSpellId.toString());
    }
}
