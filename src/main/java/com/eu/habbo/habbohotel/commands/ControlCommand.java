package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserWhisperComposer;

public class ControlCommand extends Command
{
    public ControlCommand()
    {
        super("cmd_control", Emulator.getTexts().getValue("commands.keys.cmd_control").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception
    {
        if(gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null)
        {
            if(params.length == 2)
            {
                Habbo target = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);

                if(target == null)
                {
                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.error.cmd_control.not_found").replace("%user%", params[1]), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                    return true;
                }

                if(target == gameClient.getHabbo())
                {
                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.error.cmd_control.not_self"), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                    return true;
                }

                Habbo oldHabbo = (Habbo)gameClient.getHabbo().getRoomUnit().getCacheable().remove("control");

                if(oldHabbo != null)
                {
                    oldHabbo.getRoomUnit().getCacheable().remove("controller");
                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.succes.cmd_control.stopped").replace("%user%", oldHabbo.getHabboInfo().getUsername()), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                }
                gameClient.getHabbo().getRoomUnit().getCacheable().put("control", target);
                target.getRoomUnit().getCacheable().put("controller", gameClient.getHabbo());
                gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.succes.cmd_control.controlling").replace("%user%", params[1]), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                return true;
            }
            else
            {
                Object habbo = gameClient.getHabbo().getRoomUnit().getCacheable().get("control");

                if(habbo != null)
                {
                    gameClient.getHabbo().getRoomUnit().getCacheable().remove("control");

                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.succes.cmd_control.stopped").replace("%user%", ((Habbo)habbo).getHabboInfo().getUsername()), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                }
                return true;
            }
        }

        return true;
    }
}
