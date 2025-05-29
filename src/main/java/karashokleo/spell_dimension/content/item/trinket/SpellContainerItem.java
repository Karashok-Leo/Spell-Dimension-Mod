package karashokleo.spell_dimension.content.item.trinket;

import karashokleo.l2hostility.content.item.traits.TraitSymbol;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.content.trait.SpellTrait;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.spell_power.api.SpellSchool;

public class SpellContainerItem extends SingleEpicTrinketItem
{
    protected static final String STORAGE_KEY = "Storage";

    public SpellContainerItem()
    {
        super();
    }

    protected void changeAmount(ItemStack stack, SpellSchool school, int inc)
    {
        NbtCompound nbt = stack.getOrCreateSubNbt(STORAGE_KEY);
        String schoolId = school.id.toString();
        int amount = nbt.getInt(schoolId);
        nbt.putInt(schoolId, amount + inc);
    }

    protected void storage(ItemStack stack, SpellTrait trait, int count)
    {
        changeAmount(stack, trait.getSpell().school, count * trait.getPower(1));
    }

    protected int getSchoolAmount(ItemStack stack, SpellSchool school)
    {
        NbtCompound nbt = stack.getOrCreateSubNbt(STORAGE_KEY);
        return nbt.getInt(school.id.toString());
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player)
    {
        if (clickType != ClickType.RIGHT)
        {
            return false;
        }
        ItemStack clicked = slot.getStack();
        int count = clicked.getCount();
        if (count <= 0)
        {
            return false;
        }
        if (!(clicked.getItem() instanceof TraitSymbol symbol))
        {
            return false;
        }
        if (!(symbol.get() instanceof SpellTrait trait))
        {
            return false;
        }
        storage(stack, trait, count);
        clicked.setCount(0);
        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.6F + player.getWorld().getRandom().nextFloat() * 0.4F);
        return true;
    }
}
