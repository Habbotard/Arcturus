package com.eu.habbo.messages.outgoing.unknown;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;

public class SnowWarsUnknownComposer extends MessageComposer
{
    @Override
    public ServerMessage compose()
    {
        this.response.init(2869);
        this.response.appendString("snowwar");
        this.response.appendInt32(0);
        this.response.appendInt32(0);
        this.response.appendInt32(0);
        this.response.appendInt32(0);
        this.response.appendBoolean(true);
        this.response.appendBoolean(true);
        this.response.appendInt32(0);
        this.response.appendInt32(0);
        this.response.appendInt32(0);
        return this.response;
    }
}
