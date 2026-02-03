package karashokleo.spell_dimension.util;

import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestTag;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class TagUtil
{
    public static TagKey<Item> itemTag(Identifier id)
    {
        return TagKey.of(RegistryKeys.ITEM, id);
    }

    public static TagKey<Item> itemTag(String path)
    {
        return itemTag(SpellDimension.modLoc(path));
    }

    public static TagKey<Block> blockTag(Identifier id)
    {
        return TagKey.of(RegistryKeys.BLOCK, id);
    }

    public static TagKey<Block> blockTag(String path)
    {
        return blockTag(SpellDimension.modLoc(path));
    }

    public static TagKey<EntityType<?>> entityTypeTag(Identifier id)
    {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, id);
    }

    public static TagKey<EntityType<?>> entityTypeTag(String path)
    {
        return entityTypeTag(SpellDimension.modLoc(path));
    }

    public static TagKey<Enchantment> enchantmentTag(Identifier id)
    {
        return TagKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    public static TagKey<Enchantment> enchantmentTag(String path)
    {
        return enchantmentTag(SpellDimension.modLoc(path));
    }

    public static TagKey<Fluid> fluidTag(Identifier id)
    {
        return TagKey.of(RegistryKeys.FLUID, id);
    }

    public static TagKey<Fluid> fluidTag(String path)
    {
        return fluidTag(SpellDimension.modLoc(path));
    }

    public static TagKey<Quest> questTag(String path)
    {
        return QuestTag.of(SpellDimension.modLoc(path));
    }

    public static TagKey<Biome> biomeTag(Identifier id)
    {
        return TagKey.of(RegistryKeys.BIOME, id);
    }

    public static TagKey<Biome> biomeTag(String path)
    {
        return biomeTag(SpellDimension.modLoc(path));
    }

    public static TagKey<MobTrait> traitTag(Identifier id)
    {
        return TagKey.of(LHTraits.TRAIT_KEY, id);
    }

    public static TagKey<MobTrait> traitTag(String path)
    {
        return traitTag(SpellDimension.modLoc(path));
    }
}
