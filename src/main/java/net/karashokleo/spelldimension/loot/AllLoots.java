package net.karashokleo.spelldimension.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.config.mod_config.LootConfig;
import net.karashokleo.spelldimension.item.AllItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.MagicSchool;

public class AllLoots
{
    public static LootFunctionType RANDOM_ENCHANTED_ESSENCE;
    public static LootFunctionType RANDOM_ENLIGHTENING_ESSENCE;

    public static void register()
    {
        RANDOM_ENCHANTED_ESSENCE = Registry.register(Registries.LOOT_FUNCTION_TYPE, SpellDimension.modLoc("random_enchanted_essence"), new LootFunctionType(new RandomEnchantedEssenceFunction.Serializer()));
        RANDOM_ENLIGHTENING_ESSENCE = Registry.register(Registries.LOOT_FUNCTION_TYPE, SpellDimension.modLoc("random_enlightening_essence"), new LootFunctionType(new RandomEnlighteningEssenceFunction.Serializer()));

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->
        {
            if (id.getPath().contains("chest"))
            {
                LootPool.Builder builder = LootPool.builder();
                addPool(builder, AllConfigs.loot.value.random_essence.chest_pool);
                tableBuilder.pool(builder.build());
            }
            if (id.getPath().contains("entities"))
            {
                LootPool.Builder builder = LootPool.builder();
                addPool(builder, AllConfigs.loot.value.random_essence.entity_pool);
                builder.conditionally(KilledByPlayerLootCondition.builder());
                tableBuilder.pool(builder.build());
            }
        });
    }

    private static void addPool(LootPool.Builder builder, LootConfig.RandomEssenceConfig.LootPool pool)
    {
        builder.rolls(UniformLootNumberProvider.create(pool.min_rolls, pool.max_rolls));
        LeafEntry.Builder<?> emptyEntry = EmptyEntry
                .builder()
                .weight(pool.empty_weight);
        builder.with(emptyEntry);

        // EnchantedEssence
        for (LootConfig.RandomEssenceConfig.EcEntry entry : pool.entries)
        {
            LeafEntry.Builder<?> ecEntry = ItemEntry
                    .builder(AllItems.ENCHANTED_ESSENCE)
                    .weight(entry.weight)
                    .apply(RandomEnchantedEssenceFunction.builder(UniformLootNumberProvider.create(entry.min_threshold, entry.max_threshold)));
            builder.with(ecEntry);
        }
        // EnlighteningEssence
        LeafEntry.Builder<?> elEntry = ItemEntry
                .builder(AllItems.ENLIGHTENING_ESSENCE)
                .weight(pool.el_weight)
                .apply(RandomEnlighteningEssenceFunction.builder());
        builder.with(elEntry);

        // MendingEssence
        LeafEntry.Builder<?> mdEntry = ItemEntry
                .builder(AllItems.MENDING_ESSENCE)
                .weight(pool.md_weight);
        builder.with(mdEntry);
    }

    public static void essenceLoot(LivingEntity caster, Entity target, MagicSchool impactSchool, MagicSchool spellSchool)
    {
        for (String id : AllConfigs.misc.value.essence_loot_blacklist)
            if (Registries.ENTITY_TYPE.getId(target.getType()).toString().equals(id))
                return;
        if (caster.getRandom().nextFloat() < AllConfigs.loot.value.essence_loot.drop_chance) return;
        MagicSchool school = impactSchool != null ? impactSchool : spellSchool;
        int grade = (caster instanceof PlayerEntity player) ? MageComponent.get(player).grade() : 3;
        target.dropItem(AllItems.BASE_ESSENCES.get(school).get(randomGrade(caster.getRandom(), grade)));
    }

    public static int randomGrade(Random random, int maxGrade)
    {
        float f = random.nextFloat();
        int grade;
        if (f < AllConfigs.loot.value.essence_loot.grade_0_1) grade = 0;
        else if (f < AllConfigs.loot.value.essence_loot.grade_1_2) grade = 1;
        else grade = 2;
        return Math.min(grade, maxGrade);
    }
}
