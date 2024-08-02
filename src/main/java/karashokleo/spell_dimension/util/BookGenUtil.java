package karashokleo.spell_dimension.util;

import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.condition.BookEntryReadConditionModel;
import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BookGenUtil
{
    public static final String NAMESPACE = SpellDimension.MOD_ID + "-book";

    public static Identifier id(String path)
    {
        return new Identifier(NAMESPACE, path);
    }

    public static Item getItem(Identifier id)
    {
        return Registries.ITEM.get(id);
    }

    public static Ingredient getIngredient(Identifier... ids)
    {
        return getIngredient(Arrays.stream(ids).map(id -> Registries.ITEM.get(id).getDefaultStack()));
    }

    public static Ingredient getIngredient(List<ItemStack> stacks)
    {
        return getIngredient(stacks.stream());
    }

    public static Ingredient getIngredient(Stream<ItemStack> stacks)
    {
        return Ingredient.ofStacks(stacks);
    }

    public static Ingredient getNbtIngredient(ItemStack stack)
    {
        return DefaultCustomIngredients.nbt(stack, true);
    }

    public static Ingredient getNbtIngredient(List<ItemStack> stacks)
    {
        return DefaultCustomIngredients.any(stacks.stream().map(BookGenUtil::getNbtIngredient).toArray(Ingredient[]::new));
    }

    public static void setParent(BookEntryModel sub, BookEntryModel parent)
    {
        sub.withParent(parent);
        sub.withCondition(BookEntryReadConditionModel.builder().withEntry(parent.getId()).build());
    }
}
