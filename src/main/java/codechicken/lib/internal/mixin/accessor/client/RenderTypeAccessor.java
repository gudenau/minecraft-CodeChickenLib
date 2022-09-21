package codechicken.lib.internal.mixin.accessor.client;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(RenderType.class)
public interface RenderTypeAccessor {
    @Invoker static RenderType.CompositeRenderType invokeCreate(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, RenderType.CompositeState compositeState) { throw new AssertionError(); }
    @Invoker static RenderType.CompositeRenderType invokeCreate(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, RenderType.CompositeState compositeState) { throw new AssertionError(); }
}
