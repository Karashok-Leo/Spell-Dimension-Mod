package karashokleo.spell_dimension;

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAnnotationNode;
import com.bawnorton.mixinsquared.adjuster.tools.AdjustableInjectNode;
import com.bawnorton.mixinsquared.api.MixinAnnotationAdjuster;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

public class SDMixinAnnotationAdjuster implements MixinAnnotationAdjuster
{
    @Override
    public AdjustableAnnotationNode adjust(List<String> targetClassNames, String mixinClassName, MethodNode handlerNode, AdjustableAnnotationNode annotationNode)
    {
        if (mixinClassName.equals("io.github.fabricators_of_create.porting_lib.loot.mixin.loottable.LootTableMixin") &&
            annotationNode.is(WrapMethod.class)
        )
        {
            System.out.println("Mixin " + mixinClassName + " cancelled");
            return null;
        }
        if (mixinClassName.equals("io.ix0rai.rainglow.mixin.SlimeEntityMixin") &&
            annotationNode.is(Inject.class) &&
            annotationNode.as(AdjustableInjectNode.class)
                    .getMethod()
                    .get(0)
                    .equals("tick")
        )
        {
            System.out.println("Mixin " + mixinClassName + " cancelled");
            return null;
        }
        return annotationNode;
    }
}
