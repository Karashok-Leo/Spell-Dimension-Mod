package karashokleo.spell_dimension.config.recipe;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import net.adventurez.init.ItemInit;
import net.aleganza.plentyofarmors.item.ModItems;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpellScrollConfig
{
    public static Set<Identifier> getAllSpells()
    {
        return SPELL_TEXTS.keySet();
    }

    public static Set<Identifier> getCraftSpells()
    {
        return CRAFT_SPELLS;
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

    private static final Map<Identifier, MutableText> SPELL_TEXTS = new HashMap<>();
    private static final Set<Identifier> CRAFT_SPELLS = new HashSet<>();

    static
    {
        // Primary
        {
            fromPrimary(AllSpells.ARCANE_BOLT);
            fromPrimary(AllSpells.FIRE_SCORCH);
            fromPrimary(AllSpells.FROST_SHARD);
            fromPrimary(AllSpells.HEAL);
        }

        // Binding
        {
            fromBinding(AllSpells.ARCANE_MISSILE);
            fromBinding(AllSpells.ARCANE_BLAST);
            fromBinding(AllSpells.ARCANE_BLINK);

            fromBinding(AllSpells.FIREBALL);
            fromBinding(AllSpells.FIRE_WALL);
            fromBinding(AllSpells.FIRE_METEOR);

            fromBinding(AllSpells.FROSTBOLT);
            fromBinding(AllSpells.FROST_NOVA);
            fromBinding(AllSpells.FROST_SHIELD);

            fromBinding(AllSpells.FLASH_HEAL);
            fromBinding(AllSpells.HOLY_SHOCK);
            fromBinding(AllSpells.DIVINE_PROTECTION);
        }

        // Infusion
        {
            fromCrafting(AllSpells.LOCATE, Items.RECOVERY_COMPASS);
            fromCrafting(AllSpells.SUMMON, AllItems.SPAWNER_SOUL);
            fromCrafting(AllSpells.PLACE, ModRegistry.ENDERSOUL_HAND_ITEM.get());
            fromCrafting(AllSpells.LIGHT, Items.GLOWSTONE);
            fromCrafting(AllSpells.MOON_SWIM, ModItems.HARDENED_PHANTOM_MEMBRANE);

            fromCrafting(AllSpells.PHASE, Items.ECHO_SHARD);
            fromCrafting(AllSpells.CONVERGE, "illagerinvasion:primal_essence");
            fromCrafting(AllSpells.ELDRITCH_BLAST, ComplementItems.WARDEN_BONE_SHARD);
            fromCrafting(AllSpells.SHIFT, ModItems.HEART_OF_THE_END);
            fromCrafting(AllSpells.FORCE_LANDING, LHTraits.GRAVITY.asItem());
            fromCrafting(AllSpells.INCARCERATE, ComplementItems.BLACKSTONE_CORE);
            fromCrafting(AllSpells.MAELSTROM, "soulsweapons:lord_soul_purple");
            fromCrafting(AllSpells.FINALSTRIKE, "deeperdarker:soul_crystal");


            fromCrafting(AllSpells.FIRE_BREATH, "soulsweapons:lord_soul_rose");
            fromCrafting(AllSpells.BLAST, AllItems.ACCURSED_BLACKSTONE);
            fromCrafting(AllSpells.IGNITE, ItemInit.BLACKSTONE_GOLEM_HEART);
            fromCrafting(AllSpells.FIRE_OF_RETRIBUTION, LHTraits.SOUL_BURNER.asItem());
            fromCrafting(AllSpells.FIREFLOURISH, "bosses_of_mass_destruction:blazing_eye");
            fromCrafting(AllSpells.FLICKER_STRIKE, "soulsweapons:lord_soul_red");

            fromCrafting(AllSpells.FROST_BLIZZARD, "soulsweapons:lord_soul_white");
            fromCrafting(AllSpells.NUCLEUS, "endrem:cold_eye");
            fromCrafting(AllSpells.AURA, "aquamirae:maze_rose");
            fromCrafting(AllSpells.ICICLE, "spell-dimension:abyss_guard");
            fromCrafting(AllSpells.FROST_BLINK, AquamiraeItems.DEAD_SEA_SCROLL);
            fromCrafting(AllSpells.FROZEN, AquamiraeItems.SHIP_GRAVEYARD_ECHO);
            fromCrafting(AllSpells.FROSTFLOURISH, "aquamirae:poisoned_chakra");

            fromCrafting(AllSpells.HOLY_BEAM, "bosses_of_mass_destruction:ancient_anima");
            fromCrafting(AllSpells.CIRCLE_OF_HEALING, "soulsweapons:arkenstone");
            fromCrafting(AllSpells.BARRIER, "graveyard:dark_iron_block");
            fromCrafting(AllSpells.JUDGEMENT, "soulsweapons:lord_soul_dark");
            fromCrafting(AllSpells.CLEANSE, ComplementItems.LIFE_ESSENCE);
            fromCrafting(AllSpells.EXORCISM, LHTraits.DISPELL.asItem());
            fromCrafting(AllSpells.POWER, "soulsweapons:essence_of_luminescence");
            fromCrafting(AllSpells.RESIST, "soulsweapons:essence_of_eventide");
            fromCrafting(AllSpells.REGEN, "bosses_of_mass_destruction:void_thorn");
        }
    }

    public static final String[] BOSSES = {
            "soulsweapons:lord_soul_void",
            "soulsweapons:lord_soul_day_stalker",
            "soulsweapons:lord_soul_night_prowler",
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

    private static void fromPrimary(Identifier spellId)
    {
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$PRIMARY.get());
    }

    private static void fromBinding(Identifier spellId)
    {
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$BINDING.get());
    }

    private static void fromCrafting(Identifier spellId, String itemId)
    {
        fromCrafting(spellId, new Identifier(itemId));
    }

    private static void fromCrafting(Identifier spellId, Identifier itemId)
    {
        fromCrafting(spellId, Registries.ITEM.get(itemId));
    }

    private static void fromCrafting(Identifier spellId, Item item)
    {
        SPELL_TEXTS.put(spellId, SDTexts.SCROLL$CRAFT.get(item.getName().copy().formatted(Formatting.BOLD)));
        CRAFT_SPELLS.add(spellId);
        InfusionRecipes.register(Items.PAPER, item, AllItems.SPELL_SCROLL.getStack(spellId), false, 20 * 60);
    }
}
