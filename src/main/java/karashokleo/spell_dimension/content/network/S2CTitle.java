package karashokleo.spell_dimension.content.network;

import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public record S2CTitle(Text title, @Nullable Text subTitle) implements FabricPacket
{
    public static final PacketType<S2CTitle> TYPE = PacketType.create(SpellDimension.modLoc("title"), buf -> new S2CTitle(buf.readText(), buf.readBoolean() ? buf.readText() : null));

    public S2CTitle(Text title)
    {
        this(title, null);
    }

    @Override
    public void write(PacketByteBuf buf)
    {
        buf.writeText(title);
        buf.writeBoolean(subTitle != null);
        if (subTitle != null) buf.writeText(subTitle);
    }

    @Override
    public PacketType<?> getType()
    {
        return TYPE;
    }
}
