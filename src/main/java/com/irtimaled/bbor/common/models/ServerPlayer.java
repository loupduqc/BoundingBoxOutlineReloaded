package com.irtimaled.bbor.common.models;

import com.irtimaled.bbor.common.messages.PayloadBuilder;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.Packet;

import java.util.function.Consumer;

public class ServerPlayer {
    private final int dimensionId;
    private final Consumer<Packet<?>> packetConsumer;

    public ServerPlayer(EntityPlayer player) {
        this.dimensionId = player.dimension.getDimensionID();
        this.packetConsumer = player.playerConnection::sendPacket;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void sendPacket(PayloadBuilder payloadBuilder) {
        packetConsumer.accept(payloadBuilder.build());
    }
}
