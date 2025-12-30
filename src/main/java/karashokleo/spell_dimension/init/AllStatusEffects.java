package karashokleo.spell_dimension.init;

import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.datagen.builder.StatusEffectBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.effect.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_engine.api.effect.ActionImpairing;
import net.spell_engine.api.effect.EntityActionsAllowed;
import net.spell_engine.api.effect.Synchronized;

public class AllStatusEffects
{
    public static PhaseEffect PHASE;
    public static CustomStatusEffect IGNITE;
    public static FrostAuraEffect FROST_AURA;
    public static FrostedEffect FROSTED;
    public static CustomStatusEffect AIR_HOP;
    public static ForceLandingEffect FORCE_LANDING;
    public static SpellPowerEffect SPELL_POWER;
    public static DivineAuraEffect DIVINE_AURA;
    public static NirvanaEffect NIRVANA;
    public static CustomStatusEffect STUN;
    public static QuantumFieldEffect QUANTUM_FIELD;
    public static CustomStatusEffect SOUL_MARK;
    public static RebirthEffect REBIRTH;
    //    public static final PhaseEffect ASTRAL_TRIP = new PhaseEffect();

    public static void register()
    {
        PHASE = new Entry<>("phase", new PhaseEffect())
            .addEN()
            .addZH("相位")
            .addENDesc("Can fly freely and pass through blocks")
            .addZHDesc("自由飞行并且可以穿过方块")
            .addTag(LHTags.CLEANSE_BLACKLIST)
            .register();
        IGNITE = new Entry<>("ignite", new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0xFF0000))
            .addEN()
            .addZH("引火")
            .addENDesc(BlazingMark.getDesc(true))
            .addZHDesc(BlazingMark.getDesc(false))
            .register();
        FROST_AURA = new Entry<>("frost_aura", new FrostAuraEffect())
            .addEN()
            .addZH("霜环")
            .addENDesc(FrostAuraEffect.getDesc(true))
            .addZHDesc(FrostAuraEffect.getDesc(false))
            .addTag(LHTags.CLEANSE_BLACKLIST)
            .register();
        FROSTED = new Entry<>("frosted", new FrostedEffect())
            .addEN()
            .addZH("霜冻")
            .addENDesc(FrostedEffect.getDesc(true))
            .addZHDesc(FrostedEffect.getDesc(false))
            .register();
        AIR_HOP = new Entry<>("air_hop", new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x00FF00))
            .addEN()
            .addZH("空中跳跃")
            .addENDesc("Jump in the air")
            .addZHDesc("在空中跳跃")
            .register();
        FORCE_LANDING = new Entry<>("force_landing", new ForceLandingEffect())
            .addEN()
            .addZH("迫降")
            .addENDesc("Force landing")
            .addZHDesc("强制着陆")
            .register();
        SPELL_POWER = new Entry<>("spell_power", new SpellPowerEffect())
            .addEN("Empowering Presence")
            .addZH("魔力增强")
            .addENDesc("Increases spell power by 10% per level")
            .addZHDesc("每级增加10%法术强度")
            .register();
        DIVINE_AURA = new Entry<>("divine_aura", new DivineAuraEffect())
            .addEN()
            .addZH("神圣光环")
            .addENDesc(DivineAuraEffect.getDesc(true))
            .addZHDesc(DivineAuraEffect.getDesc(false))
            .addTag(LHTags.CLEANSE_BLACKLIST)
            .register();
        NIRVANA = new Entry<>("nirvana", new NirvanaEffect())
            .addEN()
            .addZH("涅槃")
            .addENDesc(NirvanaEffect.getDesc(true))
            .addZHDesc(NirvanaEffect.getDesc(false))
            .addTag(LHTags.CLEANSE_BLACKLIST)
            .register();
        STUN = new Entry<>("stun", new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x0000FF))
            .addEN()
            .addZH("眩晕")
            .addENDesc("Cannot perform any actions")
            .addZHDesc("无法进行任何行动")
            .register();
        QUANTUM_FIELD = new Entry<>("quantum_field", new QuantumFieldEffect())
            .addEN()
            .addZH("量子场")
            .addENDesc(QuantumFieldEffect.getDesc(true))
            .addZHDesc(QuantumFieldEffect.getDesc(false))
            .addTag(LHTags.CLEANSE_BLACKLIST)
            .register();
        SOUL_MARK = new Entry<>("soul_mark", new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x800080))
            .addEN()
            .addZH("魂印")
            .addENDesc("Takes additional soul spell damage when attacked by soul minions")
            .addZHDesc("被灵仆攻击时受到额外灵魂法术伤害")
            .addTag(LHTags.CLEANSE_BLACKLIST)
            .register();
        REBIRTH = new Entry<>("rebirth", new RebirthEffect())
            .addEN()
            .addZH("复生")
            .addENDesc("Unable to do anything, health gradually recovers to maximum")
            .addZHDesc("无法行动，生命值逐渐恢复至最大值")
            .addTag(LHTags.CLEANSE_BLACKLIST)
            .register();

        Synchronized.configure(PHASE, true);
        Synchronized.configure(FROSTED, true);
        Synchronized.configure(DIVINE_AURA, true);
        Synchronized.configure(STUN, true);
        Synchronized.configure(QUANTUM_FIELD, true);
        Synchronized.configure(REBIRTH, true);

        ActionImpairing.configure(STUN, EntityActionsAllowed.STUN);
        ActionImpairing.configure(REBIRTH, EntityActionsAllowed.STUN);
    }

    public static class Entry<T extends StatusEffect> extends StatusEffectBuilder<T>
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
