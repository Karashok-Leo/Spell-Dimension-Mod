package karashokleo.spell_dimension.mixin.modded;

import dev.emi.trinkets.api.TrinketInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TrinketInventory.class)
public abstract class TrinketInventoryMixin
{
    @Shadow
    private DefaultedList<ItemStack> stacks;

    @Shadow(remap = false)
    public abstract void update();

    /**
     * @author Karashok Leo
     * @reason try to fix ArrayIndexOutOfBoundsException
     */
    @Overwrite
    public ItemStack getStack(int slot)
    {
        this.update();
        if (slot >= this.stacks.size()) return ItemStack.EMPTY;
        return this.stacks.get(slot);
    }

    /**
     * @author Karashok Leo
     * @reason try to fix ArrayIndexOutOfBoundsException
     */
    @Overwrite
    public void setStack(int slot, ItemStack stack)
    {
        this.update();
        if (slot >= this.stacks.size()) return;
        this.stacks.set(slot, stack);
    }
}
