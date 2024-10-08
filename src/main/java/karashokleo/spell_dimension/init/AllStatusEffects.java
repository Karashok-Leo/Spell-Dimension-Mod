package karashokleo.spell_dimension.init;

import karashokleo.leobrary.datagen.builder.StatusEffectBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.effect.*;
import net.minecraft.entity.effect.StatusEffect;
import net.spell_engine.api.effect.Synchronized;

public class AllStatusEffects
{
    public static PhaseEffect PHASE = new PhaseEffect();
    public static IgniteEffect IGNITE = new IgniteEffect();
    public static FrostAuraEffect FROST_AURA = new FrostAuraEffect();
    public static FrostedEffect FROSTED = new FrostedEffect();
    public static AirHopEffect AIR_HOP = new AirHopEffect();
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
                .addZHDesc("冻结并持续收到冰霜法术伤害")
                .register();
        AIR_HOP = new Entry<>("air_hop", new AirHopEffect())
                .addEN()
                .addENDesc("Jump in the air")
                .addZH("空中跳跃")
                .addZHDesc("在空中跳跃")
                .register();

        Synchronized.configure(PHASE, true);
        Synchronized.configure(FROSTED, true);
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
