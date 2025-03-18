package karashokleo.spell_dimension.content.trait;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class LeechTrait extends MobTrait
{
    private static final float LEECH_RATIO_PER_LV = 0.2f;

    public LeechTrait()
    {
        super(() -> 0xff0500);
    }

    @Override
    public void onHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        entity.heal(event.getAmount() * LEECH_RATIO_PER_LV * level);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(i -> Text.literal(Math.round(LEECH_RATIO_PER_LV * i * 100) + "%")
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }
}
