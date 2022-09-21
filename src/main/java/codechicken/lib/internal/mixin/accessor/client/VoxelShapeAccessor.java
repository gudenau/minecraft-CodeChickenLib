package codechicken.lib.internal.mixin.accessor.client;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(VoxelShapeAccessor.class)
public interface VoxelShapeAccessor {
    @Accessor DiscreteVoxelShape getShape();
    
    @Invoker DoubleList invokeGetCoords(Direction.Axis var1);
}
