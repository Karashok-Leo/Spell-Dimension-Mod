package net.karashokleo.spelldimension.item.mod_item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.karashokleo.spelldimension.SpellDimensionNetworking;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.karashokleo.spelldimension.util.ParticleUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.spell_power.api.MagicSchool;
import net.spell_power.api.SpellPower;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MageMedalItem extends Item
{
    private static final int COOL_DOWN = 200;

    public MageMedalItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        Mage mage = Mage.readFromStack(stack);
        if (mage.isInvalid()) return TypedActionResult.fail(stack);
        if (world.isClient() && user.isSneaking() && mage.school() != null)
        {
            SpellPower.Result result = SpellPower.getSpellPower(mage.school(), user);
            user.sendMessage(Text
                    .translatable("attribute.name.spell_power." + result.school().spellName())
                    .append(String.format(" - %s", result.baseValue()))
                    .setStyle(Style.EMPTY.withColor(mage.school().color())));
            user.sendMessage(Text
                    .translatable("attribute.name.spell_power.critical_chance")
                    .append(String.format(" - %.1f%%", result.criticalChance() * 100))
                    .setStyle(Style.EMPTY.withColor(mage.school().color())));
            user.sendMessage(Text
                    .translatable("attribute.name.spell_power.critical_damage")
                    .append(String.format(" - × %.1f%%", result.criticalDamage() * 100))
                    .setStyle(Style.EMPTY.withColor(mage.school().color())));
            user.sendMessage(Text
                    .translatable("attribute.name.spell_power.haste")
                    .append(Text.translatable(LangData.FASTER, (SpellPower.getHaste(user) - 1.0) * 100))
                    .setStyle(Style.EMPTY.withColor(mage.school().color())));
            return TypedActionResult.success(stack);
        }
        if (!world.isClient())
        {
            PacketByteBuf buf = PacketByteBufs.create();
            mage.writeToPacket(buf);
            Mage playerMage = MageComponent.get(user);
            if (mage.grade() == 0)
                SpellDimensionNetworking.sendToTrackers(user, SpellDimensionNetworking.CLEAR_PACKET, buf);
            else if (playerMage.grade() == 0 ||
                    (playerMage.grade() < mage.grade() &&
                            playerMage.checkSchoolAndMajor(mage.school(), mage.major())))
                SpellDimensionNetworking.sendToTrackers(user, SpellDimensionNetworking.UPGRADE_PACKET, buf);
        }
        MageComponent.set(user, mage);
        user.getItemCooldownManager().set(this, COOL_DOWN);
        ParticleUtil.ringParticleEmit(user, (mage.grade() + 1) * 30, 5, mage.school());
        return TypedActionResult.success(stack);
    }

    @Override
    public Text getName()
    {
        return Text.translatable(LangData.MAGE_MEDAL);
    }

    @Override
    public Text getName(ItemStack stack)
    {
        Mage mage = Mage.readFromStack(stack);
        if (mage.grade() == 0) return Text.translatable(LangData.BLANK_MAGE_MEDAL);
        return mage.getMageTitle(Text.translatable(LangData.MAGE_MEDAL));
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return Mage.readFromStack(stack).grade() > 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        Mage mage = Mage.readFromStack(stack);
        if (mage.isInvalid())
        {
            tooltip.add(Text.translatable(LangData.TOOLTIP_INVALID));
            return;
        }
        tooltip.add(Text.translatable(LangData.TOOLTIP_MEDAL_USE_1).formatted(Formatting.GRAY));
        tooltip.add(mage.getMageTitle(Text.translatable(LangData.MAGE)));
        tooltip.add(Text.translatable(LangData.TOOLTIP_MEDAL_USE_2).formatted(Formatting.GRAY));
    }

    public ItemStack getStack(Mage mage)
    {
        ItemStack stack = this.getDefaultStack();
        mage.writeToStack(stack);
        return stack;
    }

    public ItemStack getStack(int grade, @Nullable MagicSchool school, @Nullable MageMajor major)
    {
        ItemStack stack = this.getDefaultStack();
        Mage.writeToStack(stack, grade, school, major);
        return stack;
    }
}
