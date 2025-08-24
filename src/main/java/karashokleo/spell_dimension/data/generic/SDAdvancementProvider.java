package karashokleo.spell_dimension.data.generic;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.advancement.MiningCriterion;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
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

        Advancement medal = Advancement.Builder.create()
                .parent(root)
                .display(
                        AllItems.MEDAL.getDefaultStack(),
                        SDTexts.ADVANCEMENT$MEDAL$TITLE.get(),
                        SDTexts.ADVANCEMENT$MEDAL$DESCRIPTION.get(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
                .criterion("0", ConsumeItemCriterion.Conditions.item(AllItems.MEDAL))
                .build(SpellDimension.modLoc("spell_dimension/medal"));

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
                .criterion("0", MiningCriterion.condition(10))
                .rewards(
                        AdvancementRewards.Builder.loot(SpellDimension.modLoc("pool/miner_helmet"))
                )
                .build(SpellDimension.modLoc("spell_dimension/miner"));

        consumer.accept(root);
        consumer.accept(medal);
        consumer.accept(miner);
    }
}
