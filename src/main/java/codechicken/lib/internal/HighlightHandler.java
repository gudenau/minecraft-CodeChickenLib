package codechicken.lib.internal;

import codechicken.lib.colour.EnumColour;
import codechicken.lib.internal.mixin.accessor.client.RenderTypeAccessor;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.render.buffer.TransformingVertexConsumer;
import codechicken.lib.vec.Cuboid6;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.client.renderer.RenderStateShard.*;

/**
 * Created by covers1624 on 9/06/18.
 */
public class HighlightHandler {

    private static final Cuboid6 BOX = Cuboid6.full.copy().expand(0.02);
    private static final float[] RED = EnumColour.RED.getColour(128).packArray();

    @Nullable
    public static BlockPos highlight;
    public static boolean useDepth = true;

    private static final RenderType box = RenderTypeAccessor.invokeCreate("ccl:box_depth", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder()
            .setShaderState(POSITION_COLOR_SHADER)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .createCompositeState(false)
    );

    private static final DepthTestStateShard DISABLE_DEPTH = new DepthTestStateShard("none", 519) {
        @Override
        public void setupRenderState() {
            RenderSystem.disableDepthTest();
        }
    };

    private static final RenderType boxNoDepth = RenderTypeAccessor.invokeCreate("ccl:box_no_depth", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder()
            .setShaderState(POSITION_COLOR_SHADER)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .setDepthTestState(DISABLE_DEPTH)
            .createCompositeState(false)
    );
    
    public static void renderLevelLast(WorldRenderContext context) {
        if (highlight != null) {
            MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
            Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            Vec3 cameraPos = camera.getPosition();
            PoseStack pStack = context.matrixStack();
            pStack.pushPose();

            pStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
            pStack.translate(highlight.getX(), highlight.getY(), highlight.getZ());

            RenderUtils.bufferCuboidSolid(
                    new TransformingVertexConsumer(source.getBuffer(useDepth ? box : boxNoDepth), pStack),
                    BOX,
                    RED[0], RED[1], RED[2], RED[3]
            );

            source.endBatch();

            pStack.popPose();
        }
    }
    
    public static void init() {
        WorldRenderEvents.END.register(HighlightHandler::renderLevelLast);
    }
}
