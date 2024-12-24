package karashokleo.spell_dimension.mixin.client;

import net.combatroll.internals.RollManager;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RollManager.class)
public interface RollManagerInvoker
{
    @Invoker
    void invokeRechargeRoll(ClientPlayerEntity clientPlayerEntity);
}
