package carpetextra.mixins.rules.creativeOpenContainerNoCheck;

import carpetextra.CarpetExtraSettings;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShulkerBoxBlock.class)
public abstract class ShulkerBoxBlockMixin {
    @ModifyExpressionValue(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getCollisions(Lnet/minecraft/util/math/Box;)Z"
            )
    )
    private boolean creativeOpenShulker(boolean original, @Local(argsOnly = true) PlayerEntity player) {
        if (CarpetExtraSettings.creativeOpenContainerNoCheck && player.isCreative()) {
            return false;
        }

        return original;
    }

}
