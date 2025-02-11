package karashokleo.spell_dimension.content.trait;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.internals.casting.SpellCast;

import java.util.List;
import java.util.function.IntUnaryOperator;

public class IntervalSpellTrait extends SpellTrait
{
    protected final IntUnaryOperator interval;

    public IntervalSpellTrait(IntUnaryOperator interval, Identifier spellId)
    {
        super(spellId);
        this.interval = interval;
    }

    public IntervalSpellTrait(IntUnaryOperator interval, Identifier spellId, float powerFactor)
    {
        super(spellId, powerFactor);
        this.interval = interval;
    }

    @Override
    public void serverTick(LivingEntity e, int level)
    {
        var diff = MobDifficulty.get(e);
        if (diff.isEmpty()) return;
        var cap = diff.get();
        var data = getData(cap);
        if (data.tickCount++ < interval.applyAsInt(level)) return;
        action(cap.owner, level, data);
    }

    public Data getData(MobDifficulty diff)
    {
        return diff.getOrCreateData(getId(), Data::new);
    }

    public void action(MobEntity mob, int level, Data data)
    {
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive())
        {
            World world = mob.getWorld();

            target.sendMessage(SDTexts.TEXT$SPELL_TRAIT$ACTION.get(
                    mob.getName(),
                    this.getName()
            ));
            ImpactUtil.performSpell(world, mob, spellId, List.of(target), SpellCast.Action.RELEASE, 1.0F);

            data.tickCount = 0;
        }
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(
                Text.translatable(
                        getDescKey(),
                        mapLevel(lv -> Text.literal(interval.applyAsInt(lv) / 20d + "").formatted(Formatting.AQUA))
                ).formatted(Formatting.GRAY)
        );
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int tickCount;
    }
}
