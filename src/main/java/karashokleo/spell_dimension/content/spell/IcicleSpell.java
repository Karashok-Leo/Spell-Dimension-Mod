package karashokleo.spell_dimension.content.spell;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.item.trinket.endgame.GlacialNuclearEraItem;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.spell_engine.entity.SpellProjectile;

import java.util.Optional;

public class IcicleSpell
{
    public static void handle(SpellProjectile projectile, Identifier spellId, HitResult hitResult)
    {
        if (!spellId.equals(AllSpells.ICICLE)) return;

        Entity owner = projectile.getOwner();
        if (owner == null ||
            owner.isRemoved() ||
            (!(owner instanceof LivingEntity caster)))
        {
            return;
        }

        if (caster.getRandom().nextFloat() > GlacialNuclearEraItem.CHANCE)
        {
            return;
        }

        if (!TrinketCompat.hasItemInTrinket(caster, AllItems.GLACIAL_NUCLEAR_ERA))
        {
            return;
        }

        if (!(hitResult instanceof EntityHitResult result))
        {
            return;
        }
        LivingEntity living = ImpactUtil.castToLiving(result.getEntity());
        if (living == null)
        {
            return;
        }
        if (!living.isAttackable())
        {
            return;
        }
        Optional<Nucleus> nucleus = Buff.get(living, Nucleus.TYPE);
        if (nucleus.isPresent())
        {
            return;
        }
        Buff.apply(living, Nucleus.TYPE, new Nucleus(), caster);
    }
}
