package karashokleo.spell_dimension.init;

import karashokleo.leobrary.datagen.builder.StatusEffectBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.effect.*;
import net.minecraft.entity.effect.StatusEffect;
import net.spell_engine.api.effect.Synchronized;

public class AllStatusEffects
{
    public static PhaseEffect PHASE;
    public static IgniteEffect IGNITE;
    public static FrostAuraEffect FROST_AURA;
    public static FrostedEffect FROSTED;
    public static AirHopEffect AIR_HOP;
    public static ForceLandingEffect FORCE_LANDING;
    public static SpellPowerEffect SPELL_POWER;
    public static DivineAuraEffect DIVINE_AURA;
    //    public static final PhaseEffect ASTRAL_TRIP = new PhaseEffect();

    public static void register()
    {
        PHASE = new Entry<>("phase", new PhaseEffect())
                .addEN()
                .addENDesc("Can fly freely and pass through blocks")
                .addZH("相位")
                .addZHDesc("自由飞行并且可以穿过方块")
                .register();
        IGNITE = new Entry<>("ignite", new IgniteEffect())
                .addEN()
                .addENDesc(BlazingMark.DESC_EN)
                .addZH("引火")
                .addZHDesc(BlazingMark.DESC_ZH)
                .register();
        FROST_AURA = new Entry<>("aura", new FrostAuraEffect())
                .addEN()
                .addENDesc(FrostAuraEffect.DESC_EN)
                .addZH("霜环")
                .addZHDesc(FrostAuraEffect.DESC_ZH)
                .register();
        FROSTED = new Entry<>("frosted", new FrostedEffect())
                .addEN()
                .addENDesc("Frozen and continuously receiving frost spell damage")
                .addZH("霜冻")
                .addZHDesc("冻结并持续收到寒冰法术伤害")
                .register();
        AIR_HOP = new Entry<>("air_hop", new AirHopEffect())
                .addEN()
                .addENDesc("Jump in the air")
                .addZH("空中跳跃")
                .addZHDesc("在空中跳跃")
                .register();
        FORCE_LANDING = new Entry<>("force_landing", new ForceLandingEffect())
                .addEN()
                .addENDesc("Force landing")
                .addZH("迫降")
                .addZHDesc("强制着陆")
                .register();
        SPELL_POWER = new Entry<>("spell_power", new SpellPowerEffect())
                .addEN("Empowering Presence")
                .addENDesc("Increases spell power by 10% per level")
                .addZH("魔力增强")
                .addZHDesc("每级增加10%法术强度")
                .register();
        DIVINE_AURA = new Entry<>("divine_aura", new DivineAuraEffect())
                .addEN()
                .addENDesc(DivineAuraEffect.DESC_EN)
                .addZH("神圣光环")
                .addZHDesc(DivineAuraEffect.DESC_ZH)
                .register();

        Synchronized.configure(PHASE, true);
        Synchronized.configure(FROSTED, true);
        Synchronized.configure(DIVINE_AURA, true);
//        ActionImpairing.configure(ASTRAL_TRIP_EFFECT, EntityActionsAllowed.STUN);
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
