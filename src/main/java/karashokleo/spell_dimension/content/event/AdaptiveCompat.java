package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.init.AllEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_power.api.SpellPowerTags;

import java.util.List;
import java.util.Optional;

public class AdaptiveCompat
{
    private static final String ADAPTIVE_CACHE = "[SpellCache]";

    public static void postSpellImpact(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        for (Entity target : targets)
        {
            Optional<MobDifficulty> optional = MobDifficulty.get(target);
            if (optional.isEmpty())
            {
                return;
            }
            MobDifficulty diff = optional.get();
            AdaptingTrait.Data data = diff.getOrCreateData(LHTraits.ADAPTIVE.getId(), AdaptingTrait.Data::new);
            int adaptLv = diff.getTraitLevel(LHTraits.ADAPTIVE);
            if (adaptLv <= 0)
            {
                continue;
            }
            String token = ADAPTIVE_CACHE + spellInfo.id().toString();
            data.adapt(token, adaptLv);
            int antiLv = EnchantmentHelper.getEquipmentLevel(AllEnchantments.ANTI_ADAPTION, caster);
            for (int i = 0; i < antiLv; i++)
            {
                data.adapt(token + "[Anti]" + i, adaptLv);
            }
        }
    }

    /**
     * @return true to cancel further processing
     */
    public static boolean adaptSpell(int level, LivingEntity entity, LivingHurtEvent event, AdaptingTrait.Data data)
    {
        if (event.getSource().isIn(SpellPowerTags.DamageType.ALL))
        {
            var cap = MobDifficulty.get(entity);
            if (cap.isEmpty())
            {
                return false;
            }

            Optional<String> firstToken = data.memory.stream().filter(string -> string.startsWith(ADAPTIVE_CACHE)).findFirst();
            if (firstToken.isEmpty())
            {
                return false;
            }

            data.adapt(firstToken.get(), level)
                .ifPresent(factor -> event.setAmount(event.getAmount() * factor));
            return true;
        }
        return false;
    }
}
