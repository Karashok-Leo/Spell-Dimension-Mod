package karashokleo.spell_dimension.mixin;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin
{
    @Shadow
    private RegistryKey<World> spawnPointDimension;

    @Shadow
    public abstract void sendMessage(Text message, boolean overlay);

    @Inject(
            method = "setSpawnPoint",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_setSpawnPoint(RegistryKey<World> dimension, BlockPos pos, float angle, boolean forced, boolean sendMessage, CallbackInfo ci)
    {
        if (this.spawnPointDimension.equals(AllWorldGen.OC_WORLD) &&
            !dimension.equals(AllWorldGen.OC_WORLD))
        {
            this.sendMessage(SDTexts.TEXT$SPAWN_POINT_RESTRICTION.get(), false);
            ci.cancel();
        }
    }
}
