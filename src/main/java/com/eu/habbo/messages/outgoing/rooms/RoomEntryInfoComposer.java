package com.eu.habbo.messages.outgoing.rooms;

import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class RoomEntryInfoComposer extends MessageComposer
{
    private final Room room;

    public RoomEntryInfoComposer(Room room)
    {
        this.room = room;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.RoomEntryInfoComposer);
        this.response.appendInt32(this.room.getId());
        this.response.appendString(this.room.getOwnerName());
        return this.response;
    }
}
