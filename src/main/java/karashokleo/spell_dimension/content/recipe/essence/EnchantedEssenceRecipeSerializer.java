package karashokleo.spell_dimension.content.recipe.essence;

import com.google.gson.JsonObject;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

public class EnchantedEssenceRecipeSerializer implements RecipeSerializer<EnchantedEssenceRecipe>
{
    public static final EnchantedEssenceRecipeSerializer INSTANCE = new EnchantedEssenceRecipeSerializer();

    @Override
    public EnchantedEssenceRecipe read(Identifier id, JsonObject json)
    {
        int grade = JsonHelper.getInt(json, "grade");
        int threshold = JsonHelper.getInt(json, "threshold");
        EquipmentSlot slot = EquipmentSlot.valueOf(JsonHelper.getString(json, "slot"));
        SpellSchool school = SpellSchools.getSchool(JsonHelper.getString(json, "school"));
        return new EnchantedEssenceRecipe(id, grade, threshold, slot, school);
    }

    @Override
    public EnchantedEssenceRecipe read(Identifier id, PacketByteBuf buf)
    {
        int grade = buf.readVarInt();
        int threshold = buf.readVarInt();
        EquipmentSlot slot = buf.readEnumConstant(EquipmentSlot.class);
        SpellSchool school = SpellSchools.getSchool(buf.readString());
        return new EnchantedEssenceRecipe(id, grade, threshold, slot, school);
    }

    @Override
    public void write(PacketByteBuf buf, EnchantedEssenceRecipe recipe)
    {
        buf.writeVarInt(recipe.grade);
        buf.writeVarInt(recipe.threshold);
        buf.writeEnumConstant(recipe.slot);
        buf.writeString(recipe.school.id.toString());
    }
}
