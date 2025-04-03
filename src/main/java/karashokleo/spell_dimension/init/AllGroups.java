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
    public static final ItemGroupBuilder SPELL_BOOKS = GroupEntry.of("spell_books");
    public static final ItemGroupBuilder SPELL_SCROLLS = GroupEntry.of("spell_scrolls");
    public static final ItemGroupBuilder QUEST_SCROLLS = GroupEntry.of("quest_scrolls");
    public static final ItemGroupBuilder ENCHANTED_ESSENCES = GroupEntry.of("enchanted_essences");
    public static final ItemGroupBuilder ENLIGHTENING_ESSENCES = GroupEntry.of("enlightening_essences");
    public static final ItemGroupBuilder MISC = GroupEntry.of("misc");

    public static void register()
    {
        List<ItemStack> spellBooks = AllStacks.getSpellBooks();
        ItemStack firstSpellBook = spellBooks.get(0);
        SPELL_BOOKS.addAll(spellBooks)
                .setIcon(() -> firstSpellBook)
                .addEN("Spell Dimension: Spell Books")
                .addZH("咒次元：法术书")
                .register();

        List<ItemStack> spellScrolls = AllStacks.getSpellScrolls();
        ItemStack firstSpellScroll = spellScrolls.get(0);
        SPELL_SCROLLS.addAll(spellScrolls)
                .setIcon(() -> firstSpellScroll)
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

        List<ItemStack> baseEssences = AllStacks.getBaseEssences();
        MISC.addAll(baseEssences)
                .setIcon(() -> AllItems.MAGIC_MIRROR.getDefaultStack())
                .addEN("Spell Dimension: Miscellaneous")
                .addZH("咒次元：杂项")
                .register();

        ItemGroupEvents.modifyEntriesEvent(LootBagItemRegistry.ITEM_GROUP_KEY).register(entries ->
        {
            AllItems.RANDOM_MATERIAL.forEach(entries::add);
            AllItems.RANDOM_GEAR.forEach(entries::add);
            AllItems.RANDOM_BOOK.forEach(entries::add);
        });
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
