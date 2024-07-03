package karashokleo.spell_dimension.content.item;

import dev.emi.trinkets.api.SlotReference;
import karashokleo.spell_dimension.content.misc.Mage;
import karashokleo.spell_dimension.content.misc.MageMajor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import karashokleo.spell_dimension.data.LangData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.item.trinket.SpellBookTrinketItem;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MageSpellBookItem extends SpellBookTrinketItem implements IMageRequirement
{
    public final Mage mage;

    @Override
    public Mage getMage(ItemStack stack)
    {
        return mage;
    }

    public MageSpellBookItem(Identifier poolId, int grade, SpellSchool school, MageMajor major)
    {
        super(poolId, new FabricItemSettings().maxCount(3 - 2));
        this.mage = new Mage(grade, school, major);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        return meetRequirement(stack, entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        addRequirementTooltip(stack, tooltip);
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
