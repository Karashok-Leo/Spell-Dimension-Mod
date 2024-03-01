package net.karashokleo.spelldimension.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.config.AllConfig;
import net.karashokleo.spelldimension.item.AllItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.MagicSchool;

public class AllLoot
{
    public static LootFunctionType RANDOM_ENCHANTED_ESSENCE;

    public static void register()
    {
        RANDOM_ENCHANTED_ESSENCE = Registry.register(Registries.LOOT_FUNCTION_TYPE, SpellDimension.modLoc("random_enchanted_essence"), new LootFunctionType(new RandomEnchantedEssenceFunction.Serializer()));

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->
        {
            if (!id.getPath().contains("chest") && !id.getPath().contains("entities")) return;
            LootPool.Builder builder = LootPool.builder();
            builder.rolls(UniformLootNumberProvider.create(AllConfig.INSTANCE.loot.chest_loot.min_rolls, AllConfig.INSTANCE.loot.chest_loot.max_rolls));
            LeafEntry.Builder<?> emptyEntry = EmptyEntry
                    .builder()
                    .weight(AllConfig.INSTANCE.loot.chest_loot.empty_weight);
            LeafEntry.Builder<?> commonEntry = ItemEntry
                    .builder(AllItems.ENCHANTED_ESSENCE)
                    .weight(AllConfig.INSTANCE.loot.chest_loot.common_loot.weight)
                    .apply(RandomEnchantedEssenceFunction.builder(UniformLootNumberProvider.create(AllConfig.INSTANCE.loot.chest_loot.common_loot.min_threshold, AllConfig.INSTANCE.loot.chest_loot.common_loot.max_threshold)));
            LeafEntry.Builder<?> uncommonEntry = ItemEntry
                    .builder(AllItems.ENCHANTED_ESSENCE)
                    .weight(AllConfig.INSTANCE.loot.chest_loot.uncommon_loot.weight)
                    .apply(RandomEnchantedEssenceFunction.builder(UniformLootNumberProvider.create(AllConfig.INSTANCE.loot.chest_loot.uncommon_loot.min_threshold, AllConfig.INSTANCE.loot.chest_loot.uncommon_loot.max_threshold)));
            LeafEntry.Builder<?> rareEntry = ItemEntry
                    .builder(AllItems.ENCHANTED_ESSENCE)
                    .weight(AllConfig.INSTANCE.loot.chest_loot.rare_loot.weight)
                    .apply(RandomEnchantedEssenceFunction.builder(UniformLootNumberProvider.create(AllConfig.INSTANCE.loot.chest_loot.rare_loot.min_threshold, AllConfig.INSTANCE.loot.chest_loot.rare_loot.max_threshold)));
            builder.with(emptyEntry);
            builder.with(commonEntry);
            builder.with(uncommonEntry);
            builder.with(rareEntry);
            tableBuilder.pool(builder.build());
        });
    }

    public static void essenceLoot(LivingEntity caster, Entity target, MagicSchool impactSchool, MagicSchool spellSchool)
    {
        for (String id : AllConfig.INSTANCE.loot_blacklist)
            if (Registries.ENTITY_TYPE.getId(target.getType()).toString().equals(id))
                return;
        if (caster.getRandom().nextFloat() < AllConfig.INSTANCE.loot.mob_loot.drop_chance) return;
        MagicSchool school = impactSchool != null ? impactSchool : spellSchool;
        int grade = (caster instanceof PlayerEntity player) ? MageComponent.get(player).grade() : 3;
        target.dropItem(AllItems.BASE_ESSENCES.get(school).get(randomGrade(caster.getRandom(), grade)));
    }

    public static int randomGrade(Random random, int maxGrade)
    {
        float f = random.nextFloat();
        int grade;
        if (f < AllConfig.INSTANCE.loot.mob_loot.grade_0_1) grade = 0;
        else if (f < AllConfig.INSTANCE.loot.mob_loot.grade_1_2) grade = 1;
        else grade = 2;
        return Math.min(grade, maxGrade);
    }

    public static MagicSchool randomSchool(Random random)
    {
        return MagicSchool.values()[random.nextInt(MagicSchool.values().length)];
    }

    public static EquipmentSlot randomSlot(Random random)
    {
        return EquipmentSlot.values()[random.nextInt(EquipmentSlot.values().length)];
    }
}
