package carpetextra.mixins.rules.antiSpamDisabled;

import carpetextra.CarpetExtraSettings;
import net.minecraft.network.packet.c2s.play.CreativeMenuSlotC2SPacket;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow private int dropItemCooldown;

    @Inject(method = "handleCreativeMenuSlot", at = @At("RETURN"))
    private void noItemDropCooldown(CreativeMenuSlotC2SPacket packet, CallbackInfo ci) {
        if (CarpetExtraSettings.antiSpamDisabled) {
            dropItemCooldown = 0;
        }
    }
}
