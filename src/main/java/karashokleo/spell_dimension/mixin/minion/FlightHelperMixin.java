package karashokleo.spell_dimension.mixin.minion;

import io.github.ladysnake.pal.VanillaAbilities;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tocraft.walkers.api.FlightHelper;

@Mixin(FlightHelper.class)
public interface FlightHelperMixin
{
    /**
     * @author Karashok Leo
     * @reason use PAL
     */
    @Overwrite
    static boolean hasFlight(ServerPlayerEntity player)
    {
        return SoulControl.MINION_FLY.grants(player, VanillaAbilities.ALLOW_FLYING);
    }

    /**
     * @author Karashok Leo
     * @reason use PAL
     */
    @Overwrite
    static void grantFlightTo(ServerPlayerEntity player)
    {
        if (!FlightHelper.GRANT.invoke().event(player).isAccepted())
        {
            SoulControl.MINION_FLY.grantTo(player, VanillaAbilities.ALLOW_FLYING);
        }
    }

    /**
     * @author Karashok Leo
     * @reason use PAL
     */
    @Overwrite
    static void revokeFlight(ServerPlayerEntity player)
    {
        if (!FlightHelper.REVOKE.invoke().event(player).isAccepted())
        {
            SoulControl.MINION_FLY.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
        }
    }
}
