package com.irtimaled.bbor.common.events;

import net.minecraft.server.v1_13_R2.Chunk;
import net.minecraft.server.v1_13_R2.WorldServer;

public class ChunkLoaded {
    private final Chunk chunk;
    private final int dimensionId;

    public ChunkLoaded(Chunk chunk) {
        this.chunk = chunk;
        this.dimensionId = ((WorldServer) chunk.getWorld()).dimension.getDimensionID();
    }

    public Chunk getChunk() {
        return chunk;
    }

    public int getDimensionId() {
        return dimensionId;
    }
}
