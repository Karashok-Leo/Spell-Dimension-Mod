package karashokleo.spell_dimension.content.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import karashokleo.spell_dimension.init.AllParticles;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;

/**
 * Learned from <a href="https://github.com/iron431/irons-spells-n-spellbooks/blob/1.20.1/src/main/java/io/redspace/ironsspellbooks/particle/ZapParticleOption.java">...</a>
 */
public record ZapParticleOption(
        Either<Integer, Vec3d> from,
        Either<Integer, Vec3d> to,
        int strands
) implements ParticleEffect
{
    public ZapParticleOption(int fromEntity, int toEntity, int strands)
    {
        this(Either.left(fromEntity), Either.left(toEntity), strands);
    }

    public ZapParticleOption(Vec3d from, Vec3d to, int strands)
    {
        this(Either.right(from), Either.right(to), strands);
    }

    public static class Type extends ParticleType<ZapParticleOption>
    {
        public Type()
        {
            super(false, ZapParticleOption.DESERIALIZER);
        }

        @Override
        public Codec<ZapParticleOption> getCodec()
        {
            return ZapParticleOption.CODEC;
        }
    }

    public static final Codec<ZapParticleOption> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                    PositionProvider.CODEC.fieldOf("from").forGetter(ZapParticleOption::from),
                    PositionProvider.CODEC.fieldOf("to").forGetter(ZapParticleOption::to),
                    Codecs.POSITIVE_INT.fieldOf("strands").forGetter(ZapParticleOption::strands)
            ).apply(ins, ZapParticleOption::new)
    );

    @SuppressWarnings("deprecation")
    public static final Factory<ZapParticleOption> DESERIALIZER = new Factory<>()
    {
        @Override
        public ZapParticleOption read(ParticleType<ZapParticleOption> type, StringReader reader) throws CommandSyntaxException
        {
            reader.expect(' ');
            var from = PositionProvider.readFromString(reader);
            reader.expect(' ');
            var to = PositionProvider.readFromString(reader);
            reader.expect(' ');
            int strands = reader.readInt();
            return new ZapParticleOption(from, to, strands);
        }

        @Override
        public ZapParticleOption read(ParticleType<ZapParticleOption> type, PacketByteBuf buf)
        {
            var from = PositionProvider.readFromPacket(buf);
            var to = PositionProvider.readFromPacket(buf);
            var strands = buf.readInt();
            return new ZapParticleOption(from, to, strands);
        }
    };

    @Override
    public ParticleType<ZapParticleOption> getType()
    {
        return AllParticles.ZAP_PARTICLE;
    }

    @Override
    public void write(PacketByteBuf buf)
    {
        PositionProvider.writeToPacket(buf, from);
        PositionProvider.writeToPacket(buf, to);
        buf.writeInt(strands);
    }

    @Override
    public String asString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(Registries.PARTICLE_TYPE.getId(this.getType()));
        sb.append(' ');
        PositionProvider.writeToString(sb, from);
        sb.append(' ');
        PositionProvider.writeToString(sb, to);
        sb.append(' ');
        sb.append(strands);
        return sb.toString();
    }
}
