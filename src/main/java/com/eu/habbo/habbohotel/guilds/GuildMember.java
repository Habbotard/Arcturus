package com.eu.habbo.habbohotel.guilds;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildMember implements Comparable
{
    private int userId;
    private String username;
    private String look;
    private int joinDate;
    private GuildRank rank;

    public GuildMember(ResultSet set) throws SQLException
    {
        this.userId = set.getInt("user_id");
        this.username = set.getString("username");
        this.look = set.getString("look");
        this.joinDate = set.getInt("member_since");
        this.rank = GuildRank.getRank(set.getInt("level_id"));
    }

    public GuildMember(int user_id, String username, String look, int joinDate, int guildRank)
    {
        this.userId = user_id;
        this.username = username;
        this.look = look;
        this.joinDate = joinDate;
        this.rank = GuildRank.values()[guildRank];
    }

    public int getUserId()
    {
        return userId;
    }

    public String getUsername()
    {
        return username;
    }

    public String getLook()
    {
        return look;
    }

    public void setLook(String look)
    {
        this.look = look;
    }

    public int getJoinDate()
    {
        return joinDate;
    }

    public void setJoinDate(int joinDate)
    {
        this.joinDate = joinDate;
    }

    public GuildRank getRank()
    {
        return rank;
    }

    public void setRank(GuildRank rank)
    {
        this.rank = rank;
    }

    @Override
    public int compareTo(Object o)
    {
        return 0;
    }
}
