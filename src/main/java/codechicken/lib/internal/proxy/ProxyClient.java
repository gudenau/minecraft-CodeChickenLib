package codechicken.lib.internal.proxy;

import codechicken.lib.CodeChickenLib;
import codechicken.lib.config.ConfigCategory;
import codechicken.lib.config.ConfigSyncManager;
import codechicken.lib.internal.ExceptionMessageEventHandler;
import codechicken.lib.internal.HighlightHandler;
import codechicken.lib.model.bakery.ModelBakery;
import codechicken.lib.render.CCRenderEventHandler;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.item.map.MapRenderRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

/**
 * Created by covers1624 on 30/10/19.
 */
public class ProxyClient extends Proxy {

    public static boolean catchBlockRenderExceptions;
    public static boolean messagePlayerOnRenderExceptionCaught;

    @Override
    public void clientSetup() {
        loadClientConfig();
        //OpenGLUtils.loadCaps();
        //        CustomParticleHandler.init();
        BlockRenderingRegistry.init();
        ModelBakery.init();
        CCRenderEventHandler.init();

        MapRenderRegistry.init();
        //        ClientCommandHandler.instance.registerCommand(new CCLClientCommand());
        ClientPlayConnectionEvents.DISCONNECT.register(this::onClientDisconnected);
        ExceptionMessageEventHandler.init();
        HighlightHandler.init();
    }

    private void loadClientConfig() {
        ConfigCategory clientTag = CodeChickenLib.config.getCategory("client");
        clientTag.delete("block_renderer_dispatcher_misc");
        clientTag.delete("catchItemRenderExceptions");
        clientTag.delete("attemptRecoveryOnItemRenderException");

        catchBlockRenderExceptions = clientTag.getValue("catchBlockRenderExceptions")
                .setComment(
                        "With this enabled, CCL will catch all exceptions thrown whilst rendering blocks.",
                        "If an exception is caught, the block will not be rendered."
                )
                .setDefaultBoolean(true)
                .getBoolean();
        messagePlayerOnRenderExceptionCaught = clientTag.getValue("messagePlayerOnRenderExceptionCaught")
                .setComment(
                        "With this enabled, CCL will message the player upon an exception from rendering blocks or items.",
                        "Messages are Rate-Limited to one per 5 seconds in the event that the exception continues."
                )
                .setDefaultBoolean(true)
                .getBoolean();

        clientTag.save();
    }

    private void onClientDisconnected(ClientPacketListener handler, Minecraft client) {
        ConfigSyncManager.onClientDisconnected();
    }
}
