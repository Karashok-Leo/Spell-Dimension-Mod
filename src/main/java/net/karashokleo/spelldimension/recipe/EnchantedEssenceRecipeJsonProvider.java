package net.karashokleo.spelldimension.recipe;

import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

public class EnchantedEssenceRecipeJsonProvider implements RecipeJsonProvider
{
    final Identifier recipeId;
    final int grade;
    final MagicSchool school;
    final EquipmentSlot slot;
    final int threshold;
    final Identifier attributeId;

    public EnchantedEssenceRecipeJsonProvider(Identifier recipeId, int grade, MagicSchool school, EquipmentSlot slot, int threshold, Identifier attributeId)
    {
        this.recipeId = recipeId;
        this.grade = grade;
        this.school = school;
        this.slot = slot;
        this.threshold = threshold;
        this.attributeId = attributeId;
    }

    @Override
    public void serialize(JsonObject json)
    {
        json.addProperty(EnchantedEssenceRecipe.GRADE_KEY, grade);
        json.addProperty(EnchantedEssenceRecipe.SCHOOL_KEY, school.name());
        json.addProperty(EnchantedEssenceRecipe.SLOT_KEY, slot.name());
        json.addProperty(EnchantedEssenceRecipe.THRESHOLD_KEY, threshold);
        json.addProperty(EnchantedEssenceRecipe.ATTRIBUTE_ID_KEY, attributeId.toString());
    }

    @Override
    public Identifier getRecipeId()
    {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return EnchantedEssenceRecipe.Serializer.INSTANCE;
    }

    @Nullable
    @Override
    public JsonObject toAdvancementJson()
    {
        return null;
    }

    @Nullable
    @Override
    public Identifier getAdvancementId()
    {
        return null;
    }
}
