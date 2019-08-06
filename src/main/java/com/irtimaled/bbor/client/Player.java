package com.irtimaled.bbor.client;

import com.irtimaled.bbor.client.models.Point;
import com.irtimaled.bbor.common.models.Coords;
import net.minecraft.client.network.ClientPlayerEntity;

public class Player {
    private static double x;
    private static double y;
    private static double z;
    private static int dimensionId;

    public static void setPosition(double partialTicks, ClientPlayerEntity player) {
        x = player.prevRenderX + (player.x - player.prevRenderX) * partialTicks;
        y = player.prevRenderY + (player.y - player.prevRenderY) * partialTicks;
        z = player.prevRenderZ + (player.z - player.prevRenderZ) * partialTicks;
        dimensionId = player.dimension.getRawId();
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

    public static int getDimensionId() {
        return dimensionId;
    }

    public static Coords getCoords() {
        return new Coords(x, y, z);
    }

    public static Point getPoint() {
        return new Point(x, y, z);
    }
}
