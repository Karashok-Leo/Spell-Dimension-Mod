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
        json.addProperty("grade", grade);
        json.addProperty("school",school.name());
        json.addProperty("slot",slot.name());
        json.addProperty("threshold",threshold);
        json.addProperty("attributeId",attributeId.toString());

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
