package net.karashokleo.spelldimension.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.item.mod_item.MageSpellBookItem;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;

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

        for (MagicSchool school : MagicSchool.values())
        {
            if (!school.isMagical) continue;

            generateItemModel(generator, AllItems.SPELL_BOOKS.get(school).primary, SpellDimension.modLoc("spell_book/" + school.spellName() + "/0"));
            for (int i = 0; i < 3; i++)
            {
                generateItemModel(generator, AllItems.BASE_ESSENCES.get(school).get(i), SpellDimension.modLoc("essence/" + school.spellName() + "/" + i));
                for (List<MageSpellBookItem> books : AllItems.SPELL_BOOKS.get(school).majors.values())
                    generateItemModel(generator, books.get(i), SpellDimension.modLoc("spell_book/" + school.spellName() + "/" + (i + 1)));
            }
        }
    }

    private void generateItemModel(ItemModelGenerator generator, Item item, Identifier textureId)
    {
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(textureId.withPrefixedPath("item/")), generator.writer);
    }
}