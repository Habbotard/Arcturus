package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.messages.outgoing.catalog.*;
import com.eu.habbo.messages.outgoing.catalog.marketplace.MarketplaceConfigComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserWhisperComposer;

public class UpdateCatalogCommand extends Command {

    public UpdateCatalogCommand()
    {
        super("cmd_update_catalogue", Emulator.getTexts().getValue("commands.keys.cmd_update_catalogue").split(";"));
    }
    @Override
    public boolean handle(GameClient gameClient, String[] params)
    {
        Emulator.getGameEnvironment().getCatalogManager().initialize();
        Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new CatalogUpdatedComposer());
        Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new CatalogModeComposer(0));
        Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new DiscountComposer());
        Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new MarketplaceConfigComposer());
        Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new GiftConfigurationComposer());
        Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new RecyclerLogicComposer());
        gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.succes.cmd_update_catalog"), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
        return true;
    }
}
