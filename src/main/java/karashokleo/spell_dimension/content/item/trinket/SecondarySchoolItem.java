package karashokleo.spell_dimension.content.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SecondarySchoolItem extends SingleEpicTrinketItem
{
    public static final float SECONDARY_SCHOOL_RATIO = 0.3F;
    public final SpellSchool school;

    public SecondarySchoolItem(SpellSchool school)
    {
        super();
        this.school = school;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
        double spellPower = SchoolUtil.getEntitySpellPower(entity);
        if (spellPower == 0)
        {
            return modifiers;
        }
        modifiers.put(
            school.attribute,
            new EntityAttributeModifier(uuid, "Secondary School Bonus", spellPower * SECONDARY_SCHOOL_RATIO, EntityAttributeModifier.Operation.ADDITION)
        );
        return modifiers;
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        return !SchoolUtil.getLivingSchools(entity).contains(school) &&
            TrinketCompat.getTrinketItems(entity, e -> e.getItem() instanceof SecondarySchoolItem).isEmpty();
    }

    @Override
    public Text getName()
    {
        return Text.translatable(this.getTranslationKey())
            .setStyle(Style.EMPTY.withColor(school.color));
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return Text.translatable(this.getTranslationKey(stack))
            .setStyle(Style.EMPTY.withColor(school.color));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        MutableText schoolText = SDTexts.getSchoolText(school);
        Style style = Style.EMPTY.withColor(school.color);
        int ratio = Math.round(SECONDARY_SCHOOL_RATIO * 100);
        tooltip.add(SDTexts.TOOLTIP$SECONDARY_SCHOOL_ITEM$0.get(schoolText, schoolText).setStyle(style).formatted(Formatting.BOLD));
        tooltip.add(SDTexts.TOOLTIP$SECONDARY_SCHOOL_ITEM$1.get(schoolText, ratio).setStyle(style));
        tooltip.add(SDTexts.TOOLTIP$SECONDARY_SCHOOL_ITEM$2.get(schoolText).setStyle(style));
        tooltip.add(SDTexts.TOOLTIP$SECONDARY_SCHOOL_ITEM$3.get(ratio, schoolText).setStyle(style));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
