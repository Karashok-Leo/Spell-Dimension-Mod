package karashokleo.spell_dimension.config;

import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellInfo;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScrollLootConfig
{
    public static Set<Identifier> getAllSpells()
    {
        return SPELL2TEXT.keySet();
    }

    public static Text getSpellScrollText(@Nullable SpellInfo spellInfo)
    {
        return spellInfo == null ?
                SDTexts.TOOLTIP_UNAVAILABLE
                        .get()
                        .formatted(Formatting.GRAY) :
                SPELL2TEXT
                        .get(spellInfo.id())
                        .setStyle(Style.EMPTY.withColor(spellInfo.spell().school.color));
    }

    @Nullable
    public static Identifier getLootSpellId(Identifier lootTableId)
    {
        return LOOT2SPELL.get(lootTableId);
    }

    private static final Map<Identifier, MutableText> SPELL2TEXT = new HashMap<>();
    private static final Map<Identifier, Identifier> LOOT2SPELL = new HashMap<>();

    static
    {
        // Primary
        fromPrimary("wizards:arcane_bolt");
        fromPrimary("wizards:fire_scorch");
        fromPrimary("wizards:frost_shard");
        fromPrimary("wizards:heal");

        // Pool
        fromBinding("wizards:arcane_missile");
        fromBinding("wizards:arcane_blast");
        fromBinding("wizards:arcane_blink");

        fromBinding("wizards:fireball");
        fromBinding("wizards:fire_wall");
        fromBinding("wizards:fire_meteor");

        fromBinding("wizards:frostbolt");
        fromBinding("wizards:frost_nova");
        fromBinding("wizards:frost_shield");

        fromBinding("paladins:flash_heal");
        fromBinding("paladins:holy_shock");
        fromBinding("paladins:divine_protection");

        // Dynamic
        fromStructureChest("spell-dimension:phase", "ancient_city");

        fromEntityLoot("spellbladenext:eldritchblast", "minecraft:warden");
        fromEntityLoot("spellbladenext:maelstrom", "minecraft:ender_dragon");
        fromEntityLoot("spellbladenext:finalstrike", "deeperdarker:stalker");
        fromEntityLoot("spell-dimension:converge", "illagerinvasion:invoker");
//        fromEntityLoot("spell-dimension:phase", "bosses_of_mass_destruction:lich");

        fromEntityLoot("wizards:fire_breath", "soulsweapons:draugr_boss");
        fromEntityLoot("spellbladenext:fireflourish", "soulsweapons:night_shade");
        fromEntityLoot("spellbladenext:flicker_strike", "bosses_of_mass_destruction:gauntlet");
        fromEntityLoot("spell-dimension:blast", "soulsweapons:accursed_lord_boss");
        fromEntityLoot("spell-dimension:ignite", "adventurez:blackstone_golem");

        fromEntityLoot("wizards:frost_blizzard", "soulsweapons:moonknight");
        fromEntityLoot("spellbladenext:frostflourish", "soulsweapons:night_prowler");
        fromEntityLoot("spell-dimension:nucleus", "aquamirae:maze_mother");
        fromEntityLoot("spell-dimension:aura", "aquamirae:captain_cornelia");
        fromEntityLoot("spell-dimension:icicle", "minecraft:elder_guardian");

        fromEntityLoot("paladins:holy_beam", "soulsweapons:chaos_monarch");
        fromEntityLoot("paladins:circle_of_healing", "soulsweapons:returning_knight");
        fromEntityLoot("paladins:barrier", "graveyard:lich");
        fromEntityLoot("paladins:judgement", "minecraft:wither");
        fromEntityLoot("spell-dimension:resist", "soulsweapons:draugr_boss");
        fromEntityLoot("spell-dimension:regen", "bosses_of_mass_destruction:void_blossom");
    }

    public static final String[] BOSSES = {
            "illagerinvasion:invoker", "minecraft:totem_of_undying",
            "soulsweapons:draugr_boss", "soulsweapons:essence_of_eventide",
            """
                古英雄的遗骸生成于Champion's Grave内。
                可以用月石罗盘寻找。
                结构名称Champion Graves
                结构定位指令/locate structure soulsweapons:champions_graves
                或手持德拉古尔对破旧的月光祭坛右键召唤古英雄的残骸，德拉古尔不会消耗。
                """,
            "soulsweapons:returning_knight", "soulsweapons:arkenstone",
            "手持迷失的灵魂对破旧的月光祭坛右键召唤复仇骑士。",
            "soulsweapons:moonknight", "soulsweapons:essence_of_luminescence",
            "手持日暮精粹对破旧的月光祭坛右键召唤陨落王者。",
            "bosses_of_mass_destruction:lich", "bosses_of_mass_destruction:ancient_anima",
            "bosses_of_mass_destruction:void_blossom", "bosses_of_mass_destruction:void_thorn",

            "bosses_of_mass_destruction:gauntlet", "bosses_of_mass_destruction:blazing_eye",
            "adventurez:stone_golem", "adventurez:blackstone_golem_heart",
            "soulsweapons:accursed_lord_boss", "soulsweapons:darkin_blade",
            "soulsweapons:chaos_monarch", "soulsweapons:chaos_crown",
            "graveyard:lich", "graveyard:upper_bone_staff",
            "soulsweapons:day_stalker", "soulsweapons:soul_reaper",
            "soulsweapons:night_prowler", "soulsweapons:forlorn_scythe",
            "使用混沌宝珠召唤。"
    };

    private static void fromPrimary(String spellId)
    {
        SPELL2TEXT.put(new Identifier(spellId), SDTexts.TOOLTIP_PRIMARY.get());
    }

    private static void fromBinding(String spellId)
    {
        SPELL2TEXT.put(new Identifier(spellId), SDTexts.TOOLTIP_BINDING.get());
    }

    private static void fromStructureChest(String spellId, String structureId)
    {
        fromStructureChest(new Identifier(spellId), new Identifier(structureId), new Identifier(structureId).withPrefixedPath("chests/"));
    }

    private static void fromStructureChest(Identifier spellId, Identifier structureId, Identifier chestId)
    {
        SPELL2TEXT.put(spellId, SDTexts.TOOLTIP_EXPLORING.get(Text.translatable(structureId.toTranslationKey("struture")).formatted(Formatting.BOLD)));
        LOOT2SPELL.put(chestId, spellId);
    }

    private static void fromEntityLoot(String spellId, String entityTypeId)
    {
        fromEntityLoot(new Identifier(spellId), new Identifier(entityTypeId));
    }

    private static void fromEntityLoot(Identifier spellId, Identifier entityTypeId)
    {
        EntityType<?> type = Registries.ENTITY_TYPE.get(entityTypeId);
        SPELL2TEXT.put(spellId, SDTexts.TOOLTIP_KILLING.get(type.getName().copy().formatted(Formatting.BOLD)));
        LOOT2SPELL.put(type.getLootTableId(), spellId);
    }
}
