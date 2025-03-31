package karashokleo.spell_dimension.init;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.event.*;
import karashokleo.spell_dimension.content.spell.LightSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.spell_power.api.SpellPowerTags;

import java.util.Optional;

public class AllEvents
{
    public static void init()
    {
        PlayerHealthEvents.init();
        EnchantmentEvents.init();
        DifficultyEvents.init();
        TrinketEvents.init();
        LightSpell.init();
        ConsciousOceanEvents.init();
        initAdaptiveCompat();
        MiscEvents.init();
    }

    private static final String ADAPTIVE_CACHE = "[SpellCache]";

    private static void initAdaptiveCompat()
    {
        SpellImpactEvents.BEFORE.register((world, caster, targets, spellInfo) ->
        {
            for (Entity target : targets)
            {
                Optional<MobDifficulty> optional = MobDifficulty.get(target);
                if (optional.isEmpty()) return;
                MobDifficulty diff = optional.get();
                AdaptingTrait.Data data = diff.getOrCreateData(LHTraits.ADAPTIVE.getId(), AdaptingTrait.Data::new);
                int adaptLv = diff.getTraitLevel(LHTraits.ADAPTIVE);
                if (adaptLv > 0)
                    data.adapt(ADAPTIVE_CACHE + spellInfo.id().toString(), adaptLv);
            }
        });
    }

    /**
     * @return true to cancel further processing
     */
    public static boolean adaptSpell(int level, LivingEntity entity, LivingHurtEvent event, AdaptingTrait.Data data)
    {
        if (event.getSource().isIn(SpellPowerTags.DamageType.ALL))
        {
            var cap = MobDifficulty.get(entity);
            if (cap.isEmpty()) return false;

            Optional<String> firstToken = data.memory.stream().filter(string -> string.startsWith(ADAPTIVE_CACHE)).findFirst();
            if (firstToken.isEmpty()) return false;

            data.adapt(firstToken.get(), level)
                    .ifPresent(factor -> event.setAmount(event.getAmount() * factor));
            return true;
        }
        return false;
    }
}
