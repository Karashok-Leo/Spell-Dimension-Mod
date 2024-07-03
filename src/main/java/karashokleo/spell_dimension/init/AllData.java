package karashokleo.spell_dimension.init;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import karashokleo.spell_dimension.data.LangData;
import karashokleo.spell_dimension.data.ModelData;
import karashokleo.spell_dimension.data.RecipeData;

public class AllData
{
    public static void register(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelData::new);
        pack.addProvider(LangData::new);
        pack.addProvider(RecipeData::new);
    }
}