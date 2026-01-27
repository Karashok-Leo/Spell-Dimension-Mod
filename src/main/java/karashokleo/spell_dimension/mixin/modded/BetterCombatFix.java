package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import tocraft.walkers.api.PlayerShape;

@SuppressWarnings("UnresolvedMixinReference")
@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(targets = "net.bettercombat.client.compat.FirstPersonAnimationCompatibility", remap = false)
public abstract class BetterCombatFix
{
    @WrapOperation(
        method = "firstPersonMode",
        at = @At(
            value = "FIELD",
            target = "Lnet/bettercombat/client/compat/FirstPersonAnimationCompatibility;isCameraModPresent:Z",
            opcode = Opcodes.GETSTATIC
        ),
        require = 0
    )
    private static boolean onFirstPersonRenderCall(Operation<Boolean> original)
    {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && PlayerShape.getCurrentShape(player) != null)
        {
            return true;
        }
        return original.call();
    }
}
