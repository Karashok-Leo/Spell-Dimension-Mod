package karashokleo.spell_dimension.content.trait;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.spell_dimension.api.BeamProvider;
import karashokleo.spell_dimension.content.network.S2CBeam;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCast;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.IntUnaryOperator;

import static net.spell_engine.api.spell.Spell.Release.Target.Type.BEAM;

public class ChanelIntervalSpellTrait extends SpellTrait
{
    protected final IntUnaryOperator interval;
    protected final IntUnaryOperator cooldown;

    public ChanelIntervalSpellTrait(IntUnaryOperator interval, IntUnaryOperator cooldown, Identifier spellId, IntUnaryOperator power)
    {
        super(spellId, power);
        this.interval = interval;
        this.cooldown = cooldown;
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity e, int level)
    {
        var data = getData(difficulty);
        if (data.casting)
        {
            continueCasting(difficulty.owner, level, data);
            return;
        }
        data.tickCount++;
        int interval = this.interval.applyAsInt(level);
        if (data.tickCount >= interval)
        {
            startCasting(difficulty.owner, data);
        } else if (data.tickCount + getCastDuration() == interval)
        {
            tryNotifyTarget(difficulty.owner);
        }
    }

    @Override
    public int getCastDuration()
    {
        return 20;
    }

    public Data getData(MobDifficulty diff)
    {
        return diff.getOrCreateData(getId(), Data::new);
    }

    public void continueCasting(MobEntity mob, int level, Data data)
    {
        if (mob.age % cooldown.applyAsInt(level) != 0)
        {
            return;
        }

        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive())
        {
            World world = mob.getWorld();

            float progress = data.castLength <= 0 ?
                1.0f : (float) (Math.max(world.getTime() - data.startedAt, 0)) / data.castLength;

            ImpactUtil.performSpell(world, mob, spellId, List.of(target), SpellCast.Action.CHANNEL, progress);

            if (progress <= 1.0f)
            {
                updateBeam(mob, true, target);
            } else
            {
                stopCasting(mob, data);
            }
        } else
        {
            stopCasting(mob, data);
        }
    }

    public void startCasting(MobEntity mob, Data data)
    {
        LivingEntity target = mob.getTarget();
        if (target == null || target.isDead())
        {
            return;
        }
        if (ReflectTrinket.canReflect(target, this))
        {
            return;
        }

        SpellCast.Duration details = SpellHelper.getCastTimeDetails(mob, this.getSpell());

        data.tickCount = 0;
        data.casting = true;
        data.castLength = details.length();
        data.startedAt = mob.getWorld().getTime();

        updateBeam(mob, true, target);
    }

    public void stopCasting(MobEntity mob, Data data)
    {
        data.tickCount = 0;
        data.casting = false;
        data.castLength = 0;
        data.startedAt = 0;

        updateBeam(mob, false, null);
    }

    protected void updateBeam(MobEntity entity, boolean beaming, @Nullable LivingEntity target)
    {
        if (!(entity instanceof BeamProvider beamProvider))
        {
            return;
        }
        if (beaming && beamProvider.getBeam() == null)
        {
            var spell = SpellRegistry.getSpell(spellId);
            if (spell != null &&
                spell.release != null &&
                spell.release.target.type == BEAM)
            {
                beamProvider.setBeam(spell.release.target.beam);
                AllPackets.toTracking(entity, new S2CBeam(entity, true, target, spellId));
            }
        } else if (!beaming)
        {
            beamProvider.setBeam(null);
            AllPackets.toTracking(entity, new S2CBeam(entity, false, null, spellId));
        }
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(
            SDTexts.TEXT$SPELL_TRAIT$INTERVAL.get(
                mapLevel(lv -> Text.literal(interval.applyAsInt(lv) / 20d + "").formatted(Formatting.AQUA))
            ).formatted(Formatting.GRAY)
        );
        super.addDetail(list);
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int tickCount;

        @SerialClass.SerialField
        public boolean casting;

        @SerialClass.SerialField
        public int castLength;

        @SerialClass.SerialField
        public long startedAt;
    }
}
