package karashokleo.spell_dimension.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.ModifiersConfig;
import karashokleo.spell_dimension.content.misc.AttrModifier;
import karashokleo.spell_dimension.content.misc.EnchantedModifier;
import karashokleo.spell_dimension.content.misc.Mage;
import karashokleo.spell_dimension.content.misc.MageMajor;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.AttributeResolver;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

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
                        .icon(() -> new ItemStack(AllItems.SPELL_BOOKS.get(SpellSchools.ARCANE).primary))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_books"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_eles"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.ENLIGHTENING_ESSENCE.getStack(
                                new AttrModifier(
                                        SpellSchools.FIRE.attribute,
                                        UuidUtil.getSelfUuid(EntityAttributeModifier.Operation.ADDITION),
                                        1.0,
                                        EntityAttributeModifier.Operation.ADDITION
                                )
                        ))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_eles"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_eces"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.ENCHANTED_ESSENCE.getStack(
                                new EnchantedModifier(30,
                                        EquipmentSlot.MAINHAND,
                                        new AttrModifier(
                                                SpellSchools.FROST.attribute,
                                                UuidUtil.getEquipUuid(EquipmentSlot.MAINHAND, EntityAttributeModifier.Operation.ADDITION),
                                                1.0,
                                                EntityAttributeModifier.Operation.ADDITION
                                        )
                                )
                        ))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_eces"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_medals"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.MAGE_MEDAL.getStack(new Mage(3, SpellSchools.HEALING, null)))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_medals"))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_misc"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.BASE_ESSENCES.get(SpellSchools.LIGHTNING).get(1).getDefaultStack())
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
            for (SpellSchool school : SpellSchools.all())
                entries.add(AllItems.ENLIGHTENING_ESSENCE.getStack(
                        new AttrModifier(
                                school.attribute,
                                UuidUtil.getSelfUuid(EntityAttributeModifier.Operation.ADDITION),
                                1.0,
                                EntityAttributeModifier.Operation.ADDITION
                        )
                ));
            for (ModifiersConfig.AttributeModifier modifier : AllConfigs.modifiers.value.modifiers)
                entries.add(AllItems.ENLIGHTENING_ESSENCE.getStack(
                        new AttrModifier(
                                AttributeResolver.get(new Identifier(modifier.attributeId)),
                                UuidUtil.getSelfUuid(EntityAttributeModifier.Operation.valueOf(modifier.operation)),
                                modifier.amount,
                                EntityAttributeModifier.Operation.valueOf(modifier.operation)
                        )
                ));
        });
        ItemGroupEvents.modifyEntriesEvent(ECES_GROUP_KEY).register(entries ->
        {
            for (int i = 0; i < 3; i++)
                for (EquipmentSlot slot : EquipmentSlot.values())
                {
                    for (SpellSchool school : SpellSchools.all())
                        entries.add(AllItems.ENCHANTED_ESSENCE.getStack(

                                new EnchantedModifier((i + 1) * 10,
                                        slot,
                                        new AttrModifier(
                                                school.attribute,
                                                UuidUtil.getEquipUuid(slot, EntityAttributeModifier.Operation.ADDITION),
                                                1.0,
                                                EntityAttributeModifier.Operation.ADDITION
                                        )
                                )
                        ));
                    for (ModifiersConfig.AttributeModifier modifier : AllConfigs.modifiers.value.modifiers)
                        entries.add(AllItems.ENCHANTED_ESSENCE.getStack(
                                new EnchantedModifier(
                                        (i + 1) * 10,
                                        slot,
                                        new AttrModifier(
                                                AttributeResolver.get(new Identifier(modifier.attributeId)),
                                                UuidUtil.getSelfUuid(EntityAttributeModifier.Operation.valueOf(modifier.operation)),
                                                modifier.amount,
                                                EntityAttributeModifier.Operation.valueOf(modifier.operation)
                                        )
                                )
                        ));
                }
        });
        ItemGroupEvents.modifyEntriesEvent(MEDALS_GROUP_KEY).register(entries ->
        {
            entries.add(AllItems.MAGE_MEDAL.getDefaultStack());
            for (SpellSchool school : SpellSchools.all())
            {
                if (!(school.archetype == SpellSchool.Archetype.MAGIC)) continue;
                for (MageMajor major : MageMajor.getMajors(school))
                    for (int i = 1; i <= Mage.MAX_GRADE; i++)
                        entries.add(AllItems.MAGE_MEDAL.getStack(new Mage(i, school, major)));
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
