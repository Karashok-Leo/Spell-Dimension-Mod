package karashokleo.spell_dimension.content.event;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.data.config.WeaponConfig;
import karashokleo.l2hostility.init.LHData;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.util.TagUtil;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DifficultyEvent
{
    private static final Identifier WEAPON_LOADER_ID = L2Hostility.id("weapon_config");
    private static final String[] rarities = {"common", "uncommon", "rare", "epic", "legendary"};

    public static void init()
    {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new WeaponConfigResolver());
    }

    private static class WeaponConfigResolver implements SimpleSynchronousResourceReloadListener
    {
        @Override
        public Identifier getFabricId()
        {
            return SpellDimension.modLoc("weapon_config");
        }

        @Override
        public Collection<Identifier> getFabricDependencies()
        {
            return List.of(WEAPON_LOADER_ID);
        }

        @Override
        public void reload(ResourceManager manager)
        {
            WeaponConfig config = new WeaponConfig();
            config.putArmor(0, 200, Items.AIR);
            config.putMeleeWeapon(0, 200, Items.AIR);
            config.putRangedWeapon(0, 200, Items.AIR);
            for (int i = 0; i < 5; i++)
            {
                int level = 40 * (i + 1);
                TagKey<Item> armorTag = TagUtil.itemTag(rarities[i] + "/armor");
                TagKey<Item> weaponTag = TagUtil.itemTag(rarities[i] + "/weapon");
                putArmors(armorTag, level, config);
                putWeapons(weaponTag, level, config);
            }
            LHData.weapons.armors.clear();
            LHData.weapons.melee_weapons.clear();
            LHData.weapons.ranged_weapons.clear();
            LHData.weapons.merge(config);
        }
    }

    private static void putArmors(TagKey<Item> armorTag, int level, WeaponConfig config)
    {
        Registries.ITEM.getEntryList(armorTag)
                .map(entries ->
                        new WeaponConfig.ItemConfig(
                                entries.stream()
                                        .map(e -> e.value().getDefaultStack())
                                        .collect(
                                                ArrayList<ItemStack>::new,
                                                ArrayList<ItemStack>::add,
                                                ArrayList::addAll
                                        ),
                                level, 240 - level
                        )
                )
                .ifPresent(config.armors::add);
    }

    private static void putWeapons(TagKey<Item> weaponTag, int level, WeaponConfig config)
    {
        Registries.ITEM.getEntryList(weaponTag)
                .map(
                        entries -> entries.stream()
                                .map(RegistryEntry::value)
                                .collect(Collectors.partitioningBy(item -> item instanceof RangedWeaponItem))
                )
                .ifPresent(map ->
                {
                    ArrayList<ItemStack> ranged = map.get(true).stream()
                            .map(Item::getDefaultStack)
                            .collect(
                                    ArrayList::new,
                                    ArrayList::add,
                                    ArrayList::addAll
                            );
                    config.ranged_weapons.add(new WeaponConfig.ItemConfig(ranged, level, 240 - level));

                    ArrayList<ItemStack> melee = map.get(false).stream()
                            .map(Item::getDefaultStack)
                            .collect(
                                    ArrayList::new,
                                    ArrayList::add,
                                    ArrayList::addAll
                            );
                    config.melee_weapons.add(new WeaponConfig.ItemConfig(melee, level, 240 - level));
                });
    }
}
