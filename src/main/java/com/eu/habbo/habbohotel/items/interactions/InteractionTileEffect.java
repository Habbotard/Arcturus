package com.eu.habbo.habbohotel.items.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserEffectComposer;
import gnu.trove.map.hash.THashMap;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionTileEffect extends InteractionCustomValues
{
    public static THashMap<String, String> defaultValues = new THashMap<String, String>()
    {
            {put("effectId", "0");}
    };

    public InteractionTileEffect(ResultSet set, Item baseItem) throws SQLException
    {
        super(set, baseItem, defaultValues);
    }

    public InteractionTileEffect(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells, defaultValues);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects)
    {
        return true;
    }

    @Override
    public boolean isWalkable()
    {
        return true;
    }

    @Override
    public void onWalkOn(RoomUnit roomUnit, final Room room, Object[] objects) throws Exception
    {
        super.onWalkOn(roomUnit, room, objects);

        int effectId = Integer.valueOf(this.values.get("effectId"));

        if(roomUnit.getEffectId() == effectId)
        {
            effectId = 0;
        }

        this.values.put("state", "1");
        room.updateItem(this);

        final InteractionTileEffect proxy = this;
        Emulator.getThreading().run(new Runnable()
        {
            @Override
            public void run()
            {
                proxy.values.put("state", "0");
                room.updateItem(proxy);
            }
        }, 500);

        roomUnit.setEffectId(effectId);
        room.sendComposer(new RoomUserEffectComposer(roomUnit).compose());
    }
}
