package karashokleo.spell_dimension.init;

import karashokleo.leobrary.datagen.builder.ItemGroupBuilder;
import karashokleo.loot_bag.internal.item.LootBagItemRegistry;
import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;

public class AllGroups
{
    public static final ItemGroupBuilder SPELL_BOOKS = GroupEntry.of("sd_spell_books");
    public static final ItemGroupBuilder SPELL_SCROLLS = GroupEntry.of("sd_spell_scrolls");
    public static final ItemGroupBuilder QUEST_SCROLLS = GroupEntry.of("sd_quest_scrolls");
    public static final ItemGroupBuilder ENCHANTED_ESSENCES = GroupEntry.of("sd_enchanted_essences");
    public static final ItemGroupBuilder ENLIGHTENING_ESSENCES = GroupEntry.of("sd_enlightening_essences");
    public static final ItemGroupBuilder MISC = GroupEntry.of("sd_misc");

    public static void register()
    {
        List<ItemStack> spellBooks = AllStacks.getSpellBooks();
        ItemStack firstSpellBook = spellBooks.get(0);
        SPELL_BOOKS.addAll(spellBooks)
                .setIcon(() -> firstSpellBook)
                .addEN("Spell Dimension: Spell Books")
                .addZH("咒次元：法术书")
                .register();

        SPELL_SCROLLS.setIcon(() -> AllItems.SPELL_SCROLL.getStack(AllSpells.ARCANE_BEAM))
                .addEN("Spell Dimension: Spell Scrolls")
                .addZH("咒次元：法术卷轴")
                .register();

        List<ItemStack> questScrolls = AllStacks.getQuestScrolls();
        ItemStack firstQuestScroll = questScrolls.get(0);
        QUEST_SCROLLS.addAll(questScrolls)
                .setIcon(() -> firstQuestScroll)
                .addEN("Spell Dimension: Quest Scrolls")
                .addZH("咒次元：任务卷轴")
                .register();

        List<ItemStack> enchantedEssences = AllStacks.getEnchantedEssences();
        ItemStack firstEnchantedEssence = enchantedEssences.get(0);
        ENCHANTED_ESSENCES.addAll(enchantedEssences)
                .setIcon(() -> firstEnchantedEssence)
                .addEN("Spell Dimension: Enchanted Essences")
                .addZH("咒次元：束魔精华")
                .register();

        List<ItemStack> enlighteningEssences = AllStacks.getEnlighteningEssences();
        ItemStack firstEnlighteningEssence = enlighteningEssences.get(0);
        ENLIGHTENING_ESSENCES.addAll(enlighteningEssences)
                .setIcon(() -> firstEnlighteningEssence)
                .addEN("Spell Dimension: Enlightening Essences")
                .addZH("咒次元：源启精华")
                .register();

        MISC.setIcon(() -> AllItems.MAGIC_MIRROR.getDefaultStack())
                .addEN("Spell Dimension: Miscellaneous")
                .addZH("咒次元：杂项")
                .register();

        ItemGroupEvents.modifyEntriesEvent(LootBagItemRegistry.ITEM_GROUP_KEY).register(entries ->
        {
            AllItems.RANDOM_MATERIAL.forEach(entries::add);
            AllItems.RANDOM_GEAR.forEach(entries::add);
            AllItems.RANDOM_BOOK.forEach(entries::add);
        });

        ItemGroupEvents.modifyEntriesEvent(SPELL_SCROLLS.registryKey).register(entries -> entries.addAll(AllStacks.getSpellScrolls()));
    }

    private static class GroupEntry extends ItemGroupBuilder
    {
        public GroupEntry(String name)
        {
            super(name);
        }

        public static GroupEntry of(String name)
        {
            return new GroupEntry(name);
        }

        public GroupEntry addAll(Collection<ItemStack> stacks)
        {
            entries.addAll(stacks);
            return this;
        }

        @Override
        public String getNameSpace()
        {
            return SpellDimension.MOD_ID;
        }
    }
}
