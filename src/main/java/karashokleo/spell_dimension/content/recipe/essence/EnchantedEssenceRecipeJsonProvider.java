package karashokleo.spell_dimension.content.recipe.essence;

import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

public record EnchantedEssenceRecipeJsonProvider(
        Identifier recipeId,
        int grade,
        int threshold,
        EquipmentSlot slot,
        SpellSchool school
) implements RecipeJsonProvider
{
    @Override
    public void serialize(JsonObject json)
    {
        json.addProperty("grade", grade);
        json.addProperty("threshold", threshold);
        json.addProperty("slot", slot.name());
        json.addProperty("school", school.id.toString());
    }

    @Override
    public Identifier getRecipeId()
    {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return EnchantedEssenceRecipeSerializer.INSTANCE;
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
