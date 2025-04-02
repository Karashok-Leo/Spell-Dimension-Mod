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
    public static NirvanaEffect NIRVANA;
    //    public static final PhaseEffect ASTRAL_TRIP = new PhaseEffect();

    public static void register()
    {
        PHASE = new Entry<>("phase", new PhaseEffect())
                .addEN()
                .addZH("相位")
                .addENDesc("Can fly freely and pass through blocks")
                .addZHDesc("自由飞行并且可以穿过方块")
                .register();
        IGNITE = new Entry<>("ignite", new IgniteEffect())
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
                .register();
        FROSTED = new Entry<>("frosted", new FrostedEffect())
                .addEN()
                .addZH("霜冻")
                .addENDesc(FrostedEffect.getDesc(true))
                .addZHDesc(FrostedEffect.getDesc(false))
                .register();
        AIR_HOP = new Entry<>("air_hop", new AirHopEffect())
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
                .register();
        NIRVANA = new Entry<>("nirvana", new NirvanaEffect())
                .addEN()
                .addZH("涅槃")
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
