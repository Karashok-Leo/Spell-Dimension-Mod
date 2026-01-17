package karashokleo.spell_dimension.content.item.trinket.endgame;

import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import karashokleo.spell_dimension.content.network.S2CFloatingItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NirvanaStarfallItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public static final int SPELL_POWER_THRESHOLD = 100;
    public static final int DURATION = 20 * 60 * 10;

    public NirvanaStarfallItem()
    {
        super();
    }

    @Override
    public boolean allowDeath(ItemStack stack, LivingEntity entity, DamageSource source, float amount)
    {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
        {
            return true;
        }

        if (SpellPower.getSpellPower(SpellSchools.FIRE, entity).baseValue() < SPELL_POWER_THRESHOLD)
        {
            return true;
        }

        StatusEffectInstance effect = entity.getStatusEffect(AllStatusEffects.NIRVANA);
        int level = effect == null ? 0 : effect.getAmplifier() + 1;

        EffectHelper.forceAddEffectWithEvent(entity, new StatusEffectInstance(AllStatusEffects.NIRVANA, DURATION, level, false, false, true), entity);

        S2CUndying packet = new S2CUndying(entity);
        LHNetworking.toTracking(entity, packet);
        if (entity instanceof ServerPlayerEntity player)
        {
            LHNetworking.toClientPlayer(player, packet);
            S2CFloatingItem floatingItem = new S2CFloatingItem(stack.copy());
            AllPackets.toClientPlayer(player, floatingItem);
        }

        entity.setHealth(entity.getMaxHealth());
        entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0F, 1.0F);
        EffectUtil.streamEffects(entity, StatusEffectCategory.HARMFUL)
            .toList()
            .forEach(entity::removeStatusEffect);

        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$NIRVANA_STARFALL$DEFRAUDING_REAPER.get(
            SPELL_POWER_THRESHOLD,
            DURATION / 20
        ).formatted(Formatting.DARK_RED));
        tooltip.add(SDTexts.TOOLTIP$NIRVANA_STARFALL$NIRVANA_REBIRTH.get().formatted(Formatting.DARK_RED));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
