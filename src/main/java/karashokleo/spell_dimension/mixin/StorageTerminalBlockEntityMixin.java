package karashokleo.spell_dimension.mixin;

import com.tom.storagemod.tile.StorageTerminalBlockEntity;
import karashokleo.spell_dimension.content.block.tile.ConsciousnessCoreTile;
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
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = StorageTerminalBlockEntity.class, remap = false)
public abstract class StorageTerminalBlockEntityMixin extends BlockEntity
{
    @Shadow
    private int beaconLevel;

    private StorageTerminalBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    @Redirect(
            method = "updateServer",
            at = @At(
                    value = "FIELD",
                    target = "Lcom/tom/storagemod/tile/StorageTerminalBlockEntity;beaconLevel:I",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void redirect_beaconLevel_putField(StorageTerminalBlockEntity instance, int value)
    {
        this.beaconLevel = BlockPos
                .stream((new Box(this.pos)).expand(8.0))
                .anyMatch(
                        (blockPos) -> this.world != null &&
                                      this.world.getBlockEntity(blockPos, AllBlocks.CONSCIOUSNESS_CORE_TILE)
                                              .map(ConsciousnessCoreTile::isActivated)
                                              .orElse(false)
                ) ? 6 : value;
    }
}
