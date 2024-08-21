package karashokleo.spell_dimension.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.component.BuffComponent;
import karashokleo.spell_dimension.content.component.EnlighteningComponent;

public class AllComponents
{
    public static final ComponentKey<EnlighteningComponent> ENLIGHTENING = ComponentRegistry.getOrCreate(SpellDimension.modLoc("enlightening"), EnlighteningComponent.class);
    public static final ComponentKey<BuffComponent> BUFF = ComponentRegistry.getOrCreate(SpellDimension.modLoc("buff"), BuffComponent.class);
}
