package karashokleo.spell_dimension.content.item.trinket;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProtectiveSpellContainerItem extends SpellContainerItem implements DamageListenerTrinket
{
    public ProtectiveSpellContainerItem()
    {
        super();
    }

    @Override
    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
        SpellSchool school = SchoolUtil.getDamageSchool(event.getSource());
        if (school == null)
        {
            return;
        }
        int schoolAmount = getSchoolAmount(stack, school);
        if (schoolAmount <= 0)
        {
            return;
        }
        final float oldAmount = event.getAmount();
        final float newAmount = Math.max(oldAmount - schoolAmount, 0);
        final float reduction = oldAmount - newAmount;
        changeAmount(stack, school, -(int) Math.ceil(reduction));
        event.setAmount(newAmount);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$CONTAINER$SPELL.get().formatted(Formatting.AQUA));
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            int amount = getSchoolAmount(stack, school);
            if (amount > 0)
            {
                tooltip.add(SDTexts.TOOLTIP$CONTAINER$DAMAGE_REDUCTION.get(
                        SDTexts.getSchoolText(school),
                        amount
                ).setStyle(Style.EMPTY.withColor(school.color)));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
