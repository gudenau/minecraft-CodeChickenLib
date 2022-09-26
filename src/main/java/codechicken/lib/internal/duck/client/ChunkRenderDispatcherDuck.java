package codechicken.lib.internal.duck.client;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

//TODO
public interface ChunkRenderDispatcherDuck {
    @NotNull
    default ModelData getModelData(@NotNull BlockPos pos) {
        return ModelData.EMPTY;
    }
}
