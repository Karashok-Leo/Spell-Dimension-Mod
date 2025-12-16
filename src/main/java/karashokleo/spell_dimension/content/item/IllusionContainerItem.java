package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IllusionContainerItem extends Item
{
    public IllusionContainerItem()
    {
        super(
            new FabricItemSettings()
                .fireproof()
                .maxCount(1)
                .rarity(Rarity.EPIC)
        );
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player)
    {
        if (clickType != ClickType.RIGHT)
        {
            return false;
        }
        ItemStack clicked = slot.getStack();
        int materialTier = IllusionContainer.getMaterialTier(clicked);
        int enchantmentPoints = IllusionContainer.getEnchantmentPoints(clicked);
        if (materialTier < 0 && enchantmentPoints <= 0)
        {
            return false;
        }
        IllusionContainer.convert(stack, materialTier, enchantmentPoints);
        clicked.setCount(0);
        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.6F + player.getWorld().getRandom().nextFloat() * 0.4F);
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient())
        {
            IllusionContainer.retrieve(stack, user.getInventory());
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$USE$CLICK.get().formatted(Formatting.GRAY));
        tooltip.add(SDTexts.TOOLTIP$CONTAINER$ILLUSION.get().formatted(Formatting.GRAY));
        IllusionContainer.appendTooltip(stack, tooltip);
    }
}
