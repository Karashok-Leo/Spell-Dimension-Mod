package karashokleo.spell_dimension.client.misc;

import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.item.ModonomiconItem;
import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.content.item.DynamicSpellBookItem;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_power.api.SpellSchool;

import java.util.List;
import java.util.Optional;

public class AdditionalTooltip
{
    public static final Identifier FINAL = SpellDimension.modLoc("tooltip_final");

    public static void register()
    {
        ItemTooltipCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, FINAL);
        ItemTooltipCallback.EVENT.register(EnchantedModifier::levelTooltip);
        ItemTooltipCallback.EVENT.register(FINAL, AdditionalTooltip::removeDynamicBookBindingTip);
        ItemTooltipCallback.EVENT.register(AdditionalTooltip::appendQuestScroll);
        ItemTooltipCallback.EVENT.register(AdditionalTooltip::appendFlexBreastplate);
        ItemTooltipCallback.EVENT.register(AdditionalTooltip::modifyCursePride);
        ItemTooltipCallback.EVENT.register(AdditionalTooltip::appendModonomicon);
        ItemTooltipCallback.EVENT.register(AdditionalTooltip::appendSpellScroll);
        ItemTooltipCallback.EVENT.register(AdditionalTooltip::appendBottleNightmare);
    }

    private static void removeDynamicBookBindingTip(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        if (stack.getItem() instanceof DynamicSpellBookItem)
            lines.removeIf(line ->
                    line.getContent() instanceof TranslatableTextContent content &&
                    content.getKey().equals("spell.tooltip.spell_binding_tip"));
    }

    private static void appendQuestScroll(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        if (!stack.isOf(AllItems.QUEST_SCROLL)) return;
        var player = MinecraftClient.getInstance().player;
        Optional<Quest> optional = AllItems.QUEST_SCROLL.getQuest(stack);
        if (player != null &&
            optional.isPresent() &&
            QuestUsage.isQuestCompleted(player, optional.get()))
            lines.add(SDTexts.TEXT$QUEST$COMPLETED.get());
        if (player != null && player.isCreative())
            lines.add(SDTexts.TOOLTIP$SHIFT_RESET.get().formatted(Formatting.GRAY));
    }

    private static void appendFlexBreastplate(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        if (!stack.isOf(AllItems.FLEX_BREASTPLATE)) return;
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;
        lines.add(SDTexts.TOOLTIP$FLEX_BREASTPLATE$DAMAGE_FACTOR.get(
                "%.1f%%".formatted((1 - AllItems.FLEX_BREASTPLATE.getDamageFactor(player)) * 100)
        ).formatted(Formatting.RED));
    }

    private static void modifyCursePride(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        if (!stack.isOf(TrinketItems.CURSE_PRIDE)) return;
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;
        int level = DifficultyLevel.ofAny(player);
        double rate = LHConfig.common().items.curse.prideDamageBonus;
        lines.add(SDTexts.TOOLTIP$CURSE_PRIDE_2.get(
                "%.1f%%".formatted((level * rate) * 100)
        ).formatted(Formatting.AQUA));
    }

    private static void appendModonomicon(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        if (!(stack.getItem() instanceof ModonomiconItem)) return;
        Book book = ModonomiconItem.getBook(stack);
        if (!book.getId().equals(MagicGuidanceProvider.BOOK_ID)) return;
        lines.add(SDTexts.TEXT$MAGE_BOOK$1.get().formatted(Formatting.GRAY).formatted(Formatting.STRIKETHROUGH));
        lines.add(SDTexts.TEXT$MAGE_BOOK$2.get().formatted(Formatting.LIGHT_PURPLE));
    }

    private static void appendSpellScroll(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        // book requirement
        if (!stack.isOf(AllItems.SPELL_SCROLL)) return;
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null) return;
        if (GameStageComponent.isNormalMode(player)) return;
        SpellInfo spellInfo = AllItems.SPELL_SCROLL.getSpellInfo(stack);
        if (spellInfo == null) return;
        SpellSchool school = spellInfo.spell().school;
        if (!SchoolUtil.SCHOOLS.contains(school)) return;
        int grade = AllSpells.getSpellTier(spellInfo.id());
        DynamicSpellBookItem bookItem = AllItems.SPELL_BOOKS.get(school).get(grade);
        lines.add(SDTexts.SCROLL$BOOK_REQUIREMENT.get(grade + 1, bookItem.getName()).formatted(Formatting.GRAY));
    }

    private static void appendBottleNightmare(ItemStack stack, TooltipContext context, List<Text> lines)
    {
        if (!stack.isOf(AllItems.BOTTLE_NIGHTMARE)) return;
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null) return;

        int currentDifficulty = GameStageComponent.getDifficulty(player);
        int nextDifficulty = GameStageComponent.getNextDifficulty(currentDifficulty);
        MutableText currentTierText = SDTexts.getDifficultyTierText(currentDifficulty);
        MutableText nextTierText = SDTexts.getDifficultyTierText(nextDifficulty);

        if (currentDifficulty != GameStageComponent.NIGHTMARE)
            lines.add(SDTexts.TOOLTIP$BOTTLE_NIGHTMARE.get(nextTierText).formatted(Formatting.RED));

        lines.add(SDTexts.TOOLTIP$DIFFICULTY_TIER$CURRENT.get(currentTierText).formatted(Formatting.BOLD));

        if (currentDifficulty == GameStageComponent.NORMAL)
        {
            appendHardcoreTooltip(lines);
        } else if (currentDifficulty == GameStageComponent.HARDCORE)
        {
            appendHardcoreTooltip(lines);
            appendNightmareTooltip(lines);
        } else if (currentDifficulty == GameStageComponent.NIGHTMARE)
        {
            appendNightmareTooltip(lines);
        }

        if (player.isCreative())
            lines.add(SDTexts.TOOLTIP$SHIFT_RESET.get().formatted(Formatting.GRAY));
    }

    private static void appendHardcoreTooltip(List<Text> lines)
    {
        lines.add(
                SDTexts.TOOLTIP$BRACKETS.get(SDTexts.DIFFICULTY_TIER$1.get())
                        .append(":")
                        .formatted(Formatting.DARK_RED)
        );
        for (int i = 1; i <= 7; i++)
            lines.add(
                    SDTexts.TOOLTIP$DIFFICULTY_TIER$DESC.get(
                            Text.translatable("tooltip.spell-dimension.difficulty_tier.hardcore." + i)
                    ).formatted(Formatting.DARK_RED)
            );
    }

    private static void appendNightmareTooltip(List<Text> lines)
    {
        lines.add(
                SDTexts.TOOLTIP$BRACKETS.get(SDTexts.DIFFICULTY_TIER$2.get())
                        .append(":")
                        .formatted(Formatting.DARK_PURPLE)
        );
        lines.add(
                SDTexts.TOOLTIP$DIFFICULTY_TIER$DESC.get(
                        SDTexts.TOOLTIP$DIFFICULTY_TIER$NIGHTMARE.get()
                ).formatted(Formatting.DARK_PURPLE)
        );
    }
}
