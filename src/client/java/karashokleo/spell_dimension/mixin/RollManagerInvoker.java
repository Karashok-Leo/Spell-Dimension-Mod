package karashokleo.spell_dimension.mixin;

import net.combatroll.internals.RollManager;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = RollManager.class, remap = false)
public interface RollManagerInvoker
{
    @Invoker
    void invokeRechargeRoll(ClientPlayerEntity clientPlayerEntity);
}
