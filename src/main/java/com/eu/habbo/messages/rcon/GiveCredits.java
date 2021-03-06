package com.eu.habbo.messages.rcon;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.google.gson.Gson;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GiveCredits extends RCONMessage<GiveCredits.JSONGiveCredits>
{
    public GiveCredits()
    {
        super(JSONGiveCredits.class);
    }

    @Override
    public void handle(JSONGiveCredits object)
    {
        Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(object.username);

        if (habbo != null)
        {
            habbo.giveCredits(object.credits);
        }
        else
        {
            PreparedStatement statement = Emulator.getDatabase().prepare("UPDATE users SET credits = credits + ? WHERE username = ? LIMIT 1");
            try
            {
                statement.setInt(1, object.credits);
                statement.setString(2, object.username);
                statement.execute();
                statement.close();
                statement.getConnection().close();
            }
            catch (SQLException e)
            {
                this.status = RCONMessage.SYSTEM_ERROR;
            }

            this.message = "offline";
        }
    }

    public class JSONGiveCredits
    {
        private String username;
        private int credits;
    }
}
