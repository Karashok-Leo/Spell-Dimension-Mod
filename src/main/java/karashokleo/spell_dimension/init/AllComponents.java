package karashokleo.spell_dimension.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.component.*;

public class AllComponents
{
    public static final ComponentKey<EnlighteningComponent> ENLIGHTENING = ComponentRegistry.getOrCreate(SpellDimension.modLoc("enlightening"), EnlighteningComponent.class);
    public static final ComponentKey<EndStageComponent> ENTER_END = ComponentRegistry.getOrCreate(SpellDimension.modLoc("end_stage"), EndStageComponent.class);
    public static final ComponentKey<ConsciousModeComponent> CONSCIOUS_MODE = ComponentRegistry.getOrCreate(SpellDimension.modLoc("conscious_mode"), ConsciousModeComponent.class);
    public static final ComponentKey<QuestComponent> QUEST = ComponentRegistry.getOrCreate(SpellDimension.modLoc("quest"), QuestComponent.class);
    public static final ComponentKey<BuffComponent> BUFF = ComponentRegistry.getOrCreate(SpellDimension.modLoc("buff"), BuffComponent.class);
}
