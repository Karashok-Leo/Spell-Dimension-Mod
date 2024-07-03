package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.spell_dimension.init.AllComponents;
import karashokleo.spell_dimension.content.misc.Mage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class MageComponent implements AutoSyncedComponent
{
    final PlayerEntity owner;
    Mage mage = new Mage(0, null, null);

    public MageComponent(PlayerEntity owner)
    {
        this.owner = owner;
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        this.set(Mage.readFromNbt(tag));
    }

    @Override
    public void writeToNbt(NbtCompound tag)
    {
        mage.writeToNbt(tag);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient)
    {
        mage.writeToPacket(buf);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf)
    {
        this.set(Mage.readFromPacket(buf));
    }

    public static Mage get(PlayerEntity playerEntity)
    {
        return AllComponents.MAGE.get(playerEntity).mage;
    }

    public static void set(PlayerEntity playerEntity, Mage mage)
    {
        AllComponents.MAGE.get(playerEntity).set(mage);
    }

    public void set(Mage mage)
    {
        this.mage = mage;
    }
}
