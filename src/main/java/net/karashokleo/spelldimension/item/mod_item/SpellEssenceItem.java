package net.karashokleo.spelldimension.item.mod_item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.util.ParticleUtil;
import net.karashokleo.spelldimension.util.SoundUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

public abstract class SpellEssenceItem extends Item
{
    protected SpellEssenceItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Nullable
    public MagicSchool getSchool(ItemStack stack)
    {
        return null;
    }

    protected void success(ItemStack essence, PlayerEntity player)
    {
        MagicSchool school = getSchool(essence);
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