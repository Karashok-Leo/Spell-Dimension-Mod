package karashokleo.spell_dimension.mixin.modded;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.content.item.trinket.curse.CurseOfPride;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.UUID;

@Mixin(CurseOfPride.class)
public abstract class CurseOfPrideMixin extends CurseTrinketItem
{
    private CurseOfPrideMixin(Settings settings)
    {
        super(settings);
    }

    @Override
    public void onHurt(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        int level = DifficultyLevel.ofAny(entity);
        double rate = LHConfig.common().items.curse.prideDamageBonus;
        event.setAmount(event.getAmount() * (float) (1 + level * rate));
    }

    /**
     * @author Karashok-Leo
     * @reason modifiers overwrite
     */
    @Overwrite
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        return super.getModifiers(stack, slot, entity, uuid);
    }

    /**
     * @author Karashok-Leo
     * @reason tooltip overwrite
     */
    @Overwrite
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int damage = (int) Math.round(100 * LHConfig.common().items.curse.prideDamageBonus);
        int trait = (int) Math.round(100 * (1 / LHConfig.common().items.curse.prideTraitFactor - 1));
        tooltip.add(SDTexts.TOOLTIP$CURSE_PRIDE_1.get(damage).formatted(Formatting.GOLD));
        tooltip.add(LHTexts.ITEM_CHARM_TRAIT_CHEAP.get(trait).formatted(Formatting.RED));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
