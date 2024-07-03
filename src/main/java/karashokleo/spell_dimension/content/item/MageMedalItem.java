package karashokleo.spell_dimension.content.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import karashokleo.spell_dimension.util.NetworkUtil;
import karashokleo.spell_dimension.content.component.MageComponent;
import karashokleo.spell_dimension.init.AllConfigs;
import karashokleo.spell_dimension.data.LangData;
import karashokleo.spell_dimension.content.misc.Mage;
import karashokleo.spell_dimension.util.ParticleUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
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
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MageMedalItem extends Item implements IMageRequirement
{
    @Override
    public boolean meetRequirement(ItemStack stack, LivingEntity entity)
    {
        if (entity instanceof PlayerEntity player)
        {
            Mage requirement = getMage(stack);
            Mage playerMage = MageComponent.get(player);
            return requirement.grade() > playerMage.grade() &&
                    (requirement.school() == playerMage.school() ||
                            requirement.school() == null ||
                            playerMage.school() == null) &&
                    (requirement.major() == playerMage.major() ||
                            requirement.major() == null ||
                            playerMage.major() == null);
        }
        return false;
    }

    public MageMedalItem()
    {
        super(new FabricItemSettings().maxCount(1).fireproof());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        Mage mage = getMage(stack);
        if (mage.isInvalid()) return TypedActionResult.fail(stack);
        if (user.isSneaking() && world.isClient())
            attributesNotify(user, mage.school());
        else if (!user.isSneaking() && meetRequirement(stack, user))
        {
            if (!world.isClient())
                upgradeNotify(user, mage);
            MageComponent.set(user, mage);
            user.getItemCooldownManager().set(this, AllConfigs.misc.value.mage_medal_cool_down);
            ParticleUtil.ringParticleEmit(user, (mage.grade() + 1) * 30, 5, mage.school());
        }
        return TypedActionResult.success(stack);
    }

    private void attributesNotify(PlayerEntity player, @Nullable SpellSchool school)
    {
        if (school == null) return;
        SpellPower.Result result = SpellPower.getSpellPower(school, player);
        player.sendMessage(Text
                .translatable("attribute.name.spell_power." + SchoolUtil.getName(school))
                .append(String.format(" - %.1f", result.baseValue()))
                .setStyle(Style.EMPTY.withColor(school.color)));
        player.sendMessage(Text
                .translatable("attribute.name.spell_power.critical_chance")
                .append(String.format(" - %.1f%%", result.criticalChance() * 100))
                .setStyle(Style.EMPTY.withColor(school.color)));
        player.sendMessage(Text
                .translatable("attribute.name.spell_power.critical_damage")
                .append(String.format(" - × %.1f%%", result.criticalDamage() * 100))
                .setStyle(Style.EMPTY.withColor(school.color)));
        player.sendMessage(Text
                .translatable("attribute.name.spell_power.haste")
                .append(String.format(" - × %.1f%%", SpellPower.getHaste(player, school) * 100))
                .setStyle(Style.EMPTY.withColor(school.color)));
    }

    private void upgradeNotify(PlayerEntity player, Mage mage)
    {
        PacketByteBuf buf = mage.writeToPacket(PacketByteBufs.create());
        if (mage.grade() == 0)
            NetworkUtil.sendToTrackers(player, NetworkUtil.CLEAR_PACKET, buf);
        else
            NetworkUtil.sendToTrackers(player, NetworkUtil.UPGRADE_PACKET, buf);
    }

    @Override
    public Text getName()
    {
        return Text.translatable(LangData.MAGE_MEDAL);
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return getMage(stack).grade() == 0 ? Text.translatable(LangData.BLANK_MAGE_MEDAL) : getMage(stack).getMageTitle(Text.translatable(LangData.MAGE_MEDAL));
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return getMage(stack).grade() > 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        Mage mage = getMage(stack);
        if (mage.isInvalid())
        {
            tooltip.add(Text.translatable(LangData.TOOLTIP_INVALID));
            return;
        }
        addRequirementTooltip(stack, tooltip);
        tooltip.add(Text.translatable(LangData.TOOLTIP_MEDAL_USE_1).formatted(Formatting.GRAY));
        tooltip.add(mage.getMageTitle(Text.translatable(LangData.MAGE)));
        tooltip.add(Text.translatable(LangData.TOOLTIP_MEDAL_USE_2).formatted(Formatting.GRAY));
    }

    public ItemStack getStack(Mage mage)
    {
        return mage.writeToStack(this.getDefaultStack());
    }
}
