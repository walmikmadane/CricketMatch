package com.pp.cricket.CricketMatch.service.impl;

import com.pp.cricket.CricketMatch.model.Player;
import com.pp.cricket.CricketMatch.model.Team;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;

/**
 * Created by wmadane on 10/21/2021.
 */

public class InputService
{

    public static boolean validateBowlerInput(String bowlerName, Team bowlingTeam)
    {
        Iterator<Player> playerIterator = bowlingTeam.getBatsman().iterator();
        while (playerIterator.hasNext())
        {
            if(playerIterator.next().getName().equals(bowlerName))
                return true;
        }
        return false;
    }
}
