package codechicken.lib.render.particle;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Trait like interface for adding CustomParticle support to any block, Simply implement :D
 * <p>
 * Created by covers1624 on 31/10/19.
 */
public interface ICustomParticleBlock {

    default boolean addLandingEffects(ServerLevel worldserver, BlockPos pos, LivingEntity entity, int numberOfParticles) {
        CustomParticleHandler.handleLandingEffects(worldserver, pos, entity, numberOfParticles);
        return true;
    }

    default boolean addRunningEffects(BlockState state, Level world, BlockPos pos, Entity entity) {
        return world.isClientSide && CustomParticleHandler.handleRunningEffects(world, pos, state, entity);
    }

    // TODO IBlockRenderProperties
//    @Override
//    @Environment(EnvType.CLIENT)
//    default boolean addHitEffects(BlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
//        return CustomParticleHandler.handleHitEffects(state, worldObj, target, manager);
//    }
//
//    @Override
//    @Environment(EnvType.CLIENT)
//    default boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
//        return CustomParticleHandler.handleDestroyEffects(world, pos, state, manager);
//    }
}
