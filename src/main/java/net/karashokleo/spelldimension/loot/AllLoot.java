package net.karashokleo.spelldimension.loot;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.config.AllConfig;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.util.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
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
import net.spell_power.api.SpellPowerTags;

public class AllLoot
{
    public static LootFunctionType RANDOM_ENCHANTED_ESSENCE;

    public static void register()
    {
        RANDOM_ENCHANTED_ESSENCE = Registry.register(Registries.LOOT_FUNCTION_TYPE, SpellDimension.modLoc("random_enchanted_essence"), new LootFunctionType(new RandomEnchantedEssenceFunction.Serializer()));

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) ->
        {
            if (!source.isIn(SpellPowerTags.DamageType.IS_SPELL)) return true;
            for (String id : AllConfig.INSTANCE.loot_blacklist)
                if (Registries.ENTITY_TYPE.getId(entity.getType()).toString().equals(id))
                    return true;
            if (entity.getRandom().nextFloat() < 0.9F) return true;
            MagicSchool school = DamageUtil.getDamageSchool(source);
            if (school == null) return true;
            Entity attacker = source.getAttacker();
            int grade = (attacker instanceof PlayerEntity player) ? MageComponent.get(player).grade() : 3;
            entity.dropItem(AllItems.BASE_ESSENCES.get(school).get(randomGrade(entity.getRandom(), grade)));
            return true;
        });

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->
        {
            if (!id.getPath().contains("chest")) return;
            LootPool.Builder builder = LootPool.builder();
            builder.rolls(UniformLootNumberProvider.create(0, 3));
            LeafEntry.Builder<?> entry1 = ItemEntry
                    .builder(AllItems.ENCHANTED_ESSENCE)
                    .weight(1)
                    .apply(RandomEnchantedEssenceFunction.builder(UniformLootNumberProvider.create(40, 60)));
            LeafEntry.Builder<?> entry3 = ItemEntry
                    .builder(AllItems.ENCHANTED_ESSENCE)
                    .weight(3)
                    .apply(RandomEnchantedEssenceFunction.builder(UniformLootNumberProvider.create(20, 40)));
            LeafEntry.Builder<?> entry9 = ItemEntry
                    .builder(AllItems.ENCHANTED_ESSENCE)
                    .weight(9)
                    .apply(RandomEnchantedEssenceFunction.builder(UniformLootNumberProvider.create(1, 20)));
            LeafEntry.Builder<?> entry50 = EmptyEntry
                    .builder()
                    .weight(140);
            builder.with(entry1);
            builder.with(entry3);
            builder.with(entry9);
            builder.with(entry50);
            tableBuilder.pool(builder.build());
        });
    }

    public static int randomGrade(Random random, int maxGrade)
    {
        float f = random.nextFloat();
        int grade;
        if (f < 0.9F) grade = 0;
        else if (f < 0.99F) grade = 1;
        else grade = 2;
        return Math.min(grade, maxGrade);
    }

    public static MagicSchool randomSchool(Random random)
    {
        return MagicSchool.values()[random.nextInt(MagicSchool.values().length)];
    }

    public static EquipmentSlot randomSlot(Random random)
    {
        return EquipmentSlot.values()[random.nextInt(MagicSchool.values().length)];
    }
}
