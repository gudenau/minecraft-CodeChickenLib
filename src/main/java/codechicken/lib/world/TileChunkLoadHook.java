package codechicken.lib.world;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;

public class TileChunkLoadHook {

    private static boolean init;

    public static void init() {
        if (init) {
            return;
        }
        init = true;
    
        ServerChunkEvents.CHUNK_LOAD.register(new TileChunkLoadHook()::onChunkLoad);
    }
    
    public void onChunkLoad(ServerLevel world, LevelChunk chunkArg) {
        ChunkAccess iChunk = chunkArg;
        LevelChunk chunk;
        if (iChunk instanceof LevelChunk) {
            chunk = (LevelChunk) iChunk;
        } else if (iChunk instanceof ImposterProtoChunk) {
            chunk = ((ImposterProtoChunk) iChunk).getWrapped();
        } else {
            return;
        }

        for (BlockEntity tile : chunk.getBlockEntities().values()) {
            if (tile instanceof IChunkLoadTile) {
                ((IChunkLoadTile) tile).onChunkLoad(chunk);
            }
        }
    }
}
