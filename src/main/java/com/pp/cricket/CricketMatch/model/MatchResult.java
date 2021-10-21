package com.pp.cricket.CricketMatch.model;

import com.pp.cricket.CricketMatch.service.impl.Inning;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wmadane on 10/21/2021.
 */

@Data
@Builder
public class MatchResult
{
    private Team winTeam;
    private Team lostTeam;
    private boolean isDraw;
    private boolean isByWickets;
    private boolean isByRuns;
    private long byRuns;
    private int byWickets;

    public void processInnings(Inning inning1, Inning inning2, int totalWickets)
    {

        if(inning1.getTotalRunScored()> inning2.getTotalRunScored())
        {
            winTeam = inning1.getBattingTeam();
            lostTeam = inning1.getBowlingTeam();
            isByRuns = true;
            byRuns = inning1.getTotalRunScored() - inning2.getTotalRunScored();
        }else if (inning2.getTotalRunScored() > inning1.getTotalRunScored())
        {
            winTeam = inning2.getBattingTeam();
            lostTeam = inning2.getBowlingTeam();
            isByWickets = true;
            byWickets = totalWickets - inning2.getWickets();
        }else
        {
            isDraw = true;
        }
    }

    public void printMatchResult()
    {

        String tmp ="";
        if (isDraw)
        {
            System.out.println("Match is drawn..");
            return;
        }
        if(isByRuns)
            tmp =""+byRuns+" Runs";
        if(isByWickets)
            tmp = ""+byWickets+" Wickets";
        System.out.println(winTeam.getName()+" won by "+tmp);
    }
}
