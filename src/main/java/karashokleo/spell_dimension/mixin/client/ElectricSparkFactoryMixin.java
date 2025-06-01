package karashokleo.spell_dimension.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.spell_engine.client.particle.SpellFlameParticle;
import net.spell_engine.client.util.Color;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SpellFlameParticle.ElectricSparkFactory.class)
public abstract class ElectricSparkFactoryMixin extends SpellFlameParticle.ColoredAnimatedFactory
{
    private ElectricSparkFactoryMixin(Color color, float scale, SpriteProvider spriteProvider)
    {
        super(color, scale, spriteProvider);
    }

    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/spell_engine/client/particle/SpellFlameParticle$ColoredAnimatedFactory;<init>(Lnet/spell_engine/client/util/Color;FLnet/minecraft/client/particle/SpriteProvider;)V"
            ),
            index = 1
    )
    private static float inject_init(float scale)
    {
        return 0.4F;
    }

    @Override
    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i)
    {
        Particle particle = super.createParticle(defaultParticleType, clientWorld, d, e, f, g, h, i);
        int rgb = MathHelper.hsvToRgb(0.5F, 0.5F * clientWorld.getRandom().nextFloat(), 1.0F);
        float red = (float) (rgb >> 16 & 255) / 255.0F;
        float green = (float) (rgb >> 8 & 255) / 255.0F;
        float blue = (float) (rgb & 255) / 255.0F;
        particle.setColor(red, green, blue);
        return particle;
    }
}
