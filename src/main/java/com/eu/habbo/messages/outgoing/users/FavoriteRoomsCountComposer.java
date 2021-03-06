package com.eu.habbo.messages.outgoing.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import gnu.trove.procedure.TIntProcedure;

public class FavoriteRoomsCountComposer extends MessageComposer
{
    private final Habbo habbo;

    public FavoriteRoomsCountComposer(Habbo habbo)
    {
        this.habbo = habbo;
    }

    @Override
    public ServerMessage compose() {
        this.response.init(Outgoing.FavoriteRoomsCountComposer);

        this.response.appendInt32(Emulator.getConfig().getInt("hotel.rooms.max.favorite"));

        this.response.appendInt32(this.habbo.getHabboStats().getFavoriteRooms().size());
        this.habbo.getHabboStats().getFavoriteRooms().forEach(new TIntProcedure()
        {
            @Override
            public boolean execute(int value)
            {
                response.appendInt32(value);
                return true;
            }
        });

        return this.response;
    }
}
