package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.util.TeleportUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class LocatePortalEntity extends Entity
{
    public static final String LIFESPAN_KEY = "Lifespan";
    public static final String DESTINATION_KEY = "Destination";
    public static final int SPAWN_DELAY = 50;
    public static final int PORTAL_COOLDOWN = 100;
    protected int lifespan;
    protected BlockPos destination;

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
        int activeTime = this.getActiveTime();
        if (activeTime > this.lifespan)
            this.remove(RemovalReason.DISCARDED);
        if (!this.getWorld().isClient() && activeTime > 0)
        {
            List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(1, 2, 1));
            entities.forEach(entity ->
            {
                if (entity.hasPortalCooldown()) return;
                TeleportUtil.teleportPlayer(entity, this.getDestination());
                entity.teleport(this.getDestination().getX(), this.getDestination().getY(), this.getDestination().getZ());
                entity.setPortalCooldown(PORTAL_COOLDOWN);
            });
        }
    }

    public int getActiveTime()
    {
        return this.age - SPAWN_DELAY;
    }

    public BlockPos getDestination()
    {
        return destination;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {
        this.lifespan = nbt.getInt(LIFESPAN_KEY);
        this.destination = BlockPos.fromLong(nbt.getLong(DESTINATION_KEY));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt)
    {
        nbt.putInt(LIFESPAN_KEY, this.lifespan);
        nbt.putLong(DESTINATION_KEY, this.destination.asLong());
    }
}
