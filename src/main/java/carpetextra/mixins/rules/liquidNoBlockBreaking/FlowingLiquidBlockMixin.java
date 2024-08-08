package carpetextra.mixins.rules.liquidNoBlockBreaking;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.FlowingLiquidBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlowingLiquidBlock.class)
public abstract class FlowingLiquidBlockMixin {
    @Inject(
            method = "spreadTo",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/FlowingLiquidBlock;fizz(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"
            ),
            cancellable = true
    )
    private void noLavaFlow(World world, BlockPos pos, BlockState state, int level, CallbackInfo ci){
        if (CarpetExtraSettings.liquidNoBlockBreaking) {
            ci.cancel();
        }
    }

    @Inject(
            method = "spreadTo",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/state/BlockState;getBlock()Lnet/minecraft/block/Block;"
            ),
            cancellable = true
    )
    private void noWaterFlow(World world, BlockPos pos, BlockState state, int level, CallbackInfo ci) {
        if (CarpetExtraSettings.liquidNoBlockBreaking) {
            ci.cancel();
        }
    }
}
