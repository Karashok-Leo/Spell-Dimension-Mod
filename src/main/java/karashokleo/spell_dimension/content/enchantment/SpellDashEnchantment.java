package karashokleo.spell_dimension.content.enchantment;

import karashokleo.spell_dimension.content.network.S2CSpellDash;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SpellDashEnchantment extends SpellImpactEnchantment
{
    public SpellDashEnchantment()
    {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void onSpellImpact(World world, LivingEntity caster, Context context, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }
        S2CSpellDash spellDash = new S2CSpellDash();
        AllPackets.toClientPlayer(player, spellDash);
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.AQUA;
    }
}
