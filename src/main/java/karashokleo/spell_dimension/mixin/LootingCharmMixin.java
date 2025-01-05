package karashokleo.spell_dimension.mixin;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.BaseTrinketItem;
import karashokleo.l2hostility.content.item.trinket.misc.LootingCharm;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(LootingCharm.class)
public abstract class LootingCharmMixin extends BaseTrinketItem
{
    public LootingCharmMixin(Settings settings)
    {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        var map = super.getModifiers(stack, slot, entity, uuid);
        SlotAttributes.addSlotModifier(map, "chest/charm", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
        return map;
    }
}
