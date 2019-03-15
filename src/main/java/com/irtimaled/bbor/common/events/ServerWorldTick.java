package com.irtimaled.bbor.common.events;

import net.minecraft.server.v1_13_R2.WorldServer;

public class ServerWorldTick {
    private final int dimensionId;
    private WorldServer world;

    public ServerWorldTick(WorldServer world) {
        this.world = world;
        this.dimensionId = world.dimension.getDimensionID();
    }

    public WorldServer getWorld() {
        return world;
    }

    public int getDimensionId() {
        return dimensionId;
    }
}
