package karashokleo.spell_dimension.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.SpellSchool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RandomUtil
{
    public static final int MAX_TRIES = 100;
    public static final Item DEFAULT_ITEM = Items.ENCHANTED_GOLDEN_APPLE;

    public static SpellSchool randomSchool(Random random)
    {
        return randomFromList(random, SchoolUtil.SCHOOLS);
    }

    public static EquipmentSlot randomSlot(Random random)
    {
        return randomEnum(random, EquipmentSlot.class);
    }

    public static <T> T randomFromList(Random random, List<T> list)
    {
        if (list.isEmpty())
        {
            throw new IllegalArgumentException("The list must not be empty.");
        }
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static <T> T randomFromSet(Random random, Set<T> set)
    {
        if (set.isEmpty())
        {
            throw new IllegalArgumentException("The set must not be empty.");
        }
        return randomFromList(random, new ArrayList<>(set));
    }

    public static <T extends Enum<T>> T randomEnum(Random random, Class<T> enumClass)
    {
        if (!enumClass.isEnum())
        {
            throw new IllegalArgumentException("The class must be an enum.");
        }
        T[] enumValues = enumClass.getEnumConstants();
        int randomIndex = random.nextInt(enumValues.length);
        return enumValues[randomIndex];
    }

    public static List<Item> randomItemsFromRegistry(Random random, TagKey<Item> blacklist, int count)
    {
        List<Item> results = new ArrayList<>(count);
        HashSet<Item> chosen = new HashSet<>();
        int tries = 0;
        int maxTries = MAX_TRIES * count;
        while (results.size() < count && tries++ < maxTries)
        {
            Item item = Registries.ITEM
                .getRandom(random)
                .filter(e -> !e.isIn(blacklist))
                .map(RegistryEntry.Reference::value)
                .orElse(Items.AIR);
            if (item == Items.AIR || chosen.contains(item))
            {
                continue;
            }
            chosen.add(item);
            results.add(item);
        }
        while (results.size() < count)
        {
            results.add(DEFAULT_ITEM);
        }
        return results;
    }

    public static Item randomItemFromRegistry(Random random, TagKey<Item> blacklist)
    {
        int tries = 0;
        while (tries++ < MAX_TRIES)
        {
            Item item = Registries.ITEM
                .getRandom(random)
                .filter(e -> !e.isIn(blacklist))
                .map(RegistryEntry.Reference::value)
                .orElse(Items.AIR);
            if (item == Items.AIR)
            {
                continue;
            }
            return item;
        }
        return DEFAULT_ITEM;
    }

    public static Vec3d perturbDirection(Vec3d direction, double sigma, Random random)
    {
        double length = direction.length();
        if (length == 0)
        {
            return Vec3d.ZERO;
        }

        Vec3d normalized = direction.normalize();

        // 构造正交基底
        Vec3d u, w;

        // 初始基准向量为x轴
        Vec3d t1 = new Vec3d(1, 0, 0);
        u = normalized.crossProduct(t1);

        // 检查是否平行，若平行则换用y轴作为基准
        if (u.lengthSquared() < 1e-12)
        {
            Vec3d t2 = new Vec3d(0, 1, 0);
            u = normalized.crossProduct(t2);
        }
        u = u.normalize();

        // 计算第二个正交向量
        w = normalized.crossProduct(u).normalize();

        // 生成高斯随机扰动
        double a = random.nextGaussian() * sigma;
        double b = random.nextGaussian() * sigma;

        // 计算扰动向量并应用
        Vec3d perturbation = u.multiply(a).add(w.multiply(b));
        return normalized.add(perturbation).normalize().multiply(length);
    }
}
