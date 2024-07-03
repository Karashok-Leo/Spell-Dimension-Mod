package karashokleo.spell_dimension.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.component.BlazingMarkComponent;
import karashokleo.spell_dimension.content.component.NucleusComponent;
import karashokleo.spell_dimension.content.component.MageComponent;
import net.minecraft.entity.LivingEntity;

public class AllComponents
{
    public static final ComponentKey<MageComponent> MAGE = ComponentRegistry.getOrCreate(SpellDimension.modLoc("mage"), MageComponent.class);
    public static final ComponentKey<BlazingMarkComponent> BLAZING_MARK = ComponentRegistry.getOrCreate(SpellDimension.modLoc("blazing_mark"), BlazingMarkComponent.class);
    public static final ComponentKey<NucleusComponent> NUCLEUS = ComponentRegistry.getOrCreate(SpellDimension.modLoc("nucleus"), NucleusComponent.class);

    public static void register(EntityComponentFactoryRegistry registry)
    {
        registry.registerForPlayers(MAGE, MageComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(LivingEntity.class, BLAZING_MARK, BlazingMarkComponent::new);
        registry.registerFor(LivingEntity.class, NUCLEUS, NucleusComponent::new);
    }
}
