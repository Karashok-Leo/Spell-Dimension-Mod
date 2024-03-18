package net.karashokleo.spelldimension.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.config.mod_config.ModifiersConfig;
import net.karashokleo.spelldimension.misc.AttrModifier;
import net.karashokleo.spelldimension.misc.EnchantedModifier;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;

public class AllGroups
{
    public static final RegistryKey<ItemGroup> BOOKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_books"));
    public static final RegistryKey<ItemGroup> ELES_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_eles"));
    public static final RegistryKey<ItemGroup> ECES_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_eces"));
    public static final RegistryKey<ItemGroup> MEDALS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_medals"));
    public static final RegistryKey<ItemGroup> MISC_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_misc"));

    public static void register()
    {
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_books"),
                FabricItemGroup
                        .builder()
                        .icon(() -> new ItemStack(AllItems.SPELL_BOOKS.get(MagicSchool.ARCANE).primary))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_books"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_eles"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.ENLIGHTENING_ESSENCE.getStack(AttrModifier.forSelf(MagicSchool.FIRE.attributeId())))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_eles"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_eces"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.ENCHANTED_ESSENCE.getStack(new EnchantedModifier(30, EquipmentSlot.MAINHAND, MagicSchool.FROST)))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_eces"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_medals"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.MAGE_MEDAL.getStack(new Mage(3, MagicSchool.HEALING, null)))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_medals"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_misc"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.BASE_ESSENCES.get(MagicSchool.LIGHTNING).get(1).getDefaultStack())
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_misc"))
                        .build()
        );

        ItemGroupEvents.modifyEntriesEvent(BOOKS_GROUP_KEY).register(entries ->
                AllItems.SPELL_BOOKS.values().forEach(entry ->
                {
                    entries.add(entry.primary);
                    entry.majors.values().forEach(books -> books.forEach(entries::add));
                }));
        ItemGroupEvents.modifyEntriesEvent(ELES_GROUP_KEY).register(entries ->
        {
            for (MagicSchool school : MagicSchool.values())
                entries.add(AllItems.ENLIGHTENING_ESSENCE.getStack(AttrModifier.forSelf(school.attributeId())));
            for (ModifiersConfig.AttributeModifier modifier : AllConfigs.modifiers.value.modifiers)
                entries.add(AllItems.ENLIGHTENING_ESSENCE.getStack(AttrModifier.forSelf(new Identifier(modifier.attributeId), modifier.amount, EntityAttributeModifier.Operation.valueOf(modifier.operation))));
        });
        ItemGroupEvents.modifyEntriesEvent(ECES_GROUP_KEY).register(entries ->
        {
            for (int i = 0; i < 3; i++)
                for (EquipmentSlot slot : EquipmentSlot.values())
                {
                    for (MagicSchool school : MagicSchool.values())
                        entries.add(AllItems.ENCHANTED_ESSENCE.getStack(new EnchantedModifier((i + 1) * 10, slot, school)));
                    for (ModifiersConfig.AttributeModifier modifier : AllConfigs.modifiers.value.modifiers)
                        entries.add(AllItems.ENCHANTED_ESSENCE.getStack(new EnchantedModifier((i + 1) * 10, slot, new Identifier(modifier.attributeId), modifier.amount, EntityAttributeModifier.Operation.valueOf(modifier.operation))));
                }
        });
        ItemGroupEvents.modifyEntriesEvent(MEDALS_GROUP_KEY).register(entries ->
        {
            entries.add(AllItems.MAGE_MEDAL.getDefaultStack());
            for (MagicSchool school : MagicSchool.values())
            {
                if (!school.isMagical) continue;
                for (MageMajor major : MageMajor.getMajors(school))
                    for (int i = 1; i <= Mage.MAX_GRADE; i++)
                        entries.add(AllItems.MAGE_MEDAL.getStack(i, school, major));
            }
        });
        ItemGroupEvents.modifyEntriesEvent(MISC_GROUP_KEY).register(entries ->
        {
            AllItems.BASE_ESSENCES.values().forEach(items -> items.forEach(entries::add));
            entries.add(AllItems.DISENCHANTED_ESSENCE);
            entries.add(AllItems.MENDING_ESSENCE);
            entries.add(AllItems.DEBUG_STAFF);
        });
    }
}
