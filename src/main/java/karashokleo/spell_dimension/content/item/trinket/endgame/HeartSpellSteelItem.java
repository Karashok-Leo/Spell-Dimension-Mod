package karashokleo.spell_dimension.content.item.trinket.endgame;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class HeartSpellSteelItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    private static final String KEY = "Accumulated";
    private static final String MODIFIER_NAME = "HeartSpellSteel";
    private static final int MAX_DISTANCE = 6;
    private static final int MIN_MOB_LEVEL = 999;
    private static final double ACCUMULATE_RATE = 0.01;
    public static final int COOLDOWN = 100;

    public HeartSpellSteelItem()
    {
        super();
    }

    @Override
    public void afterKilling(ItemStack stack, LivingEntity entity, LivingEntity killed, DamageSource source)
    {
        if (!source.isOf(SpellSchools.HEALING.damageType))
            return;
        if (entity.distanceTo(killed) >= MAX_DISTANCE)
            return;
        if (MobDifficulty.get(killed).map(MobDifficulty::getLevel).orElse(0) <= MIN_MOB_LEVEL)
            return;
        if (!(entity instanceof PlayerEntity player))
            return;
        ItemCooldownManager manager = player.getItemCooldownManager();
        if (manager.isCoolingDown(this))
            return;
        manager.set(this, COOLDOWN);

        NbtCompound nbt = stack.getOrCreateNbt();
        double added = nbt.getDouble(KEY) + killed.getMaxHealth() * ACCUMULATE_RATE;
        nbt.putDouble(KEY, added);

        entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, entity.getSoundCategory(), 1.0f, 1.0f);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        modifiers.put(
                EntityAttributes.GENERIC_MAX_HEALTH,
                new EntityAttributeModifier(
                        uuid,
                        MODIFIER_NAME,
                        stack.getOrCreateNbt().getDouble(KEY),
                        EntityAttributeModifier.Operation.ADDITION
                )
        );
        return modifiers;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(
                SDTexts.TOOLTIP$HEART_SPELL_STEEL$USAGE.get(
                        MAX_DISTANCE,
                        MIN_MOB_LEVEL,
                        "%.1f%%".formatted(ACCUMULATE_RATE * 100),
                        COOLDOWN / 20
                ).formatted(Formatting.DARK_PURPLE)
        );
        tooltip.add(
                SDTexts.TOOLTIP$HEART_SPELL_STEEL$ACCUMULATED.get(
                        "%.1f".formatted(stack.getOrCreateNbt().getDouble(KEY))
                ).formatted(Formatting.GOLD)
        );
    }
}
