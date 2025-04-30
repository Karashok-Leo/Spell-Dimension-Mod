package karashokleo.spell_dimension.content.event;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerTickEvents;
import karashokleo.l2hostility.data.config.WeaponConfig;
import karashokleo.l2hostility.init.LHData;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.data.loot_bag.TextConstants;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import karashokleo.spell_dimension.util.TagUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.LifecycledResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;
import net.spell_power.api.SpellPower;

import java.util.ArrayList;
import java.util.HashMap;

public class DifficultyEvents
{
    private static final HashMap<String, Integer> BOSS_HEALTH_SET = new HashMap<>();

    static
    {
        int[] healths = {200, 400, 500, 600, 700, 800};

        BOSS_HEALTH_SET.put("mutantmonsters:mutant_zombie", healths[0]);
        BOSS_HEALTH_SET.put("mutantmonsters:mutant_skeleton", healths[0]);
        BOSS_HEALTH_SET.put("mutantmonsters:mutant_creeper", healths[0]);
        BOSS_HEALTH_SET.put("mutantmonsters:mutant_enderman", healths[0]);

        BOSS_HEALTH_SET.put("soulsweapons:accursed_lord_boss", healths[1]);
        BOSS_HEALTH_SET.put("soulsweapons:draugr_boss", healths[1]);
        BOSS_HEALTH_SET.put("soulsweapons:night_shade", 60);
        BOSS_HEALTH_SET.put("illagerinvasion:invoker", healths[1]);
        BOSS_HEALTH_SET.put("minecraft:wither", healths[1]);
        BOSS_HEALTH_SET.put("minecraft:warden", healths[1]);
        BOSS_HEALTH_SET.put("minecraft:elder_guardian", 300);

        BOSS_HEALTH_SET.put("deeperdarker:stalker", healths[2]);
        BOSS_HEALTH_SET.put("adventurez:blackstone_golem", healths[2]);
        BOSS_HEALTH_SET.put("aquamirae:captain_cornelia", healths[2]);
        BOSS_HEALTH_SET.put("soulsweapons:chaos_monarch", healths[2]);
        BOSS_HEALTH_SET.put("soulsweapons:moonknight", healths[2]);
        BOSS_HEALTH_SET.put("soulsweapons:returning_knight", healths[2]);

        BOSS_HEALTH_SET.put("graveyard:lich", healths[3]);
        BOSS_HEALTH_SET.put("bosses_of_mass_destruction:lich", healths[3]);
        BOSS_HEALTH_SET.put("bosses_of_mass_destruction:gauntlet", healths[3]);
        BOSS_HEALTH_SET.put("bosses_of_mass_destruction:void_blossom", healths[3]);

        BOSS_HEALTH_SET.put("soulsweapons:day_stalker", healths[4]);
        BOSS_HEALTH_SET.put("soulsweapons:night_prowler", healths[4]);
        BOSS_HEALTH_SET.put("minecraft:ender_dragon", healths[4]);
        BOSS_HEALTH_SET.put("adventurez:the_eye", healths[4]);

        BOSS_HEALTH_SET.put("bosses_of_mass_destruction:obsidilith", healths[5]);
        BOSS_HEALTH_SET.put("adventurez:void_shadow", healths[5]);
        BOSS_HEALTH_SET.put("spellbladenext:magus", healths[5]);
    }

    public static void init()
    {
        // Unify boss max health
        EntityEvents.ON_JOIN_WORLD.register((entity, world, b) ->
        {
            if (!(entity instanceof LivingEntity living))
                return true;
            EntityType<?> type = living.getType();
            if (!type.isIn(LHTags.SEMIBOSS))
                return true;
            EntityAttributeInstance attributeInstance = living.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (attributeInstance == null)
                return true;
            String id = Registries.ENTITY_TYPE.getId(type).toString();
            attributeInstance.setBaseValue(BOSS_HEALTH_SET.getOrDefault(id, 400));
            return true;
        });

        // 逝不过一
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) ->
        {
            if (!entity.getType().isIn(LHTags.SEMIBOSS)) return;
            if (!(killedEntity instanceof PlayerEntity)) return;
            if (!(entity instanceof LivingEntity living)) return;
            // if other players are in the range
            if (!world.getPlayers(player -> player.isAlive() && player.isInRange(entity, 32)).isEmpty()) return;
            living.setHealth(living.getMaxHealth());
        });

        // Ban items in specific difficulty tier
        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            for (int i = 0; i < 3; i++)
            {
                TagKey<Item> tagKey = AllTags.DIFFICULTY_ALLOW.get(i);
                if (stack.isIn(tagKey) &&
                    GameStageComponent.getDifficulty(player) != i)
                {
                    player.sendMessage(SDTexts.TEXT$DIFFICULTY$BAN.get(SDTexts.getDifficultyTierText(i)).formatted(Formatting.RED));
                    return TypedActionResult.fail(stack);
                }
            }
            return TypedActionResult.pass(stack);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            for (int i = 0; i < 3; i++)
            {
                TagKey<Item> tagKey = AllTags.DIFFICULTY_ALLOW.get(i);
                if (stack.isIn(tagKey) &&
                    GameStageComponent.getDifficulty(player) != i)
                {
                    player.sendMessage(SDTexts.TEXT$DIFFICULTY$BAN.get(SDTexts.getDifficultyTierText(i)).formatted(Formatting.RED));
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
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
            // first remove the old modifier
            AttributeUtil.removeModifier(player, LHMiscs.ADD_LEVEL, GameStageComponent.NIGHTMARE_DIFFICULTY_BONUS);
            if (GameStageComponent.getDifficulty(player) < GameStageComponent.NIGHTMARE) return;
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
                TagKey<Item> armorTag = TagUtil.itemTag(TextConstants.RARITIES[i] + "/armor");
                TagKey<Item> weaponTag = TagUtil.itemTag(TextConstants.RARITIES[i] + "/weapon");
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
