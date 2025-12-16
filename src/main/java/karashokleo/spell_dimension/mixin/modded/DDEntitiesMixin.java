package karashokleo.spell_dimension.mixin.modded;

import com.kyanite.deeperdarker.content.DDEntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = DDEntities.class, remap = false)
public abstract class DDEntitiesMixin
{
    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/fabricmc/fabric/api/object/builder/v1/entity/FabricEntityTypeBuilder;trackRangeBlocks(I)Lnet/fabricmc/fabric/api/object/builder/v1/entity/FabricEntityTypeBuilder;"
        )
    )
    private static <T extends Entity> FabricEntityTypeBuilder<T> redirect_trackRangeBlocks(FabricEntityTypeBuilder<T> instance, int range)
    {
        return instance.trackRangeChunks(range);
    }
}
