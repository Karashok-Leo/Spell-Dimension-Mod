package karashokleo.spell_dimension.content.spell;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.misc.wand.TraitAdderWand;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;

import java.util.List;
import java.util.Set;

public class FireOfRetributionSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!spellInfo.id().equals(AllSpells.FIRE_OF_RETRIBUTION) || world.isClient()) return;
        for (Entity entity : targets)
        {
            if (!(entity instanceof LivingEntity target)) continue;
            if (target.getType().isIn(LHTags.SEMIBOSS)) continue;

            if (target.age % 20 != 0) continue;
            var opt = MobDifficulty.get(target);
            if (opt.isEmpty()) return;
            var cap = opt.get();
            Set<MobTrait> traitSet = cap.traits.keySet();
            if (traitSet.isEmpty()) return;
            MobTrait trait = RandomUtil.randomFromSet(target.getRandom(), traitSet);
            Integer ans = cap.traits.compute(trait, TraitAdderWand::decrease);
            int val = ans == null ? 0 : ans;
            trait.initialize(cap, target, val);
            trait.postInit(cap, target, val);
            cap.sync();

            SpellSchool school = spellInfo.spell().school;
            double damageMultiplier = trait.getCost(1) / 10.0;
            double spellPower = SpellPower.getSpellPower(school, caster).randomValue();
            DamageUtil.spellDamage(target, school, caster, (float) (spellPower * damageMultiplier), true);
        }
    }
}
