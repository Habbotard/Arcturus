package com.eu.habbo.habbohotel.items.interactions.wired.conditions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionWiredCondition;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.habbohotel.wired.WiredConditionType;
import com.eu.habbo.messages.ClientMessage;
import com.eu.habbo.messages.ServerMessage;
import gnu.trove.set.hash.THashSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredConditionNotFurniTypeMatch extends InteractionWiredCondition
{
    public static final WiredConditionType type = WiredConditionType.NOT_STUFF_IS;

    private THashSet<HabboItem> items = new THashSet<HabboItem>();

    public WiredConditionNotFurniTypeMatch(ResultSet set, Item baseItem) throws SQLException
    {
        super(set, baseItem);
    }

    public WiredConditionNotFurniTypeMatch(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff)
    {
        this.refresh();

        if(stuff != null)
        {
            if(stuff.length >= 1)
            {
                if(stuff[0] instanceof HabboItem)
                {
                    HabboItem item = (HabboItem) stuff[0];

                    for(HabboItem i : this.items)
                    {
                        if(i.getBaseItem().getId() == item.getBaseItem().getId())
                            return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public String getWiredData()
    {
        this.refresh();

        String data = "";

        for(HabboItem item : this.items)
            data += item.getId() + ";";

        return data;
    }

    @Override
    public void loadWiredData(ResultSet set, Room room) throws SQLException
    {
        this.items.clear();

        String[] data = set.getString("wired_data").split(";");

        for(String s : data)
            this.items.add(room.getHabboItem(Integer.valueOf(s)));
    }

    @Override
    public void onPickUp()
    {
        this.items.clear();
    }

    @Override
    public WiredConditionType getType()
    {
        return type;
    }

    @Override
    public void serializeWiredData(ServerMessage message)
    {
        this.refresh();

        message.appendBoolean(false);
        message.appendInt32(Emulator.getConfig().getInt("hotel.wired.furni.selection.count"));
        message.appendInt32(this.items.size());

        for(HabboItem item : this.items)
            message.appendInt32(item.getId());

        message.appendInt32(this.getBaseItem().getSpriteId());
        message.appendInt32(this.getId());
        message.appendString("");
        message.appendInt32(0);
        message.appendInt32(0);
        message.appendInt32(this.getType().code);
        message.appendInt32(0);
        message.appendInt32(0);
    }

    @Override
    public boolean saveData(ClientMessage packet)
    {
        this.items.clear();

        packet.readInt();
        packet.readString();

        int count = packet.readInt();

        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(this.getRoomId());

        if(room != null)
        {
            for (int i = 0; i < count; i++)
            {
                this.items.add(room.getHabboItem(packet.readInt()));
            }
        }

        return true;
    }

    private void refresh()
    {
        THashSet<HabboItem> items = new THashSet<HabboItem>();

        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(this.getRoomId());
        if(room == null)
        {
            items.addAll(this.items);
        }
        else
        {
            for (HabboItem item : this.items)
            {
                if (room.getHabboItem(item.getId()) == null)
                    items.add(item);
            }
        }

        for(HabboItem item : items)
        {
            this.items.remove(item);
        }
    }
}
