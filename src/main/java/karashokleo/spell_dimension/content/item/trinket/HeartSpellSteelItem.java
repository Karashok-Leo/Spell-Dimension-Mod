package karashokleo.spell_dimension.content.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.init.LHTags;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;

import java.util.UUID;

public class HeartSpellSteelItem extends TrinketItem
{
    private static final String KEY = "Accumulated";

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
            attacker.distanceTo(target) >= 60)
        {
            MobDifficulty.get(target).ifPresent(diff ->
            {
                if (diff.getLevel() >= 100)
                {
                    stack.getOrCreateNbt().putInt(KEY, (int) damage);
                    attacker.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
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
                        "HeartSpellSteel",
                        stack.getOrCreateNbt().getInt(KEY),
                        EntityAttributeModifier.Operation.ADDITION
                )
        );
        return modifiers;
    }
}
