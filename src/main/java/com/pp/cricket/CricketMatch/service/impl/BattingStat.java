package com.pp.cricket.CricketMatch.service.impl;

import com.pp.cricket.CricketMatch.model.enums.Delivery;
import com.pp.cricket.CricketMatch.service.DeliveryService;
import java.text.DecimalFormat;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wmadane on 10/21/2021.
 */

@Data
@Builder
public class BattingStat extends PlayerStats
{
    private long runsScored = 0;
    private long ballsPlayed = 0;
    private double strikeRate = 0.0;
    private int noOfFour = 0;
    private int noOfSix = 0;

    private  boolean isOut;

    @Override
    public void printStat()
    {
        DecimalFormat df = new DecimalFormat("####0.00");
        if(doBatting)
        {
            System.out.println(runsScored+"  "+ballsPlayed+"  "+noOfFour+"  "+noOfSix+"  "+df.format(strikeRate));
        }else{
            System.out.println("Did Not Bat");
        }

    }

    @Override
    public void processDelivery(Delivery delivery)
    {
        if(ballsPlayed ==0)
        {
            // Playing first ball
            doBatting = true;
        }
        if(delivery.isWicket())
        {
            ballsPlayed++;
            isOut = true;
            strikeRate = ((double)runsScored/(double)ballsPlayed)*100;
        }else
        {
            if(delivery.isValidDelivery())
            {
                ballsPlayed++;
            }

            if(delivery.getValue()==4)
                noOfFour++;
            if(delivery.getValue()==6)
                noOfSix++;

            runsScored+=delivery.getValue();
            if(delivery.getText().equals("wd") || delivery.getText().equals("nb"))
                runsScored-=delivery.getValue();

            if(ballsPlayed!=0)
                strikeRate = ((double)runsScored/(double)ballsPlayed)*100;
        }
    }

    public void proceessDeliveryForRunout(Delivery delivery)
    {
        isOut = true;
    }
}
