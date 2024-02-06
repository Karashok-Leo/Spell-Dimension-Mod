package net.karashokleo.spelldimension.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.spell_power.api.MagicSchool;

public class AllGroups
{
    public static final RegistryKey<ItemGroup> BOOKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_books"));
    public static final RegistryKey<ItemGroup> EES_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_ees"));
    public static final RegistryKey<ItemGroup> MEDALS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_medals"));
    public static final RegistryKey<ItemGroup> MISC_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_misc"));

    public static void register()
    {
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_books"),
                FabricItemGroup
                        .builder()
                        .icon(() -> new ItemStack(AllItems.SPELL_BOOKS.get(MagicSchool.ARCANE).primary))
                        .displayName(Text.translatable("itemGroup.spellplus.group_books"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_ees"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.BASE_ESSENCES.get(MagicSchool.FIRE).get(1).getDefaultStack())
                        .displayName(Text.translatable("itemGroup.spellplus.group_ees"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_medals"),
                FabricItemGroup
                        .builder()
                        .icon(() -> new ItemStack(AllItems.MAGE_MEDAL))
                        .displayName(Text.translatable("itemGroup.spellplus.group_medals"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_misc"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.BASE_ESSENCES.get(MagicSchool.FROST).get(1).getDefaultStack())
                        .displayName(Text.translatable("itemGroup.spellplus.group_misc"))
                        .build()
        );

        ItemGroupEvents.modifyEntriesEvent(BOOKS_GROUP_KEY).register(entries ->
                AllItems.SPELL_BOOKS.values().forEach(entry ->
                {
                    entries.add(entry.primary);
                    entry.majors.values().forEach(books -> books.forEach(entries::add));
                }));
        ItemGroupEvents.modifyEntriesEvent(EES_GROUP_KEY).register(entries ->
        {
            for (MagicSchool school : MagicSchool.values())
            {
                if (!school.isMagical) continue;
                for (int i = 0; i < 3; i++)
                {
                    for (EquipmentSlot slot : EquipmentSlot.values())
                        entries.add(AllItems.ENCHANTED_ESSENCE.getStack(new Mage(i, school, null), new ExtraModifier((i + 1) * 10, slot, school.attributeId())));
                }
            }
        });
        ItemGroupEvents.modifyEntriesEvent(MEDALS_GROUP_KEY).register(entries ->
        {
            entries.add(AllItems.MAGE_MEDAL.getDefaultStack());
            for (MagicSchool school : MagicSchool.values())
            {
                if (!school.isMagical) continue;
                entries.add(AllItems.MAGE_MEDAL.getStack(0, school, null));
                for (MageMajor major : MageMajor.getMajors(school))
                    for (int i = 1; i <= Mage.MAX_GRADE; i++)
                        entries.add(AllItems.MAGE_MEDAL.getStack(i, school, major));
            }
        });
        ItemGroupEvents.modifyEntriesEvent(MISC_GROUP_KEY).register(entries ->
        {
            AllItems.BASE_ESSENCES.values().forEach(items -> items.forEach(entries::add));
            entries.add(AllItems.DISENCHANTED_ESSENCE);
            entries.add(AllItems.DEBUG_STAFF);
        });
    }
}
