package karashokleo.spell_dimension.config.recipe;

import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.spell_dimension.content.spell.LocateSpell;
import karashokleo.spell_dimension.content.spell.PlaceSpell;
import karashokleo.spell_dimension.content.spell.SummonSpell;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.adventurez.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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

public class SpellScrollConfig
{
    public static Set<Identifier> getAllSpells()
    {
        return SPELL_TEXTS.keySet();
    }

    public static Text getSpellScrollText(@Nullable SpellInfo spellInfo)
    {
        return spellInfo == null ?
                SDTexts.SCROLL$UNAVAILABLE
                        .get()
                        .formatted(Formatting.GRAY) :
                SPELL_TEXTS
                        .get(spellInfo.id())
                        .setStyle(Style.EMPTY.withColor(spellInfo.spell().school.color));
    }

    @Nullable
    public static Identifier getLootSpellId(Identifier lootTableId)
    {
        return LOOT_SPELLS.get(lootTableId);
    }

    private static final Map<Identifier, MutableText> SPELL_TEXTS = new HashMap<>();
    private static final Map<Identifier, Identifier> LOOT_SPELLS = new HashMap<>();

    static
    {
        SPELL_TEXTS.put(LocateSpell.SPELL_ID, Text.empty());
        SPELL_TEXTS.put(SummonSpell.SPELL_ID, Text.empty());
        SPELL_TEXTS.put(PlaceSpell.SPELL_ID, Text.empty());

        // Primary
        {
            fromPrimary("wizards:arcane_bolt");
            fromPrimary("wizards:fire_scorch");
            fromPrimary("wizards:frost_shard");
            fromPrimary("paladins:heal");
        }

        // Binding
        {
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
        }

        // Infusion
        {
            fromCrafting("spell-dimension:locate", Items.RECOVERY_COMPASS);
            fromCrafting("spell-dimension:summon", AllItems.SPAWNER_SOUL);
            fromCrafting("spell-dimension:place", ModRegistry.ENDERSOUL_HAND_ITEM.get());
            fromCrafting("spell-dimension:light", Items.GLOWSTONE);

            fromCrafting("spell-dimension:phase", Items.ECHO_SHARD);
            fromCrafting("spell-dimension:converge", "illagerinvasion:primal_essence");
            fromCrafting("spellbladenext:eldritchblast", ComplementItems.WARDEN_BONE_SHARD);

            fromCrafting("wizards:fire_breath", "soulsweapons:lord_soul_rose");
            fromCrafting("spell-dimension:blast", AllItems.ACCURSED_BLACKSTONE);
            fromCrafting("spell-dimension:ignite", ItemInit.BLACKSTONE_GOLEM_HEART);

            fromCrafting("wizards:frost_blizzard", "soulsweapons:lord_soul_white");
            fromCrafting("spell-dimension:nucleus", "endrem:cold_eye");
            fromCrafting("spell-dimension:aura", "aquamirae:maze_rose");
            fromCrafting("spell-dimension:icicle", "spell-dimension:abyss_guard");

            fromCrafting("paladins:holy_beam", "bosses_of_mass_destruction:ancient_anima");
            fromCrafting("paladins:circle_of_healing", "soulsweapons:arkenstone");
            fromCrafting("paladins:barrier", "graveyard:dark_iron_block");
            fromCrafting("paladins:judgement", "soulsweapons:lord_soul_dark");

            fromCrafting("spell-dimension:power", "soulsweapons:essence_of_luminescence");
            fromCrafting("spell-dimension:resist", "soulsweapons:essence_of_eventide");
            fromCrafting("spell-dimension:regen", "bosses_of_mass_destruction:void_thorn");
        }

        // Dynamic
        {
            fromEntityLoot("spellbladenext:maelstrom", "minecraft:ender_dragon");
            fromEntityLoot("spellbladenext:finalstrike", "deeperdarker:stalker");

            fromEntityLoot("spellbladenext:fireflourish", "soulsweapons:night_shade");
            fromEntityLoot("spellbladenext:flicker_strike", "bosses_of_mass_destruction:gauntlet");

            fromEntityLoot("spellbladenext:frostflourish", "soulsweapons:night_prowler");
        }
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
        SPELL_TEXTS.put(new Identifier(spellId), SDTexts.SCROLL$PRIMARY.get());
    }

    private static void fromBinding(String spellId)
    {
        SPELL_TEXTS.put(new Identifier(spellId), SDTexts.SCROLL$BINDING.get());
    }

    private static void fromCrafting(String spellId, String itemId)
    {
        fromCrafting(new Identifier(spellId), new Identifier(itemId));
    }

    private static void fromCrafting(Identifier spellId, Identifier itemId)
    {
        fromCrafting(spellId, Registries.ITEM.get(itemId));
    }

    private static void fromCrafting(String spellId, Item item)
    {
        fromCrafting(new Identifier(spellId), item);
    }

    private static void fromCrafting(Identifier spellId, Item item)
    {
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$CRAFT.get(item.getName().copy().formatted(Formatting.BOLD)));
        InfusionRecipes.register(Items.PAPER, item, AllItems.SPELL_SCROLL.getStack(spellId), false, 20 * 60);
    }

    private static void fromEntityLoot(String spellId, String entityTypeId)
    {
        fromEntityLoot(new Identifier(spellId), new Identifier(entityTypeId));
    }

    private static void fromEntityLoot(Identifier spellId, Identifier entityTypeId)
    {
        EntityType<?> type = Registries.ENTITY_TYPE.get(entityTypeId);
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$KILLING.get(type.getName().copy().formatted(Formatting.BOLD)));
        LOOT_SPELLS.put(type.getLootTableId(), spellId);
    }
}
