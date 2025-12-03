package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.ServerPlayerCreationCallback;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.ApplyFoodEffectsCallback;
import karashokleo.spell_dimension.content.object.EnlighteningModifier;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.UuidUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class PlayerHealthEvents
{
    public static final UUID FROM_KILL = UuidUtil.getUUIDFromString(SpellDimension.modLoc("from_kill").toString());
    public static final UUID FROM_EAT = UuidUtil.getUUIDFromString(SpellDimension.modLoc("from_eat").toString());
    public static final UUID FROM_ADVANCEMENT = UuidUtil.getUUIDFromString(SpellDimension.modLoc("from_advancement").toString());
    public static final int INITIAL_MAX_HEALTH = 10;
    public static final int FROM_KILL_THRESHOLD = 66;

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
        instance.setBaseValue(INITIAL_MAX_HEALTH);
    }

    private static void onKill(ServerWorld world, Entity entity, LivingEntity killedEntity)
    {
        if (!(entity instanceof PlayerEntity player))
        {
            return;
        }
        int playerLevel = DifficultyLevel.ofAny(player);
        int killedLevel = DifficultyLevel.ofAny(killedEntity);
        if (playerLevel >= killedLevel)
        {
            return;
        }
        EntityAttributeInstance attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attributeInstance == null)
        {
            return;
        }
        EntityAttributeModifier modifier = attributeInstance.getModifier(FROM_KILL);
        double amount = modifier == null ? 0 : modifier.getValue();
        if (amount >= FROM_KILL_THRESHOLD)
        {
            return;
        }
        createHeartModifier(FROM_KILL).applyToEntityOrPlayer(player);
    }

    private static void onEatHeartFood(LivingEntity entity, World world, ItemStack stack)
    {
        if (!stack.isIn(AllTags.HEART_FOOD))
        {
            return;
        }
        createHeartModifier(FROM_EAT).applyToEntityOrPlayer(entity);
    }

    private static final Identifier ROOT_ADVANCEMENT_ID = SpellDimension.modLoc("spell_dimension/root");

    public static void onAdvancementGrant(Advancement advancement, PlayerEntity player)
    {
        if (advancement.getId().equals(ROOT_ADVANCEMENT_ID))
        {
            return;
        }
        AdvancementDisplay display = advancement.getDisplay();
        if (display == null)
        {
            return;
        }
        if (!display.shouldAnnounceToChat())
        {
            return;
        }
        createHeartModifier(FROM_ADVANCEMENT).applyToEntityOrPlayer(player);
    }

    public static EnlighteningModifier createHeartModifier(UUID uuid)
    {
        return new EnlighteningModifier(EntityAttributes.GENERIC_MAX_HEALTH, uuid, 2, EntityAttributeModifier.Operation.ADDITION);
    }
}
