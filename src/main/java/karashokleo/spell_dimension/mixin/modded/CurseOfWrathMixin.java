package karashokleo.spell_dimension.mixin.modded;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.content.item.trinket.curse.CurseOfWrath;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllDamageTypes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Set;

@Mixin(CurseOfWrath.class)
public abstract class CurseOfWrathMixin extends CurseTrinketItem
{
    @Shadow(remap = false)
    protected abstract void addTooltip(List<Text> list, Set<StatusEffect> set);

    @Shadow(remap = false)
    @Final
    private static Set<StatusEffect> IMMUNE;

    @Override
    public void onHurt(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        if (event.getSource().isOf(AllDamageTypes.OBLIVION_BREASTPLATE))
        {
            return;
        }
        if (!(event.getSource().getAttacker() instanceof LivingEntity attacker))
        {
            return;
        }
        int level = DifficultyLevel.ofAny(attacker) - DifficultyLevel.ofAny(entity);
        if (level > 0)
        {
            double rate = LHConfig.common().items.curse.wrathDamageBonus;
            event.setAmount(event.getAmount() * (float) (1 + level * rate));
        }
    }

    /**
     * @author Karashok-Leo
     * @reason tooltip overwrite
     */
    @Overwrite
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int rate = (int) Math.round(100 * LHConfig.common().items.curse.wrathDamageBonus);
        tooltip.add(SDTexts.TOOLTIP$CURSE_WRATH.get(rate).formatted(Formatting.GOLD));
        addTooltip(tooltip, IMMUNE);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
