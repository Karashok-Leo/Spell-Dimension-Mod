package karashokleo.spell_dimension.data.generic;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;

import java.util.function.Consumer;

public class SDAdvancementProvider extends FabricAdvancementProvider
{
    public SDAdvancementProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer)
    {
        Advancement medal = Advancement.Builder.create()
                .display(
                        AllItems.MEDAL.getDefaultStack(),
                        SDTexts.ADVANCEMENT$MEDAL$TITLE.get(),
                        SDTexts.ADVANCEMENT$MEDAL$DESCRIPTION.get(),
                        SpellDimension.modLoc("textures/block/protective_cover.png"),
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("0", ConsumeItemCriterion.Conditions.item(AllItems.MEDAL))
                .build(SpellDimension.modLoc("medal"));
        consumer.accept(medal);
    }
}
