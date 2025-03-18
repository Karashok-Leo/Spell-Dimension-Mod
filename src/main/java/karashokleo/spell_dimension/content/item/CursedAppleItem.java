package karashokleo.spell_dimension.content.item;

import dev.emi.trinkets.api.TrinketsApi;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.UuidUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class CursedAppleItem extends Item
{
    private static final UUID CURSED_APPLE_BONUS_UUID = UuidUtil.getUUIDFromString(SpellDimension.MOD_ID + ":cursed_apple");

    public CursedAppleItem()
    {
        super(
                new FabricItemSettings()
                        .maxCount(1)
                        .rarity(Rarity.EPIC)
                        .fireproof()
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public int getMaxUseTime(ItemStack stack)
    {
        return 60;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        var optional = TrinketsApi.getTrinketComponent(user);
        if (optional.isEmpty())
            return stack;
        var group = optional.get().getInventory().get("chest");
        if (group == null)
            return stack;
        var inventory = group.get("hostility_curse");
        if (inventory == null)
            return stack;

        if (inventory.getModifiers().containsKey(CURSED_APPLE_BONUS_UUID))
            return stack;
        inventory.addPersistentModifier(new EntityAttributeModifier(CURSED_APPLE_BONUS_UUID, "Cursed Apple Bonus", 7, EntityAttributeModifier.Operation.ADDITION));

        world.playSound(null, user.getX(), user.getY(), user.getZ(), user.getEatSound(stack), SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
        user.damage(user.getDamageSources().magic(), user.getHealth() + user.getAbsorptionAmount() - 2);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 30, 9));
        user.emitGameEvent(GameEvent.EAT);
        if (user instanceof ServerPlayerEntity player)
            Criteria.CONSUME_ITEM.trigger(player, stack);
        stack.decrement(1);
        return stack;
    }

    @Override
    public Text getName()
    {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.DARK_PURPLE);
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.DARK_PURPLE);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$CURSED_APPLE.get().formatted(Formatting.DARK_PURPLE));
    }
}
