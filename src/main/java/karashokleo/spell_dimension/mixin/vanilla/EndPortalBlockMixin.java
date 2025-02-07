package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.component.EndStageComponent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public abstract class EndPortalBlockMixin
{
    @Inject(
            method = "onEntityCollision",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci)
    {
        if (world.isClient()) return;
        if (entity instanceof PlayerEntity player &&
            !EndStageComponent.canEnterEnd(player))
        {
            player.sendMessage(SDTexts.TEXT$END_STAGE$LOCK.get(AllItems.CELESTIAL_LUMINARY.getName()), true);
            ci.cancel();
        }
    }
}
