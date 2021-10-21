package com.pp.cricket.CricketMatch.model;

import com.pp.cricket.CricketMatch.service.impl.BattingStat;
import com.pp.cricket.CricketMatch.service.impl.BowlingStat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by wmadane on 10/21/2021.
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
public class Player
{

    private String name;

    @EqualsAndHashCode.Include
    private String teamName;

    @EqualsAndHashCode.Include
    private int battingOrder;

    private BattingStat battingStat;
    private BowlingStat bowlingStat;

    public Player(String name, String team, int battingOrder)
    {
        this.name = name;
        this.teamName = team;
        this.battingOrder = battingOrder;
    }
    public Player()
    {

    }
}
