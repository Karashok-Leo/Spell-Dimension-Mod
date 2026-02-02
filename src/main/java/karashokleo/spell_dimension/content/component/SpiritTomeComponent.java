package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.spell_dimension.content.network.C2SSpiritTomeShopBuy;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllComponents;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpiritTomeComponent implements AutoSyncedComponent, ServerTickingComponent
{
    public enum SpiritType
    {
        POSITIVE,
        NEGATIVE,
        TOTAL
    }

    public static SpiritTomeComponent get(PlayerEntity player)
    {
        return AllComponents.SPIRIT_TOME.get(player);
    }

    public static void onSpiritTomeRule(ServerPlayerEntity player, MobEntity mob, boolean captured)
    {
        if (!TrinketCompat.hasItemInTrinket(player, AllItems.SPIRIT_TOME))
        {
            return;
        }
        boolean hostile = MobDifficulty.get(mob).isPresent();
        int amount = hostile ?
            DifficultyLevel.ofAny(mob) :
            DifficultyLevel.ofAny(player);
        // at least 1
        amount = Math.max(1, amount);
        SpiritTomeComponent component = get(player);
        if (hostile)
        {
            if (captured)
            {
                // rule 1
                component.revealRule(1);
                player.sendMessage(SDTexts.TEXT$SPIRIT_TOME$RULE$1$EFFECT.get(amount).formatted(Formatting.DARK_AQUA));
                component.changeSpirit(SpiritType.POSITIVE, amount);
            } else
            {
                // rule 2
                component.revealRule(2);
                player.sendMessage(SDTexts.TEXT$SPIRIT_TOME$RULE$2$EFFECT.get(amount).formatted(Formatting.DARK_AQUA));
                component.changeSpirit(SpiritType.NEGATIVE, amount);
            }
        } else
        {
            if (captured)
            {
                // rule 3
                component.revealRule(3);
                player.sendMessage(SDTexts.TEXT$SPIRIT_TOME$RULE$3$EFFECT.get(amount).formatted(Formatting.DARK_AQUA));
                component.changeSpirit(SpiritType.NEGATIVE, -amount);
            } else
            {
                // rule 4
                component.revealRule(4);
                player.sendMessage(SDTexts.TEXT$SPIRIT_TOME$RULE$4$EFFECT.get(amount).formatted(Formatting.DARK_AQUA));
                component.changeSpirit(SpiritType.POSITIVE, -amount);
            }
        }
    }

    private static final String BASE_WEIGHT_KEY = "BaseWeight";
    private static final String POSITIVE_SPIRIT_KEY = "PositiveSpirit";
    private static final String NEGATIVE_SPIRIT_KEY = "NegativeSpirit";
    private static final String RULE_REVEALED_KEY = "RuleRevealed";
    private static final String SHOP_PURCHASED_KEY = "ShopPurchased";
    private static final String SHOP_DAY_KEY = "ShopDay";
    private static final String SHOP_SEED_KEY = "ShopSeed";

    public static final int SHOP_SLOT_COUNT = 6;
    public static final int SHOP_ITEM_COST = 200;
    public static final int SHOP_LOTTERY_COST = 300;
    public static final int SHOP_REFRESH_COST = 50;

    private final PlayerEntity player;
    private float baseWeight;
    private int positiveSpirit;
    private int negativeSpirit;
    private int ruleRevealedMask;
    private int shopPurchasedMask;
    private long shopDay;
    private long shopSeed;
    private List<Item> shopItems;

    public SpiritTomeComponent(PlayerEntity player)
    {
        this.player = player;
        this.ruleRevealedMask = 1;
        this.shopItems = List.of();
    }

    @Override
    public void serverTick()
    {
        if (this.player.age % 20 != 0)
        {
            return;
        }
        refreshShop(false);
    }

    public void sync()
    {
        if (this.player.getWorld().isClient())
        {
            throw new UnsupportedOperationException();
        }
        AllComponents.SPIRIT_TOME.sync(this.player);
    }

    public float getWeight()
    {
        var hungerManager = this.player.getHungerManager();
        float hungerNormalized = (hungerManager.getFoodLevel() - 10f) / 10f;
        float saturationNormalized = (hungerManager.getSaturationLevel() - 5f) / 5f;
        float adjustment = 1.5f * MathHelper.clamp(hungerNormalized, -1f, 1f)
            + 0.5f * MathHelper.clamp(saturationNormalized, -1f, 1f);
        return MathHelper.clamp(this.baseWeight + adjustment, 35f, 120f);
    }

    public int getSpirit()
    {
        return this.getSpirit(SpiritType.TOTAL);
    }

    public int getSpirit(SpiritType type)
    {
        return switch (type)
        {
            case POSITIVE -> this.positiveSpirit;
            case NEGATIVE -> this.negativeSpirit;
            case TOTAL -> this.positiveSpirit + this.negativeSpirit;
        };
    }

    public boolean isRuleRevealed(int rule)
    {
        return (this.ruleRevealedMask & (1 << rule)) != 0;
    }

    private void revealRule(int rule)
    {
        if (this.player.getWorld().isClient())
        {
            throw new UnsupportedOperationException();
        }
        int mask = 1 << rule;
        if ((this.ruleRevealedMask & mask) != 0)
        {
            return;
        }
        this.ruleRevealedMask |= mask;
        this.player.sendMessage(SDTexts.TEXT$SPIRIT_TOME$RULE$REVEAL.get());
        sync();
    }

    public boolean isShopItemPurchased(int index)
    {
        if (index < 0 || index >= SHOP_SLOT_COUNT)
        {
            return true;
        }
        return (this.shopPurchasedMask & (1 << index)) != 0;
    }

    public void markShopItemPurchased(int index)
    {
        if (index < 0 || index >= SHOP_SLOT_COUNT)
        {
            return;
        }
        this.shopPurchasedMask |= (1 << index);
        sync();
    }

    /**
     * Changes spirit by type.
     *
     * @param type   cannot be TOTAL
     * @param amount can be positive or negative
     */
    public void changeSpirit(SpiritType type, int amount)
    {
        if (amount == 0)
        {
            return;
        }
        if (this.player.getWorld().isClient())
        {
            throw new UnsupportedOperationException();
        }
        switch (type)
        {
            case POSITIVE -> this.positiveSpirit += amount;
            case NEGATIVE -> this.negativeSpirit += amount;
            case TOTAL -> throw new UnsupportedOperationException();
        }
        if (this.getSpirit() < 0)
        {
            erase();
        }
        sync();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean tryConsumeSpirit(int amount)
    {
        if (amount <= 0)
        {
            throw new UnsupportedOperationException();
        }
        if (this.player.getWorld().isClient())
        {
            throw new UnsupportedOperationException();
        }
        if (this.getSpirit() < amount)
        {
            return false;
        }
        if (!TrinketCompat.hasItemInTrinket(this.player, AllItems.SPIRIT_TOME))
        {
            return false;
        }
        int pos = this.positiveSpirit;
        int neg = this.negativeSpirit;
        if (pos > 0 && neg > 0)
        {
            // consume proportionally
            float posRatio = (float) pos / (pos + neg);
            int posConsume = Math.round(amount * posRatio);
            int negConsume = amount - posConsume;
            this.positiveSpirit -= posConsume;
            this.negativeSpirit -= negConsume;
        } else if (pos > 0)
        {
            this.positiveSpirit -= amount;
        } else if (neg > 0)
        {
            this.negativeSpirit -= amount;
        } else
        {
            // impossible case
            return false;
        }
        revealRule(5);
        sync();
        return true;
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        float weight = tag.getFloat(BASE_WEIGHT_KEY);
        // generate weight if not present
        this.baseWeight = weight > 0 ? weight : generateWeight();
        this.positiveSpirit = tag.getInt(POSITIVE_SPIRIT_KEY);
        this.negativeSpirit = tag.getInt(NEGATIVE_SPIRIT_KEY);
        this.ruleRevealedMask = tag.getInt(RULE_REVEALED_KEY);
        this.ruleRevealedMask |= 1;
        this.shopDay = tag.getLong(SHOP_DAY_KEY);
        this.shopPurchasedMask = tag.getInt(SHOP_PURCHASED_KEY);
        this.shopSeed = tag.contains(SHOP_SEED_KEY, NbtElement.LONG_TYPE) ?
            tag.getLong(SHOP_SEED_KEY) :
            this.player.getRandom().nextLong();
        refreshShopItems();
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putInt(POSITIVE_SPIRIT_KEY, this.positiveSpirit);
        tag.putInt(NEGATIVE_SPIRIT_KEY, this.negativeSpirit);
        tag.putInt(RULE_REVEALED_KEY, this.ruleRevealedMask);
        tag.putFloat(BASE_WEIGHT_KEY, this.baseWeight);
        tag.putLong(SHOP_DAY_KEY, this.shopDay);
        tag.putInt(SHOP_PURCHASED_KEY, this.shopPurchasedMask);
        tag.putLong(SHOP_SEED_KEY, this.shopSeed);
    }

    private float generateWeight()
    {
        double gaussian = player.getRandom().nextGaussian();
        float weight = (float) (65 + gaussian * 10);
        return MathHelper.clamp(weight, 45, 100);
    }

    private void erase()
    {
        if (this.player.getWorld().isClient())
        {
            throw new UnsupportedOperationException();
        }
        boolean hasTome = false;
        for (var access : TrinketCompat.getItemAccess(this.player))
        {
            if (access.get().isOf(AllItems.SPIRIT_TOME))
            {
                hasTome = true;
                access.set(ItemStack.EMPTY);
                break;
            }
        }
        if (!hasTome)
        {
            return;
        }
        this.player.sendMessage(SDTexts.TEXT$SPIRIT_TOME$RULE$0$EFFECT.get().formatted(Formatting.DARK_AQUA));
        this.player.damage(this.player.getDamageSources().outOfWorld(), Float.MAX_VALUE);
        // ensure death
        this.player.setHealth(0);
    }

    public void refreshShop(boolean forced)
    {
        if (!(this.player instanceof ServerPlayerEntity serverPlayer))
        {
            throw new UnsupportedOperationException();
        }
        long day = serverPlayer.getServerWorld().getTimeOfDay() / 24000L;
        if (!forced && day == this.shopDay)
        {
            return;
        }
        this.shopPurchasedMask = 0;
        this.shopDay = day;
        this.shopSeed = this.player.getRandom().nextLong();
        refreshShopItems();
        sync();
    }

    public List<Item> getShopItems()
    {
        return this.shopItems;
    }

    private void refreshShopItems()
    {
        long seed = this.shopSeed ^ (this.shopDay * 31L);
        Random random = Random.create(seed);
        this.shopItems = RandomUtil.randomItemsFromRegistry(random, AllTags.SPIRIT_TOME_SHOP_BLACKLIST, SHOP_SLOT_COUNT);
    }

    /**
     * @return item cost if index >= 0, refresh cost if index == SHOP_REFRESH_INDEX, otherwise lottery cost
     */
    public int getShopCost(int index)
    {
        if (index == C2SSpiritTomeShopBuy.SHOP_REFRESH_INDEX)
        {
            return SHOP_REFRESH_COST;
        }
        return index >= 0 ? SHOP_ITEM_COST : SHOP_LOTTERY_COST;
    }
}
