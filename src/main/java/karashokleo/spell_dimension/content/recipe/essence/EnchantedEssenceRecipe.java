package karashokleo.spell_dimension.content.recipe.essence;

import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.content.object.EnlighteningModifier;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.spell_power.api.SpellSchool;

import java.util.Map;

public class EnchantedEssenceRecipe extends ShapedRecipe
{
    public static final Map<EquipmentSlot, String[]> PATTERNS = Map.of(
            EquipmentSlot.MAINHAND, new String[]{
                    " ##",
                    "###",
                    "## "
            },
            EquipmentSlot.OFFHAND, new String[]{
                    "###",
                    "###",
                    " # "
            },
            EquipmentSlot.HEAD, new String[]{
                    "###",
                    "# #"
            },
            EquipmentSlot.CHEST, new String[]{
                    "# #",
                    "###",
                    "###"
            },
            EquipmentSlot.LEGS, new String[]{
                    "###",
                    "# #",
                    "# #"
            },
            EquipmentSlot.FEET, new String[]{
                    "# #",
                    "# #"
            }
    );

    protected final int grade;
    protected final int threshold;
    protected final EquipmentSlot slot;
    protected final SpellSchool school;

    public EnchantedEssenceRecipe(Identifier id, int grade, int threshold, EquipmentSlot slot, SpellSchool school)
    {
        super(id, "", CraftingRecipeCategory.MISC, getWidth(slot), getHeight(slot), getIngredients(slot, school, grade), AllItems.ENCHANTED_ESSENCE.getStack(
                new EnchantedModifier(
                        threshold,
                        slot,
                        new EnlighteningModifier(
                                school.attribute,
                                UuidUtil.getEquipmentUuid(slot, EntityAttributeModifier.Operation.ADDITION),
                                1.0,
                                EntityAttributeModifier.Operation.ADDITION
                        )
                )
        ));
        this.grade = grade;
        this.threshold = threshold;
        this.slot = slot;
        this.school = school;
    }

    public static int getHeight(EquipmentSlot slot)
    {
        String[] pattern = PATTERNS.get(slot);
        return pattern.length;
    }

    public static int getWidth(EquipmentSlot slot)
    {
        String[] pattern = PATTERNS.get(slot);
        return pattern[0].length();
    }

    public static DefaultedList<Ingredient> getIngredients(EquipmentSlot slot, SpellSchool school, int tier)
    {
        String[] pattern = PATTERNS.get(slot);
        int height = getHeight(slot);
        int width = getWidth(slot);
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(height * width, Ingredient.EMPTY);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (pattern[i].charAt(j) == ' ')
                    defaultedList.set(i * width + j, Ingredient.EMPTY);
                else
                    defaultedList.set(i * width + j, Ingredient.ofItems(AllItems.BASE_ESSENCES.get(school).get(tier)));
        return defaultedList;
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager)
    {
        return getOutput(dynamicRegistryManager);
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager)
    {
        return AllItems.ENCHANTED_ESSENCE.getStack(
                new EnchantedModifier(
                        threshold,
                        slot,
                        new EnlighteningModifier(
                                school.attribute,
                                UuidUtil.getEquipmentUuid(slot, EntityAttributeModifier.Operation.ADDITION),
                                1.0,
                                EntityAttributeModifier.Operation.ADDITION
                        )
                )
        );
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return EnchantedEssenceRecipeSerializer.INSTANCE;
    }
}
