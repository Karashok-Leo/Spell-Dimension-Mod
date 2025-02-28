package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class  SpellImpactEnchantment extends UnobtainableEnchantment
{
    public SpellImpactEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    public abstract void onSpellImpact(World world, LivingEntity caster, Context context, List<Entity> targets, SpellInfo spellInfo);

    public static @NotNull Map<SpellImpactEnchantment, Context> getContexts(LivingEntity caster)
    {
        Map<SpellImpactEnchantment, Context> map = new HashMap<>();
        for (ItemStack e : TrinketCompat.getItems(caster, e -> true))
            EnchantmentHelper.get(e).forEach((enchantment, level) ->
            {
                if (enchantment instanceof SpellImpactEnchantment impactEnchantment)
                {
                    map.compute(impactEnchantment, (en, ctx) ->
                    {
                        if (ctx == null)
                        {
                            MutableInt lv = new MutableInt(level);
                            ArrayList<ItemStack> list = new ArrayList<>();
                            list.add(e);
                            return new SpellImpactEnchantment.Context(lv, list);
                        } else
                        {
                            ctx.level().add(level);
                            ctx.stacks().add(e);
                            return ctx;
                        }
                    });
                }
            });
        return map;
    }

    public record Context(MutableInt level, ArrayList<ItemStack> stacks)
    {
        public int totalLevel()
        {
            return level.getValue();
        }
    }
}
