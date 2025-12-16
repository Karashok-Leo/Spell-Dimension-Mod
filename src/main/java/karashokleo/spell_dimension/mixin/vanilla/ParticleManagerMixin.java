package karashokleo.spell_dimension.mixin.vanilla;

import com.google.common.collect.ImmutableList;
import karashokleo.spell_dimension.content.particle.ZapParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * 将自定义ParticleTextureSheet添加到原版的粒子管理器中
 * Learned from <a href="https://github.com/VazkiiMods/Botania/blob/1.20.x/Fabric/src/main/java/vazkii/botania/fabric/mixin/client/ParticleEngineFabricMixin.java">...</a>
 */
@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin
{
    @Mutable
    @Final
    @Shadow
    private static List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS;

    @Inject(at = @At("RETURN"), method = "<clinit>")
    private static void addTypes(CallbackInfo ci)
    {
        PARTICLE_TEXTURE_SHEETS = ImmutableList.<ParticleTextureSheet>builder()
            .addAll(PARTICLE_TEXTURE_SHEETS)
            .add(ZapParticle.PARTICLE_EMISSIVE)
            .build();
    }
}
