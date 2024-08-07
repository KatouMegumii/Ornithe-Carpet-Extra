package carpetextra.mixins.core;

import carpetextra.CarpetExtraServer;
import carpet.CarpetServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarpetServer.class)
public abstract class CarpetServerMixin {
    @Inject(method = "onGameStarted", at = @At("HEAD"), remap = false)
    private static void onload(CallbackInfo ci) {
        CarpetExtraServer.loadExtension();
    }
}
