package net.karashokleo.spelldimension.loot;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.item.AllItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.function.LootFunctionType;
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
            if (entity.getRandom().nextFloat() < 0.96F) return true;
            Entity attacker = source.getAttacker();
            if (attacker instanceof PlayerEntity player &&
                    source.isIn(SpellPowerTags.DamageType.IS_SPELL))
                entity.dropItem(AllItems.BASE_ESSENCES.get(MageComponent.get(player).school()).get(randomGrade(entity.getRandom())));
            return true;
        });
    }

    public static int randomGrade(Random random)
    {
        float f = random.nextFloat();
        if (f < 0.75F) return 0;
        else if (f < 0.95F) return 1;
        else return 2;
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
