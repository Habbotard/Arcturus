package com.eu.habbo.messages.outgoing.rooms.users;

import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class RoomUserActionComposer extends MessageComposer{

    private int action;
    private RoomUnit roomUnit;

    public RoomUserActionComposer(RoomUnit roomUnit, int action)
    {
        this.roomUnit = roomUnit;
        this.action = action;
    }

    @Override
    public ServerMessage compose()    {
        this.response.init(Outgoing.RoomUserActionComposer);
        this.response.appendInt32(this.roomUnit.getId());
        this.response.appendInt32(this.action);
        return this.response;
    }
}
