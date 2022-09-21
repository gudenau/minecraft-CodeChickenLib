package codechicken.lib.internal;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by covers1624 on 25/07/18.
 */
@Environment(EnvType.CLIENT)
public class ExceptionMessageEventHandler {

    public static Set<String> exceptionMessageCache = new HashSet<>();
    private static long lastExceptionClear;

    public static void clientTick(Minecraft client) {
        { // Minimizes changes
            //Clear the known exceptions every 5 seconds.
            long time = System.nanoTime();
            if (TimeUnit.NANOSECONDS.toSeconds(time - lastExceptionClear) > 5) {
                lastExceptionClear = time;
                exceptionMessageCache.clear();
            }
        }
    }
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(ExceptionMessageEventHandler::clientTick);
    }
}
