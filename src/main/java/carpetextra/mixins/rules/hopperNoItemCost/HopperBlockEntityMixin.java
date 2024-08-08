package carpetextra.mixins.rules.hopperNoItemCost;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.state.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin extends BlockEntity {
    @Shadow public abstract void setStack(int slot, ItemStack stack);

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Inject(
            method = "pushItems()Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/inventory/Inventory;markDirty()V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void noItemCost(CallbackInfoReturnable<Boolean> cir, Inventory inventory, Direction direction, int i, ItemStack itemStack, ItemStack itemStack2) {
        if (CarpetExtraSettings.hopperNoItemCost){
            if (checkWool(getWorld(), new BlockPos(getX(), getY(), getZ()).offset(Direction.UP))){
                this.setStack(i, itemStack);
            }
        }
    }

    @Unique
    private boolean checkWool(World world, BlockPos pos){
        BlockState state = world.getBlockState(pos);
        return state.getBlock() == Blocks.WOOL;
    }
}
