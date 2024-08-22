package karashokleo.spell_dimension.content.enchantment;

import karashokleo.spell_dimension.util.NetworkUtil;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
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
    public void onSpellImpact(World world, LivingEntity caster, int totalLevel, List<Entity> targets, SpellInfo spellInfo)
    {
        if (caster instanceof ServerPlayerEntity player)
            ServerPlayNetworking.send(player, NetworkUtil.SPELL_DASH_PACKET, PacketByteBufs.create());
    }
}
