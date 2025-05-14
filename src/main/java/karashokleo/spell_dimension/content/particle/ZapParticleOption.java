package karashokleo.spell_dimension.content.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import karashokleo.spell_dimension.init.AllParticles;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Util;

import java.util.Locale;
import java.util.stream.IntStream;

/**
 * Learned from <a href="https://github.com/iron431/irons-spells-n-spellbooks/blob/1.20.1/src/main/java/io/redspace/ironsspellbooks/particle/ZapParticleOption.java">...</a>
 */
public record ZapParticleOption(
        int fromEntity,
        int toEntity,
        int strands
) implements ParticleEffect
{
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

    public static final Codec<ZapParticleOption> CODEC = Codec.INT_STREAM.comapFlatMap(
            intStream -> Util.decodeFixedLengthArray(intStream, 3).map((vec3) -> new ZapParticleOption(vec3[0], vec3[1], vec3[2])),
            option -> IntStream.of(option.fromEntity, option.toEntity, option.strands)
    );

    @SuppressWarnings("deprecation")
    public static final Factory<ZapParticleOption> DESERIALIZER = new Factory<>()
    {
        @Override
        public ZapParticleOption read(ParticleType<ZapParticleOption> type, StringReader reader) throws CommandSyntaxException
        {
            reader.expect(' ');
            int fromEntity = reader.readInt();
            reader.expect(' ');
            int toEntity = reader.readInt();
            reader.expect(' ');
            int strands = reader.readInt();
            return new ZapParticleOption(fromEntity, toEntity, strands);
        }

        @Override
        public ZapParticleOption read(ParticleType<ZapParticleOption> type, PacketByteBuf buf)
        {
            return new ZapParticleOption(buf.readInt(), buf.readInt(), buf.readInt());
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
        buf.writeInt(fromEntity);
        buf.writeInt(toEntity);
        buf.writeInt(strands);
    }

    @Override
    public String asString()
    {
        return String.format(
                Locale.ROOT,
                "%s %d %d %d",
                Registries.PARTICLE_TYPE.getId(this.getType()),
                fromEntity,
                toEntity,
                strands
        );
    }
}
