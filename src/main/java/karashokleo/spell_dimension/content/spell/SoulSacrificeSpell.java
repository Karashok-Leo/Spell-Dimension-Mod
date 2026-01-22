package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.misc.LivingEntityExtensions;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SoulSacrificeSpell
{
    public static final int THRESHOLD = 100;
    public static final float HEALTH_RATIO = 0.9F;

    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }

        if (targets.isEmpty())
        {
            return;
        }

        var target = ImpactUtil.castToLiving(targets.get(0));

        if (!(target instanceof MobEntity mob))
        {
            return;
        }

        if (!SoulControl.isSoulMinion(player, mob))
        {
            return;
        }

        sacrifice(player, mob, mob);
    }

    private static void sacrifice(PlayerEntity caster, LivingEntity minionToSacrifice, LivingEntity minionToDrop)
    {
        if (minionToSacrifice.getMaxHealth() < THRESHOLD)
        {
            return;
        }
        EntityAttributeInstance maxHealth = minionToSacrifice.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (maxHealth == null)
        {
            return;
        }
        maxHealth.addPersistentModifier(new EntityAttributeModifier(AllSpells.SOUL_SACRIFICE.toString(), -HEALTH_RATIO, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        // drop
        ((LivingEntityExtensions) minionToDrop).dropSacrificeLoot(caster);
    }
}
