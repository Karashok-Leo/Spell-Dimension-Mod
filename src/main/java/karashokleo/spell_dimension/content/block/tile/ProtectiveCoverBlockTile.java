package karashokleo.spell_dimension.content.block.tile;

import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ProtectiveCoverBlockTile extends BlockEntity
{
    private static final String LIFE_KEY = "Life";
    private int life = 20 * 16;

    public ProtectiveCoverBlockTile(BlockPos pos, BlockState state)
    {
        super(AllBlocks.PROTECTIVE_COVER_TILE, pos, state);
    }

    public void setLife(int life)
    {
        this.life = life;
        this.markDirty();
        if (world == null) return;
        this.world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket()
    {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt()
    {
        return this.createNbt();
    }

    @Override
    protected void writeNbt(NbtCompound nbt)
    {
        super.writeNbt(nbt);
        nbt.putInt(LIFE_KEY, life);
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        super.readNbt(nbt);
        life = nbt.getInt(LIFE_KEY);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ProtectiveCoverBlockTile tile)
    {
        tile.life--;
        if (tile.life <= 0)
        {
            world.setBlockState(pos, world.getFluidState(pos).getBlockState(), 3);
            world.removeBlockEntity(pos);
        }
    }
}
