package net.karashokleo.spelldimension.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.recipe.EnchantedEssenceRecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.EquipmentSlot;
import net.spell_power.api.MagicSchool;

import java.util.function.Consumer;

public class RecipeData extends FabricRecipeProvider
{
    public RecipeData(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter)
    {
        for (MagicSchool school : MagicSchool.values())
        {
            if (!school.isMagical) continue;
            for (int i = 0; i < 3; i++)
                for (EquipmentSlot slot : EquipmentSlot.values())
                    exporter.accept(new EnchantedEssenceRecipeJsonProvider(SpellDimension.modLoc(school.spellName() + "/" + slot.getName() + "/" + AllItems.GRADES[i]), i, school, slot, (i + 1) * 10, school.attributeId()));
        }
    }
}