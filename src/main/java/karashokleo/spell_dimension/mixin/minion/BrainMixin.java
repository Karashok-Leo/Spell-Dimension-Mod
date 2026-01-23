package karashokleo.spell_dimension.mixin.minion;

import com.google.common.collect.ImmutableList;
import karashokleo.spell_dimension.content.entity.brain.SoulMinionBrain;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Mixin(Brain.class)
public abstract class BrainMixin
{
    @Shadow
    @Final
    private Map<MemoryModuleType<?>, Optional<? extends Memory<?>>> memories;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void inject_init(
        Collection<? extends MemoryModuleType<?>> memoryModules,
        Collection<?> sensors,
        ImmutableList<?> memoryEntries,
        Supplier<?> codecSupplier,
        CallbackInfo ci
    )
    {
        this.memories.putIfAbsent(SoulMinionBrain.SOUL_STANDBY_MEMORY, Optional.empty());
        SoulMinionBrain.initBrain((Brain<? extends LivingEntity>) (Object) this);
    }
}
