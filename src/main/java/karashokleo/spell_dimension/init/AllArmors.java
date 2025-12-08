package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.content.item.armor.ArmorSet;
import karashokleo.spell_dimension.content.item.armor.ConfiguredArmorMaterial;
import karashokleo.spell_dimension.content.item.armor.RobeBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.spell_power.api.SpellSchools;

import java.util.function.Supplier;

public class AllArmors
{
    public static final Supplier<Ingredient> WOOL_INGREDIENTS = () -> Ingredient.ofItems(
            Items.WHITE_WOOL,
            Items.ORANGE_WOOL,
            Items.MAGENTA_WOOL,
            Items.LIGHT_BLUE_WOOL,
            Items.YELLOW_WOOL,
            Items.LIME_WOOL,
            Items.PINK_WOOL,
            Items.GRAY_WOOL,
            Items.LIGHT_GRAY_WOOL,
            Items.CYAN_WOOL,
            Items.PURPLE_WOOL,
            Items.BLUE_WOOL,
            Items.BROWN_WOOL,
            Items.GREEN_WOOL,
            Items.RED_WOOL,
            Items.BLACK_WOOL
    );

    public static final Supplier<Ingredient> NETHERITE_INGREDIENTS = () -> Ingredient.ofItems(Items.NETHERITE_INGOT);

    public static ConfiguredArmorMaterial LIGHTNING_MATERIAL = new ConfiguredArmorMaterial(
            "lightning_robe",
            20, 10,
            WOOL_INGREDIENTS,
            1, 3, 2, 1,
            SpellSchools.LIGHTNING,
            3f, 0.05f
    );
    public static ConfiguredArmorMaterial NETHERITE_LIGHTNING_MATERIAL = new ConfiguredArmorMaterial(
            "netherite_lightning_robe",
            30, 15,
            NETHERITE_INGREDIENTS,
            3, 8, 6, 3,
            SpellSchools.LIGHTNING,
            5f, 0.05f
    );
    public static ConfiguredArmorMaterial SOUL_MATERIAL = new ConfiguredArmorMaterial(
            "soul_robe",
            20, 10,
            WOOL_INGREDIENTS,
            1, 3, 2, 1,
            SpellSchools.SOUL,
            3f, 0.05f
    );
    public static ConfiguredArmorMaterial NETHERITE_SOUL_MATERIAL = new ConfiguredArmorMaterial(
            "netherite_soul_robe",
            30, 15,
            NETHERITE_INGREDIENTS,
            3, 8, 6, 3,
            SpellSchools.SOUL,
            5f, 0.05f
    );

    public static ArmorSet LIGHTNING_ROBE;
    public static ArmorSet NETHERITE_LIGHTNING_ROBE;
    public static ArmorSet SOUL_ROBE;
    public static ArmorSet NETHERITE_SOUL_ROBE;

    public static void register()
    {
        LIGHTNING_ROBE = new RobeBuilder("lightning")
                .addEN()
                .addZH("雷电")
                .material(LIGHTNING_MATERIAL)
                .addTag(
                        AllTags.TIER_2_ARMORS,
                        AllTags.MAGIC,
                        AllTags.MAGIC_ARMOR
                )
                .register();
        NETHERITE_LIGHTNING_ROBE = new RobeBuilder("netherite_lightning")
                .addEN("Netherite Lightning")
                .addZH("下界合金雷电")
                .material(NETHERITE_LIGHTNING_MATERIAL)
                .addTag(
                        AllTags.TIER_3_ARMORS,
                        AllTags.MAGIC,
                        AllTags.MAGIC_ARMOR
                )
                .register();
        SOUL_ROBE = new RobeBuilder("soul")
                .addEN()
                .addZH("灵魂")
                .material(SOUL_MATERIAL)
                .addTag(
                        AllTags.TIER_2_ARMORS,
                        AllTags.MAGIC,
                        AllTags.MAGIC_ARMOR
                )
                .register();
        NETHERITE_SOUL_ROBE = new RobeBuilder("netherite_soul")
                .addEN("Netherite Soul")
                .addZH("下界合金灵魂")
                .material(NETHERITE_SOUL_MATERIAL)
                .addTag(
                        AllTags.TIER_3_ARMORS,
                        AllTags.MAGIC,
                        AllTags.MAGIC_ARMOR
                )
                .register();
    }
}
