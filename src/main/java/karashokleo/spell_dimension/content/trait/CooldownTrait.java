package karashokleo.spell_dimension.content.trait;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

public abstract class CooldownTrait extends MobTrait
{
    private final IntUnaryOperator cooldown;

    public CooldownTrait(IntSupplier color, IntUnaryOperator cooldown)
    {
        super(color);
        this.cooldown = cooldown;
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity mob, int level)
    {
        var data = getData(difficulty);
        if (data.cooldown <= 0) return;
        data.cooldown--;
    }

    public void trigger(int level, LivingEntity entity, LivingEntity target)
    {
        var diff = MobDifficulty.get(entity);
        if (diff.isEmpty()) return;
        var cap = diff.get();
        var data = getData(cap);
        if (data.cooldown > 0) return;
        action(level, data, cap.owner, target);
        data.cooldown = cooldown.applyAsInt(level);
    }

    public abstract void action(int level, Data data, MobEntity mob, LivingEntity target);

    public CooldownTrait.Data getData(MobDifficulty diff)
    {
        return diff.getOrCreateData(getId(), CooldownTrait.Data::new);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(lv -> Text.literal(cooldown.applyAsInt(lv) / 20d + "").formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int cooldown;
    }
}
