package karashokleo.spell_dimension.mixin.modded;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uk.co.dotcode.asb.event.TooltipEvent;

@Mixin(TooltipEvent.class)
public abstract class ASBTooltipMixin
{
    @Redirect(
            method = "modifyTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Luk/co/dotcode/asb/ComponentManager;createComponent(Ljava/lang/String;Z)Lnet/minecraft/text/MutableText;",
                    ordinal = 4
            )
    )
    private static MutableText inject_modifyTooltip_1(String text, boolean isTranslatable)
    {
        return getAttributeText(text);
    }

    @Redirect(
            method = "modifyTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Luk/co/dotcode/asb/ComponentManager;createComponent(Ljava/lang/String;Z)Lnet/minecraft/text/MutableText;",
                    ordinal = 8
            )
    )
    private static MutableText inject_modifyTooltip_2(String text, boolean isTranslatable)
    {
        return getAttributeText(text);
    }

    @Unique
    private static MutableText getAttributeText(String text)
    {
        EntityAttribute attribute = Registries.ATTRIBUTE.get(new Identifier(text));
        if (attribute == null) return Text.empty();
        return Text.translatable(attribute.getTranslationKey());
    }
}
