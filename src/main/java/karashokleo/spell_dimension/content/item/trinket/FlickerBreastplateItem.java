package karashokleo.spell_dimension.content.item.trinket;

import dev.emi.trinkets.api.TrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlickerBreastplateItem extends TrinketItem
{
    public FlickerBreastplateItem()
    {
        super(
                new FabricItemSettings()
                        .maxCount(1)
                        .fireproof()
                        .rarity(Rarity.EPIC)
        );
    }

    public boolean willFlicker(LivingEntity entity, LivingEntity attacker)
    {
        double entitySpeed = entity.getMovementSpeed();
        double attackerSpeed = attacker.getMovementSpeed();
        double flickerRatio = attackerSpeed / entitySpeed;
        if (!entity.isOnGround()) flickerRatio /= 2;
        return entity.getRandom().nextDouble() > flickerRatio;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$FLICKER_BREASTPLATE.get().formatted(Formatting.GOLD));
    }
}
