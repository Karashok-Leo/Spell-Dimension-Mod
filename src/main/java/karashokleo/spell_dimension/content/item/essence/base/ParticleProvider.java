package karashokleo.spell_dimension.content.item.essence.base;

import karashokleo.spell_dimension.util.ParticleUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;

public interface ParticleProvider
{
    default ParticleEffect getParticle(ItemStack stack)
    {
        return ParticleUtil.CRIT;
    }
}
