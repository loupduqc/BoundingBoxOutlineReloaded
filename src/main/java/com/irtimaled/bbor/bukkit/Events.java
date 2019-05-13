package com.irtimaled.bbor.bukkit;

import com.irtimaled.bbor.common.interop.CommonInterop;
import com.irtimaled.bbor.common.messages.SubscribeToServer;
import net.minecraft.server.v1_14_R1.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Events implements Listener, PluginMessageListener {
    private boolean active;

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (!active) return;

        Chunk chunk = event.getChunk();
        if (chunk instanceof CraftChunk) {
            CommonInterop.chunkLoaded(((CraftChunk) chunk).getHandle());
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if (!active) return;

        World world = event.getWorld();
        if (world instanceof CraftWorld) {
            CommonInterop.loadWorld(((CraftWorld) world).getHandle());
        }
    }

    @EventHandler
    public void onPlayerLoggedIn(PlayerJoinEvent event) {
        if (!active) return;

        Player player = event.getPlayer();
        if (player instanceof CraftPlayer) {
            CommonInterop.playerLoggedIn(((CraftPlayer) player).getHandle());
        }
    }

    @EventHandler
    public void onPlayerLoggedOut(PlayerQuitEvent event) {
        if (!active) return;

        Player player = event.getPlayer();
        if (player instanceof CraftPlayer) {
            CommonInterop.playerLoggedOut(((CraftPlayer) player).getHandle());
        }
    }

    void enable() {
        this.active = true;
    }

    void disable() {
        this.active = false;
    }

    void onTick() {
        if (!active) return;

        CommonInterop.tick();
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!active) return;

        if (s.equals(SubscribeToServer.NAME) && player instanceof CraftPlayer) {
            CommonInterop.playerSubscribed(((CraftPlayer) player).getHandle());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!active) return;

        Block block = event.getBlock();
        if (!(block instanceof CraftBlock)) return;
        World world = block.getWorld();
        if (!(world instanceof CraftWorld)) return;

        WorldServer worldServer = ((CraftWorld) world).getHandle();
        CraftBlock craftBlock = (CraftBlock) block;
        CommonInterop.tryHarvestBlock(craftBlock.getNMS().getBlock(), craftBlock.getPosition(), worldServer);
    }
}
