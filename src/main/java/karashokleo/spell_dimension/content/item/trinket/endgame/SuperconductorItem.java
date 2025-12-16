package karashokleo.spell_dimension.content.item.trinket.endgame;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.event.GenericEvents;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.content.spell.ChainLightningSpell;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllDamageStates;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BooleanSupplier;

public class SuperconductorItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public static final int DURATION_FACTOR = 500;

    public SuperconductorItem()
    {
        super();
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        if (!source.isOf(SpellSchools.LIGHTNING.damageType))
        {
            return;
        }
        if (event.getAmount() <= 0)
        {
            return;
        }
        if (source.hasState(AllDamageStates.CHAIN_LIGHTNING))
        {
            return;
        }
        double power = SpellPower.getSpellPower(SpellSchools.LIGHTNING, entity).baseValue();
        int duration = 1 + (int) power / DURATION_FACTOR;
        Task task = new Task(() -> ChainLightningSpell.spawn(entity.getWorld(), entity, event.getEntity()), duration);
        GenericEvents.schedulePersistent(task);
    }

    private static class Task implements BooleanSupplier
    {
        private final Runnable task;
        private int duration;

        private Task(Runnable task, int duration)
        {
            this.task = task;
            this.duration = duration;
        }

        @Override
        public boolean getAsBoolean()
        {
            task.run();
            duration--;
            return duration <= 0;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$SUPERCONDUCTOR$1.get(DURATION_FACTOR).formatted(Formatting.AQUA));
        tooltip.add(SDTexts.TOOLTIP$SUPERCONDUCTOR$2.get().formatted(Formatting.YELLOW));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
