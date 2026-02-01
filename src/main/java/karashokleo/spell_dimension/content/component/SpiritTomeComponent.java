package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.spell_dimension.init.AllComponents;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class SpiritTomeComponent implements AutoSyncedComponent
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
                component.changeSpirit(SpiritType.POSITIVE, amount);
            } else
            {
                // rule 2
                component.changeSpirit(SpiritType.NEGATIVE, amount);
            }
        } else
        {
            if (captured)
            {
                // rule 3
                component.changeSpirit(SpiritType.NEGATIVE, -amount);
            } else
            {
                // rule 4
                component.changeSpirit(SpiritType.POSITIVE, -amount);
            }
        }
    }

    private static final String POSITIVE_SPIRIT_KEY = "PositiveSpirit";
    private static final String NEGATIVE_SPIRIT_KEY = "NegativeSpirit";
    private static final String WEIGHT_KEY = "Weight";

    private final PlayerEntity player;
    private float baseWeight;
    private int positiveSpirit;
    private int negativeSpirit;

    public SpiritTomeComponent(PlayerEntity player)
    {
        this.player = player;
    }

    public void sync()
    {
        if (this.player instanceof ServerPlayerEntity serverPlayer)
        {
            AllComponents.SPIRIT_TOME.sync(serverPlayer);
        }
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

    public boolean tryConsumeSpirit(int amount)
    {
        if (amount <= 0)
        {
            return true;
        }
        if (this.getSpirit() < amount)
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
        sync();
        return true;
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.positiveSpirit = tag.getInt(POSITIVE_SPIRIT_KEY);
        this.negativeSpirit = tag.getInt(NEGATIVE_SPIRIT_KEY);
        float weight = tag.getFloat(WEIGHT_KEY);
        this.baseWeight = weight > 0 ? weight : generateWeight();
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putInt(POSITIVE_SPIRIT_KEY, this.positiveSpirit);
        tag.putInt(NEGATIVE_SPIRIT_KEY, this.negativeSpirit);
        tag.putFloat(WEIGHT_KEY, this.baseWeight);
    }

    private float generateWeight()
    {
        double gaussian = player.getRandom().nextGaussian();
        float weight = (float) (65 + gaussian * 10);
        return MathHelper.clamp(weight, 45, 100);
    }

    private void erase()
    {
        if (!(this.player instanceof ServerPlayerEntity serverPlayer))
        {
            return;
        }
        for (var access : TrinketCompat.getItemAccess(serverPlayer))
        {
            if (access.get().isOf(AllItems.SPIRIT_TOME))
            {
                access.set(ItemStack.EMPTY);
            }
        }
        // TODO: broadcast death message?
        serverPlayer.damage(serverPlayer.getDamageSources().outOfWorld(), Float.MAX_VALUE);
        // ensure death
        serverPlayer.setHealth(0);
    }
}
