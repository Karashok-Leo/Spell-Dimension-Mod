package karashokleo.spell_dimension.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.component.*;

public class AllComponents
{
    public static final ComponentKey<EnlighteningComponent> ENLIGHTENING = ComponentRegistry.getOrCreate(SpellDimension.modLoc("enlightening"), EnlighteningComponent.class);
    public static final ComponentKey<GameStageComponent> GAME_STAGE = ComponentRegistry.getOrCreate(SpellDimension.modLoc("game_stage"), GameStageComponent.class);
    public static final ComponentKey<QuestComponent> QUEST = ComponentRegistry.getOrCreate(SpellDimension.modLoc("quest"), QuestComponent.class);
    public static final ComponentKey<BuffComponent> BUFF = ComponentRegistry.getOrCreate(SpellDimension.modLoc("buff"), BuffComponent.class);
    public static final ComponentKey<SoulControllerComponent> SOUL_CONTROLLER = ComponentRegistry.getOrCreate(SpellDimension.modLoc("soul_controller"), SoulControllerComponent.class);
}
