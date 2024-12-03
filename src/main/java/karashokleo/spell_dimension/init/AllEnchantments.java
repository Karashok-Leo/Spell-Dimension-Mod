package karashokleo.spell_dimension.init;

import karashokleo.l2hostility.content.trait.base.TargetEffectTrait;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.datagen.builder.EnchantmentBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffects;

import java.util.HashMap;
import java.util.Map;

public class AllEnchantments
{
    public static SpellCurseEnchantment SPELL_CURSE;
    public static SpellHasteEnchantment SPELL_HASTE;
    public static SpellDashEnchantment SPELL_DASH;
    public static StressResponseEnchantment STRESS_RESPONSE;
    public static SpellLeechEnchantment SPELL_LEECH;
    public static SpellResistanceEnchantment SPELL_RESISTANCE;
    public static SpellMendingEnchantment SPELL_MENDING;
    public static SpellTearingEnchantment SPELL_TEARING;

    public static TraitEffectImmunityEnchantment WEAKNESS_IMMUNITY;
    public static TraitEffectImmunityEnchantment SLOWNESS_IMMUNITY;
    public static TraitEffectImmunityEnchantment POISON_IMMUNITY;
    public static TraitEffectImmunityEnchantment WITHER_IMMUNITY;
    public static TraitEffectImmunityEnchantment BLINDNESS_IMMUNITY;
    public static TraitEffectImmunityEnchantment CONFUSION_IMMUNITY;
    public static TraitEffectImmunityEnchantment LEVITATION_IMMUNITY;
    public static TraitEffectImmunityEnchantment SOUL_BURNER_IMMUNITY;
    public static TraitEffectImmunityEnchantment FREEZING_IMMUNITY;
    public static TraitEffectImmunityEnchantment CURSED_IMMUNITY;

    public static final Map<TraitEffectImmunityEnchantment, TargetEffectTrait> EFFECT_IMMUNITY = new HashMap<>();

    public static void register()
    {
        SPELL_CURSE = new Entry<>("spell_curse", new SpellCurseEnchantment())
                .addEN()
                .addENDesc("Your spell will apply Curse effect to targets")
                .addZH("法术诅咒")
                .addZHDesc("你的法术对敌人施加诅咒效果")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_HASTE = new Entry<>("spell_haste", new SpellHasteEnchantment())
                .addEN()
                .addENDesc("Obtain Spell Haste effect when casting spells")
                .addZH("施法急速")
                .addZHDesc("施放法术时获得施法加速效果")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_DASH = new Entry<>("spell_dash", new SpellDashEnchantment())
                .addEN()
                .addENDesc("Get one combat roll opportunity when casting spells")
                .addZH("法术冲刺")
                .addZHDesc("施放法术时获得一次战术翻滚机会")
                .addTag(AllTags.LOOTABLE)
                .register();
        STRESS_RESPONSE = new Entry<>("stress_response", new StressResponseEnchantment())
                .addEN()
                .addENDesc("If the cooldown progress (percentage) of a spell when injured is less than the enchantment levelFactor × " + StressResponseEnchantment.MULTIPLIER + ", the spell will immediately cool down.")
                .addZH("应激急速")
                .addZHDesc("受伤时如果有法术冷却进度（百分比）小于 魔咒等级 × " + StressResponseEnchantment.MULTIPLIER + ", 则该法术立即冷却完毕。")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_LEECH = new Entry<>("spell_leech", new SpellLeechEnchantment())
                .addEN()
                .addENDesc("The spell damage you deal will be reduced by a certain percentage and converted into health you recover.")
                .addZH("法术吸血")
                .addZHDesc("你造成的法术伤害将按一定比例减少，转化为你回复的生命值。")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_RESISTANCE = new Entry<>("spell_resistance", new SpellResistanceEnchantment())
                .addEN()
                .addENDesc("Your spell power will help you resist some damage.")
                .addZH("魔力御体")
                .addZHDesc("你的法术强度将会为你抵御部分伤害。")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_MENDING = new Entry<>("spell_mending", new SpellMendingEnchantment())
                .addEN()
                .addENDesc("Each cast will repair your damaged equipment.")
                .addZH("魔力修补")
                .addZHDesc("每次施法将修复你的受损装备。")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_TEARING = new Entry<>("spell_tearing", new SpellTearingEnchantment())
                .addEN()
                .addENDesc("Your spell has a 25% chance of deleting the target's Dispell trait. The probability increases to 50% when wearing the Platinum Star.")
                .addZH("法术穿透")
                .addZHDesc("你的法术有25%概率删除目标的破魔词条。佩戴破风拳套时此概率提升至50%。")
                .register();

        WEAKNESS_IMMUNITY = new Entry<>("weakness_immunity", new TraitEffectImmunityEnchantment(StatusEffects.WEAKNESS))
                .addEN()
                .addENDesc("You are immune to the Weakness effect.")
                .addZH("虚弱免疫")
                .addZHDesc("你对虚弱效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(WEAKNESS_IMMUNITY, LHTraits.WEAKNESS);

        SLOWNESS_IMMUNITY = new Entry<>("slowness_immunity", new TraitEffectImmunityEnchantment(StatusEffects.SLOWNESS))
                .addEN()
                .addENDesc("You are immune to the Slowness effect.")
                .addZH("缓慢免疫")
                .addZHDesc("你对缓慢效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(SLOWNESS_IMMUNITY, LHTraits.SLOWNESS);

        POISON_IMMUNITY = new Entry<>("poison_immunity", new TraitEffectImmunityEnchantment(StatusEffects.POISON))
                .addEN()
                .addENDesc("You are immune to the Poison effect.")
                .addZH("毒性免疫")
                .addZHDesc("你对中毒效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(POISON_IMMUNITY, LHTraits.POISON);

        WITHER_IMMUNITY = new Entry<>("wither_immunity", new TraitEffectImmunityEnchantment(StatusEffects.WITHER))
                .addEN()
                .addENDesc("You are immune to the Wither effect.")
                .addZH("凋零免疫")
                .addZHDesc("你对凋零效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(WITHER_IMMUNITY, LHTraits.WITHER);

        BLINDNESS_IMMUNITY = new Entry<>("blindness_immunity", new TraitEffectImmunityEnchantment(StatusEffects.BLINDNESS))
                .addEN()
                .addENDesc("You are immune to the Blindness effect.")
                .addZH("失明免疫")
                .addZHDesc("你对失明效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(BLINDNESS_IMMUNITY, LHTraits.BLIND);

        CONFUSION_IMMUNITY = new Entry<>("nausea_immunity", new TraitEffectImmunityEnchantment(StatusEffects.NAUSEA))
                .addEN()
                .addENDesc("You are immune to the Nausea effect.")
                .addZH("反胃免疫")
                .addZHDesc("你对反胃效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(CONFUSION_IMMUNITY, LHTraits.CONFUSION);

        LEVITATION_IMMUNITY = new Entry<>("levitation_immunity", new TraitEffectImmunityEnchantment(StatusEffects.LEVITATION))
                .addEN()
                .addENDesc("You are immune to the Levitation effect.")
                .addZH("飘浮免疫")
                .addZHDesc("你对飘浮效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(LEVITATION_IMMUNITY, LHTraits.LEVITATION);

        SOUL_BURNER_IMMUNITY = new Entry<>("soul_burner_immunity", new TraitEffectImmunityEnchantment(LHEffects.FLAME))
                .addEN()
                .addENDesc("You are immune to the Soul Burning effect.")
                .addZH("魂火免疫")
                .addZHDesc("你对魂火效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(SOUL_BURNER_IMMUNITY, LHTraits.SOUL_BURNER);

        FREEZING_IMMUNITY = new Entry<>("freezing_immunity", new TraitEffectImmunityEnchantment(LHEffects.ICE))
                .addEN()
                .addENDesc("You are immune to the Frost effect.")
                .addZH("寒流免疫")
                .addZHDesc("你对寒流效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(FREEZING_IMMUNITY, LHTraits.FREEZING);

        CURSED_IMMUNITY = new Entry<>("cursed_immunity", new TraitEffectImmunityEnchantment(LHEffects.CURSE))
                .addEN()
                .addENDesc("You are immune to the Cursed effect.")
                .addZH("诅咒免疫")
                .addZHDesc("你对诅咒效果免疫。")
                .register();
        EFFECT_IMMUNITY.put(CURSED_IMMUNITY, LHTraits.CURSED);
    }

    public static class Entry<T extends Enchantment> extends EnchantmentBuilder<T>
    {
        public Entry(String name, T content)
        {
            super(name, content);
        }

        @Override
        public String getNameSpace()
        {
            return SpellDimension.MOD_ID;
        }
    }
}
