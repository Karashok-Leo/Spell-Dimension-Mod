package karashokleo.spell_dimension.init;

import karashokleo.l2hostility.init.LHEffects;
import karashokleo.leobrary.datagen.builder.EnchantmentBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffects;
import net.spell_power.api.SpellSchools;
import net.spell_power.api.enchantment.EnchantmentRestriction;

import java.util.ArrayList;
import java.util.List;

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

    public static SpellBladePowerEnchantment SPELL_BLADE_SUNFIRE;
    public static SpellBladePowerEnchantment SPELL_BLADE_SOULFROST;
    public static SpellBladePowerEnchantment SPELL_BLADE_ENERGIZE;
    public static SpellBladeHasteEnchantment SPELL_BLADE_HASTE;

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

    public static final List<TraitEffectImmunityEnchantment> EFFECT_IMMUNITY = new ArrayList<>();

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
                .addENDesc("If the cooldown progress (percentage) of a spell when injured is less than the enchantment level × %.2f, the spell will immediately cool down.".formatted(StressResponseEnchantment.MULTIPLIER))
                .addZH("应激急速")
                .addZHDesc("受伤时如果有法术冷却进度（百分比）小于 魔咒等级 × %.2f, 则该法术立即冷却完毕。".formatted(StressResponseEnchantment.MULTIPLIER))
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_LEECH = new Entry<>("spell_leech", new SpellLeechEnchantment())
                .addEN()
                .addENDesc("The spell damage you deal will be reduced by a certain percentage (0.01x spell power per level) and converted into health you recover.")
                .addZH("法术吸血")
                .addZHDesc("你造成的法术伤害将按一定比例（每级0.01×法术强度）减少，转化为你回复的生命值。")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_RESISTANCE = new Entry<>("spell_resistance", new SpellResistanceEnchantment())
                .addEN()
                .addENDesc("Your spell power will help you resist some damage (0.01x spell power per level).")
                .addZH("魔力御体")
                .addZHDesc("你的法术强度将会为你抵御部分伤害（每级0.01×法术强度）。")
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
                .addENDesc("Your spells can penetrate the target's Dispell trait. Restores 20% of the original damage per level.")
                .addZH("法术穿透")
                .addZHDesc("你的法术可以穿透目标的破魔词条。每级恢复原有伤害的20%。")
                .register();

        SPELL_BLADE_SUNFIRE = new Entry<>("spell_blade_sunfire", new SpellBladePowerEnchantment(SpellSchools.ARCANE, SpellSchools.FIRE))
                .addEN("Spell Blade - Sunfire")
                .addZH("咒剑-阳炎")
                .addENDesc("Increases Arcane and Fire spell power by an amount equal to the damage bonus of physical attacks.")
                .addZHDesc("增加与物理攻击伤害加成等额的奥秘和火焰法术强度。")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_BLADE_SOULFROST = new Entry<>("spell_blade_soulfrost", new SpellBladePowerEnchantment(SpellSchools.SOUL, SpellSchools.FROST))
                .addEN("Spell Blade - Soulfrost")
                .addZH("咒剑-灵冻")
                .addENDesc("Increases Soul and Frost spell power by an amount equal to the damage bonus of physical attacks.")
                .addZHDesc("增加与物理攻击伤害加成等额的灵魂和寒冰法术强度。")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_BLADE_ENERGIZE = new Entry<>("spell_blade_energize", new SpellBladePowerEnchantment(SpellSchools.HEALING, SpellSchools.LIGHTNING))
                .addEN("Spell Blade - Energize")
                .addZH("咒剑-蓄能")
                .addENDesc("Increases Healing and Lightning spell power by an amount equal to the damage bonus of physical attacks.")
                .addZHDesc("增加与物理攻击伤害加成等额的治愈和雷电法术强度。")
                .addTag(AllTags.LOOTABLE)
                .register();
        SPELL_BLADE_HASTE = new Entry<>("spell_blade_haste", new SpellBladeHasteEnchantment())
                .addEN("Spell Blade - Haste")
                .addZH("咒剑-急速")
                .addENDesc("Increased Spell Haste based on attack speed bonus.")
                .addZHDesc("根据攻击速度加成增加法术施放速度。")
                .addTag(AllTags.LOOTABLE)
                .register();

        WEAKNESS_IMMUNITY = new Entry<>("weakness_immunity", new TraitEffectImmunityEnchantment(StatusEffects.WEAKNESS))
                .addEN()
                .addENDesc("You are immune to the Weakness effect.")
                .addZH("虚弱免疫")
                .addZHDesc("你对虚弱效果免疫。")
                .register();

        SLOWNESS_IMMUNITY = new Entry<>("slowness_immunity", new TraitEffectImmunityEnchantment(StatusEffects.SLOWNESS))
                .addEN()
                .addENDesc("You are immune to the Slowness effect.")
                .addZH("缓慢免疫")
                .addZHDesc("你对缓慢效果免疫。")
                .register();

        POISON_IMMUNITY = new Entry<>("poison_immunity", new TraitEffectImmunityEnchantment(StatusEffects.POISON))
                .addEN()
                .addENDesc("You are immune to the Poison effect.")
                .addZH("毒性免疫")
                .addZHDesc("你对中毒效果免疫。")
                .register();

        WITHER_IMMUNITY = new Entry<>("wither_immunity", new TraitEffectImmunityEnchantment(StatusEffects.WITHER))
                .addEN()
                .addENDesc("You are immune to the Wither effect.")
                .addZH("凋零免疫")
                .addZHDesc("你对凋零效果免疫。")
                .register();

        BLINDNESS_IMMUNITY = new Entry<>("blindness_immunity", new TraitEffectImmunityEnchantment(StatusEffects.BLINDNESS))
                .addEN()
                .addENDesc("You are immune to the Blindness effect.")
                .addZH("失明免疫")
                .addZHDesc("你对失明效果免疫。")
                .register();

        CONFUSION_IMMUNITY = new Entry<>("nausea_immunity", new TraitEffectImmunityEnchantment(StatusEffects.NAUSEA))
                .addEN()
                .addENDesc("You are immune to the Nausea effect.")
                .addZH("反胃免疫")
                .addZHDesc("你对反胃效果免疫。")
                .register();

        LEVITATION_IMMUNITY = new Entry<>("levitation_immunity", new TraitEffectImmunityEnchantment(StatusEffects.LEVITATION))
                .addEN()
                .addENDesc("You are immune to the Levitation effect.")
                .addZH("飘浮免疫")
                .addZHDesc("你对飘浮效果免疫。")
                .register();

        SOUL_BURNER_IMMUNITY = new Entry<>("soul_burner_immunity", new TraitEffectImmunityEnchantment(LHEffects.FLAME))
                .addEN()
                .addENDesc("You are immune to the Soul Burning effect.")
                .addZH("魂火免疫")
                .addZHDesc("你对魂火效果免疫。")
                .register();

        FREEZING_IMMUNITY = new Entry<>("freezing_immunity", new TraitEffectImmunityEnchantment(LHEffects.ICE))
                .addEN()
                .addENDesc("You are immune to the Frost effect.")
                .addZH("寒流免疫")
                .addZHDesc("你对寒流效果免疫。")
                .register();

        CURSED_IMMUNITY = new Entry<>("cursed_immunity", new TraitEffectImmunityEnchantment(LHEffects.CURSE))
                .addEN()
                .addENDesc("You are immune to the Cursed effect.")
                .addZH("诅咒免疫")
                .addZHDesc("你对诅咒效果免疫。")
                .register();

        EFFECT_IMMUNITY.add(WEAKNESS_IMMUNITY);
        EFFECT_IMMUNITY.add(SLOWNESS_IMMUNITY);
        EFFECT_IMMUNITY.add(POISON_IMMUNITY);
        EFFECT_IMMUNITY.add(WITHER_IMMUNITY);
        EFFECT_IMMUNITY.add(BLINDNESS_IMMUNITY);
        EFFECT_IMMUNITY.add(CONFUSION_IMMUNITY);
        EFFECT_IMMUNITY.add(LEVITATION_IMMUNITY);
        EFFECT_IMMUNITY.add(SOUL_BURNER_IMMUNITY);
        EFFECT_IMMUNITY.add(FREEZING_IMMUNITY);
        EFFECT_IMMUNITY.add(CURSED_IMMUNITY);

        EnchantmentRestriction.Condition condition = stack -> stack.isIn(AllTags.MAGIC_WEAPON);
        EnchantmentRestriction.permit(SPELL_CURSE, condition);
        EnchantmentRestriction.permit(SPELL_LEECH, condition);
        EnchantmentRestriction.permit(SPELL_TEARING, condition);
        EnchantmentRestriction.permit(SPELL_DASH, condition);
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
