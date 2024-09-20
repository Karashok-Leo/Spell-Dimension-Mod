package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class AllGroups
{
    public static final RegistryKey<ItemGroup> BOOKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_books"));
    public static final RegistryKey<ItemGroup> SPELL_SCROLLS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_spell_scrolls"));
    public static final RegistryKey<ItemGroup> QUEST_SCROLLS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_quest_scrolls"));
    public static final RegistryKey<ItemGroup> ELES_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_eles"));
    public static final RegistryKey<ItemGroup> ECES_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_eces"));
    public static final RegistryKey<ItemGroup> MISC_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_misc"));

    public static void register()
    {
        Registry.register(Registries.ITEM_GROUP, BOOKS_GROUP_KEY,
                FabricItemGroup
                        .builder()
                        .icon(() -> AllStacks.SPELL_BOOK_STACKS.get(0))
                        .displayName(Text.translatable(BOOKS_GROUP_KEY.getValue().toTranslationKey("itemGroup")))
                        .entries((displayContext, entries) -> entries.addAll(AllStacks.SPELL_BOOK_STACKS))
                        .build()
        );

        Registry.register(Registries.ITEM_GROUP, SPELL_SCROLLS_GROUP_KEY,
                FabricItemGroup
                        .builder()
                        .icon(() -> AllStacks.getScrolls().get(0))
                        .displayName(Text.translatable(SPELL_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup")))
                        .entries((displayContext, entries) -> entries.addAll(AllStacks.getScrolls()))
                        .build()
        );

        Registry.register(Registries.ITEM_GROUP, QUEST_SCROLLS_GROUP_KEY,
                FabricItemGroup
                        .builder()
                        .icon(() -> AllStacks.QUEST_SCROLL_STACKS.get(0))
                        .displayName(Text.translatable(QUEST_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup")))
                        .entries((displayContext, entries) -> entries.addAll(AllStacks.QUEST_SCROLL_STACKS))
                        .build()
        );

        Registry.register(Registries.ITEM_GROUP, ELES_GROUP_KEY,
                FabricItemGroup
                        .builder()
                        .icon(() -> AllStacks.ELES_STACKS.get(0))
                        .displayName(Text.translatable(ELES_GROUP_KEY.getValue().toTranslationKey("itemGroup")))
                        .entries((displayContext, entries) -> entries.addAll(AllStacks.ELES_STACKS))
                        .build()
        );

        Registry.register(Registries.ITEM_GROUP, ECES_GROUP_KEY,
                FabricItemGroup
                        .builder()
                        .icon(() -> AllStacks.ECES_STACKS.get(0))
                        .displayName(Text.translatable(ECES_GROUP_KEY.getValue().toTranslationKey("itemGroup")))
                        .entries((displayContext, entries) -> entries.addAll(AllStacks.ECES_STACKS))
                        .build()
        );

        Registry.register(Registries.ITEM_GROUP, MISC_GROUP_KEY,
                FabricItemGroup
                        .builder()
                        .icon(() -> AllStacks.BASE_ESSENCE_STACKS.get(0))
                        .displayName(Text.translatable(MISC_GROUP_KEY.getValue().toTranslationKey("itemGroup")))
                        .entries((displayContext, entries) ->
                        {
                            entries.addAll(AllStacks.BASE_ESSENCE_STACKS);
                            entries.add(AllItems.DISENCHANTED_ESSENCE);
                            entries.add(AllItems.MENDING_ESSENCE);
                            entries.add(AllItems.DEBUG_STAFF);
                            entries.add(AllItems.ABYSS_GUARD);
                            entries.add(AllItems.ACCURSED_BLACKSTONE);
                            entries.add(AllItems.SUN_MOON_STAR);
                            entries.add(AllBlocks.SPELL_INFUSION_PEDESTAL.item());
                        })
                        .build()
        );
    }
}
