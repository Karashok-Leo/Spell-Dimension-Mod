package karashokleo.spell_dimension.content.trait;

import com.spellbladenext.Spellblades;
import com.spellbladenext.entity.CycloneEntity;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypeFilter;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.function.IntUnaryOperator;

public class CycloneTrait extends IntervalSpellTrait
{
    public CycloneTrait(IntUnaryOperator interval, Identifier spellId, float powerFactor)
    {
        super(interval, spellId, powerFactor);
    }

    @Override
    public void action(MobEntity mob, int level, Data data)
    {
        move(mob);
        data.tickCount = 0;
    }

    protected void stay(MobEntity mob)
    {
        LivingEntity target = mob.getTarget();
        SpellSchool actualSchool = SpellSchools.ARCANE;
        SpellPower.getSpellPower(actualSchool, mob);
        SpellHelper.ImpactContext context = ImpactUtil.createContext(mob, this.getSpell());
        if (mob.getWorld() instanceof ServerWorld world)
        {
            if (world.getEntitiesByType(TypeFilter.instanceOf(CycloneEntity.class), (cyclonex) -> cyclonex.getOwner() == mob && cyclonex.getColor() != 5).isEmpty())
            {
                CycloneEntity cyclone = new CycloneEntity(Spellblades.CYCLONEENTITY, mob.getWorld());
                cyclone.setPos(mob.getX(), mob.getY(), mob.getZ());
                cyclone.setColor(2);
                cyclone.setOwner(mob);
                mob.getWorld().spawnEntity(cyclone);
            }

            SpellHelper.performImpacts(mob.getWorld(), mob, target, mob, new SpellInfo(SpellRegistry.getSpell(Identifier.of(Spellblades.MOD_ID, "maelstrom")), Identifier.of(Spellblades.MOD_ID, "maelstrom")), context);
        }
    }

    protected void move(MobEntity mob)
    {
        LivingEntity target = mob.getTarget();
        SpellHelper.ImpactContext context = ImpactUtil.createContext(mob, this.getSpell());
        if (target != null)
        {
            CycloneEntity cyclone = new CycloneEntity(Spellblades.CYCLONEENTITY, target.getWorld());
            cyclone.setColor(5);
            cyclone.setOwner(mob);
            cyclone.setPosition(mob.getPos().getX(), mob.getPos().getY(), mob.getPos().getZ());
            cyclone.target = target;
            cyclone.context = context;
            target.getWorld().spawnEntity(cyclone);
        }

        if (target == null)
        {
            CycloneEntity cyclone = new CycloneEntity(Spellblades.CYCLONEENTITY, mob.getWorld());
            cyclone.setColor(5);
            cyclone.setOwner(mob);
            cyclone.setPos(mob.getPos().getX(), mob.getPos().getY(), mob.getPos().getZ());
            cyclone.context = context;
            mob.getWorld().spawnEntity(cyclone);
        }
    }
}
