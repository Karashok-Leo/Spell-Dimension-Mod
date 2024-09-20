package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.config.recipe.ScrollLootConfig;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellScrollItem extends Item implements ColorProvider
{
    public SpellScrollItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public int getColor(ItemStack stack)
    {
        SpellInfo spellInfo = this.getSpellInfo(stack);
        if (spellInfo != null) return spellInfo.spell().school.color;
        return ColorProvider.super.getColor(stack);
    }

    @Override
    public Text getName()
    {
        return SDTexts.TEXT$SPELL_SCROLL.get();
    }

    @Override
    public Text getName(ItemStack stack)
    {
        return SDTexts.TEXT$SPELL_SCROLL.get().setStyle(Style.EMPTY.withColor(this.getColor(stack)));
    }

    @Nullable
    public SpellInfo getSpellInfo(ItemStack stack)
    {
        SpellContainer container = SpellContainerHelper.containerFromItemStack(stack);
        if (container != null && container.isValid() && container.spell_ids.size() == 1)
        {
            Identifier id = new Identifier(container.spell_ids.get(0));
            Spell spell = SpellRegistry.getSpell(id);
            if (spell != null) return new SpellInfo(spell, id);
        }
        return null;
    }

    public ItemStack getStack(Identifier spellId)
    {
        ItemStack stack = this.getDefaultStack();
        SpellContainer container = new SpellContainer(true, null, 1, List.of(spellId.toString()));
        SpellContainerHelper.addContainerToItemStack(container, stack);
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.SCROLL$OBTAIN.get().formatted(Formatting.GRAY));
        tooltip.add(ScrollLootConfig.getSpellScrollText(this.getSpellInfo(stack)));
    }
}
