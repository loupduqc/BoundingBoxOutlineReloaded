package com.irtimaled.bbor.common.events;

import net.minecraft.server.v1_15_R1.Chunk;
import net.minecraft.server.v1_15_R1.WorldServer;

public class ChunkLoaded {
    private final Chunk chunk;
    private final int dimensionId;

    public ChunkLoaded(Chunk chunk) {
        this.chunk = chunk;
        this.dimensionId = ((WorldServer) chunk.getWorld()).worldProvider.getDimensionManager().getDimensionID();
    }

    public Chunk getChunk() {
        return chunk;
    }

    public int getDimensionId() {
        return dimensionId;
    }
}
