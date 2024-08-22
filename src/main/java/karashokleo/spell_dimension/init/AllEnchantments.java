package karashokleo.spell_dimension.init;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.leobrary.datagen.builder.EnchantmentBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AllEnchantments
{
    public static SpellCurseEnchantment SPELL_CURSE;
    public static SpellHasteEnchantment SPELL_HASTE;
    public static SpellDashEnchantment SPELL_DASH;
    public static StressResponseEnchantment STRESS_RESPONSE;

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

        SpellImpactEvents.BEFORE.register((world, caster, targets, spellInfo) ->
        {
            Map<SpellImpactEnchantment, Integer> map = new HashMap<>();
            for (ItemStack e : TrinketCompat.getItems(caster, e -> true))
                EnchantmentHelper.get(e).forEach((enchantment, level) ->
                {
                    if (enchantment instanceof SpellImpactEnchantment impactEnchantment)
                        map.compute(impactEnchantment, (en, lv) -> (lv == null ? 0 : lv) + level);
                });
            map.forEach((enchantment, level) -> enchantment.onSpellImpact(world, caster, level, targets, spellInfo));
        });
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
