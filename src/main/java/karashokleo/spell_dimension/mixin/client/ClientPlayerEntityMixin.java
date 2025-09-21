package karashokleo.spell_dimension.mixin.client;

import com.mojang.authlib.GameProfile;
import karashokleo.spell_dimension.client.misc.SoulControlHandler;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 消费玩家输入
 */
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity
{
    @Shadow
    public Input input;
    @Unique
    private float pendingCursorDeltaX = 0;
    @Unique
    private float pendingCursorDeltaY = 0;

    private ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile)
    {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(
        method = "tickMovement",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/input/Input;tick(ZF)V",
            shift = At.Shift.AFTER,
            by = 1
        )
    )
    private void inject_tickInput(CallbackInfo ci)
    {
        var player = (ClientPlayerEntity) (Object) this;
        SoulControlHandler.consumePlayerMoveInput(player, input, pendingCursorDeltaX, pendingCursorDeltaY);
    }

    @Override
    public void changeLookDirection(double cursorDeltaX, double cursorDeltaY)
    {
        var player = (ClientPlayerEntity) (Object) this;
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(player);
        if (minionComponent.isControlling())
        {
            pendingCursorDeltaX = (float) cursorDeltaX;
            pendingCursorDeltaY = (float) cursorDeltaY;
            return;
        }
        super.changeLookDirection(cursorDeltaX, cursorDeltaY);
    }
}
