package com.irtimaled.bbor.client;

import net.minecraft.entity.player.PlayerEntity;

public class PlayerCoords {
    private static double x;
    private static double y;
    private static double z;
    private static double activeY;

    public static void setPlayerPosition(double partialTicks, PlayerEntity entityPlayer) {
        x = entityPlayer.prevRenderX + (entityPlayer.x - entityPlayer.prevRenderX) * partialTicks;
        y = entityPlayer.prevRenderY + (entityPlayer.y - entityPlayer.prevRenderY) * partialTicks;
        z = entityPlayer.prevRenderZ + (entityPlayer.z - entityPlayer.prevRenderZ) * partialTicks;
    }

    static void setActiveY() {
        activeY = y;
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static double getZ() {
        return z;
    }

    public static double getMaxY(double configMaxY) {
        if (configMaxY == -1) {
            return activeY;
        }
        if (configMaxY == 0) {
            return y;
        }
        return configMaxY;
    }
}
