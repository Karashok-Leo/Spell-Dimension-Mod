package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;
import net.spell_engine.api.spell.CustomSpellHandler;

import java.util.List;

public class RandomEffectSpell
{
    public static final int MAX_AMPLIFIER = 3;
    public static final int DURATION = 30;

    public static Boolean handle(CustomSpellHandler.Data data, StatusEffectCategory category)
    {
        Random random = data.caster().getRandom();
        boolean success = false;
        List<StatusEffect> effects = Registries.STATUS_EFFECT.stream().filter(effect -> effect.getCategory() == category).toList();
        for (Entity target : data.targets())
        {
            if (!(target instanceof LivingEntity living)) continue;
            living.addStatusEffect(
                    new StatusEffectInstance(
                            RandomUtil.randomFromList(random, effects),
                            DURATION * 20,
                            random.nextInt(MAX_AMPLIFIER)
                    )
            );
            success = true;
        }
        return success;
    }
}
