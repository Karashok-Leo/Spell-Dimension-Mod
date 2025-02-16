package karashokleo.spell_dimension.init;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.trinket.misc.LootingCharm;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.config.EssenceLootConfig;
import karashokleo.spell_dimension.config.recipe.SpellScrollConfig;
import karashokleo.spell_dimension.content.event.conscious.EventAward;
import karashokleo.spell_dimension.content.loot.entry.RandomEnchantedEssenceEntry;
import karashokleo.spell_dimension.content.loot.entry.RandomEnlighteningEssenceEntry;
import karashokleo.spell_dimension.content.loot.entry.SpellScrollEntry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;

import java.util.Set;

public class AllLoots
{
    public static LootPoolEntryType RANDOM_ENCHANTED_ESSENCE_ENTRY;
    public static LootPoolEntryType RANDOM_ENLIGHTENING_ESSENCE_ENTRY;
    public static LootPoolEntryType SPELL_SCROLL_ENTRY;

    public static void register()
    {
        RANDOM_ENCHANTED_ESSENCE_ENTRY = Registry.register(Registries.LOOT_POOL_ENTRY_TYPE, SpellDimension.modLoc("random_enchanted_essence"), new LootPoolEntryType(new RandomEnchantedEssenceEntry.Serializer()));
        RANDOM_ENLIGHTENING_ESSENCE_ENTRY = Registry.register(Registries.LOOT_POOL_ENTRY_TYPE, SpellDimension.modLoc("random_enlightening_essence"), new LootPoolEntryType(new RandomEnlighteningEssenceEntry.Serializer()));
        SPELL_SCROLL_ENTRY = Registry.register(Registries.LOOT_POOL_ENTRY_TYPE, SpellDimension.modLoc("spell_scroll"), new LootPoolEntryType(new SpellScrollEntry.Serializer()));

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->
        {
            if (id.getPath().contains("chests/"))
            {
                LootPool.Builder builder = LootPool.builder();
                addEssenceLootPool(builder, EssenceLootConfig.CHEST_POOL, false);
                tableBuilder.pool(builder.build());
            }
            if (id.getPath().contains("entities/"))
            {
                LootPool.Builder builder = LootPool.builder();
                addEssenceLootPool(builder, EssenceLootConfig.ENTITY_POOL, true);
                builder.conditionally(KilledByPlayerLootCondition.builder());
                tableBuilder.pool(builder.build());
            }
            if (id.equals(EventAward.SPELL_SCROLL.lootTable))
            {
                LootPool.Builder builder = LootPool.builder();
                addCraftSpellLootPool(builder, SpellScrollConfig.getEventLootSpells());
                tableBuilder.pool(builder.build());
            }
        });

        injectBaseEssenceLoot();
    }

    private static void injectBaseEssenceLoot()
    {
        SpellImpactEvents.BEFORE.register((world, caster, targets, spellInfo) ->
        {
            if (!(caster instanceof PlayerEntity)) return;
            SpellSchool school = spellInfo.spell().school;
            for (Entity target : targets)
            {
                if (EssenceLootConfig.BASE_CONFIG.blacklist().contains(target.getType()))
                    continue;

                var op = MobDifficulty.get(target);
                if (op.isPresent())
                {
                    MobDifficulty difficulty = op.get();
                    if (difficulty.noDrop) continue;
                    if (difficulty.hasTrait(LHTraits.UNDYING)) continue;
                    if (difficulty.hasTrait(LHTraits.DISPELL)) continue;
                    if (difficulty.hasTrait(LHTraits.ADAPTIVE)) continue;

                    // Determine the loot charm level
                    int lootCharmLevel = TrinketCompat.getTrinketItems(caster, e -> e.getItem() instanceof LootingCharm).size();

                    float dropChance = EssenceLootConfig.BASE_CONFIG.dropChance() + lootCharmLevel * 0.1F;
                    if (caster.getRandom().nextFloat() > dropChance) continue;

                    int grade = EssenceLootConfig.BASE_CONFIG.getRandomGrade(caster.getRandom(), difficulty.getLevel());

                    target.dropItem(AllItems.BASE_ESSENCES.get(school).get(grade));
                }
            }
        });
    }

    private static void addEssenceLootPool(LootPool.Builder builder, EssenceLootConfig.LootPool pool, boolean playerKill)
    {
        builder.rolls(UniformLootNumberProvider.create(pool.minRolls(), pool.maxRolls()));
        LeafEntry.Builder<?> emptyEntry = EmptyEntry
                .builder()
                .weight(pool.emptyWeight());
        builder.with(emptyEntry);

        if (playerKill)
            builder.conditionally(KilledByPlayerLootCondition.builder().build());

        // EnchantedEssence
        builder.with(RandomEnchantedEssenceEntry.builder().weight(EssenceLootConfig.EC_WEIGHT));

        // EnlighteningEssence
        builder.with(RandomEnlighteningEssenceEntry.builder().weight(EssenceLootConfig.EL_WEIGHT));

        // MendingEssence
        builder.with(ItemEntry.builder(AllItems.MENDING_ESSENCE).weight(EssenceLootConfig.MD_WEIGHT));
    }

    private static void addCraftSpellLootPool(LootPool.Builder builder, Set<Identifier> pool)
    {
        builder.rolls(UniformLootNumberProvider.create(1, 3));
        for (Identifier spellId : pool)
            builder.with(SpellScrollEntry.builder(spellId));
    }
}
