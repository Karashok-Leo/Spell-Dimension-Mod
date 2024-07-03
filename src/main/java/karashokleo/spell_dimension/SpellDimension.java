package karashokleo.spell_dimension;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import karashokleo.spell_dimension.init.*;
import karashokleo.spell_dimension.content.misc.EnchantedModifier;
import karashokleo.spell_dimension.content.misc.DebugStaffCommand;
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
        AllConfigs.refresh();
        AllItems.register();
        AllGroups.register();
        AllLoots.register();
        AllSkills.register();
        AllEnchantments.register();
        AllStatusEffects.register();
        AllRecipeSerializers.register();
        AllCustomSpellHandles.register();
        EnchantedModifier.init();
        DebugStaffCommand.init();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        AllData.register(fabricDataGenerator);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        AllComponents.register(registry);
    }
}