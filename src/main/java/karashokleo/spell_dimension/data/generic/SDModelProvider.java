package karashokleo.spell_dimension.data.generic;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;

public class SDModelProvider extends FabricModelProvider
{
    public SDModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
    {
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator)
    {
        generator.register(AllItems.SPELL_SCROLL, Models.GENERATED);
        generator.register(AllItems.DEBUG_STAFF, Models.GENERATED);
        generator.register(AllItems.ENLIGHTENING_ESSENCE, Models.GENERATED);
        generator.register(AllItems.ENCHANTED_ESSENCE, Models.GENERATED);
        generator.register(AllItems.DISENCHANTED_ESSENCE, Models.GENERATED);
        generator.register(AllItems.MENDING_ESSENCE, Models.GENERATED);

        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            for (int i = 0; i < 3; i++)
            {
                generateItemModel(generator, AllItems.BASE_ESSENCES.get(school).get(i), SpellDimension.modLoc("base_essence_" + i));
                generateItemModel(generator, AllItems.SPELL_BOOKS.get(school).get(i), SpellDimension.modLoc("spell_book/" + school.id.getPath() + "/" + i));
            }
        }
    }

    private void generateItemModel(ItemModelGenerator generator, Item item, Identifier textureId)
    {
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(textureId.withPrefixedPath("item/")), generator.writer);
    }
}