package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.network.S2CEntity;
import karashokleo.spell_dimension.api.BeamProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.spell_engine.internals.SpellRegistry;
import org.jetbrains.annotations.Nullable;

import static net.spell_engine.api.spell.Spell.Release.Target.Type.BEAM;

@SerialClass
public class S2CBeam extends S2CEntity
{
    @SerialClass.SerialField
    boolean beaming;
    @SerialClass.SerialField
    int beamTarget;
    @SerialClass.SerialField
    Identifier spellId;

    @Deprecated
    public S2CBeam()
    {
        super();
    }

    public S2CBeam(LivingEntity e, boolean beaming, @Nullable LivingEntity beamTarget, Identifier spellId)
    {
        super(e);
        this.beaming = beaming;
        this.beamTarget = beamTarget == null ? -1 : beamTarget.getId();
        this.spellId = spellId;
    }

    protected void setBeam(BeamProvider beamProvider, World world)
    {
        if (beaming)
        {
            var target = beamTarget >= 0 ? world.getEntityById(beamTarget) : null;
            if (target instanceof LivingEntity targetEntity)
                beamProvider.setBeamTarget(targetEntity);
            var spell = SpellRegistry.getSpell(spellId);
            if (spell != null &&
                spell.release != null &&
                spell.release.target.type == BEAM)
            {
                beamProvider.setBeam(spell.release.target.beam);
            }
        } else
        {
            beamProvider.setBeamTarget(null);
            beamProvider.setBeam(null);
        }
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world != null &&
            id >= 0
            && world.getEntityById(id) instanceof BeamProvider beamProvider)
        {
            setBeam(beamProvider, world);
        }
    }
}
