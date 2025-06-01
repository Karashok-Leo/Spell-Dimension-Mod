package karashokleo.spell_dimension.content.spell;

import karashokleo.enchantment_infusion.content.block.entity.EnchantmentInfusionTableTile;
import karashokleo.spell_dimension.util.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class HeavenlyJusticeSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (world.isClient()) return;
        int multiplier = getEffects(caster, StatusEffectCategory.BENEFICIAL);
        SpellPower.Result spellPower = SpellPower.getSpellPower(SpellSchools.HEALING, caster);
        for (Entity target : targets)
        {
            if (!(target instanceof LivingEntity living)) continue;
            int effects = getEffects(living, StatusEffectCategory.HARMFUL);
            float damage = (float) (spellPower.randomValue() * (multiplier + effects));
            DamageUtil.spellDamage(living, SpellSchools.HEALING, caster, damage, false);
            EnchantmentInfusionTableTile.spawnLightning(world, living.getPos());
        }
    }

    private static int getEffects(LivingEntity target, StatusEffectCategory category)
    {
        return (int) target.getStatusEffects().stream().filter(effect -> effect.getEffectType().getCategory() == category).count();
    }
}
