package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.SpellImpactCallback;
import karashokleo.spell_dimension.config.LootConfig;
import karashokleo.spell_dimension.content.loot.entry.RandomEnchantedEssenceEntry;
import karashokleo.spell_dimension.content.loot.entry.RandomEnlighteningEssenceEntry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.spell_power.api.SpellSchool;

public class AllLoots
{
    public static LootPoolEntryType RANDOM_ENCHANTED_ESSENCE_ENTRY;
    public static LootPoolEntryType RANDOM_ENLIGHTENING_ESSENCE_ENTRY;

    public static void register()
    {
        RANDOM_ENCHANTED_ESSENCE_ENTRY = Registry.register(Registries.LOOT_POOL_ENTRY_TYPE, SpellDimension.modLoc("random_enchanted_essence"), new LootPoolEntryType(new RandomEnchantedEssenceEntry.Serializer()));
        RANDOM_ENLIGHTENING_ESSENCE_ENTRY = Registry.register(Registries.LOOT_POOL_ENTRY_TYPE, SpellDimension.modLoc("random_enlightening_essence"), new LootPoolEntryType(new RandomEnlighteningEssenceEntry.Serializer()));

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->
        {
            if (id.getPath().contains("chests/"))
            {
                LootPool.Builder builder = LootPool.builder();
                addEssenceLootPool(builder, LootConfig.CHEST_POOL);
                tableBuilder.pool(builder.build());
            }
            if (id.getPath().contains("entities/"))
            {
                LootPool.Builder builder = LootPool.builder();
                addEssenceLootPool(builder, LootConfig.ENTITY_POOL);
                builder.conditionally(KilledByPlayerLootCondition.builder());
                tableBuilder.pool(builder.build());
            }
        });

        SpellImpactCallback.EVENT.register((world, caster, target, spellInfo, impact, context) ->
        {
            if (LootConfig.BASE_CONFIG.blacklist().contains(target.getType()))
                return;
            if (caster.getRandom().nextFloat() < LootConfig.BASE_CONFIG.dropChance()) return;
            SpellSchool school = impact.school != null ? impact.school : spellInfo.spell().school;
            int grade = LootConfig.BASE_CONFIG.getRandomGrade(caster.getRandom());
            target.dropItem(AllItems.BASE_ESSENCES.get(school).get(grade));
        });
    }

    private static void addEssenceLootPool(LootPool.Builder builder, LootConfig.LootPool pool)
    {
        builder.rolls(UniformLootNumberProvider.create(pool.minRolls(), pool.maxRolls()));
        LeafEntry.Builder<?> emptyEntry = EmptyEntry
                .builder()
                .weight(pool.emptyWeight());
        builder.with(emptyEntry);

        // EnchantedEssence
        for (LootConfig.EcEntry entry : LootConfig.EC_ENTRIES)
            builder.with(RandomEnchantedEssenceEntry.builder(UniformLootNumberProvider.create(entry.minThreshold(), entry.maxThreshold())).weight(entry.weight()));

        // EnlighteningEssence
        builder.with(RandomEnlighteningEssenceEntry.builder().weight(LootConfig.EL_WEIGHT));

        // MendingEssence
        builder.with(ItemEntry.builder(AllItems.MENDING_ESSENCE).weight(LootConfig.MD_WEIGHT));
    }
}
