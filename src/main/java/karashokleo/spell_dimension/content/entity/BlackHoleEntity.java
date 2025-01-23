package karashokleo.spell_dimension.content.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlackHoleEntity extends Entity implements Ownable
{
    private static final TrackedData<Float> DATA_RADIUS = DataTracker.registerData(BlackHoleEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public static BlackHoleEntity create(EntityType<BlackHoleEntity> type, World world)
    {
        return new BlackHoleEntity(type, world);
    }

    private BlackHoleEntity(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Override
    public void calculateDimensions()
    {
        super.calculateDimensions();
    }

    @Override
    protected void initDataTracker()
    {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt)
    {

    }

    @Override
    public @Nullable Entity getOwner()
    {
        return null;
    }
}
