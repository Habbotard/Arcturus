package com.eu.habbo.messages.outgoing.rooms.pets;

import com.eu.habbo.habbohotel.pets.Pet;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class RoomPetExperienceComposer extends MessageComposer
{
    private final Pet pet;
    private final int amount;

    public RoomPetExperienceComposer(Pet pet, int amount)
    {
        this.pet = pet;
        this.amount = amount;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.RoomPetExperienceComposer);
        this.response.appendInt32(this.pet.getId());
        this.response.appendInt32(this.pet.getRoomUnit().getId());
        this.response.appendInt32(this.amount);
        return this.response;
    }
}
