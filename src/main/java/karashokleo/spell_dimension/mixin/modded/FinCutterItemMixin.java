package karashokleo.spell_dimension.mixin.modded;

import com.obscuria.aquamirae.common.items.weapon.FinCutterItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FinCutterItem.class)
public abstract class FinCutterItemMixin
{
    @ModifyArg(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/obscuria/obscureapi/common/classes/ability/Ability$Builder;mod(I)Lcom/obscuria/obscureapi/common/classes/ability/Ability$Builder;",
                    ordinal = 0
            ),
            remap = false
    )
    private static int modify_0(int amount)
    {
        return 1;
    }

    @ModifyArg(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/obscuria/obscureapi/common/classes/ability/Ability$Builder;mod(I)Lcom/obscuria/obscureapi/common/classes/ability/Ability$Builder;",
                    ordinal = 1
            ),
            remap = false
    )
    private static int modify_1(int amount)
    {
        return 30;
    }
}
