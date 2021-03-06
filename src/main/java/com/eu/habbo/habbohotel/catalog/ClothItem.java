package com.eu.habbo.habbohotel.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClothItem
{
    /**
     * Identifier.
     */
    public int id;

    /**
     * Name
     */
    public String name;

    /**
     * Set
     */
    public int[] setId;

    public ClothItem(ResultSet set) throws SQLException
    {
        this.id = set.getInt("id");
        this.name = set.getString("name");
        String[] parts = set.getString("setid").split(",");

        setId = new int[parts.length];
        for (int i = 0; i < setId.length; i++)
        {
            setId[i] = Integer.valueOf(parts[i]);
        }
    }
}
