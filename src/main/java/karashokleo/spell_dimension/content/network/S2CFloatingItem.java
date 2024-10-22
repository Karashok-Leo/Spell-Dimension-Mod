package karashokleo.spell_dimension.content.network;

import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public record S2CFloatingItem(ItemStack stack) implements FabricPacket
{
    public static final PacketType<S2CFloatingItem> TYPE = PacketType.create(SpellDimension.modLoc("floating_item"), buf -> new S2CFloatingItem(buf.readItemStack()));

    @Override
    public void write(PacketByteBuf buf)
    {
        buf.writeItemStack(stack);
    }

    @Override
    public PacketType<?> getType()
    {
        return TYPE;
    }
}
