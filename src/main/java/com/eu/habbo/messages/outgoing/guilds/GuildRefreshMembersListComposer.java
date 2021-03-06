package com.eu.habbo.messages.outgoing.guilds;

import com.eu.habbo.habbohotel.guilds.Guild;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class GuildRefreshMembersListComposer extends MessageComposer
{
    private final Guild guild;

    public GuildRefreshMembersListComposer(Guild guild)
    {
        this.guild = guild;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.GuildRefreshMembersListComposer);
        this.response.appendInt32(this.guild.getId());
        this.response.appendInt32(0);
        return this.response;
    }
}
