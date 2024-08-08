package carpetextra.mixins.rules.allowNetherWater;

import carpetextra.CarpetExtraSettings;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Dimension.class)
public abstract class DimensionMixin {
    @Inject(method = "yeetsWater", at = @At("HEAD"), cancellable = true)
    private void allowNetherWater(CallbackInfoReturnable<Boolean> cir) {
        if (CarpetExtraSettings.allowNetherWater) {
            cir.setReturnValue(false);
        }
    }
}
