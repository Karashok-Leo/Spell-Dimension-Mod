package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.init.AllWorldGen;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;

public class ConsciousOceanEvents
{
    public static void init()
    {
        // ocean consciousness float force
        LivingEntityEvents.LivingTickEvent.TICK.register(event ->
        {
            LivingEntity entity = event.getEntity();
            if (entity.getWorld().getFluidState(entity.getBlockPos()).isIn(AllTags.CONSCIOUSNESS))
                entity.setVelocity(entity.getVelocity().add(0.0, 0.08, 0.0));
        });

        // conscious difficulty bonus
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) ->
        {
            if (!destination.getRegistryKey().equals(AllWorldGen.OC_WORLD)) return;

            AttributeUtil.addModifier(player, LHMiscs.ADD_LEVEL, UuidUtil.getUUIDFromString("spell_dimension:conscious_difficulty"), "Conscious Difficulty Bonus", 100, EntityAttributeModifier.Operation.ADDITION);
        });
    }
}
