package karashokleo.spell_dimension.init;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.spell.ConvergeSpell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageTypes;
import net.spell_engine.api.spell.CustomSpellHandler;

import java.util.Optional;

public class AllSpells
{
    private static final String ADAPTIVE_DATA_PREFIX = "[SpellCache]";

    public static void register()
    {
        LivingDamageEvent.DAMAGE.register(BlazingMark::mark);
        CustomSpellHandler.register(Nucleus.NUCLEUS, data -> Nucleus.handle((CustomSpellHandler.Data) data));
        SpellProjectileHitEntityCallback.EVENT.register((projectile, spellId, hitResult) -> ConvergeSpell.convergeImpact(projectile, spellId));
        SpellProjectileHitBlockCallback.EVENT.register((projectile, spellId, hitResult) -> ConvergeSpell.convergeImpact(projectile, spellId));

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
                    data.adapt(ADAPTIVE_DATA_PREFIX + spellInfo.id().toString(), adaptLv);
            }
        });

        AdaptingTrait.EVENT.register((level, entity, event, data) ->
        {
            if (event.getSource().isOf(DamageTypes.MAGIC))
            {
                Optional<String> first = data.memory.stream().filter(s -> s.startsWith(ADAPTIVE_DATA_PREFIX)).findFirst();
                if (first.isPresent()) return first.get();
            }
            return null;
        });
    }
}