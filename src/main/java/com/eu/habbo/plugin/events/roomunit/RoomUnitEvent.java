package com.eu.habbo.plugin.events.roomunit;

import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.plugin.Event;

public abstract class RoomUnitEvent extends Event
{
    /**
     * The Room this event applies to.
     */
    public final Room room;

    /**
     * The RoomUnit this event applies to.
     */
    public final RoomUnit roomUnit;

    /**
     * @param roomUnit The RoomUnit this event applies to.
     */
    public RoomUnitEvent(Room room, RoomUnit roomUnit)
    {
        this.room = room;
        this.roomUnit = roomUnit;
    }
}
