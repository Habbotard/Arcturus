package com.eu.habbo.messages.outgoing.navigator;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import java.util.Set;

public class TagsComposer extends MessageComposer
{
    private final Set<String> tags;

    public TagsComposer(Set<String> tags)
    {
        this.tags = tags;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.TagsComposer);
        this.response.appendInt32(this.tags.size());

        int i = 1;
        for(String s : this.tags)
        {
            this.response.appendString(s);
            this.response.appendInt32(i);
            i++;
        }

        return this.response;
    }
}
