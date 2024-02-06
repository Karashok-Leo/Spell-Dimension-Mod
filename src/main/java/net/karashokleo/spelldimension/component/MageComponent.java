package net.karashokleo.spelldimension.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.karashokleo.spelldimension.SpellDimensionComponents;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

public class MageComponent implements AutoSyncedComponent
{
    final PlayerEntity owner;
    int grade = 0;
    @Nullable
    MagicSchool school = null;
    @Nullable
    MageMajor major = null;

    public MageComponent(PlayerEntity owner)
    {
        this.owner = owner;
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        Mage mage = Mage.readFromNbt(tag);
        this.set(mage);
    }

    @Override
    public void writeToNbt(NbtCompound tag)
    {
        Mage.writeToNbt(tag, grade, school, major);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient)
    {
        Mage.writeToPacket(buf, this.grade, this.school, this.major);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf)
    {
        Mage mage = Mage.readFromPacket(buf);
        this.set(mage);
    }

    public static Mage get(PlayerEntity playerEntity)
    {
        return SpellDimensionComponents.MAGE.get(playerEntity).toMage();
    }

    public static void set(PlayerEntity playerEntity, Mage mage)
    {
        SpellDimensionComponents.MAGE.get(playerEntity).set(mage);
    }

    public Mage toMage()
    {
        return new Mage(this.grade, this.school, this.major);
    }

    public void set(int grade, @Nullable MagicSchool school, @Nullable MageMajor major)
    {
        this.grade = grade;
        this.school = school;
        this.major = major;
    }

    public void set(Mage mage)
    {
        this.set(mage.grade(), mage.school(), mage.major());
    }
}
