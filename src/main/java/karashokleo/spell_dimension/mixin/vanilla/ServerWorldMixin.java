package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin
{
    @Inject(
        method = "onDimensionChanged",
        at = @At("RETURN")
    )
    private void inject_onDimensionChanged(Entity entity, CallbackInfo ci)
    {
        if (entity instanceof FakePlayerEntity fakePlayer)
        {
            fakePlayer.refreshRefData();
        }
    }
}
