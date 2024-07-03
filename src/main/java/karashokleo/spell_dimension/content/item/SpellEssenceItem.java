package karashokleo.spell_dimension.content.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import karashokleo.spell_dimension.init.AllConfigs;
import karashokleo.spell_dimension.data.LangData;
import karashokleo.spell_dimension.util.ParticleUtil;
import karashokleo.spell_dimension.util.SoundUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

public abstract class SpellEssenceItem extends Item
{
    protected SpellEssenceItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Nullable
    public SpellSchool getSchool(ItemStack stack)
    {
        return null;
    }

    protected void success(ItemStack essence, PlayerEntity player)
    {
        SpellSchool school = getSchool(essence);
        ParticleUtil.ringParticleEmit(player, 4 * 30, 5, school);
        SoundUtil.playSound(player, school);
        player.sendMessage(Text.translatable(LangData.TITLE_SUCCESS), true);
        player.getItemCooldownManager().set(this, AllConfigs.misc.value.spell_essence_cool_down);
        if (!player.isCreative()) essence.decrement(1);
    }

    protected void fail(ItemStack essence, PlayerEntity player)
    {
        player.sendMessage(Text.translatable(LangData.TITLE_FAILURE), true);
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return true;
    }
}