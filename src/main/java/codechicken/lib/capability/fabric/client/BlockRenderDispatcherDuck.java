package codechicken.lib.capability.fabric.client;

import codechicken.lib.render.ModelData;
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
