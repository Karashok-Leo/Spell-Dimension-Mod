package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.spell_engine.client.compatibility.FirstPersonModelCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import tocraft.walkers.api.PlayerShape;

@Mixin(FirstPersonModelCompatibility.class)
public abstract class FirstPersonModelCompatibilityMixin
{
    @WrapMethod(
        method = "isActive"
    )
    private static boolean wrap_isActive(Operation<Boolean> original)
    {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null &&
            PlayerShape.getCurrentShape(player) != null)
        {
            return true;
        }
        return original.call();
    }
}
