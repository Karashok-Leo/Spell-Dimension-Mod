package karashokleo.spell_dimension.mixin;

import karashokleo.spell_dimension.init.AllWorldGen;
import net.minecraft.network.ClientConnection;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin
{
    @Redirect(
            method = "onPlayerConnect",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/World;OVERWORLD:Lnet/minecraft/registry/RegistryKey;"
            )
    )
    private RegistryKey<World> redirect_onPlayerConnect()
    {
        return AllWorldGen.OC_WORLD;
    }

    @Inject(
            method = "onPlayerConnect",
            at = @At("TAIL")
    )
    private void inject_onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci)
    {
        if (player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.LEAVE_GAME)) < 1)
        {
            ServerWorld world = player.getServerWorld();
            if (world.getRegistryKey() != AllWorldGen.OC_WORLD)
                world = world.getServer().getWorld(AllWorldGen.OC_WORLD);
            if (world != null)
                player.setSpawnPoint(world.getRegistryKey(), world.getSpawnPos(), world.getSpawnAngle(), true, false);
        }
    }
}
