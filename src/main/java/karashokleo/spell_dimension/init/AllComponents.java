package karashokleo.spell_dimension.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.component.BlazingMarkComponent;
import karashokleo.spell_dimension.content.component.NucleusComponent;

public class AllComponents
{
    public static final ComponentKey<BlazingMarkComponent> BLAZING_MARK = ComponentRegistry.getOrCreate(SpellDimension.modLoc("blazing_mark"), BlazingMarkComponent.class);
    public static final ComponentKey<NucleusComponent> NUCLEUS = ComponentRegistry.getOrCreate(SpellDimension.modLoc("nucleus"), NucleusComponent.class);
}
