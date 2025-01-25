package karashokleo.spell_dimension.content.item.essence.base;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.ParticleUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class SpellEssenceItem extends Item implements ColorProvider, ParticleProvider
{
    protected static int COOLDOWN = 10;

    protected SpellEssenceItem(Settings settings)
    {
        super(settings);
    }

    protected void success(ItemStack essence, PlayerEntity player)
    {
        ParticleUtil.ringParticleEmit(player, 4 * 30, 5, getParticle(essence));
        player.sendMessage(SDTexts.TEXT$ESSENCE$SUCCESS.get(), true);
        player.getItemCooldownManager().set(this, COOLDOWN);
        if (!player.getAbilities().creativeMode) essence.decrement(1);
    }

    protected void fail(ItemStack essence, PlayerEntity player)
    {
        player.sendMessage(SDTexts.TEXT$ESSENCE$FAIL.get(), true);
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return true;
    }
}