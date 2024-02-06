package net.karashokleo.spelldimension.item.mod_item;

import net.karashokleo.spelldimension.item.ExtraModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class DisenchantedEssenceItem extends SpellEssenceItem
{
    public DisenchantedEssenceItem()
    {
        super();
    }

    @Override
    protected boolean applyEffect(ItemStack stack, PlayerEntity player)
    {
        return ExtraModifier.remove(player.getOffHandStack());
    }
}
