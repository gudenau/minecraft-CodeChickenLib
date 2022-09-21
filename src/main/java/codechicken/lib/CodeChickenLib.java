package codechicken.lib;

import codechicken.lib.config.ConfigCategory;
import codechicken.lib.config.ConfigFile;
import codechicken.lib.internal.command.CCLCommands;
import codechicken.lib.internal.network.CCLNetwork;
import codechicken.lib.internal.proxy.Proxy;
import codechicken.lib.internal.proxy.ProxyClient;
import net.fabricmc.api.*;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Paths;
import java.util.function.Supplier;

/**
 * Created by covers1624 on 12/10/2016.
 */
@EnvironmentInterface(value = EnvType.CLIENT, itf = ClientModInitializer.class)
@EnvironmentInterface(value = EnvType.SERVER, itf = DedicatedServerModInitializer.class)
public class CodeChickenLib implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer {

    public static final String MOD_ID = "codechickenlib";

    public static ConfigCategory config;

    public static Proxy proxy;

    public CodeChickenLib() {
        config = new ConfigFile(MOD_ID)
                .path(Paths.get("config/ccl.cfg"))
                .load();
        
        proxy = (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ?
            (Supplier<Supplier<Proxy>>)() -> ProxyClient::new :
            (Supplier<Supplier<Proxy>>)() -> Proxy::new
        ).get().get();
        CCLCommands.init();
    }
    
    @Override
    public void onInitialize() {
        proxy.commonSetup();
        CCLNetwork.init();
    }
    
    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
        proxy.clientSetup();
    }
    
    @Environment(EnvType.SERVER)
    @Override
    public void onInitializeServer() {
        proxy.serverSetup();
    }
}
