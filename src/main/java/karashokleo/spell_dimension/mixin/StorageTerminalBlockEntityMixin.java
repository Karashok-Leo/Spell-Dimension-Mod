package karashokleo.spell_dimension.mixin;

import com.tom.storagemod.tile.StorageTerminalBlockEntity;
import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = StorageTerminalBlockEntity.class, remap = false)
public abstract class StorageTerminalBlockEntityMixin extends BlockEntity
{
    @Shadow
    private int beaconLevel;

    private StorageTerminalBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Inject(
            method = "updateServer",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/tom/storagemod/tile/StorageTerminalBlockEntity;beaconLevel:I",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER,
                    by = 1
            )
    )
    private void inject_beaconLevel_putField(CallbackInfo ci)
    {
        int coreLv = BlockPos
                .stream(new Box(this.pos).expand(8.0))
                .mapToInt(
                        (blockPos) ->
                        {
                            if (this.world != null)
                            {
                                var optional = this.world.getBlockEntity(blockPos, AllBlocks.CONSCIOUSNESS_CORE_TILE);
                                if (optional.isPresent())
                                {
                                    return (int) (optional.get().getLevelFactor() / 0.1) + 1;
                                }
                            }
                            return 0;
                        }
                ).max().orElse(0);
        this.beaconLevel = Math.max(this.beaconLevel, coreLv);
    }
}
