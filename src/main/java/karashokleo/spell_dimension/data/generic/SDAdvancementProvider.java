package karashokleo.spell_dimension.data.generic;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.advancement.DroppedItemsCriterion;
import karashokleo.spell_dimension.content.advancement.MinedOresCriterion;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllStacks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.TickCriterion;

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
        Advancement root = Advancement.Builder.create()
                .display(
                        AllItems.MAGIC_MIRROR.getDefaultStack(),
                        SDTexts.ADVANCEMENT$ROOT$TITLE.get(),
                        SDTexts.ADVANCEMENT$ROOT$DESCRIPTION.get(),
                        SpellDimension.modLoc("textures/block/protective_cover.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("0", TickCriterion.Conditions.createTick())
                .rewards(
                        AdvancementRewards.Builder.loot(SpellDimension.modLoc("pool/starter_kit"))
                )
                .build(SpellDimension.modLoc("spell_dimension/root"));

        Advancement medal_1 = Advancement.Builder.create()
                .parent(root)
                .display(
                        AllItems.MEDAL.getDefaultStack(),
                        SDTexts.ADVANCEMENT$MEDAL_1$TITLE.get(),
                        SDTexts.ADVANCEMENT$MEDAL_1$DESCRIPTION.get(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("0", InventoryChangedCriterion.Conditions.items(AllItems.MEDAL))
                .build(SpellDimension.modLoc("spell_dimension/medal_1"));

        Advancement medal_2 = Advancement.Builder.create()
                .parent(medal_1)
                .display(
                        AllItems.MEDAL.getDefaultStack(),
                        SDTexts.ADVANCEMENT$MEDAL_2$TITLE.get(),
                        SDTexts.ADVANCEMENT$MEDAL_2$DESCRIPTION.get(),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("0", ConsumeItemCriterion.Conditions.item(AllItems.MEDAL))
                .build(SpellDimension.modLoc("spell_dimension/medal_2"));

        Advancement miner = Advancement.Builder.create()
                .parent(root)
                .display(
                        AllItems.ZHUZI_MINER_HELMET.getDefaultStack(),
                        SDTexts.ADVANCEMENT$MINER$TITLE.get(),
                        SDTexts.ADVANCEMENT$MINER$DESCRIPTION.get(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("0", MinedOresCriterion.condition(64 * 64))
                .rewards(
                        AdvancementRewards.Builder.loot(SpellDimension.modLoc("pool/miner_helmet"))
                )
                .build(SpellDimension.modLoc("spell_dimension/miner"));

        Advancement drop_guide_1 = Advancement.Builder.create()
                .parent(root)
                .display(
                        AllStacks.GUIDE_BOOK,
                        SDTexts.ADVANCEMENT$DROP_GUIDE_1$TITLE.get(),
                        SDTexts.ADVANCEMENT$DROP_GUIDE_1$DESCRIPTION.get(),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("0", DroppedItemsCriterion.condition(AllStacks.GUIDE_BOOK.getItem(), 1))
                .build(SpellDimension.modLoc("spell_dimension/drop_guide_1"));

        Advancement drop_guide_2 = Advancement.Builder.create()
                .parent(drop_guide_1)
                .display(
                        AllStacks.GUIDE_BOOK,
                        SDTexts.ADVANCEMENT$DROP_GUIDE_2$TITLE.get(),
                        SDTexts.ADVANCEMENT$DROP_GUIDE_2$DESCRIPTION.get(),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("0", DroppedItemsCriterion.condition(AllStacks.GUIDE_BOOK.getItem(), 2))
                .build(SpellDimension.modLoc("spell_dimension/drop_guide_2"));

        consumer.accept(root);
        consumer.accept(medal_1);
        consumer.accept(medal_2);
        consumer.accept(miner);
        consumer.accept(drop_guide_1);
        consumer.accept(drop_guide_2);
    }
}
