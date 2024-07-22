package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.item.essence.logic.EnchantedModifier;
import karashokleo.spell_dimension.content.item.essence.logic.EnlighteningModifier;
import karashokleo.spell_dimension.util.UuidUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellSchools;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class AllGroups
{
    public static final RegistryKey<ItemGroup> BOOKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_books"));
    public static final RegistryKey<ItemGroup> SCROLLS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_scrolls"));
    public static final RegistryKey<ItemGroup> ELES_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_eles"));
    public static final RegistryKey<ItemGroup> ECES_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_eces"));
    public static final RegistryKey<ItemGroup> MISC_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), SpellDimension.modLoc("group_misc"));

    public static void register()
    {
        List<AttributeModifier> allModifiers = AttributeModifier.getAll();

        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_books"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.SPELL_BOOKS.get(SpellSchools.ARCANE).get(2).getDefaultStack())
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_books"))
                        .entries((displayContext, entries) ->
                                AllItems.SPELL_BOOKS.values().stream().flatMap(Collection::stream).forEach(entries::add))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_scrolls"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.SPELL_SCROLL.getStack(SpellDimension.modLoc("converge")))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_scrolls"))
                        .entries((displayContext, entries) ->
                                entries.addAll(SpellRegistry.all().keySet().stream().map(AllItems.SPELL_SCROLL::getStack).sorted(Comparator.comparing(stack ->
                                {
                                    SpellInfo spellInfo = AllItems.SPELL_SCROLL.getSpellInfo(stack);
                                    return spellInfo == null ? "" : spellInfo.spell().school.id.toString();
                                })).toList()))
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_eles"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.ENLIGHTENING_ESSENCE.getStack(
                                new EnlighteningModifier(
                                        SpellSchools.FIRE.attribute,
                                        UuidUtil.getSelfUuid(EntityAttributeModifier.Operation.ADDITION),
                                        1.0,
                                        EntityAttributeModifier.Operation.ADDITION
                                )
                        ))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_eles"))
                        .entries((displayContext, entries) ->
                        {
                            for (AttributeModifier modifier : allModifiers)
                                entries.add(AllItems.ENLIGHTENING_ESSENCE.getStack(
                                        new EnlighteningModifier(
                                                modifier.attribute(),
                                                UuidUtil.getSelfUuid(modifier.operation()),
                                                modifier.amount(),
                                                modifier.operation()
                                        )
                                ));
                        })
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_eces"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.ENCHANTED_ESSENCE.getStack(
                                new EnchantedModifier(30,
                                        EquipmentSlot.MAINHAND,
                                        new EnlighteningModifier(
                                                SpellSchools.FROST.attribute,
                                                UuidUtil.getEquipmentUuid(EquipmentSlot.MAINHAND, EntityAttributeModifier.Operation.ADDITION),
                                                1.0,
                                                EntityAttributeModifier.Operation.ADDITION
                                        )
                                )
                        ))
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_eces"))
                        .entries((displayContext, entries) ->
                        {
                            for (int i = 0; i < 3; i++)
                                for (EquipmentSlot slot : EquipmentSlot.values())
                                    for (AttributeModifier modifier : allModifiers)
                                        entries.add(AllItems.ENCHANTED_ESSENCE.getStack(
                                                new EnchantedModifier(
                                                        (i + 1) * 10,
                                                        slot,
                                                        new EnlighteningModifier(
                                                                modifier.attribute(),
                                                                UuidUtil.getEquipmentUuid(slot, modifier.operation()),
                                                                modifier.amount(),
                                                                modifier.operation()
                                                        )
                                                )
                                        ));
                        })
                        .build()
        );
        Registry.register(Registries.ITEM_GROUP, SpellDimension.modLoc("group_misc"),
                FabricItemGroup
                        .builder()
                        .icon(() -> AllItems.BASE_ESSENCES.get(SpellSchools.LIGHTNING).get(1).getDefaultStack())
                        .displayName(Text.translatable("itemGroup.spell-dimension.group_misc"))
                        .entries((displayContext, entries) ->
                        {
                            AllItems.BASE_ESSENCES.values().stream().flatMap(Collection::stream).forEach(entries::add);
                            entries.add(AllItems.DISENCHANTED_ESSENCE);
                            entries.add(AllItems.MENDING_ESSENCE);
                            entries.add(AllItems.DEBUG_STAFF);
                        })
                        .build()
        );
    }
}
