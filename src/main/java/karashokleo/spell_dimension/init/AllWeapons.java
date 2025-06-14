package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.content.item.ConfiguredStaffItem;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.weapon.StaffItem;
import net.spell_power.api.SpellSchools;
import net.wizards.WizardsMod;

import java.util.Optional;

public class AllWeapons
{
    private static final float WAND_ATTACK_DAMAGE = 2.0F;
    private static final float WAND_ATTACK_SPEED = -2.4F;
    private static final float STAFF_ATTACK_DAMAGE = 4.0F;
    private static final float STAFF_ATTACK_SPEED = -3.0F;
    private static final float WAND_SPELL_POWER = 6.0F;
    private static final float NETHERITE_WAND_SPELL_POWER = 9.0F;
    private static final float STAFF_SPELL_POWER = 12.0F;
    private static final float NETHERITE_STAFF_SPELL_POWER = 15.0F;

    public static final Model MEDIUM_STAFF_MODEL = new Model(Optional.of(new Identifier(WizardsMod.ID, "item/medium_staff")), Optional.empty(), TextureKey.LAYER0);

    public static StaffItem LIGHTNING_WAND;
    public static StaffItem NETHERITE_LIGHTNING_WAND;
    public static StaffItem LIGHTNING_STAFF;
    public static StaffItem NETHERITE_LIGHTNING_STAFF;
//    public static StaffItem SOUL_WAND;
//    public static StaffItem NETHERITE_SOUL_WAND;
//    public static StaffItem SOUL_STAFF;
//    public static StaffItem NETHERITE_SOUL_STAFF;

    public static void register()
    {
        LIGHTNING_WAND = AllItems.Entry.of(
                        "lightning_wand",
                        new ConfiguredStaffItem(
                                ToolMaterials.IRON,
                                Items.COPPER_INGOT,
                                WAND_ATTACK_DAMAGE,
                                WAND_ATTACK_SPEED,
                                SpellSchools.LIGHTNING,
                                WAND_SPELL_POWER
                        )
                )
                .addEN()
                .addZH("雷电魔杖")
                .addTag(
                        AllTags.WANDS,
                        AllTags.MAGIC,
                        AllTags.MAGIC_WEAPON
                )
                .addModel(Models.HANDHELD)
                .setTab(AllGroups.EQUIPMENTS)
                .register();
        NETHERITE_LIGHTNING_WAND = AllItems.Entry.of(
                        "netherite_lightning_wand",
                        new ConfiguredStaffItem(
                                ToolMaterials.NETHERITE,
                                Items.NETHERITE_INGOT,
                                WAND_ATTACK_DAMAGE,
                                WAND_ATTACK_SPEED,
                                SpellSchools.LIGHTNING,
                                NETHERITE_WAND_SPELL_POWER
                        )
                )
                .addEN()
                .addZH("下界合金雷电魔杖")
                .addTag(
                        AllTags.WANDS,
                        AllTags.MAGIC,
                        AllTags.MAGIC_WEAPON
                )
                .addModel(Models.HANDHELD)
                .setTab(AllGroups.EQUIPMENTS)
                .register();
        LIGHTNING_STAFF = AllItems.Entry.of(
                        "lightning_staff",
                        new ConfiguredStaffItem(
                                ToolMaterials.IRON,
                                Items.COPPER_INGOT,
                                STAFF_ATTACK_DAMAGE,
                                STAFF_ATTACK_SPEED,
                                SpellSchools.LIGHTNING,
                                STAFF_SPELL_POWER
                        )
                )
                .addEN()
                .addZH("雷电权杖")
                .addTag(
                        AllTags.STAVES,
                        AllTags.TIER_2_WEAPONS,
                        AllTags.MAGIC,
                        AllTags.MAGIC_WEAPON
                )
                .addModel(MEDIUM_STAFF_MODEL)
                .setTab(AllGroups.EQUIPMENTS)
                .register();
        NETHERITE_LIGHTNING_STAFF = AllItems.Entry.of(
                        "netherite_lightning_staff",
                        new ConfiguredStaffItem(
                                ToolMaterials.NETHERITE,
                                Items.NETHERITE_INGOT,
                                STAFF_ATTACK_DAMAGE,
                                STAFF_ATTACK_SPEED,
                                SpellSchools.LIGHTNING,
                                NETHERITE_STAFF_SPELL_POWER
                        )
                )
                .addEN()
                .addZH("下界合金雷电权杖")
                .addTag(
                        AllTags.STAVES,
                        AllTags.TIER_3_WEAPONS,
                        AllTags.MAGIC,
                        AllTags.MAGIC_WEAPON
                )
                .addModel(MEDIUM_STAFF_MODEL)
                .setTab(AllGroups.EQUIPMENTS)
                .register();
//        SOUL_WAND = AllItems.Entry.of(
//                        "soul_wand",
//                        new ConfiguredStaffItem(
//                                ToolMaterials.IRON,
//                                Items.IRON_INGOT,
//                                WAND_ATTACK_DAMAGE,
//                                WAND_ATTACK_SPEED,
//                                SpellSchools.SOUL,
//                                WAND_SPELL_POWER
//                        )
//                )
//                .addEN()
//                .addZH("灵魂魔杖")
//                .addTag(
//                        AllTags.WANDS,
//                        AllTags.MAGIC,
//                        AllTags.MAGIC_WEAPON
//                )
//                .addModel(Models.HANDHELD)
//                .setTab(AllGroups.EQUIPMENTS)
//                .register();
//        NETHERITE_SOUL_WAND = AllItems.Entry.of(
//                        "netherite_soul_wand",
//                        new ConfiguredStaffItem(
//                                ToolMaterials.NETHERITE,
//                                Items.NETHERITE_INGOT,
//                                WAND_ATTACK_DAMAGE,
//                                WAND_ATTACK_SPEED,
//                                SpellSchools.SOUL,
//                                NETHERITE_WAND_SPELL_POWER
//                        )
//                )
//                .addEN()
//                .addZH("下界合金灵魂魔杖")
//                .addTag(
//                        AllTags.WANDS,
//                        AllTags.MAGIC,
//                        AllTags.MAGIC_WEAPON
//                )
//                .addModel(Models.HANDHELD)
//                .setTab(AllGroups.EQUIPMENTS)
//                .register();
//        SOUL_STAFF = AllItems.Entry.of(
//                        "soul_staff",
//                        new ConfiguredStaffItem(
//                                ToolMaterials.IRON,
//                                Items.IRON_INGOT,
//                                STAFF_ATTACK_DAMAGE,
//                                STAFF_ATTACK_SPEED,
//                                SpellSchools.SOUL,
//                                STAFF_SPELL_POWER
//                        )
//                )
//                .addEN()
//                .addZH("灵魂权杖")
//                .addTag(
//                        AllTags.STAVES,
//                        AllTags.TIER_2_WEAPONS,
//                        AllTags.MAGIC,
//                        AllTags.MAGIC_WEAPON
//                )
//                .addModel(MEDIUM_STAFF_MODEL)
//                .setTab(AllGroups.EQUIPMENTS)
//                .register();
//        NETHERITE_SOUL_STAFF = AllItems.Entry.of(
//                        "netherite_soul_staff",
//                        new ConfiguredStaffItem(
//                                ToolMaterials.NETHERITE,
//                                Items.NETHERITE_INGOT,
//                                STAFF_ATTACK_DAMAGE,
//                                STAFF_ATTACK_SPEED,
//                                SpellSchools.SOUL,
//                                NETHERITE_STAFF_SPELL_POWER
//                        )
//                )
//                .addEN()
//                .addZH("下界合金灵魂权杖")
//                .addTag(
//                        AllTags.STAVES,
//                        AllTags.TIER_3_WEAPONS,
//                        AllTags.MAGIC,
//                        AllTags.MAGIC_WEAPON
//                )
//                .addModel(MEDIUM_STAFF_MODEL)
//                .setTab(AllGroups.EQUIPMENTS)
//                .register();
    }
}
