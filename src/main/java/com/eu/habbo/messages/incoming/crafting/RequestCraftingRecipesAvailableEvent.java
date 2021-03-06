package com.eu.habbo.messages.incoming.crafting;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.crafting.CraftingAltar;
import com.eu.habbo.habbohotel.crafting.CraftingRecipe;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.crafting.CraftingRecipesAvailableComposer;
import gnu.trove.map.hash.THashMap;

import java.util.Map;

public class RequestCraftingRecipesAvailableEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        System.out.println(this.getClass().getName());
        int altarId = this.packet.readInt();

        HabboItem item = this.client.getHabbo().getHabboInfo().getCurrentRoom().getHabboItem(altarId);

        CraftingAltar altar = Emulator.getGameEnvironment().getCraftingManager().getAltar(item.getBaseItem());

        if (altar != null)
        {
            Map<Item, Integer> items = new THashMap<Item, Integer>();

            int count = this.packet.readInt();
            for (int i = 0; i < count; i++)
            {
                HabboItem habboItem = this.client.getHabbo().getHabboInventory().getItemsComponent().getHabboItem(this.packet.readInt());

                if (habboItem != null)
                {
                    if (!items.containsKey(habboItem.getBaseItem()))
                    {
                        items.put(habboItem.getBaseItem(), 0);
                    }

                    items.put(habboItem.getBaseItem(), items.get(habboItem.getBaseItem()) + 1);
                }
            }

            Map<CraftingRecipe, Boolean> recipes = altar.matchRecipes(items);

            boolean found = false;
            for (Map.Entry<CraftingRecipe, Boolean> set : recipes.entrySet())
            {
                if (set.getValue() && !this.client.getHabbo().getHabboStats().hasRecipe(set.getKey().getId()))
                {
                    found = true;
                    break;
                }
            }
            this.client.sendResponse(new CraftingRecipesAvailableComposer(recipes.size(), found));
        }
    }
}
