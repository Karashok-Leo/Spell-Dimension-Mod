package karashokleo.spell_dimension.content.item.trinket;

import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllDamageTypes;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OblivionBreastplateItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public static final float THRESHOLD_RATIO = 0.5F;
    public static final float OBLIVION_RATIO = 0.1F;
    public static final float MAX_SPELL_POWER_RATIO = 0.5F;
    private static final String AMOUNT_KEY = "OblivionAmount";
    private static final String MAX_AMOUNT_KEY = "MaxOblivionAmount";

    public OblivionBreastplateItem()
    {
        super();
    }

    @Override
    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
        if (event.getSource().isOf(AllDamageTypes.OBLIVION_BREASTPLATE))
            return;

        float originalDamage = event.getAmount();
        float oblivionAmount = getOblivionAmount(stack);
        if (oblivionAmount == 0)
            return;

        if (originalDamage > oblivionAmount)
        {
            event.setAmount(originalDamage - oblivionAmount);
            AllItems.OBLIVION_BREASTPLATE.increaseOblivionAmount(stack, -oblivionAmount);
        } else
        {
            event.setCanceled(true);
            AllItems.OBLIVION_BREASTPLATE.increaseOblivionAmount(stack, -originalDamage);
        }
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        super.tick(stack, slot, entity);
        // Check client side and interval
        if (entity.getWorld().isClient()) return;
        if (entity.age % 20 != 0) return;
        // Refresh max oblivion amount
        double spellPower = SchoolUtil.getEntitySpellPower(entity);
        float maxOblivionAmount = (float) (spellPower * MAX_SPELL_POWER_RATIO);
        stack.getOrCreateNbt().putFloat(MAX_AMOUNT_KEY, maxOblivionAmount);
        // Check health threshold
        float maxHealth = entity.getMaxHealth();
        if (entity.getHealth() < maxHealth * THRESHOLD_RATIO) return;
        // Check if oblivion amount reaches the maximum
        double oblivionAmount = getOblivionAmount(stack);
        if (oblivionAmount >= maxOblivionAmount) return;
        // Apply oblivion damage
        DamageSource source = entity.getDamageSources().create(AllDamageTypes.OBLIVION_BREASTPLATE, entity);
        float amount = maxHealth * OBLIVION_RATIO;
        entity.damage(source, amount);
        // Increase oblivion amount
        increaseOblivionAmount(stack, amount);
    }

    @Override
    public int getItemBarColor(ItemStack stack)
    {
        return 5636095;
    }

    @Override
    public int getItemBarStep(ItemStack stack)
    {
        return Math.round(13.0F * getOblivionAmount(stack) / getMaxOblivionAmount(stack));
    }

    public void increaseOblivionAmount(ItemStack stack, float increase)
    {
        float oblivionAmount = getOblivionAmount(stack);
        stack.getOrCreateNbt().putFloat(AMOUNT_KEY, oblivionAmount + increase);
    }

    public float getOblivionAmount(ItemStack stack)
    {
        return stack.getOrCreateNbt().getFloat(AMOUNT_KEY);
    }

    public float getMaxOblivionAmount(ItemStack stack)
    {
        return stack.getOrCreateNbt().getFloat(MAX_AMOUNT_KEY);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$OBLIVION_BREASTPLATE_1.get(
                "%d%%".formatted((int) (THRESHOLD_RATIO * 100)),
                "%d%%".formatted((int) (OBLIVION_RATIO * 100))
        ).formatted(Formatting.GRAY));
        tooltip.add(SDTexts.TOOLTIP$OBLIVION_BREASTPLATE_2.get(
                "%d%%".formatted((int) (MAX_SPELL_POWER_RATIO * 100))
        ).formatted(Formatting.DARK_GRAY));
        tooltip.add(SDTexts.TOOLTIP$OBLIVION_BREASTPLATE_3.get(getOblivionAmount(stack)).formatted(Formatting.AQUA));
    }
}
