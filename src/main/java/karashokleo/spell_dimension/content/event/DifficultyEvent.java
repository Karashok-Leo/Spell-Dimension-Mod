package karashokleo.spell_dimension.content.event;

import karashokleo.l2hostility.data.config.WeaponConfig;
import karashokleo.l2hostility.init.LHData;
import karashokleo.spell_dimension.util.TagUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.LifecycledResourceManager;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;

public class DifficultyEvent
{
    private static final String[] rarities = {"common", "uncommon", "rare", "epic", "legendary"};

    public static void init()
    {
        WeaponConfigResolver resolver = new WeaponConfigResolver();
        ServerLifecycleEvents.SERVER_STARTED.register(resolver);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(resolver);
    }

    private static class WeaponConfigResolver implements ServerLifecycleEvents.ServerStarted, ServerLifecycleEvents.EndDataPackReload
    {
        private void resolve()
        {
            WeaponConfig config = new WeaponConfig();
            config.putArmor(0, 200, Items.AIR);
            config.putMeleeWeapon(0, 200, Items.AIR);
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
            LHData.weapons.merge(config);
        }

        @Override
        public void endDataPackReload(MinecraftServer server, LifecycledResourceManager resourceManager, boolean success)
        {
            resolve();
        }

        @Override
        public void onServerStarted(MinecraftServer server)
        {
            resolve();
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
                .ifPresent(config.melee_weapons::add);
    }
}
