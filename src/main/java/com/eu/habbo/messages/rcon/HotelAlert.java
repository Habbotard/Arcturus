package com.eu.habbo.messages.rcon;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.outgoing.generic.alerts.GenericAlertComposer;
import com.google.gson.Gson;

public class HotelAlert extends RCONMessage<HotelAlert.JSONHotelAlert>
{
    public HotelAlert()
    {
        super(JSONHotelAlert.class);
    }

    @Override
    public void handle(JSONHotelAlert object)
    {
        Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new GenericAlertComposer(object.message));
    }

    public class JSONHotelAlert
    {
        private String message;
    }
}
