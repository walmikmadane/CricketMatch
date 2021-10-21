package com.pp.cricket.CricketMatch.model;

import java.util.Comparator;

/**
 * Created by wmadane on 10/21/2021.
 */

public class PlayerComparator implements Comparator<Player>
{

    @Override
    public int compare(Player p1, Player p2)
    {
        if(p1.getBattingOrder() < p2.getBattingOrder())
        {
            return -1;
        }else if (p2.getBattingOrder() < p1.getBattingOrder())
        {
            return 1;
        }
        return 0;
    }
}
