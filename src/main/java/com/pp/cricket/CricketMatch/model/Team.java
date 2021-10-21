package com.pp.cricket.CricketMatch.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wmadane on 10/21/2021.
 */


@Data
@Builder
public class Team
{
    private String name;
    private int noOfPlayers;
    private PriorityQueue<Player> battingOrder = new PriorityQueue<Player>(new PlayerComparator());
    private TreeSet batsman = new TreeSet<Player>(new PlayerComparator());
    private TreeSet bowler = new TreeSet<Player>(new PlayerComparator());


    public void addPlayer(Player player)
    {
        battingOrder.add(player);
        batsman.add(player);
        noOfPlayers++;
    }

    public Player getplayerByName(String name)
    {
        Iterator<Player> playerIterator = this.getBatsman().iterator();
        while (playerIterator.hasNext())
        {
            Player player = playerIterator.next();
            if(player.getName().equals(name))
                return player;
        }
        return null;
    }

}
