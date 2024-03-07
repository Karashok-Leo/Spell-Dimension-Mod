package net.karashokleo.spelldimension.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.karashokleo.spelldimension.SpellDimensionComponents;
import net.karashokleo.spelldimension.spell.NucleusSpell;
import net.karashokleo.spelldimension.util.AttributeUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class NucleusComponent extends WithCasterComponent implements ServerTickingComponent, AutoSyncedComponent
{
    public static final UUID uuid = UUID.fromString("977AE476-9F10-5499-4B5D-09B296214773");

    private static final String MODIFIER_KEY = "IceNucleus";
    private static final String DURATION_KEY = "duration";

    private int duration = 0;

    public NucleusComponent(LivingEntity source)
    {
        super(source);
    }

    @Override
    public void serverTick()
    {
        if (duration == 0) return;
        --duration;
        if (duration % 20 == 0)
        {
            if (duration == 0)
            {
                NucleusSpell.nucleusBoom(this.source, this.getCaster());
                clear();
            }
            if (source.isOnFire())
                clear();
        }
        SpellDimensionComponents.NUCLEUS.sync(this.source);
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        this.duration = tag.getInt(DURATION_KEY);
        super.readFromNbt(tag);
    }

    @Override
    public void writeToNbt(NbtCompound tag)
    {
        tag.putInt(DURATION_KEY, this.duration);
        super.writeToNbt(tag);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient)
    {
        buf.writeInt(this.duration);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf)
    {
        this.duration = buf.readInt();
    }

    public float getScale()
    {
        return 2 - (float) this.duration / NucleusSpell.TOTAL_DURATION;
    }

    public boolean isActive()
    {
        return this.duration > 0;
    }

    public void clear()
    {
        AttributeUtil.removeModifier(source, EntityAttributes.GENERIC_MOVEMENT_SPEED, uuid);
        this.duration = 0;
        this.setCaster(null);
    }

    public static NucleusComponent get(LivingEntity livingEntity)
    {
        return SpellDimensionComponents.NUCLEUS.get(livingEntity);
    }

    public static void applyToLiving(LivingEntity source, LivingEntity caster)
    {
        NucleusComponent component = get(source);
        component.duration = NucleusSpell.TOTAL_DURATION;
        component.setCaster(caster);
        AttributeUtil.addModifier(source,EntityAttributes.GENERIC_MOVEMENT_SPEED, uuid, MODIFIER_KEY, NucleusSpell.MULTIPLIER, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
