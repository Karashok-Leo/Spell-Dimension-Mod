package karashokleo.spell_dimension.mixin.modded;

import karashokleo.l2hostility.content.logic.TraitGenerator;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.spell_dimension.content.trait.SpellTrait;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.List;

@Mixin(value = TraitGenerator.class, remap = false)
public abstract class TraitGeneratorMixin
{
    @Shadow
    @Final
    private HashMap<MobTrait, Integer> traits;

    @Shadow
    @Final
    private Random rand;

    @Inject(
            method = "generate",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/HashMap;entrySet()Ljava/util/Set;"
            )
    )
    private void inject_generate(CallbackInfo ci)
    {
        List<MobTrait> spellTraits = traits.keySet().stream().filter(trait -> trait instanceof SpellTrait).toList();
        if (spellTraits.isEmpty()) return;
        MobTrait random = RandomUtil.randomFromList(rand, spellTraits);
        for (MobTrait spellTrait : spellTraits)
        {
            if (spellTraits == random) continue;
            traits.remove(spellTrait);
        }
    }
}
