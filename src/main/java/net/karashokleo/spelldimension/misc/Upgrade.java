package net.karashokleo.spelldimension.misc;

import net.karashokleo.spelldimension.component.MageComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;

public record Upgrade(int grade, MagicSchool school, Item originItem, Item upgradeItem)
{
    private static final String GRADE_KEY = "Grade";
    private static final String SCHOOL_KEY = "School";
    private static final String ORIGIN_ITEM_KEY = "OriginItem";
    private static final String UPGRADE_ITEM_KEY = "UpgradeItem";

    private Identifier getOriginItemId()
    {
        return Registries.ITEM.getId(originItem);
    }

    private Identifier getUpgradeItemId()
    {
        return Registries.ITEM.getId(upgradeItem);
    }

    public boolean test(PlayerEntity player)
    {
        return (MageComponent.get(player).greaterThan(new Mage(grade, school, null)) &&
                player.getOffHandStack().isOf(originItem));
    }

    public boolean apply(PlayerEntity player)
    {
        if (test(player))
        {
            ItemStack origin = player.getOffHandStack();
            ItemStack upgrade = upgradeItem.getDefaultStack();
            upgrade.setNbt(origin.getNbt());
            origin.decrement(1);
            player.giveItemStack(upgrade);
            return true;
        }
        return false;
    }

    public void toNbt(NbtCompound nbt)
    {
        nbt.putInt(GRADE_KEY, grade);
        nbt.putString(SCHOOL_KEY, school.name());
        nbt.putString(ORIGIN_ITEM_KEY, getOriginItemId().toString());
        nbt.putString(UPGRADE_ITEM_KEY, getUpgradeItemId().toString());
    }

    public static Upgrade fromNbt(NbtCompound nbt)
    {
        try
        {
            return new Upgrade(
                    nbt.getInt(GRADE_KEY),
                    MagicSchool.valueOf(nbt.getString(SCHOOL_KEY)),
                    Registries.ITEM.get(new Identifier(nbt.getString(ORIGIN_ITEM_KEY))),
                    Registries.ITEM.get(new Identifier(nbt.getString(UPGRADE_ITEM_KEY)))
            );
        } catch (Exception e)
        {
            return null;
        }
    }
}
