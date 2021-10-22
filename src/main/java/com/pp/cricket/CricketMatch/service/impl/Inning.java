package com.pp.cricket.CricketMatch.service.impl;

import com.pp.cricket.CricketMatch.model.DeliveryContext;
import com.pp.cricket.CricketMatch.model.Player;
import com.pp.cricket.CricketMatch.model.Team;
import com.pp.cricket.CricketMatch.model.enums.Delivery;
import com.pp.cricket.CricketMatch.service.InningService;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wmadane on 10/21/2021.
 */

@Data
@Builder
public class Inning implements InningService
{

    private Team battingTeam;
    private Team bowlingTeam;

    private long totalRunScored;
    private int wickets;
    private String overs;
    private long validDeliveries;
    private long extraRuns;

    private Scanner scanner;


    private static InputService inputService = new InputService();

    @Override
    public void processDelivery(Delivery delivery, DeliveryContext deliveryContext)
    {
        Player player = null;
        if(delivery.isRunOut())
        {
             player = getOutBatsman(delivery, deliveryContext);
        }
        // process delivery for batsman
        deliveryContext.getOnStrike().getBattingStat().processDelivery(delivery);

        // process delivery for bowler
        deliveryContext.getBowler().getBowlingStat().processDelivery(delivery);

        // Process Delivery for Run-out for batsman
        if(Objects.nonNull(player))
        {
            if(player.getName().equals(deliveryContext.getOnStrike().getName()))
                deliveryContext.getOnStrike().getBattingStat().proceessDeliveryForRunout(delivery);

            if(player.getName().equals(deliveryContext.getNonStrike().getName()))
                deliveryContext.getNonStrike().getBattingStat().proceessDeliveryForRunout(delivery);
        }


        //process delivery for match stats..

        if (delivery.isWicket() || delivery.isRunOut())
        {
            wickets++;
            validDeliveries++;
            if(delivery.isRunOut())
                totalRunScored+=delivery.getValue();
        } else
        {
            if (delivery.isValidDelivery())
            {
                validDeliveries++;
            }

            totalRunScored += delivery.getValue();
            if (delivery.getText().equals("nb") || delivery.getText().equals("wd"))
            {
                extraRuns++;
            }
        }
        calculateOvers();
       /* if(overCompleted(delivery))
        {
            printInningStat();
        }*/

    }

    public Player getOutBatsman(Delivery delivery, DeliveryContext deliveryContext)
    {
        switch (delivery.getValue())
        {
            case 0:
                return deliveryContext.getNonStrike();
            case 1:
                return deliveryContext.getOnStrike();
            case 2:
                return deliveryContext.getNonStrike();
            case 3:
                return deliveryContext.getOnStrike();
            default:
                return null;
        }
    }

    public void calculateOvers()
    {
        overs = validDeliveries / 6 + "." + validDeliveries % 6;
    }

    @Override
    public void printInningStat(DeliveryContext deliveryContext)
    {
        System.out.println("Batter Team:" + battingTeam.getName());
        printBattingTeamStats(deliveryContext);
        System.out.println("Total Runs:" + totalRunScored);
        System.out.println("Total Wickets:" + wickets);
        System.out.println("Extras:" + extraRuns);
        printBowlingTeamStats();
    }

    @Override
    public void printBattingTeamStats(
        DeliveryContext deliveryContext)
    {
        Iterator<Player> iterator = battingTeam.getBatsman().iterator();
        System.out.println("Batsman  Run  balls  4s  6s  SR.");
        while (iterator.hasNext())
        {
            Player player = iterator.next();
            boolean onstrike = false;
            if (Objects.nonNull(deliveryContext) && deliveryContext.getOnStrike().getName()
                .equals(player.getName()))
            {
                onstrike = true;
            }
            System.out.print(player.getName());
            if (onstrike)
            {
                System.out.print("*");
            }
            System.out.print("  ");
            player.getBattingStat().printStat();
        }
    }

    @Override
    public void printBowlingTeamStats()
    {
        Iterator<Player> iterator = bowlingTeam.getBowler().iterator();
        System.out.println("Bowler  Overs  Maiden  Dots  Run  Wickets  Eco.");
        while (iterator.hasNext())
        {
            Player player = iterator.next();
            if (player.getBowlingStat().doBowling)
            {
                System.out.print(player.getName() + "    ");
                player.getBowlingStat().printStat();
            }
        }
    }

    @Override
    public void printOverStat()
    {
        System.out
            .println("Overs:" + overs + "  Total Runs:" + totalRunScored + "  Wickets:" + wickets);
        printBattingTeamStats(null);
    }

    @Override
    public DeliveryContext getNextDeliveryContext(Delivery delivery,
        DeliveryContext deliveryContext,
        int totalMatchOvers, int totalWickets, boolean secondInning,
        Inning firstInning)
    {
        if (wickets >= totalWickets - 1)
        {
            return null;
        }

        if (secondInning && totalRunScored > firstInning.getTotalRunScored())
        {
            return null;
        }

        if (delivery.isWicket())
        {

            //Set new player on strike on wicket...
            Player batsman = deliveryContext.getOnStrike();
            battingTeam.getBattingOrder().remove(batsman);
            battingTeam.getBatsman().add(batsman);
            deliveryContext.setOnStrike(battingTeam.getBattingOrder().poll());

        }else if(delivery.isRunOut())
        {
            // check which batsman got out
            Player player = getOutBatsman(delivery, deliveryContext);
            if(player.getName().equals(deliveryContext.getOnStrike().getName()))
            {
                // set new batsman on strike
                Player batsman = deliveryContext.getOnStrike();
                battingTeam.getBattingOrder().remove(batsman);
                battingTeam.getBatsman().add(batsman);
                deliveryContext.setOnStrike(battingTeam.getBattingOrder().poll());

            }else
            {
                // set new batsma on non strike
                Player batsman = deliveryContext.getNonStrike();
                battingTeam.getBattingOrder().remove(batsman);
                battingTeam.getBatsman().add(batsman);
                deliveryContext.setNonStrike(battingTeam.getBattingOrder().poll());

            }
        }
        if (isStrikeRotated(delivery))
        {
            //Rotate strikes......
            Player tmp = deliveryContext.getNonStrike();
            deliveryContext.setNonStrike(deliveryContext.getOnStrike());
            deliveryContext.setOnStrike(tmp);
        }
        if (overCompleted(delivery))
        {
            // Check for inning completeness...
            if (totalMatchOvers * 6 == validDeliveries)
            {
                if (deliveryContext.isFirstInningCompleted())
                {
                    deliveryContext.setSecondInningCompleted(true);
                } else
                {
                    deliveryContext.setFirstInningCompleted(true);
                }

                // Return null if inning finished....
                return null;

            }

            // set next bowler on context....
            while (true)
            {
                System.out.println("Enter name of next bowler of team " + bowlingTeam.getName());
                String input = scanner.nextLine();
                Player player = bowlingTeam.getplayerByName(input);
                if (Objects.nonNull(player))
                {
                    if (!bowlingTeam.getBowler().contains(player))
                    {
                        bowlingTeam.getBowler().add(player);
                    }

                    deliveryContext.setBowler(player);
                    break;
                }
                System.out.println("Invalid player name provided...");
            }

            // Change strike of batsman
            Player tmp = deliveryContext.getNonStrike();
            deliveryContext.setNonStrike(deliveryContext.getOnStrike());
            deliveryContext.setOnStrike(tmp);


        }
        deliveryContext.setCurrentDeliveryOfInning(this.validDeliveries);
        return deliveryContext;
    }

    public boolean overCompleted(Delivery delivery)
    {
        if (!delivery.isValidDelivery())
        {
            return false;
        }
        return validDeliveries % 6 == 0;
    }

    public boolean isStrikeRotated(Delivery delivery)
    {
        if (delivery.getText().equals("1") || delivery.getText().equals("3"))
        {
            return true;
        }
        return false;
    }


}
