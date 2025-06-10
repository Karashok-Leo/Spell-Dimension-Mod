package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.entity.RailgunEntity;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class RailgunSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (AllWorldGen.disableInWorld(world))
        {
            caster.sendMessage(SDTexts.TEXT$BANNED_SPELL.get().formatted(Formatting.RED));
            return;
        }
        RailgunEntity railgun = new RailgunEntity(world, caster);
        railgun.setPosition(caster.getPos().add(0, caster.getStandingEyeHeight() - railgun.getBoundingBox().getYLength() * .5f, 0));
        railgun.setVelocity(caster.getRotationVector().multiply(1.6f));
        world.spawnEntity(railgun);
    }
}
