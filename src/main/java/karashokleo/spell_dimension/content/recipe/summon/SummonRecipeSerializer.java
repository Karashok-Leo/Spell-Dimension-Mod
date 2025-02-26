package karashokleo.spell_dimension.content.recipe.summon;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class SummonRecipeSerializer implements RecipeSerializer<SummonRecipe>
{
    public static final SummonRecipeSerializer INSTANCE = new SummonRecipeSerializer();

    @Override
    public SummonRecipe read(Identifier id, JsonObject json)
    {
        Ingredient ingredient = Ingredient.fromJson(JsonHelper.getElement(json, "ingredient"));
        Identifier entityTypeId = new Identifier(JsonHelper.getString(json, "entity_type"));
        EntityType<?> entityType = Registries.ENTITY_TYPE.getOrEmpty(entityTypeId).orElseThrow(() -> new JsonParseException("Unknown entity type: " + entityTypeId));
        int count = JsonHelper.getInt(json, "count");
        return new SummonRecipe(id, ingredient, entityType, count);
    }

    @Override
    public SummonRecipe read(Identifier id, PacketByteBuf buf)
    {
        Ingredient ingredient = Ingredient.fromPacket(buf);
        EntityType<?> entityType = Registries.ENTITY_TYPE.get(buf.readVarInt());
        int count = buf.readVarInt();
        return new SummonRecipe(id, ingredient, entityType, count);
    }

    @Override
    public void write(PacketByteBuf buf, SummonRecipe recipe)
    {
        recipe.ingredient().write(buf);
        buf.writeVarInt(Registries.ENTITY_TYPE.getRawId(recipe.entityType()));
        buf.writeVarInt(recipe.count());
    }
}
