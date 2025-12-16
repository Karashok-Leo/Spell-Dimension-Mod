package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ApplyBonusLootFunction.class)
public abstract class ApplyBonusLootFunctionMixin
{
    @Shadow
    @Final
    Enchantment enchantment;

    @ModifyExpressionValue(
        method = "process",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;getLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I"
        )
    )
    private int inject_getEnchantmentLevel(int level, ItemStack stack, LootContext context)
    {
        if (this.enchantment == Enchantments.FORTUNE &&
            context.get(LootContextParameters.THIS_ENTITY) instanceof LivingEntity living &&
            TrinketCompat.hasItemInTrinket(living, AllItems.ZHUZI_MINER_HELMET))
        {
            level += AllItems.ZHUZI_MINER_HELMET.getFortuneBonus(living);
        }
        return level;
    }
}
