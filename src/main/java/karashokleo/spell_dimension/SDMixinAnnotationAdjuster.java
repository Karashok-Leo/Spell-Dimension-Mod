package karashokleo.spell_dimension;

import com.bawnorton.mixinsquared.adjuster.tools.AdjustableAnnotationNode;
import com.bawnorton.mixinsquared.api.MixinAnnotationAdjuster;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

public class SDMixinAnnotationAdjuster implements MixinAnnotationAdjuster
{
    @Override
    public AdjustableAnnotationNode adjust(List<String> targetClassNames, String mixinClassName, MethodNode handlerNode, AdjustableAnnotationNode annotationNode)
    {
        if (!mixinClassName.equals("io.github.fabricators_of_create.porting_lib.loot.mixin.loottable.LootTableMixin"))
            return annotationNode;
        if (!annotationNode.is(WrapMethod.class)) return annotationNode;
        return null;
    }
}
