package karashokleo.spell_dimension.init;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.spell.ConvergeSpell;
import karashokleo.spell_dimension.content.spell.LocateSpell;
import karashokleo.spell_dimension.content.spell.SummonSpell;
import net.minecraft.entity.attribute.EntityAttributes;
import net.spell_engine.api.spell.CustomSpellHandler;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

public class AllSpells
{
    public static final SpellSchool GENERIC = SpellSchools.createMagic(SpellDimension.modLoc("generic"), 0xdddddd, false, EntityAttributes.GENERIC_ATTACK_DAMAGE, null);

    public static void register()
    {
        SpellSchools.register(GENERIC);

        LivingDamageEvent.DAMAGE.register(BlazingMark::mark);
        CustomSpellHandler.register(Nucleus.SPELL_ID, data -> Nucleus.handle((CustomSpellHandler.Data) data));
        SpellProjectileHitEntityCallback.EVENT.register(ConvergeSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(ConvergeSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(LocateSpell::handle);
        SpellProjectileHitBlockCallback.EVENT.register(SummonSpell::handle);
    }
}