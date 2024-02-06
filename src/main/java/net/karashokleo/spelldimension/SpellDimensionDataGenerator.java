package net.karashokleo.spelldimension;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.data.ModelData;
import net.karashokleo.spelldimension.data.RecipeData;

public class SpellDimensionDataGenerator implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelData::new);
        pack.addProvider(LangData::new);
        pack.addProvider(RecipeData::new);
    }
}