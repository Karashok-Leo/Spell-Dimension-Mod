package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AllEntities
{
    public static EntityType<LocatePortalEntity> LOCATE_PORTAL;
    public static EntityType<ConsciousnessEventEntity> CONSCIOUSNESS_EVENT;
    public static EntityType<BlackHoleEntity> BLACK_HOLE;
    public static EntityType<ChainLightningEntity> CHAIN_LIGHTNING;
    public static EntityType<BallLightningEntity> BALL_LIGHTNING;
    public static EntityType<RailgunEntity> RAILGUN;

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
        CHAIN_LIGHTNING = FabricEntityTypeBuilder.<ChainLightningEntity>create()
                .entityFactory(ChainLightningEntity::new)
                .spawnGroup(SpawnGroup.MISC)
                .dimensions(EntityDimensions.fixed(1, 1))
                .fireImmune()
                .trackRangeChunks(4)
                .build();
        BALL_LIGHTNING = FabricEntityTypeBuilder.<BallLightningEntity>create()
                .entityFactory(BallLightningEntity::new)
                .spawnGroup(SpawnGroup.MISC)
                .dimensions(EntityDimensions.fixed(1, 1))
                .fireImmune()
                .trackRangeChunks(4)
                .build();
        RAILGUN = FabricEntityTypeBuilder.<RailgunEntity>create()
                .entityFactory(RailgunEntity::new)
                .spawnGroup(SpawnGroup.MISC)
                .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                .fireImmune()
                .trackRangeChunks(4)
                .build();
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("locate_portal"), LOCATE_PORTAL);
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("consciousness_event"), CONSCIOUSNESS_EVENT);
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("black_hole"), BLACK_HOLE);
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("chain_lightning"), CHAIN_LIGHTNING);
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("ball_lightning"), BALL_LIGHTNING);
        Registry.register(Registries.ENTITY_TYPE, SpellDimension.modLoc("railgun"), RAILGUN);
    }
}
