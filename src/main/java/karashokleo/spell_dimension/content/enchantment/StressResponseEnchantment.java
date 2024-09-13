package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.content.enchantment.UnobtainableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellCooldownManager;
import net.spell_engine.internals.casting.SpellCasterEntity;

import java.util.List;

public class StressResponseEnchantment extends UnobtainableEnchantment
{
    public static final float MULTIPLIER = 0.16F;

    public StressResponseEnchantment()
    {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level)
    {
        if (!(user instanceof PlayerEntity player)) return;
        SpellContainer equipped = SpellContainerHelper.getEquipped(player.getMainHandStack(), player);
        if (equipped == null) return;
        List<Identifier> spellIds = equipped.spell_ids.stream().map(Identifier::new).toList();
        SpellCooldownManager manager = ((SpellCasterEntity) player).getCooldownManager();
        for (Identifier spellId : spellIds)
        {
            float progress = manager.getCooldownProgress(spellId, 0);
            if (progress < level * MULTIPLIER) manager.set(spellId, 0);
        }
    }
}
