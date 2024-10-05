package karashokleo.spell_dimension.init;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.spell.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.CustomSpellHandler;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

public class AllSpells
{
    public static final SpellSchool GENERIC = SpellSchools.createMagic(SpellDimension.modLoc("generic"), 0xdddddd, false, EntityAttributes.GENERIC_ATTACK_DAMAGE, null);

    public static final Identifier FROZEN = SpellDimension.modLoc("frozen");
    public static final Identifier INCARCERATE = SpellDimension.modLoc("incarcerate");
    public static final Identifier FORCE_LANDING = SpellDimension.modLoc("force_landing");
    public static final Identifier MOON_SWIM = SpellDimension.modLoc("moon_swim");

    public static void register()
    {
        SpellSchools.register(GENERIC);

        LivingDamageEvent.DAMAGE.register(BlazingMark::mark);

        CustomSpellHandler.register(Nucleus.SPELL_ID, data -> Nucleus.handle((CustomSpellHandler.Data) data));
        CustomSpellHandler.register(ExorcismSpell.SPELL_ID, data -> ExorcismSpell.handle((CustomSpellHandler.Data) data));

        SpellProjectileHitEntityCallback.EVENT.register(ShiftSpell::handle);
        SpellProjectileHitEntityCallback.EVENT.register(ConvergeSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(ConvergeSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(LightSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(LocateSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(SummonSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(PlaceSpell::handle);

        SpellImpactEvents.BEFORE.register(FrostBlinkSpell::handle);
        SpellImpactEvents.BEFORE.register(FireOfRetributionSpell::handle);
    }
}