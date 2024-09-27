package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.config.recipe.SummonSpellConfig;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class SpawnerSoulItem extends Item
{
    public static final String ENTITY_KEY = "Entity";

    public SpawnerSoulItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    public ItemStack getStack(ISpawnerExtension spawnerExtension)
    {
        int remain = spawnerExtension.getRemain();
        NbtCompound entityNbt = spawnerExtension.getEntityNbt();
        Optional<EntityType<?>> optional = EntityType.fromNbt(entityNbt);
        if (remain <= 0 || optional.isEmpty()) return ItemStack.EMPTY;
        ItemStack stack = this.getDefaultStack();
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(ISpawnerExtension.KEY_REMAIN, remain);
        nbt.put(ENTITY_KEY, entityNbt);
        return stack;
    }

    @SuppressWarnings("unchecked")
    public Optional<SummonSpellConfig.Entry> getSummonEntry(ItemStack stack)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return Optional.empty();
        NbtCompound entityNbt = nbt.getCompound(ENTITY_KEY);
        if (entityNbt.isEmpty()) return Optional.empty();
        Optional<EntityType<?>> optional = EntityType.fromNbt(entityNbt);
        if (optional.isPresent())
        {
            try
            {
                EntityType<LivingEntity> entityType = (EntityType<LivingEntity>) optional.get();
                int remain = nbt.getInt(ISpawnerExtension.KEY_REMAIN);
                return Optional.of(new SummonSpellConfig.Entry(entityType, remain));
            } catch (Exception ignored)
            {
            }
        }
        return Optional.empty();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        this.getSummonEntry(stack).ifPresent(entry ->
        {
            tooltip.add(SDTexts.TOOLTIP$SUMMON_ENTITY.get(entry.entityType().getName()).formatted(Formatting.DARK_AQUA));
            tooltip.add(SDTexts.TOOLTIP$SUMMON_REMAIN.get(entry.count()).formatted(Formatting.DARK_AQUA));
        });
    }
}
