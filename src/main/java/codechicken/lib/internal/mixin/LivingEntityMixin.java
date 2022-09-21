package codechicken.lib.internal.mixin;

import codechicken.lib.render.particle.ICustomParticleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin() {
        super(null, null);
        throw new AssertionError();
    }
    
    @Inject(
        method = "checkFallDamage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"
        ),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void checkFallDamage(
        double d, boolean bl, BlockState blockState, BlockPos blockPos,
        CallbackInfo ci,
        float f, double d2, int i
    ) {
        var block = blockState.getBlock();
        if (
            block instanceof ICustomParticleBlock particleBlock &&
            particleBlock.addLandingEffects((ServerLevel) level, blockPos, (LivingEntity)(Object) this, i)
        ) {
            super.checkFallDamage(d, bl, blockState, blockPos);
            ci.cancel();
        }
    }
}
