package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.quest.FirstDayQuest;
import karashokleo.spell_dimension.content.quest.HealthQuest;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.spell_power.api.SpellPowerMechanics;

import java.util.List;

public class AllQuests
{
    public static final FirstDayQuest FIRST_DAY = new FirstDayQuest(List.of());
    public static final HealthQuest HEALTH = new HealthQuest(20, List.of(
            AllItems.ENLIGHTENING_ESSENCE.getStack(
                    new AttributeModifier(SpellPowerMechanics.HASTE.attribute, 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE).toELM()
            )
    ));

    public static void register()
    {
        register("first_day", FIRST_DAY);
        register("health", HEALTH);
    }

    public static void register(String path, Quest quest)
    {
        QuestRegistry.register(SpellDimension.modLoc(path), quest);
    }
}
