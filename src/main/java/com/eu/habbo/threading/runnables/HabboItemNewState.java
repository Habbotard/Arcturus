package com.eu.habbo.threading.runnables;

import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.rooms.items.FloorItemUpdateComposer;

public class HabboItemNewState implements Runnable
{
    private final HabboItem item;
    private final Room room;
    private final String state;

    public HabboItemNewState(HabboItem item, Room room, String state)
    {
        this.item = item;
        this.room = room;
        this.state = state;
    }

    @Override
    public void run()
    {
        this.item.setExtradata(this.state);

        if(this.item.getRoomId() == this.room.getId())
        {
            this.room.updateItem(this.item);
        }
    }
}
