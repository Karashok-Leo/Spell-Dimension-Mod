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
    final int threshold;
    final EquipmentSlot slot;
    final MagicSchool school;

    public EnchantedEssenceRecipeJsonProvider(Identifier recipeId, int grade, int threshold, EquipmentSlot slot, MagicSchool school)
    {
        this.recipeId = recipeId;
        this.grade = grade;
        this.threshold = threshold;
        this.slot = slot;
        this.school = school;
    }

    @Override
    public void serialize(JsonObject json)
    {
        json.addProperty(EnchantedEssenceRecipe.GRADE_KEY, grade);
        json.addProperty(EnchantedEssenceRecipe.THRESHOLD_KEY, threshold);
        json.addProperty(EnchantedEssenceRecipe.SLOT_KEY, slot.name());
        json.addProperty(EnchantedEssenceRecipe.SCHOOL_KEY, school.name());
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
