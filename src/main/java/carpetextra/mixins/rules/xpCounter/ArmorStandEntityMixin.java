package carpetextra.mixins.rules.xpCounter;

import carpetextra.CarpetExtraSettings;
import carpetextra.logging.logHelpers.XpCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.XpOrbEntity;
import net.minecraft.entity.living.ArmorStandEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntityMixin extends LivingEntity {
    @Shadow protected abstract void pushAway(Entity entity);

    public ArmorStandEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "pushAway", at = @At("HEAD"))
    private void countXp(Entity entity, CallbackInfo ci) {
        if (entity instanceof XpOrbEntity) {
            XpCounter.armorStandCounter.add(getServer(), ((XpOrbEntity) entity).getXp());
            entity.remove();
        }
    }

    @Override
    public void tickAi() {
        super.tickAi();

        if (CarpetExtraSettings.xpCounter.equals("armorstand")) {
            Box box = this.getShape().expand(1.0D, 0.5D, 1.0D);
            List<Entity> list = this.world.getEntities(this, box);
            for (Entity entity : list) {
                if (!entity.removed) {
                    this.pushAway(entity);
                }
            }
        }
    }
}
