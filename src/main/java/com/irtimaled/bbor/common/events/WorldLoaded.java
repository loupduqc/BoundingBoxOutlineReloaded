package com.irtimaled.bbor.common.events;

import net.minecraft.server.v1_15_R1.WorldData;
import net.minecraft.server.v1_15_R1.WorldServer;

public class WorldLoaded {
    private final int dimensionId;
    private final long seed;
    private final int spawnX;
    private final int spawnZ;

    public WorldLoaded(WorldServer world) {
        WorldData info = world.getWorldData();
        this.dimensionId = world.worldProvider.getDimensionManager().getDimensionID();
        this.seed = info.getSeed();
        this.spawnX = info.b();
        this.spawnZ = info.d();
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public long getSeed() {
        return seed;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnZ() {
        return spawnZ;
    }
}
