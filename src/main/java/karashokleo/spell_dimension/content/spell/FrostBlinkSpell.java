package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;

import java.util.List;

public class FrostBlinkSpell
{
    public static final Identifier MINI_ICICLE = SpellDimension.modLoc("mini_icicle");

    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (world.isClient())
        {
            return;
        }

        Spell spell = SpellRegistry.getSpell(MINI_ICICLE);

        SpellHelper.ImpactContext context = ImpactUtil.createContext(caster, spell);

        for (int i = 0; i < 360; i += 18)
        {
            ImpactUtil.shootProjectile(world, caster, caster.getPos().add(0, caster.getHeight() / 2, 0), ImpactUtil.fromEulerAngle(0, i, 0), 0, new SpellInfo(spell, MINI_ICICLE), context);
        }
    }
}
