package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.ServerPlayerCreationCallback;
import karashokleo.spell_dimension.api.ApplyFoodEffectsCallback;
import karashokleo.spell_dimension.content.item.logic.EnlighteningModifier;
import karashokleo.spell_dimension.util.TagUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PlayerHealthEvent
{
    public static final TagKey<Item> HEART_FOOD = TagUtil.itemTag("heart_food");
    public static final int XP_THRESHOLD = 6;
    public static final int HEALTH_THRESHOLD = 66;

    public static void init()
    {
        ServerPlayerCreationCallback.EVENT.register(PlayerHealthEvent::setPlayerBaseHealth);
        PlayerEvents.XP_CHANGE.register(PlayerHealthEvent::onXpChange);
        ApplyFoodEffectsCallback.EVENT.register(PlayerHealthEvent::onEatHeartFood);
    }

    private static void setPlayerBaseHealth(PlayerEntity player)
    {
        EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        assert instance != null;
        instance.setBaseValue(10);
    }

    private static void onXpChange(PlayerEvents.XpChange event)
    {
        int amount = event.getAmount();
        if (amount <= 0) return;
        PlayerEntity player = event.getPlayer();
        Random random = player.getRandom();
        if (random.nextInt(amount) < XP_THRESHOLD ||
                random.nextInt(HEALTH_THRESHOLD) < player.getMaxHealth()) return;
        addHeart(player);
    }

    private static void onEatHeartFood(LivingEntity entity, World world, ItemStack stack)
    {
        if (stack.isIn(HEART_FOOD))
            addHeart(entity);
    }

    public static void addHeart(LivingEntity entity)
    {
        createHeartModifier().applyToEntityOrPlayer(entity);
    }

    public static EnlighteningModifier createHeartModifier()
    {
        return new EnlighteningModifier(EntityAttributes.GENERIC_MAX_HEALTH, UuidUtil.getSelfUuid(EntityAttributeModifier.Operation.ADDITION), 2, EntityAttributeModifier.Operation.ADDITION);
    }
}
