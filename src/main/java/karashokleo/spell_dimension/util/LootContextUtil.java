package karashokleo.spell_dimension.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

public class LootContextUtil
{
    @Nullable
    public static PlayerEntity getContextPlayer(LootContext context)
    {
        PlayerEntity player = null;
        if (context.get(LootContextParameters.THIS_ENTITY) instanceof PlayerEntity thisEntity)
            player = thisEntity;
        if (context.get(LootContextParameters.KILLER_ENTITY) instanceof PlayerEntity killerEntity)
            player = killerEntity;
        return player;
    }

    @Nullable
    public static DamageSource getContextDamageSource(LootContext context)
    {
        return context.get(LootContextParameters.DAMAGE_SOURCE);
    }

    @Nullable
    public static SpellSchool getContextSchool(LootContext context)
    {
        DamageSource source = getContextDamageSource(context);
        return source == null ? null : SchoolUtil.getDamageSchool(source);
    }
}
