package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpiritTomeComponent implements AutoSyncedComponent, ServerTickingComponent
{
    public static final int LOTTERY_FLAG = -1;
    public static final int REFRESH_FLAG = -2;
    private static final int LOTTERY_BASE_COST = 200;
    private static final int REFRESH_BASE_COST = 500;
    private static final int SHOP_COST_STEP = 300;

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
                player.sendMessage(SDTexts.getSpiritTomeRuleText(1, amount).formatted(Formatting.DARK_AQUA));
                component.changeSpirit(SpiritType.POSITIVE, amount);
            } else
            {
                // rule 2
                component.revealRule(2);
                player.sendMessage(SDTexts.getSpiritTomeRuleText(2, amount).formatted(Formatting.DARK_AQUA));
                component.changeSpirit(SpiritType.NEGATIVE, amount);
            }
        } else
        {
            if (captured)
            {
                // rule 3
                component.revealRule(3);
                player.sendMessage(SDTexts.getSpiritTomeRuleText(3, amount).formatted(Formatting.DARK_AQUA));
                component.changeSpirit(SpiritType.NEGATIVE, -amount);
            } else
            {
                // rule 4
                component.revealRule(4);
                player.sendMessage(SDTexts.getSpiritTomeRuleText(4, amount).formatted(Formatting.DARK_AQUA));
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
    private static final String SHOP_REFRESH_COST_KEY = "ShopRefreshCost";
    private static final String SHOP_LOTTERY_COST_KEY = "ShopLotteryCost";
    private static final int SHOP_SLOT_COUNT = 6;

    private final PlayerEntity player;
    private float baseWeight;
    private int positiveSpirit;
    private int negativeSpirit;
    private int ruleRevealedMask;
    private int shopPurchasedMask;
    private long shopDay;
    private long shopSeed;
    private int shopRefreshCost;
    private int shopLotteryCost;
    private List<Item> shopItems;

    // statistics
    public long positiveSpiritIncrease;
    public long positiveSpiritDecrease;
    public long negativeSpiritIncrease;
    public long negativeSpiritDecrease;
    public long spiritConsumed;

    public SpiritTomeComponent(PlayerEntity player)
    {
        this.player = player;
        this.shopItems = List.of();
        this.ruleRevealedMask = 1;
        if (this.player.getWorld().isClient())
        {
            return;
        }
        this.baseWeight = generateWeight();
        this.positiveSpirit = 0;
        this.negativeSpirit = 0;
        tryRefreshDay();
        refresh(true);
    }

    @Override
    public void serverTick()
    {
        if (this.player.age % 20 != 0)
        {
            return;
        }
        tickRefresh();
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
        float hungerNormalized = (hungerManager.getFoodLevel() - 20f) / 20f;
        float saturationNormalized = (hungerManager.getSaturationLevel() - 5f) / 15f;
        float adjustment = 3.0f * MathHelper.clamp(hungerNormalized, -1f, 0f)
            + 0.5f * MathHelper.clamp(saturationNormalized, -1f, 1f);
        return MathHelper.clamp(this.baseWeight + adjustment, 35f, 110f);
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
            case TOTAL -> plusClamp(this.positiveSpirit, this.negativeSpirit);
        };
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isRuleRevealed(int rule)
    {
        return (this.ruleRevealedMask & (1 << rule)) != 0;
    }

    public boolean isDisarmActive()
    {
        return this.positiveSpirit >= 1_000_000 && this.negativeSpirit <= 10_000;
    }

    public boolean isJudgmentActive()
    {
        return this.negativeSpirit >= 1_000_000 && this.positiveSpirit <= 10_000;
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
        this.player.sendMessage(SDTexts.getSpiritTomeRuleText(-1).formatted(Formatting.DARK_AQUA));
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
            case POSITIVE -> this.positiveSpirit = plusClamp(this.positiveSpirit, amount);
            case NEGATIVE -> this.negativeSpirit = plusClamp(this.negativeSpirit, amount);
            case TOTAL -> throw new UnsupportedOperationException();
        }
        switch (type)
        {
            case POSITIVE ->
            {
                if (amount > 0)
                {
                    this.positiveSpiritIncrease += amount;
                } else
                {
                    this.positiveSpiritDecrease -= amount;
                }
            }
            case NEGATIVE ->
            {
                if (amount > 0)
                {
                    this.negativeSpiritIncrease += amount;
                } else
                {
                    this.negativeSpiritDecrease -= amount;
                }
            }
        }
        tryUnlockAdvancedRules();
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
        this.spiritConsumed += amount;
        revealRule(5);
        sync();
        return true;
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        // generate weight if not present
        if (tag.contains(BASE_WEIGHT_KEY, NbtElement.FLOAT_TYPE))
        {
            this.baseWeight = tag.getFloat(BASE_WEIGHT_KEY);
        }
        this.positiveSpirit = tag.getInt(POSITIVE_SPIRIT_KEY);
        this.negativeSpirit = tag.getInt(NEGATIVE_SPIRIT_KEY);
        if (tag.contains(RULE_REVEALED_KEY, NbtElement.INT_TYPE))
        {
            this.ruleRevealedMask = tag.getInt(RULE_REVEALED_KEY);
        }
        this.ruleRevealedMask |= 1;
        if (tag.contains(SHOP_DAY_KEY, NbtElement.LONG_TYPE))
        {
            this.shopDay = tag.getLong(SHOP_DAY_KEY);
        }
        if (tag.contains(SHOP_SEED_KEY, NbtElement.LONG_TYPE))
        {
            this.shopSeed = tag.getLong(SHOP_SEED_KEY);
        }
        this.shopPurchasedMask = tag.getInt(SHOP_PURCHASED_KEY);
        this.shopRefreshCost = Math.max(REFRESH_BASE_COST, tag.getInt(SHOP_REFRESH_COST_KEY));
        this.shopLotteryCost = Math.max(LOTTERY_BASE_COST, tag.getInt(SHOP_LOTTERY_COST_KEY));
        refreshShopItems();
        tryUnlockAdvancedRules();

        // statistics
        this.positiveSpiritIncrease = tag.getLong("PositiveSpiritIncrease");
        this.positiveSpiritDecrease = tag.getLong("PositiveSpiritDecrease");
        this.negativeSpiritIncrease = tag.getLong("NegativeSpiritIncrease");
        this.negativeSpiritDecrease = tag.getLong("NegativeSpiritDecrease");
        this.spiritConsumed = tag.getLong("SpiritConsumed");
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putInt(POSITIVE_SPIRIT_KEY, this.positiveSpirit);
        tag.putInt(NEGATIVE_SPIRIT_KEY, this.negativeSpirit);
        tag.putInt(RULE_REVEALED_KEY, this.ruleRevealedMask);
        tag.putFloat(BASE_WEIGHT_KEY, this.baseWeight);
        tag.putLong(SHOP_DAY_KEY, this.shopDay);
        tag.putLong(SHOP_SEED_KEY, this.shopSeed);
        tag.putInt(SHOP_PURCHASED_KEY, this.shopPurchasedMask);
        tag.putInt(SHOP_REFRESH_COST_KEY, this.shopRefreshCost);
        tag.putInt(SHOP_LOTTERY_COST_KEY, this.shopLotteryCost);

        // statistics
        tag.putLong("PositiveSpiritIncrease", this.positiveSpiritIncrease);
        tag.putLong("PositiveSpiritDecrease", this.positiveSpiritDecrease);
        tag.putLong("NegativeSpiritIncrease", this.negativeSpiritIncrease);
        tag.putLong("NegativeSpiritDecrease", this.negativeSpiritDecrease);
        tag.putLong("SpiritConsumed", this.spiritConsumed);
    }

    private float generateWeight()
    {
        double gaussian = player.getRandom().nextGaussian();
        float weight = (float) (60 + gaussian * 7.5);
        return MathHelper.clamp(weight, 42, 90);
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
        this.player.sendMessage(SDTexts.getSpiritTomeRuleText(0).formatted(Formatting.DARK_AQUA));
        this.player.damage(this.player.getDamageSources().outOfWorld(), Float.MAX_VALUE);
        // ensure death
        this.player.setHealth(0);
    }

    private void tryUnlockAdvancedRules()
    {
        if (this.player.getWorld().isClient())
        {
            return;
        }
        if (isDisarmActive() && !isRuleRevealed(6))
        {
            revealRule(6);
            this.player.sendMessage(SDTexts.getSpiritTomeRuleText(6).formatted(Formatting.DARK_AQUA));
        }
        if (isJudgmentActive() && !isRuleRevealed(7))
        {
            revealRule(7);
            this.player.sendMessage(SDTexts.getSpiritTomeRuleText(7).formatted(Formatting.DARK_AQUA));
        }
    }

    private boolean tryRefreshDay()
    {
        if (!(this.player instanceof ServerPlayerEntity serverPlayer))
        {
            return false;
        }
        MinecraftServer server = serverPlayer.getServer();
        if (server == null)
        {
            return false;
        }
        long day = server.getOverworld().getTimeOfDay() / 24000L;
        if (day == this.shopDay)
        {
            return false;
        }
        this.shopDay = day;
        return true;
    }

    public void tickRefresh()
    {
        if (!tryRefreshDay())
        {
            return;
        }
        refresh(true);
        sync();
    }

    public void refresh(boolean daily)
    {
        this.shopPurchasedMask = 0;
        this.shopSeed = this.player.getRandom().nextLong();
        refreshShopItems();
        if (daily)
        {
            this.shopRefreshCost = REFRESH_BASE_COST;
            this.shopLotteryCost = LOTTERY_BASE_COST;
        }
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

    public int getShopCost(int index)
    {
        return switch (index)
        {
            case LOTTERY_FLAG -> this.shopLotteryCost;
            case REFRESH_FLAG -> this.shopRefreshCost;
            default -> (index < 0 || index >= this.shopItems.size()) ?
                Integer.MAX_VALUE :
                getShopCost(this.shopItems.get(index));
        };
    }

    private int getShopCost(Item item)
    {
        return switch (item.getRarity(item.getDefaultStack()))
        {
            case COMMON -> 300;
            case UNCOMMON -> 1000;
            case RARE -> 3000;
            case EPIC -> 9999;
        };
    }

    public void onShopping(int index)
    {
        if (this.player.getWorld().isClient())
        {
            throw new UnsupportedOperationException();
        }
        switch (index)
        {
            case LOTTERY_FLAG -> this.shopLotteryCost = plusClamp(this.shopLotteryCost, SHOP_COST_STEP);
            case REFRESH_FLAG -> this.shopRefreshCost = plusClamp(this.shopRefreshCost, SHOP_COST_STEP);
        }
    }

    private static int plusClamp(int a, int b)
    {
        long result = (long) a + (long) b;
        if (result > Integer.MAX_VALUE)
        {
            return Integer.MAX_VALUE;
        }
        if (result < Integer.MIN_VALUE)
        {
            return Integer.MIN_VALUE;
        }
        return (int) result;
    }
}
