package codechicken.lib.render;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;

public class CCRenderEventHandler {
    private static final CCRenderEventHandler INSTANCE = new CCRenderEventHandler();
    
    public static int renderTime;
    public static float renderFrame;

    private static boolean hasInit = false;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            ClientTickEvents.END_CLIENT_TICK.register(INSTANCE::clientTick);
            WorldRenderEvents.START.register(INSTANCE::renderTick);
        }
    }

    public void clientTick(Minecraft minecraft) {
        renderTime++;
    }

    public void renderTick(WorldRenderContext context) {
        renderFrame = this.f_91012_ ? this.f_91013_ : this.f_90991_.f_92518_;
    }

    /*TODO
    public void onBlockHighlight(RenderHighlightEvent.Block event) {
        //We have found a CuboidRayTraceResult, Lets render it properly..
        BlockHitResult hit = event.getTarget();
        if (hit instanceof VoxelShapeBlockHitResult voxelHit) {
            event.setCanceled(true);
            Matrix4 mat = new Matrix4(event.getPoseStack());
            mat.translate(voxelHit.getBlockPos());
            RenderUtils.bufferShapeHitBox(mat, event.getMultiBufferSource(), event.getCamera(), voxelHit.shape);
        }
    }
     */
}
