package karashokleo.spell_dimension.config.recipe;

import artifacts.registry.ModEntityTypes;
import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class SummonSpellConfig
{
    private static final Map<Item, Entry> CONFIG = new HashMap<>();

    public record Entry(EntityType<? extends LivingEntity> entityType, int count)
    {
    }

    static
    {
        register(Items.CRYING_OBSIDIAN, EntityType.ENDERMAN);
        register(Items.GREEN_DYE, EntityType.SLIME);
        register(Items.POTION, EntityType.WITCH);
        register(Items.PIGLIN_HEAD, EntityType.PIGLIN_BRUTE, 6);
        register(Items.ZOMBIE_HEAD, ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get(), 1);
        register(Items.SKELETON_SKULL, ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get(), 1);
        register(Items.CREEPER_HEAD, ModRegistry.MUTANT_CREEPER_ENTITY_TYPE.get(), 1);
        register(Items.END_CRYSTAL, ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get(), 1);
        register(MiscItems.CHAOS.ingot(), ModEntityTypes.MIMIC.get(), 1);
        register(MiscItems.MIRACLE.ingot(), ModEntityTypes.MIMIC.get());
    }

    public static void register(Item item, EntityType<? extends LivingEntity> entityType, int count)
    {
        CONFIG.put(item, new Entry(entityType, count));
    }

    public static void register(Item item, EntityType<? extends LivingEntity> entityType)
    {
        register(item, entityType, ISpawnerExtension.DEFAULT_REMAIN);
    }

    public static Entry getEntry(ItemStack itemStack)
    {
        return CONFIG.get(itemStack.getItem());
    }

    public static void forEach(BiConsumer<Item, Entry> action)
    {
        CONFIG.forEach(action);
    }
}
