package karashokleo.spell_dimension.util;

import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.item.trinket.SecondarySchoolItem;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LootContextUtil
{
    @Nullable
    public static PlayerEntity getContextPlayer(LootContext context)
    {
        PlayerEntity player = null;
        if (context.get(LootContextParameters.THIS_ENTITY) instanceof PlayerEntity thisEntity)
        {
            player = thisEntity;
        }
        if (context.get(LootContextParameters.KILLER_ENTITY) instanceof PlayerEntity killerEntity)
        {
            player = killerEntity;
        }
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

    public static AttributeModifier getContextModifier(Random random, LootContext context)
    {
        List<SpellSchool> schools = null;
        PlayerEntity player = getContextPlayer(context);
        if (random.nextFloat() < SecondarySchoolItem.SECONDARY_SCHOOL_RATIO)
        {
            schools = SchoolUtil.getLivingSecondarySchools(player);
        }
        if (schools == null || schools.isEmpty())
        {
            SpellSchool school = getContextSchool(context);
            List<SpellSchool> primarySchools = SchoolUtil.getLivingSchools(player);
            schools = school == null ?
                (player == null ?
                    SchoolUtil.SCHOOLS :
                    primarySchools) :
                List.of(school);
        }
        return AttributeModifier.getRandom(random, schools);
    }
}
