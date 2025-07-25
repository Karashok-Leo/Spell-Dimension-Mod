package karashokleo.spell_dimension.content.spell;

import karashokleo.enchantment_infusion.content.block.entity.EnchantmentInfusionTableTile;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class ThunderboltSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        for (Entity target : targets)
        {
            LivingEntity living = ImpactUtil.castToLiving(target);
            if (living == null)
            {
                continue;
            }
            EnchantmentInfusionTableTile.spawnLightning(world, living.getPos());
        }
    }
}
