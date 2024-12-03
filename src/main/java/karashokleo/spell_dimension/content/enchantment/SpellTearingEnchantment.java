package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SpellTearingEnchantment extends SpellImpactEnchantment
{
    public SpellTearingEnchantment()
    {
        super(Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public void onSpellImpact(World world, LivingEntity caster, Context context, List<Entity> targets, SpellInfo spellInfo)
    {
        float chance = TrinketCompat.hasItemInTrinket(caster, TrinketItems.PLATINUM_STAR) ? 0.5F : 0.25F;
        if (caster.getRandom().nextFloat() > chance) return;
        for (Entity target : targets)
            MobDifficulty.get(target).ifPresent(diff ->
            {
                diff.removeTrait(LHTraits.DISPELL);
                diff.sync();
            });
    }
}
