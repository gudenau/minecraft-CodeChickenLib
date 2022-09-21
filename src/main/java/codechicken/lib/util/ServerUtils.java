package codechicken.lib.util;

import codechicken.lib.data.MCDataOutput;
import codechicken.lib.data.MCDataPacket;
import codechicken.lib.internal.mixin.accessor.GameProfileCacheAccessor;
import codechicken.lib.internal.mixin.accessor.MinecraftServerAccessor;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Created by covers1624 on 22/10/2016.
 */
public class ServerUtils {
    private static MinecraftServer server;
    
    static {
        ServerLifecycleEvents.SERVER_STARTING.register((serv) -> server = serv);
        ServerLifecycleEvents.SERVER_STOPPED.register((serv) -> server = null);
    }
    
    @Deprecated
    public static MinecraftServer getServer() {
        return server;
    }

    @Deprecated
    public static ServerPlayer getPlayer(String playername) {
        return getServer().getPlayerList().getPlayerByName(playername);
    }

    public static List<ServerPlayer> getPlayers() {
        return getServer().getPlayerList().getPlayers();
    }

    public static boolean isPlayerLoadingChunk(ServerPlayer player, ChunkPos chunk) {
        return player.getLevel().getChunkSource().chunkMap.getPlayers(chunk, false).stream().anyMatch(e -> e.getId() == player.getId());
    }

    public static Path getSaveDirectory() {
        return getSaveDirectory(Level.OVERWORLD);
    }

    public static Path getSaveDirectory(ResourceKey<Level> dimension) {
        return ((MinecraftServerAccessor) getServer()).getStorageSource().getDimensionPath(dimension);
    }

    public static GameProfile getGameProfile(String username) {
        Player player = getPlayer(username);
        if (player != null) {
            return player.getGameProfile();
        }

        //try and access it in the cache without forcing a save
        username = username.toLowerCase(Locale.ROOT);
        GameProfileCache.GameProfileInfo cachedEntry = ((GameProfileCacheAccessor) getServer().getProfileCache()).getProfilesByName().get(username);
        if (cachedEntry != null) {
            return cachedEntry.getProfile();
        }

        //load it from the cache
        return getServer().getProfileCache().get(username).orElse(null);
    }

    public static boolean isPlayerOP(UUID uuid) {
        GameProfile profile = getServer().getProfileCache().get(uuid).orElse(null);
        return profile != null && getServer().getPlayerList().isOp(profile);
    }

    public static boolean isPlayerOP(String username) {
        GameProfile prof = getGameProfile(username);
        return prof != null && getServer().getPlayerList().isOp(prof);
    }

    public static void openContainer(ServerPlayer player, MenuProvider containerProvider) {
        openContainer(player, containerProvider, e -> {
        });
    }

    public static void openContainer(ServerPlayer player, MenuProvider containerProvider, Consumer<MCDataOutput> packetConsumer) {
        if (player.level.isClientSide()) {
            return;
        }
        
        var buffer = PacketByteBufs.create();
        player.openMenu(new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                packetConsumer.accept(new MCDataPacket(buffer));
            }
    
            @Override
            public Component getDisplayName() {
                return containerProvider.getDisplayName();
            }
    
            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return containerProvider.createMenu(i, inventory, player);
            }
        });
        /*
        player.doCloseContainer();
        player.nextContainerCounter();
        int containerId = player.containerCounter;

        AbstractContainerMenu container = containerProvider.createMenu(containerId, player.getInventory(), player);
        MenuType<?> type = container.getType();

        PacketCustom packet = new PacketCustom(CCLNetwork.NET_CHANNEL, C_OPEN_CONTAINER);
        packet.writeRegistryIdDirect(Registry.MENU, type);
        packet.writeVarInt(containerId);
        packet.writeTextComponent(containerProvider.getDisplayName());
        packetConsumer.accept(packet);

        packet.sendToPlayer(player);
        player.containerMenu = container;
        player.initMenu(player.containerMenu);
        MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, container));
         */
    }
    
    public static void init() {}
}
