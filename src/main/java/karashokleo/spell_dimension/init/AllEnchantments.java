package karashokleo.spell_dimension.init;

import karashokleo.leobrary.datagen.builder.EnchantmentBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.enchantment.*;
import net.minecraft.enchantment.Enchantment;

public class AllEnchantments
{
    public static SpellCurseEnchantment SPELL_CURSE;
    public static SpellHasteEnchantment SPELL_HASTE;
    public static SpellDashEnchantment SPELL_DASH;
    public static StressResponseEnchantment STRESS_RESPONSE;
    public static SpellLeechEnchantment SPELL_LEECH;
    public static SpellResistanceEnchantment SPELL_RESISTANCE;

    public static void register()
    {
        SPELL_CURSE = new Entry<>("spell_curse", new SpellCurseEnchantment())
                .addEN()
                .addENDesc("Your spell will apply Curse effect to targets")
                .addZH("法术诅咒")
                .addZHDesc("你的法术对敌人施加诅咒效果")
                .register();
        SPELL_HASTE = new Entry<>("spell_haste", new SpellHasteEnchantment())
                .addEN()
                .addENDesc("Obtain Spell Haste effect when casting spells")
                .addZH("施法急速")
                .addZHDesc("施放法术时获得施法加速效果")
                .register();
        SPELL_DASH = new Entry<>("spell_dash", new SpellDashEnchantment())
                .addEN()
                .addENDesc("Get one combat roll opportunity when casting spells")
                .addZH("法术冲刺")
                .addZHDesc("施放法术时获得一次战术翻滚机会")
                .register();
        STRESS_RESPONSE = new Entry<>("stress_response", new StressResponseEnchantment())
                .addEN()
                .addENDesc("If the cooldown progress (percentage) of a spell when injured is less than the enchantment level × " + StressResponseEnchantment.MULTIPLIER + ", the spell will immediately cool down.")
                .addZH("应激急速")
                .addZHDesc("受伤时如果有法术冷却进度（百分比）小于 魔咒等级 × " + StressResponseEnchantment.MULTIPLIER + ", 则该法术立即冷却完毕。")
                .register();
        SPELL_LEECH = new Entry<>("spell_leech", new SpellLeechEnchantment())
                .addEN()
                .addENDesc("The spell damage you deal will be reduced by a certain percentage and converted into health you recover.")
                .addZH("法术吸血")
                .addZHDesc("你造成的法术伤害将按一定比例减少，转化为你回复的生命值。")
                .register();
        SPELL_RESISTANCE = new Entry<>("spell_resistance", new SpellResistanceEnchantment())
                .addEN()
                .addENDesc("Your spell power will help you resist some damage.")
                .addZH("魔力御体")
                .addZHDesc("你的法术强度将会为你抵御部分伤害。")
                .register();
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
