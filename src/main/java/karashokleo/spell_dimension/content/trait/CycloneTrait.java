package karashokleo.spell_dimension.content.trait;

import com.spellbladenext.Spellblades;
import com.spellbladenext.entity.CycloneEntity;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.internals.SpellHelper;

import java.util.function.IntUnaryOperator;

public class CycloneTrait extends IntervalSpellTrait
{
    protected final int color;

    public CycloneTrait(IntUnaryOperator interval, Identifier spellId, IntUnaryOperator power, int color)
    {
        super(interval, spellId, power);
        this.color = color;
    }

    @Override
    public void action(MobEntity mob, int level, Data data, LivingEntity target)
    {
        spawnCyclone(mob, target);
        data.tickCount = 0;
    }

    protected void spawnCyclone(MobEntity owner, LivingEntity target)
    {
        SpellHelper.ImpactContext context = ImpactUtil.createContext(owner, this.getSpell());
        CycloneEntity cyclone = new CycloneEntity(Spellblades.CYCLONEENTITY, target.getWorld());
        cyclone.setColor(color);
        cyclone.setOwner(owner);
        cyclone.setPosition(owner.getPos());
        cyclone.target = target;
        cyclone.context = context;
        target.getWorld().spawnEntity(cyclone);
    }
}
