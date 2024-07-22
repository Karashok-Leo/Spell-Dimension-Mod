package karashokleo.spell_dimension;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import karashokleo.spell_dimension.content.component.BlazingMarkComponent;
import karashokleo.spell_dimension.content.component.NucleusComponent;
import karashokleo.spell_dimension.content.item.essence.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.misc.DebugStaffCommand;
import karashokleo.spell_dimension.data.SDChineseProvider;
import karashokleo.spell_dimension.data.SDEnglishProvider;
import karashokleo.spell_dimension.data.SDModelProvider;
import karashokleo.spell_dimension.data.SDRecipeProvider;
import karashokleo.spell_dimension.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class SpellDimension implements ModInitializer, DataGeneratorEntrypoint, EntityComponentInitializer
{
    public static final String MOD_ID = "spell-dimension";

    public static Identifier modLoc(String id)
    {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize()
    {
        AllItems.register();
        AllGroups.register();
        AllLoots.register();
        AllSkills.register();
        AllEnchantments.register();
        AllStatusEffects.register();
        AllRecipeSerializers.register();
        AllSpells.register();
        EnchantedModifier.init();
        DebugStaffCommand.init();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(SDEnglishProvider::new);
        pack.addProvider(SDChineseProvider::new);
        pack.addProvider(SDModelProvider::new);
        pack.addProvider(SDRecipeProvider::new);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerFor(LivingEntity.class, AllComponents.BLAZING_MARK, BlazingMarkComponent::new);
        registry.registerFor(LivingEntity.class, AllComponents.NUCLEUS, NucleusComponent::new);
    }
}