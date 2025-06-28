package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Trinket.class)
public interface TrinketMixin
{
    @WrapMethod(method = "canEquip")
    private boolean wrap_canEquip(ItemStack stack, SlotReference slot, LivingEntity entity, Operation<Boolean> original)
    {
        if (entity instanceof PlayerEntity player)
        {
            for (int i = 0; i < 3; i++)
            {
                TagKey<Item> tagKey = AllTags.DIFFICULTY_ALLOW_USE_TRINKET.get(i);
                if (stack.isIn(tagKey) &&
                    GameStageComponent.getDifficulty(player) != i)
                {
                    return false;
                }
            }
        }
        return original.call(stack, slot, entity);
    }
}
