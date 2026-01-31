package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class SpiritTomeComponent implements AutoSyncedComponent
{
    private static final String SPIRIT_KEY = "Spirit";
    private static final String WEIGHT_KEY = "Weight";

    private final PlayerEntity player;
    private int spirit;
    private float baseWeight;

    public SpiritTomeComponent(PlayerEntity player)
    {
        this.player = player;
    }

    public static SpiritTomeComponent get(PlayerEntity player)
    {
        return AllComponents.SPIRIT_TOME.get(player);
    }

    public static int getSpirit(PlayerEntity player)
    {
        return get(player).spirit;
    }

    public static float getWeight(PlayerEntity player)
    {
        float baseWeight = get(player).baseWeight;
        var hungerManager = player.getHungerManager();
        float hungerNormalized = (hungerManager.getFoodLevel() - 10f) / 10f;
        float saturationNormalized = (hungerManager.getSaturationLevel() - 5f) / 5f;
        float adjustment = 1.5f * MathHelper.clamp(hungerNormalized, -1f, 1f)
            + 0.5f * MathHelper.clamp(saturationNormalized, -1f, 1f);
        return MathHelper.clamp(baseWeight + adjustment, 35f, 120f);
    }

    public static void addSpirit(ServerPlayerEntity player, int amount)
    {
        if (amount <= 0)
        {
            return;
        }
        SpiritTomeComponent component = get(player);
        component.spirit += amount;
        sync(player);
    }

    public static void setSpirit(ServerPlayerEntity player, int amount)
    {
        SpiritTomeComponent component = get(player);
        component.spirit = Math.max(0, amount);
        sync(player);
    }

    public static boolean consumeSpirit(ServerPlayerEntity player, int amount)
    {
        if (amount <= 0)
        {
            return true;
        }
        SpiritTomeComponent component = get(player);
        if (component.spirit < amount)
        {
            return false;
        }
        component.spirit -= amount;
        sync(player);
        return true;
    }

    public static void sync(ServerPlayerEntity player)
    {
        AllComponents.SPIRIT_TOME.sync(player);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.spirit = tag.getInt(SPIRIT_KEY);
        if (tag.contains(WEIGHT_KEY))
        {
            float weight = tag.getFloat(WEIGHT_KEY);
            this.baseWeight = weight > 0 ? weight : generateWeight();
        } else
        {
            this.baseWeight = generateWeight();
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putInt(SPIRIT_KEY, this.spirit);
        tag.putFloat(WEIGHT_KEY, this.baseWeight);
    }

    private float generateWeight()
    {
        double gaussian = player.getRandom().nextGaussian();
        float weight = (float) (65 + gaussian * 10);
        return MathHelper.clamp(weight, 45, 100);
    }
}
