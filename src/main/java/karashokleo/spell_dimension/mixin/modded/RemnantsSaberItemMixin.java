package karashokleo.spell_dimension.mixin.modded;

import com.obscuria.aquamirae.common.items.weapon.RemnantsSaberItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RemnantsSaberItem.class)
public abstract class RemnantsSaberItemMixin
{
    @ModifyArg(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/obscuria/obscureapi/common/classes/ability/Ability$Builder;mod(I)Lcom/obscuria/obscureapi/common/classes/ability/Ability$Builder;"
            )
    )
    private static int modify(int amount)
    {
        return 30;
    }
}
