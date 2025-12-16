package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.spell_dimension.util.AttributeUtil;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uk.co.dotcode.asb.config.Bonus;
import uk.co.dotcode.asb.event.TooltipEvent;

@Mixin(TooltipEvent.class)
public abstract class ASBTooltipMixin
{
    @Redirect(
        method = "modifyTooltip",
        at = @At(
            value = "INVOKE",
            target = "Luk/co/dotcode/asb/ComponentManager;mergeComponents([Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;",
            ordinal = 5
        )
    )
    private static MutableText inject_modifyTooltip_1(Text[] c, @Local Bonus bonus)
    {
        return getAttributeModifierText(bonus);
    }

    @Redirect(
        method = "modifyTooltip",
        at = @At(
            value = "INVOKE",
            target = "Luk/co/dotcode/asb/ComponentManager;mergeComponents([Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;",
            ordinal = 10
        )
    )
    private static MutableText inject_modifyTooltip_2(Text[] c, @Local Bonus bonus)
    {
        return getAttributeModifierText(bonus);
    }

    @Unique
    private static MutableText getAttributeModifierText(Bonus bonus)
    {
        EntityAttribute attribute = Registries.ATTRIBUTE.get(new Identifier(bonus.name));
        if (attribute == null)
        {
            return Text.empty();
        }
        EntityAttributeModifier.Operation op = EntityAttributeModifier.Operation.fromId(bonus.attributeOperation);
        double amount = bonus.value;
        MutableText text = AttributeUtil.getTooltip(attribute, amount, op);
        if (text == null)
        {
            return Text.empty();
        } else
        {
            return text.setStyle(Style.EMPTY);
        }
    }
}
