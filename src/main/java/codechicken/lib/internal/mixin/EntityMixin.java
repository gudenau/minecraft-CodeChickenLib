package codechicken.lib.internal.mixin;

import codechicken.lib.render.particle.ICustomParticleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public Level level;
    
    @Inject(
        method = "spawnSprintParticle",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;getRenderShape()Lnet/minecraft/world/level/block/RenderShape;"
        ),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void spawnSprintParticle(
        CallbackInfo ci,
        int k, int j, int i, BlockPos blockPos, BlockState blockState
    ) {
        if(
            blockState.getBlock() instanceof ICustomParticleBlock particleBlock &&
            //default boolean addRunningEffects(BlockState state, Level world, BlockPos pos, Entity entity) {
            particleBlock.addRunningEffects(blockState, level, blockPos, (Entity)(Object) this)
        ) {
            ci.cancel();
        }
    }
}
