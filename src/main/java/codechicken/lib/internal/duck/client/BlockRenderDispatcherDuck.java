package codechicken.lib.internal.duck.client;

import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

//TODO
public interface BlockRenderDispatcherDuck {
    default void setModelData(@NotNull ModelData data) {}
    
    @NotNull
    default ModelData getModelData() {
        return ModelData.EMPTY;
    }
    
    default void setRenderType(@NotNull RenderType type) {}
    
    @NotNull
    default RenderType getRenderType() {
        return RenderType.cutout();
    }
}
