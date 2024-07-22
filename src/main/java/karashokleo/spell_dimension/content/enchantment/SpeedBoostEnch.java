package karashokleo.spell_dimension.content.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellCooldownManager;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCasterEntity;

import java.util.List;

public class SpeedBoostEnch extends Enchantment
{
    protected SpeedBoostEnch(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level)
    {
        if (!(user instanceof PlayerEntity player)) return;
        List<Identifier> spellIds = SpellContainerHelper.getEquipped(player.getMainHandStack(), player).spell_ids.stream().map(Identifier::new).toList();
        SpellCooldownManager manager = ((SpellCasterEntity) player).getCooldownManager();
        for (Identifier spellId : spellIds)
        {
            Spell spell = SpellRegistry.getSpell(spellId);
            float duration = SpellHelper.getCastDuration(user, spell);
            float progress = manager.getCooldownProgress(spellId, 0.1F * duration);
            manager.set(spellId, Math.round(progress * duration * 20));
        }
    }
}
