package karashokleo.spell_dimension.content.item.trinket;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MirageReflectorItem extends CurseTrinketItem implements ReflectTrinket
{
    public static final float MAX_DAMAGE_RATIO = 0.3F;

    public MirageReflectorItem()
    {
        super();
    }

    @Override
    public int getExtraLevel()
    {
        return LHConfig.common().items.abrahadabraExtraLevel;
    }

    @Override
    public void onHurt(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        float amount = event.getAmount();
        float maxDamage = entity.getMaxHealth() * MAX_DAMAGE_RATIO;
        if (amount <= maxDamage) return;
        event.setAmount(maxDamage);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(Text.literal("- ").append(LHTexts.ABRAHADABRA.get()).formatted(Formatting.GOLD));
        tooltip.add(SDTexts.TOOLTIP$MIRAGE_REFLECTOR.get(Math.round(100 * MAX_DAMAGE_RATIO)).formatted(Formatting.GREEN));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean canReflect(MobTrait trait)
    {
        return true;
    }
}
