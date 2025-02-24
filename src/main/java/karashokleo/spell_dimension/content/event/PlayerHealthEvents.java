package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.ServerPlayerCreationCallback;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.spell_dimension.api.ApplyFoodEffectsCallback;
import karashokleo.spell_dimension.content.item.logic.EnlighteningModifier;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.UuidUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class PlayerHealthEvents
{
    public static final int HEALTH_THRESHOLD = 66;

    public static void init()
    {
        ServerPlayerCreationCallback.EVENT.register(PlayerHealthEvents::setPlayerBaseHealth);
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(PlayerHealthEvents::onKill);
        ApplyFoodEffectsCallback.EVENT.register(PlayerHealthEvents::onEatHeartFood);
    }

    private static void setPlayerBaseHealth(PlayerEntity player)
    {
        EntityAttributeInstance instance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        assert instance != null;
        instance.setBaseValue(10);
    }

    private static void onKill(ServerWorld world, Entity entity, LivingEntity killedEntity)
    {
        if (!(entity instanceof PlayerEntity player)) return;
        float playerMaxHealth = player.getMaxHealth();
        if (playerMaxHealth >= HEALTH_THRESHOLD) return;
        if (playerMaxHealth > killedEntity.getMaxHealth()) return;
        int playerLevel = PlayerDifficulty.get(player).getLevel().getLevel();
        int killedLevel = MobDifficulty.get(killedEntity).map(MobDifficulty::getLevel).orElse(0);
        if (playerLevel >= killedLevel) return;
        addHeart(player);
    }

    private static void onEatHeartFood(LivingEntity entity, World world, ItemStack stack)
    {
        if (stack.isIn(AllTags.HEART_FOOD))
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
