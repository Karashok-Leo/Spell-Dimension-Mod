package karashokleo.spell_dimension.content.network;

import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record S2CSpellDash() implements FabricPacket
{
    public static final PacketType<S2CSpellDash> TYPE = PacketType.create(SpellDimension.modLoc("spell_dash"), buf -> new S2CSpellDash());

    @Override
    public void write(PacketByteBuf buf)
    {
    }

    @Override
    public PacketType<?> getType()
    {
        return TYPE;
    }
}
