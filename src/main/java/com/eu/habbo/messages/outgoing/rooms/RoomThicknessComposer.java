package com.eu.habbo.messages.outgoing.rooms;

import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class RoomThicknessComposer extends MessageComposer{

    private final Room room;

    public RoomThicknessComposer(Room room)
    {
        this.room = room;
    }

    @Override
    public ServerMessage compose()    {
        this.response.init(Outgoing.RoomThicknessComposer);
        this.response.appendBoolean(this.room.isHideWall());
        this.response.appendInt32(this.room.getWallSize());
        this.response.appendInt32(this.room.getFloorSize());
        return this.response;
    }
}
