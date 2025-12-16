package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.village.TradeOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement of {@link AllTags#TRADABLE}.
 */
@Mixin(TradeOffers.EnchantBookFactory.class)
public abstract class EnchantBookFactoryMixin
{
    @ModifyVariable(
        method = "create",
        at = @At(
            value = "STORE",
            ordinal = 0
        )
    )
    private List<Enchantment> inject_create(List<Enchantment> original)
    {
        ArrayList<Enchantment> enchantments = new ArrayList<>(original);
        enchantments.addAll(Registries.ENCHANTMENT.getOrCreateEntryList(AllTags.TRADABLE).stream().map(RegistryEntry::value).toList());
        return enchantments;
    }
}
