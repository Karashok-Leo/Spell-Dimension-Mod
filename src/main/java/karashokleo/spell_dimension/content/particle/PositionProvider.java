package karashokleo.spell_dimension.content.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface PositionProvider
{
    Vec3d getRenderPosition(float tickDelta);

    Codec<Either<Integer, Vec3d>> CODEC = Codec.either(Codec.INT, Vec3d.CODEC);

    static PositionProvider get(Either<Integer, Vec3d> position, World world)
    {
        return position.map(
            id ->
            {
                var entity = world.getEntityById(id);
                return entity == null ?
                    null :
                    tickDelta -> getPos(entity, tickDelta);
            },
            vec -> tickDelta -> vec
        );
    }

    static Vec3d getPos(Entity entity, float tickDelta)
    {
        Vec3d prevPos = new Vec3d(entity.prevX, entity.prevY, entity.prevZ);
        return prevPos.lerp(entity.getPos(), tickDelta)
            .add(0, entity.getHeight() / 2, 0);
    }

    static Either<Integer, Vec3d> readFromString(StringReader reader) throws CommandSyntaxException
    {
        boolean left = reader.readBoolean();
        reader.expect(' ');
        if (left)
        {
            return Either.left(reader.readInt());
        } else
        {
            double x = reader.readDouble();
            reader.expect(' ');
            double y = reader.readDouble();
            reader.expect(' ');
            double z = reader.readDouble();
            return Either.right(new Vec3d(x, y, z));
        }
    }

    static void writeToString(StringBuilder sb, Either<Integer, Vec3d> position)
    {
        if (position.left().isPresent())
        {
            sb.append(true)
                .append(' ')
                .append(position.left().get());
        } else
        {
            //noinspection OptionalGetWithoutIsPresent
            Vec3d vec = position.right().get();
            sb.append(false)
                .append(' ')
                .append(vec.x)
                .append(' ')
                .append(vec.y)
                .append(' ')
                .append(vec.z);
        }
    }

    static Either<Integer, Vec3d> readFromPacket(PacketByteBuf buf)
    {
        if (buf.readBoolean())
        {
            return Either.left(buf.readInt());
        } else
        {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            return Either.right(new Vec3d(x, y, z));
        }
    }

    static void writeToPacket(PacketByteBuf buf, Either<Integer, Vec3d> position)
    {
        if (position.left().isPresent())
        {
            buf.writeBoolean(true);
            buf.writeInt(position.left().get());
        } else
        {
            //noinspection OptionalGetWithoutIsPresent
            Vec3d vec = position.right().get();
            buf.writeBoolean(false);
            buf.writeDouble(vec.x);
            buf.writeDouble(vec.y);
            buf.writeDouble(vec.z);
        }
    }
}
