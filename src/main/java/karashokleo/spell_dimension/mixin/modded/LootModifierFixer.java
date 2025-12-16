package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.fabricators_of_create.porting_lib.loot.LootCollector;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class LootModifierFixer
{
    @Shadow
    public abstract void generateLoot(LootContext context, Consumer<ItemStack> lootConsumer);

    @Shadow
    @Final
    @Nullable Identifier randomSequenceId;

    @WrapOperation(
        method = "generateLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/loot/LootTable;generateUnprocessedLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V"
        )
    )
    private void finishCollectingLoot$0(LootTable instance, LootContext context, Consumer<ItemStack> lootConsumer, Operation<Void> original)
    {
        LootCollector lootCollector = new LootCollector(lootConsumer);
        original.call(instance, context, lootConsumer);
        lootCollector.finish(instance.getLootTableId(), context);
    }

    @WrapOperation(
        method = "generateLoot(Lnet/minecraft/loot/context/LootContextParameterSet;Ljava/util/function/Consumer;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/loot/LootTable;generateUnprocessedLoot(Lnet/minecraft/loot/context/LootContextParameterSet;Ljava/util/function/Consumer;)V"
        )
    )
    private void finishCollectingLoot$1(LootTable instance, LootContextParameterSet parameters, Consumer<ItemStack> lootConsumer, Operation<Void> original)
    {
        this.generateLoot((new LootContext.Builder(parameters)).build(this.randomSequenceId), lootConsumer);
    }

    @WrapOperation(
        method = "generateLoot(Lnet/minecraft/loot/context/LootContextParameterSet;JLjava/util/function/Consumer;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/loot/LootTable;generateUnprocessedLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V"
        )
    )
    private void finishCollectingLoot$2(LootTable instance, LootContext context, Consumer<ItemStack> lootConsumer, Operation<Void> original)
    {
        this.generateLoot(context, lootConsumer);
    }
}
