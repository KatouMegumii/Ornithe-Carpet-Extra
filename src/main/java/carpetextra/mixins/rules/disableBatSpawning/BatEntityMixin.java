package carpetextra.mixins.rules.disableBatSpawning;

import carpetextra.CarpetExtraSettings;
import net.minecraft.entity.living.mob.ambient.BatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BatEntity.class)
public abstract class BatEntityMixin {
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private void noBat(CallbackInfoReturnable<Boolean> cir) {
        if (CarpetExtraSettings.disableBatSpawning) {
            cir.setReturnValue(false);
        }
    }
}
