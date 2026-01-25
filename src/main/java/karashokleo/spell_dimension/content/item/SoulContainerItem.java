package karashokleo.spell_dimension.content.item;

import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SoulContainerItem extends AbstractSoulContainerItem
{
    protected static final String ENTITY_KEY = "Entity";

    protected final FloatUnaryOperator probabilityFunction;
    protected final float healthThresholdRatio;
    protected final int cooldownOnFail;

    public SoulContainerItem(FloatUnaryOperator probabilityFunction, float healthThresholdRatio, int cooldownOnFail)
    {
        super(
            new FabricItemSettings()
                .fireproof()
                .maxCount(1)
        );
        this.probabilityFunction = probabilityFunction;
        this.healthThresholdRatio = healthThresholdRatio;
        this.cooldownOnFail = cooldownOnFail;
    }

    @Override
    protected MobEntity tryAccessMob(ServerWorld world, Vec3d pos, NbtCompound itemNbt, PlayerEntity player)
    {
        // if stored, try to spawn it
        MobEntity mob = super.tryAccessMob(world, pos, itemNbt, player);
        if (mob != null)
        {
            // data changed
            itemNbt.putUuid(ENTITY_KEY, mob.getUuid());
            saveSoulMinionTooltipData(itemNbt, mob);
            return mob;
        }
        // else if last stored, try to teleport it back here
        if (itemNbt.containsUuid(ENTITY_KEY))
        {
            UUID lastUuid = itemNbt.getUuid(ENTITY_KEY);
            if (world.getEntity(lastUuid) instanceof MobEntity mob1)
            {
                mob1.setPosition(pos);
                return mob1;
            }
        }
        // not spawned && not found
        player.sendMessage(SDTexts.TOOLTIP$SOUL_CONTAINER$WARNING.get().formatted(Formatting.RED), true);
        return null;
    }

    @Override
    protected boolean isFull(NbtCompound itemNbt)
    {
        return itemNbt.contains(ENTITY_KEY, NbtElement.COMPOUND_TYPE);
    }

    @Override
    protected void saveMobToNbt(NbtCompound itemNbt, MobEntity mob)
    {
        super.saveMobToNbt(itemNbt, mob);
        saveSoulMinionTooltipData(itemNbt, mob);
    }

    @Override
    protected void putMobDataToNbt(NbtCompound itemNbt, NbtCompound mobNbt)
    {
        itemNbt.put(ENTITY_KEY, mobNbt);
    }

    @Override
    protected NbtCompound getMobDataFromNbt(NbtCompound itemNbt, boolean removeData)
    {
        if (itemNbt.contains(ENTITY_KEY, NbtElement.COMPOUND_TYPE))
        {
            NbtCompound data = itemNbt.getCompound(ENTITY_KEY);
            if (removeData)
            {
                itemNbt.remove(ENTITY_KEY);
            }
            return data;
        }
        return null;
    }

    @Override
    protected int getCooldown(boolean success)
    {
        return success ? COOLDOWN : cooldownOnFail;
    }

    @Override
    protected boolean cannotCapture(ItemStack stack, PlayerEntity user, MobEntity mob)
    {
        return super.cannotCapture(stack, user, mob) &&
            user.getRandom().nextFloat() > getCaptureProbability(mob);
    }

    public float getCaptureProbability(MobEntity entity)
    {
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        float probability = probabilityFunction.apply(health / maxHealth);
        return MathHelper.clamp(probability, 0f, 1f);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);

        NbtCompound nbt = stack.getNbt();
        if (nbt == null)
        {
            tooltip.add(SDTexts.TOOLTIP$CONTAINER_EMPTY.get().formatted(Formatting.DARK_AQUA).formatted(Formatting.GRAY));
            return;
        }

        boolean stored = nbt.contains(ENTITY_KEY, NbtElement.COMPOUND_TYPE);
        boolean lastStored = nbt.containsUuid(ENTITY_KEY);

        // stored
        if (stored)
        {
            tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$STORED.get().formatted(Formatting.GRAY));
        }
        // nothing inside
        else
        {
            tooltip.add(SDTexts.TOOLTIP$CONTAINER_EMPTY.get().formatted(Formatting.DARK_AQUA).formatted(Formatting.GRAY));
            // last stored
            if (lastStored)
            {
                tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$LAST_STORED.get().formatted(Formatting.GRAY));
            }
        }

        // content tooltips
        if (!appendSoulMinionDetailTooltip(nbt, tooltip) &&
            (stored || lastStored))
        {
            tooltip.add(SDTexts.TOOLTIP$INVALID.get().formatted(Formatting.RED));
        }
        tooltip.add(Text.empty());

        // usage tooltips
        if (stored)
        {
            tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$USAGE_2.get().formatted(Formatting.GRAY));
        } else
        {
            if (lastStored)
            {
                tooltip.add(SDTexts.TOOLTIP$SOUL_CONTAINER$USAGE_3.get().formatted(Formatting.GRAY));
                tooltip.add(Text.empty());
            }
            tooltip.add(
                SDTexts.TOOLTIP$SOUL_CONTAINER$USAGE_1.get(
                    "%d%%".formatted(Math.round(healthThresholdRatio * 100))
                ).formatted(Formatting.GRAY)
            );
        }
    }

    @Override
    public int getContainMobMaxLevel(ItemStack stack, World world)
    {
        var nbt = stack.getNbt();
        if (nbt == null)
        {
            return 0;
        }
        NbtCompound data = getMobDataFromNbt(nbt, false);
        if (data == null)
        {
            return 0;
        }
        MobEntity mob = SoulControl.loadMinionFromData(data, world);
        return DifficultyLevel.ofAny(mob);
    }
}
