package karashokleo.spell_dimension.content.block.tile;

import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ProtectiveCoverBlockTile extends BlockEntity
{
    private static final String LIFE_KEY = "Life";
    private int life = 20 * 16;

    public ProtectiveCoverBlockTile(BlockPos pos, BlockState state)
    {
        super(AllBlocks.PROTECTIVE_COVER_TILE, pos, state);
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
            world.breakBlock(pos, false);
            world.removeBlockEntity(pos);
        }
    }
}
