package karashokleo.spell_dimension.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.content.item.MageSpellBookItem;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;

import java.util.List;

public class ModelData extends FabricModelProvider
{
    public ModelData(FabricDataOutput output)
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
        generator.register(AllItems.DEBUG_STAFF, Models.GENERATED);
        generator.register(AllItems.MAGE_MEDAL, Models.GENERATED);
        generator.register(AllItems.ENLIGHTENING_ESSENCE, Models.GENERATED);
        generator.register(AllItems.ENCHANTED_ESSENCE, Models.GENERATED);
        generator.register(AllItems.DISENCHANTED_ESSENCE, Models.GENERATED);
        generator.register(AllItems.MENDING_ESSENCE, Models.GENERATED);

        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            generateItemModel(generator, (Item) AllItems.SPELL_BOOKS.get(school).primary, SpellDimension.modLoc("spell_book/" + SchoolUtil.getName(school) + "/0"));
            for (int i = 0; i < 3; i++)
            {
                generateItemModel(generator, AllItems.BASE_ESSENCES.get(school).get(i), SpellDimension.modLoc("essence/" + SchoolUtil.getName(school) + "/" + i));
                for (List<MageSpellBookItem> books : AllItems.SPELL_BOOKS.get(school).majors.values())
                    generateItemModel(generator, books.get(i), SpellDimension.modLoc("spell_book/" + SchoolUtil.getName(school) + "/" + (i + 1)));
            }
        }
    }

    private void generateItemModel(ItemModelGenerator generator, Item item, Identifier textureId)
    {
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(textureId.withPrefixedPath("item/")), generator.writer);
    }
}