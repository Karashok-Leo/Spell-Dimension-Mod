package karashokleo.spell_dimension.content.item.trinket;

import dev.emi.trinkets.api.TrinketItem;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GlacialNuclearEraItem extends TrinketItem
{
    // direct cast nucleus with stonecage
    public GlacialNuclearEraItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        ItemCooldownManager cooldownManager = user.getItemCooldownManager();
        if (cooldownManager.isCoolingDown(this))
            return TypedActionResult.fail(stack);
        double reach = 8;
        for (var e : user.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), user.getBoundingBox().expand(reach), e -> user.distanceTo(e) < reach))
        {
            if (e.isSpectator()) continue;
            if (e instanceof PlayerEntity player && player.isCreative()) continue;
            if (ImpactUtil.isAlly(user, e)) continue;
            this.applyEffect(user, e);
        }
        cooldownManager.set(this, 200);
        return super.use(world, user, hand);
    }

    protected void applyEffect(PlayerEntity player, LivingEntity target)
    {
        StatusEffectInstance newEffect = new StatusEffectInstance(LHEffects.STONE_CAGE, 40, 0, true, true);
        EffectHelper.forceAddEffectWithEvent(target, newEffect, player);
    }
}
