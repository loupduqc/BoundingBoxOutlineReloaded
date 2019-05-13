package com.irtimaled.bbor.common.interop;

import com.irtimaled.bbor.common.EventBus;
import com.irtimaled.bbor.common.events.*;
import com.irtimaled.bbor.common.models.Coords;
import com.irtimaled.bbor.common.models.ServerPlayer;
import com.irtimaled.bbor.config.ConfigManager;
import net.minecraft.server.v1_14_R1.*;

import java.io.File;
import java.util.Collection;

public class CommonInterop {
    public static void init() {
        ConfigManager.loadConfig(new File("."));
    }

    public static void chunkLoaded(Chunk chunk) {
        EventBus.publish(new ChunkLoaded(chunk));
    }

    public static void loadWorlds(Collection<WorldServer> worlds) {
        for (WorldServer world : worlds) {
            loadWorld(world);
        }
    }

    public static void loadWorld(WorldServer world) {
        EventBus.publish(new WorldLoaded(world));
    }

    public static void tick() {
        EventBus.publish(new ServerTick());
    }

    public static void playerLoggedIn(EntityPlayer player) {
        EventBus.publish(new PlayerLoggedIn(new ServerPlayer(player)));
    }

    public static void playerLoggedOut(EntityPlayer player) {
        EventBus.publish(new PlayerLoggedOut(player.getId()));
    }

    public static void playerSubscribed(EntityPlayer player) {
        EventBus.publish(new PlayerSubscribed(player.getId(), new ServerPlayer(player)));
    }

    public static void tryHarvestBlock(Block block, BlockPosition pos, WorldServer world) {
        if (block instanceof BlockMobSpawner) {
            EventBus.publish(new MobSpawnerBroken(world.worldProvider.getDimensionManager().getDimensionID(), new Coords(pos)));
        }
    }
}
