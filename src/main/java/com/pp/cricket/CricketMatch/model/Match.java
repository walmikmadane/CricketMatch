package com.pp.cricket.CricketMatch.model;

import com.pp.cricket.CricketMatch.service.impl.Inning;
import java.util.Scanner;
import lombok.Data;

/**
 * Created by wmadane on 10/21/2021.
 */

@Data
public class Match
{
    private Inning firstInning;
    private Inning secondInning;
    private MatchResult matchResult;
    private int totalOvers;
    private int noOfPlayers;
    private DeliveryContext deliveryContext;
}
