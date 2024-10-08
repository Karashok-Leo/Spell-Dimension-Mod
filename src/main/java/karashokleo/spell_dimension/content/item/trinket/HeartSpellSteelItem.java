package karashokleo.spell_dimension.content.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class HeartSpellSteelItem extends TrinketItem
{
    private static final String KEY = "Accumulated";
    private static final String MODIFIER_NAME = "HeartSpellSteel";
    private static final int MIN_DISTANCE = 32;
    private static final int MIN_MOB_LEVEL = 100;
    private static final double ACCUMULATE_RATE = 0.01;

    public HeartSpellSteelItem()
    {
        super(
                new FabricItemSettings()
                        .fireproof()
                        .maxCount(1)
                        .rarity(Rarity.EPIC)
        );
    }

    public void accumulate(ItemStack stack, LivingEntity attacker, LivingEntity target, DamageSource source, float damage)
    {
        if (source.isIn(LHTags.MAGIC) &&
            damage >= target.getMaxHealth() &&
            attacker.distanceTo(target) >= MIN_DISTANCE)
        {
            MobDifficulty.get(target).ifPresent(diff ->
            {
                if (diff.getLevel() >= MIN_MOB_LEVEL)
                {
                    NbtCompound nbt = stack.getOrCreateNbt();
                    double added = nbt.getDouble(KEY) + damage * ACCUMULATE_RATE;
                    nbt.putDouble(KEY, added);

                    attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, attacker.getSoundCategory(), 1.0f, 1.0f);
                }
            });
        }
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
        tooltip.add(SDTexts.TOOLTIP$HEART_SPELL_STEEL$USAGE.get(MIN_DISTANCE, MIN_MOB_LEVEL, String.format("%.1f%%", ACCUMULATE_RATE * 100)).formatted(Formatting.DARK_PURPLE));
        tooltip.add(SDTexts.TOOLTIP$HEART_SPELL_STEEL$ACCUMULATED.get(String.format("%.1f", stack.getOrCreateNbt().getDouble(KEY))).formatted(Formatting.GOLD));
    }
}
