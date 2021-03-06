package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserWhisperComposer;
import com.eu.habbo.messages.outgoing.users.UserCreditsComposer;

import java.util.Collection;
import java.util.Map;

public class MassCreditsCommand extends Command
{
    public MassCreditsCommand()
    {
        super("cmd_masscredits", Emulator.getTexts().getValue("commands.keys.cmd_masscredits").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception
    {
        if(params.length == 2)
        {
            int amount;

            try
            {
                amount = Integer.valueOf(params[1]);
            }
            catch (Exception e)
            {
                gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.error.cmd_masscredits.invalid_amount"), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                return true;
            }

            if(amount != 0)
            {
                for(Map.Entry<Integer, Habbo> set : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet())
                {
                    Habbo habbo = set.getValue();

                    habbo.getHabboInfo().addCredits(amount);
                    habbo.getClient().sendResponse(new UserCreditsComposer(habbo));

                    if(habbo.getHabboInfo().getCurrentRoom() != null)
                        habbo.getClient().sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.generic.cmd_credits.received").replace("%amount%", amount + ""), habbo, habbo, RoomChatMessageBubbles.ALERT)));
                }
            }
            return true;
        }
        gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.error.cmd_masscredits.invalid_amount"), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
        return true;
    }
}
