package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.entity.BlackHoleEntity;
import karashokleo.spell_dimension.content.entity.ConsciousnessEventEntity;
import karashokleo.spell_dimension.content.entity.LocatePortalEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AllEntities
{
    public static EntityType<LocatePortalEntity> LOCATE_PORTAL;
    public static EntityType<ConsciousnessEventEntity> CONSCIOUSNESS_EVENT;
    public static EntityType<BlackHoleEntity> BLACK_HOLE;

    public static void register()
    {
        LOCATE_PORTAL = FabricEntityTypeBuilder.<LocatePortalEntity>create()
                .entityFactory(LocatePortalEntity::new)
                .fireImmune()
                .disableSummon()
                .build();
        CONSCIOUSNESS_EVENT = FabricEntityTypeBuilder.<ConsciousnessEventEntity>create()
                .entityFactory(ConsciousnessEventEntity::new)
                .fireImmune()
                .disableSummon()
                .build();
        BLACK_HOLE = FabricEntityTypeBuilder.<BlackHoleEntity>create()
                .entityFactory(BlackHoleEntity::new)
                .spawnGroup(SpawnGroup.MISC)
                .fireImmune()
                .trackRangeChunks(10)
                .trackedUpdateRate(10)
                .build();
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("locate_portal"), LOCATE_PORTAL);
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("consciousness_event"), CONSCIOUSNESS_EVENT);
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("black_hole"), BLACK_HOLE);
    }
}
