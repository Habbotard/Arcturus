package com.eu.habbo.messages.outgoing.crafting;

import com.eu.habbo.habbohotel.crafting.CraftingRecipe;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

import java.util.Map;

public class CraftingRecipeComposer extends MessageComposer
{
    private final CraftingRecipe recipe;

    public CraftingRecipeComposer(CraftingRecipe recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.CraftingRecipeComposer);
        this.response.appendInt32(this.recipe.getIngredients().size());

        for (Map.Entry<Item, Integer> ingredient : this.recipe.getIngredients().entrySet())
        {
            this.response.appendInt32(ingredient.getValue());
            this.response.appendString(ingredient.getKey().getName());
        }
        return this.response;
    }
}
