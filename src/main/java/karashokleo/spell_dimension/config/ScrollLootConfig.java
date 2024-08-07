package karashokleo.spell_dimension.config;

import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
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
        return SPELL2ENTITY.keySet();
    }

    public static Text getInfo(@Nullable SpellInfo spellInfo)
    {
        EntityType<?> type;
        return spellInfo == null ||
                (type = SPELL2ENTITY.get(spellInfo.id())) == null ?
                SDTexts.TOOLTIP_UNAVAILABLE.get().formatted(Formatting.GRAY) :
                SDTexts.TOOLTIP_KILLING
                        .get(type.getName())
                        .setStyle(Style.EMPTY.withColor(spellInfo.spell().school.color));
    }

    @Nullable
    public static Identifier getLootSpellId(Identifier lootTableId)
    {
        return LOOT2SPELL.get(lootTableId);
    }

    private static final Map<Identifier, EntityType<?>> SPELL2ENTITY = new HashMap<>();
    private static final Map<Identifier, Identifier> LOOT2SPELL = new HashMap<>();

    static
    {
        put("spellbladenext:eldritchblast", "minecraft:warden");
        put("spellbladenext:maelstrom", "minecraft:ender_dragon");
        put("spellbladenext:finalstrike", "deeperdarker:stalker");
        put("spell-dimension:converge", "illagerinvasion:invoker");
        put("spell-dimension:phase", "bosses_of_mass_destruction:lich");

        put("wizards:fire_breath", "soulsweapons:draugr_boss");
        put("spellbladenext:fireflourish", "soulsweapons:night_shade");
        put("spellbladenext:flicker_strike", "bosses_of_mass_destruction:gauntlet");
        put("spell-dimension:blast", "soulsweapons:accursed_lord_boss");
        put("spell-dimension:ignite", "adventurez:blackstone_golem");

        put("wizards:frost_blizzard", "soulsweapons:moonknight");
        put("spellbladenext:frostflourish", "soulsweapons:night_prowler");
        put("spell-dimension:nucleus", "aquamirae:maze_mother");
        put("spell-dimension:aura", "aquamirae:captain_cornelia");
        put("spell-dimension:icicle", "minecraft:elder_guardian");

        put("paladins:holy_beam", "soulsweapons:chaos_monarch");
        put("paladins:circle_of_healing", "soulsweapons:returning_knight");
        put("paladins:barrier", "graveyard:lich");
        put("paladins:judgement", "minecraft:wither");
        put("spell-dimension:resist", "soulsweapons:draugr_boss");
        put("spell-dimension:regen", "bosses_of_mass_destruction:void_blossom");
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

    private static void put(String spellId, String entityTypeId)
    {
        put(new Identifier(spellId), new Identifier(entityTypeId));
    }

    private static void put(Identifier spellId, Identifier entityTypeId)
    {
        EntityType<?> type = Registries.ENTITY_TYPE.get(entityTypeId);
        SPELL2ENTITY.put(spellId, type);
        LOOT2SPELL.put(type.getLootTableId(), spellId);
    }
}
