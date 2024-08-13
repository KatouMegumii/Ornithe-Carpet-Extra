package carpetextra.mixins.rules.xpCounter;

import carpetextra.CarpetExtraSettings;
import carpetextra.logging.logHelpers.XpCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.XpOrbEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(XpOrbEntity.class)
public abstract class XpOrbEntityMixin extends Entity {
    @Shadow private int xp;

    public XpOrbEntityMixin(World world) {
        super(world);
    }

    @Inject(
            method = "onPlayerCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/living/player/PlayerEntity;sendPickup(Lnet/minecraft/entity/Entity;I)V"
            )
    )
    private void countPlayerXp(PlayerEntity player, CallbackInfo ci) {
        if ("player".equals(CarpetExtraSettings.xpCounter)) {
            String name = player.getName();

            XpCounter counter = XpCounter.getCounter(name);
            counter.add(player.getServer(), xp);
        }
    }
}
