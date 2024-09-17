package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.init.AllEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class LocatePortalEntity extends Entity
{
    protected final int lifespan;
    protected final BlockPos destination;

    public static LocatePortalEntity create(EntityType<?> type, World world)
    {
        return new LocatePortalEntity(type, world, BlockPos.ORIGIN, 200);
    }

    public LocatePortalEntity(EntityType<?> type, World world, BlockPos destination, int lifespan)
    {
        super(type, world);
        this.lifespan = lifespan;
        this.destination = destination;
    }

    public LocatePortalEntity(World world, BlockPos destination, int lifespan)
    {
        super(AllEntities.LOCATE_PORTAL, world);
        this.lifespan = lifespan;
        this.destination = destination;
    }

    public LocatePortalEntity(World world, BlockPos destination)
    {
        this(world, destination, 200);
    }

    @Override
    protected void initDataTracker()
    {
    }

    @Override
    public void tick()
    {
        super.tick();
        if (this.age > this.lifespan)
            this.remove(RemovalReason.DISCARDED);
        if (!this.getWorld().isClient())
        {
            List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(1, 2, 1));
            entities.forEach(entity ->
            {
                entity.teleport(this.getDestination().getX(), this.getDestination().getY(), this.getDestination().getZ());
            });
        }
    }

    public BlockPos getDestination()
    {
        return destination;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt)
    {

    }
}
