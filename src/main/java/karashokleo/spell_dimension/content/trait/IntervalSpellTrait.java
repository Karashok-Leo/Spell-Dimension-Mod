package karashokleo.spell_dimension.content.trait;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllMiscInit;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Style;
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

    public IntervalSpellTrait(IntUnaryOperator interval, Identifier spellId, float powerFactor)
    {
        super(spellId, powerFactor);
        this.interval = interval;
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity e, int level)
    {
        var data = getData(difficulty);
        if (data.tickCount++ < interval.applyAsInt(level)) return;
        tryAction(difficulty.owner, level, data);
    }

    public Data getData(MobDifficulty diff)
    {
        return diff.getOrCreateData(getId(), Data::new);
    }

    public void tryAction(MobEntity mob, int level, Data data)
    {
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive())
        {
            return;
        }
        int radius = LHConfig.common().items.reflectTrinketRadius;
        if (ReflectTrinket.canReflect(target, this))
            target.getWorld().getEntitiesByClass(
                            LivingEntity.class,
                            target.getBoundingBox().expand(radius),
                            e -> e.distanceTo(mob) < radius &&
                                 !ReflectTrinket.canReflect(e, this)
                    )
                    .forEach(le -> this.action(mob, level, data, le));
        else this.action(mob, level, data, target);
    }

    public void action(MobEntity mob, int level, Data data, LivingEntity target)
    {
        World world = mob.getWorld();

        if (world.getGameRules().get(AllMiscInit.NOTIFY_SPELL_TRAIT_CASTING).get())
        {
            target.sendMessage(SDTexts.TEXT$SPELL_TRAIT$ACTION.get(
                    mob.getName(),
                    this.getName().setStyle(Style.EMPTY.withColor(getColor()))
            ));
        }
        ImpactUtil.performSpell(world, mob, spellId, List.of(target), SpellCast.Action.RELEASE, 1.0F);

        data.tickCount = 0;
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
    }
}
