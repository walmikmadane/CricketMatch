package com.pp.cricket.CricketMatch.service.impl;


import com.pp.cricket.CricketMatch.model.enums.Delivery;
import java.text.DecimalFormat;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wmadane on 10/21/2021.
 */

@Data
@Builder
public class BowlingStat extends PlayerStats
{
    private String overs = "0.0";
    private long validDeliveries = 0;
    private long dots = 0;
    private long runsConceeded = 0;
    private int maidenOver = 0;
    private int wickets = 0;
    private double economy = 0.0;
    private long lastBallConceeded = 0;

    @Override
    public void processDelivery(Delivery delivery)
    {
        if(validDeliveries==0 && runsConceeded==0)
        {
            doBowling = true;

        }
        if(delivery.isWicket())
        {
            validDeliveries++;
            wickets++;
            dots++;
        }else
        {
            if(delivery.isValidDelivery())
            {
                validDeliveries++;
            }
            if(delivery.getValue()!=0)
                lastBallConceeded = validDeliveries;
            else
                dots++;
            runsConceeded+=delivery.getValue();
        }
        calculateOvers();
        calculateEconomy();
        checkForMaidenOver();
    }

    private void calculateOvers()
    {
        overs = validDeliveries/6+"."+validDeliveries%6;
    }

    private void calculateEconomy()
    {
        if(validDeliveries !=0)
            economy = ((double)runsConceeded/(double)validDeliveries)*6;
    }

    private void checkForMaidenOver()
    {
        if(validDeliveries%6 ==0)
        {
            if(validDeliveries-lastBallConceeded == 6)
                maidenOver++;
        }
    }
    @Override
    public void printStat()
    {
        DecimalFormat df = new DecimalFormat("####0.00");
        if(doBowling)
            System.out.println(overs+"  "+maidenOver+"  "+dots+"  "+runsConceeded+"  "+wickets+"  "+df.format(economy));

    }
}
