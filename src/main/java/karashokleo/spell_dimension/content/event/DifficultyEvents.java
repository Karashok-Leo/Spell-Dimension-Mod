package karashokleo.spell_dimension.content.event;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerTickEvents;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.data.config.WeaponConfig;
import karashokleo.l2hostility.init.LHData;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import karashokleo.spell_dimension.util.TagUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.LifecycledResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.TypedActionResult;
import net.spell_power.api.SpellPower;

import java.util.ArrayList;

public class DifficultyEvents
{
    private static final String[] rarities = {"common", "uncommon", "rare", "epic", "legendary"};

    public static void init()
    {
        // Ban items in hard mode
        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            if (GameStageComponent.isNormalMode(player))
                return TypedActionResult.pass(stack);
            if (!stack.isOf(ConsumableItems.HOSTILITY_ORB) &&
                !stack.isOf(ConsumableItems.BOTTLE_SANITY))
                return TypedActionResult.pass(stack);
            player.sendMessage(SDTexts.TEXT$DIFFICULTY$BAN_ITEM.get(), true);
            return TypedActionResult.fail(stack);
        });

        // 深渊守护（远古守卫者）锁船长阶段
        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            PlayerInventory inventory = player.getInventory();
            if (!world.isClient() &&
                stack.isOf(AquamiraeItems.SHELL_HORN) &&
                !inventory.containsAny(itemStack -> itemStack.isOf(AllItems.ABYSS_GUARD)))
            {
                player.sendMessage(SDTexts.TEXT$ABYSS_GUARD.get(), true);
                return TypedActionResult.fail(stack);
            }
            return TypedActionResult.pass(stack);
        });

        // Extra Difficulty bonus of Nightmare
        PlayerTickEvents.END.register(player ->
        {
            if (player.getWorld().isClient()) return;
            if (player.age % 40 != 0) return;
            if (GameStageComponent.getDifficulty(player) < GameStageComponent.NIGHTMARE) return;
            // first remove the old modifier
            AttributeUtil.removeModifier(player, LHMiscs.ADD_LEVEL, GameStageComponent.NIGHTMARE_DIFFICULTY_BONUS);
            // then add the new modifier
            // get the sum of all spell power
            double sum = SchoolUtil.SCHOOLS
                    .stream()
                    .mapToDouble(school -> SpellPower.getSpellPower(school, player).baseValue())
                    .sum();
            AttributeUtil.addModifier(player, LHMiscs.ADD_LEVEL, GameStageComponent.NIGHTMARE_DIFFICULTY_BONUS, "Nightmare Difficulty Bonus", sum * 0.5, EntityAttributeModifier.Operation.ADDITION);
        });

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
