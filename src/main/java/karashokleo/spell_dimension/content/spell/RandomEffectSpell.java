package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class RandomEffectSpell
{
    public static final int MAX_AMPLIFIER = 3;
    public static final int DURATION = 30;

    public static void handleBlessing(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!spellInfo.id().equals(AllSpells.BLESSING)) return;
        handle(world, caster, targets, spellInfo, StatusEffectCategory.BENEFICIAL);
    }

    public static void handleMisfortune(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!spellInfo.id().equals(AllSpells.MISFORTUNE)) return;
        handle(world, caster, targets, spellInfo, StatusEffectCategory.HARMFUL);
    }

    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo, StatusEffectCategory category)
    {
        Random random = caster.getRandom();
        List<StatusEffect> effects = Registries.STATUS_EFFECT.stream().filter(effect -> effect.getCategory() == category).toList();
        for (Entity target : targets)
        {
            if (!(target instanceof LivingEntity living)) continue;
            living.addStatusEffect(
                    new StatusEffectInstance(
                            RandomUtil.randomFromList(random, effects),
                            DURATION * 20,
                            random.nextInt(MAX_AMPLIFIER)
                    )
            );
        }
    }
}
