package net.karashokleo.spelldimension.item.mod_item;

import dev.emi.trinkets.api.SlotReference;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.item.trinket.SpellBookItem;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MageSpellBookItem extends SpellBookItem
{
    public final Mage mage;

    public MageSpellBookItem(int grade, MagicSchool school, MageMajor major)
    {
        super(SpellDimension.modLoc(major.majorName() + "_" + AllItems.GRADES[grade] + school.spellName()), new FabricItemSettings().maxCount(3 - 2));
        this.mage = new Mage(grade, school, major);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (entity instanceof PlayerEntity player)
            return mage.testPlayer(player);
        else return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(LangData.TOOLTIP_REQUIRE).formatted(Formatting.GRAY));
        tooltip.add(mage.getMageTitle(Text.translatable(LangData.MAGE)));
    }

    @Override
    public Text getName()
    {
        return mage.getMageTitle(Text.translatable(LangData.SPELL_BOOK));
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return getName();
    }
}
