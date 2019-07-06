package com.irtimaled.bbor.forge;

import com.irtimaled.bbor.client.ClientProxy;
import com.irtimaled.bbor.client.gui.SettingsScreen;
import com.irtimaled.bbor.client.interop.ClientInterop;
import com.irtimaled.bbor.client.keyboard.KeyListener;
import com.irtimaled.bbor.common.EventBus;
import com.irtimaled.bbor.common.messages.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.commons.lang3.ArrayUtils;

public class ForgeClientProxy extends ForgeCommonProxy {
    @Override
    void init() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, lastScreen) -> new SettingsScreen(lastScreen, 0));
        registerMessageConsumers();
        new ClientProxy().init();
        Minecraft.getInstance().gameSettings.keyBindings = ArrayUtils.addAll(Minecraft.getInstance().gameSettings.keyBindings, KeyListener.keyBindings());
    }

    private void initializeClient(PayloadReader payload) {
        EventBus.publish(InitializeClient.getEvent(payload));
        Minecraft.getInstance().getConnection().sendPacket(SubscribeToServer.getPayload().build());
    }

    @Override
    void registerMessageConsumers() {
        super.registerMessageConsumers();
        ForgeNetworkHelper.addBusEventConsumer(AddBoundingBox.NAME, AddBoundingBox::getEvent);
        ForgeNetworkHelper.addBusEventConsumer(RemoveBoundingBox.NAME, RemoveBoundingBox::getEvent);
        ForgeNetworkHelper.addClientConsumer(InitializeClient.NAME, this::initializeClient);
    }

    @SubscribeEvent
    public void loggedOutEvent(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        ClientInterop.disconnectedFromRemoteServer();
    }

    @SubscribeEvent
    public void clientChatEvent(ClientChatEvent event) {
        if (ClientInterop.interceptChatMessage(event.getMessage()))
            event.setMessage("");
    }

    @SubscribeEvent
    public void clientChatReceivedEvent(ClientChatReceivedEvent event) {
        ClientInterop.handleSeedMessage(event.getMessage());
    }
}
