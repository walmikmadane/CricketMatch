package com.pp.cricket.CricketMatch;

import com.pp.cricket.CricketMatch.model.DeliveryContext;
import com.pp.cricket.CricketMatch.model.Match;
import com.pp.cricket.CricketMatch.model.MatchResult;
import com.pp.cricket.CricketMatch.model.Player;
import com.pp.cricket.CricketMatch.model.PlayerComparator;
import com.pp.cricket.CricketMatch.model.Team;
import com.pp.cricket.CricketMatch.model.enums.Delivery;
import com.pp.cricket.CricketMatch.service.impl.BattingStat;
import com.pp.cricket.CricketMatch.service.impl.BowlingStat;
import com.pp.cricket.CricketMatch.service.impl.Inning;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CricketMatchApplication
{

    public static void main(String[] args)
    {
        Match match = new Match();
        Scanner scanner = new Scanner(System.in);

        // Input of no of players for each team
        System.out.println("No. of players for each team:");
        int totalPlayers = Integer.parseInt(scanner.nextLine());
        match.setNoOfPlayers(totalPlayers);

        //Input of no of overs..

        System.out.println("No. of overs for match:");
        int totalOvers = Integer.parseInt(scanner.nextLine());
        match.setTotalOvers(totalOvers);

        //Batting Order for team 1 input
        Team team1 = Team.builder()
            .name("Team1")
            .noOfPlayers(match.getNoOfPlayers())
            .batsman(new TreeSet<Player>(new PlayerComparator()))
            .battingOrder(new PriorityQueue<Player>(new PlayerComparator()))
            .bowler(new TreeSet<Player>(new PlayerComparator()))
            .build();
        System.out.println("Batting Order for " + team1.getName());
        int counter = 0;
        while (counter < totalPlayers)
        {
            Player player = Player.builder()
                .name(scanner.nextLine())
                .teamName(team1.getName())
                .battingOrder(counter)
                .bowlingStat(BowlingStat.builder().build())
                .battingStat(BattingStat.builder().build())
                .build();
            team1.addPlayer(player);
            counter++;
        }

        //Batting Order for team 2 input
        Team team2 = Team.builder()
            .name("Team2")
            .noOfPlayers(match.getNoOfPlayers())
            .batsman(new TreeSet<Player>(new PlayerComparator()))
            .battingOrder(new PriorityQueue<Player>(new PlayerComparator()))
            .bowler(new TreeSet<Player>(new PlayerComparator()))
            .build();
        System.out.println("Batting Order for " + team2.getName());
        counter = 0;
        while (counter < totalPlayers)
        {
            Player player = Player.builder()
                .name(scanner.nextLine())
                .teamName(team1.getName())
                .battingOrder(counter)
                .bowlingStat(BowlingStat.builder().build())
                .battingStat(BattingStat.builder().build())
                .build();
            team2.addPlayer(player);
            counter++;
        }

        System.out.println("Inning 1 Started..." + team1.getName() + " is batting..");
        Inning inning1 = Inning.builder()
            .battingTeam(team1)
            .bowlingTeam(team2)
            .scanner(scanner)
            .build();
        match.setFirstInning(inning1);

        counter = 1;
        long validDeliveries = 0;
        match.setDeliveryContext(DeliveryContext.builder()
            .onStrike(inning1.getBattingTeam().getBattingOrder().poll())
            .nonStrike(inning1.getBattingTeam().getBattingOrder().poll())
            .currentDeliveryOfInning(0)
            .build());

        System.out.println("Enter name of next bowler of team " + team2.getName());
        String input = scanner.nextLine();
        Player player = team2.getplayerByName(input);
        team2.getBowler().add(player);
        match.getDeliveryContext().setBowler(player);

        while (counter <= totalOvers)
        {
            System.out.println("Over " + counter);
            int ballsCounter = 1;
            while (ballsCounter <= 6)
            {
                String deliveryInput = scanner.nextLine();
                deliveryInput = deliveryInput.trim();
                Delivery delivery = Delivery.fromText(deliveryInput);
                inning1.processDelivery(delivery, match.getDeliveryContext());
                match.setDeliveryContext(
                    inning1.getNextDeliveryContext(delivery, match.getDeliveryContext(),
                        totalOvers, totalPlayers, false, null));
                if(delivery.isValidDelivery())
                    ballsCounter++;

                //All out in middle overs....
                if(Objects.isNull(match.getDeliveryContext()))
                    break;
            }
            // All out / Overs completed  condition...
            if(Objects.isNull(match.getDeliveryContext()))
                break;
            counter++;
        }

        System.out.println("First Inning finished...");
        System.out.println("First Inning Scorecard...");
        System.out.println("___________________________________________________");
        inning1.printInningStat();
        System.out.println("___________________________________________________");

        System.out.println("Inning 2 Started..." + team2.getName() + " is batting..");
        Inning inning2 = Inning.builder()
            .battingTeam(team2)
            .bowlingTeam(team1)
            .scanner(scanner)
            .build();
        match.setSecondInning(inning2);

        counter = 1;
        validDeliveries = 0;
        match.setDeliveryContext(DeliveryContext.builder()
            .onStrike(inning2.getBattingTeam().getBattingOrder().poll())
            .nonStrike(inning2.getBattingTeam().getBattingOrder().poll())
            .currentDeliveryOfInning(0)
            .build());

        System.out.println("Enter name of next bowler of team " + team1.getName());
        input = scanner.nextLine();
        player = team1.getplayerByName(input);
        team1.getBowler().add(player);
        match.getDeliveryContext().setBowler(player);

        while (counter <= totalOvers)
        {
            System.out.println("Over " + counter);
            int ballsCounter = 1;
            while (ballsCounter <= 6)
            {
                String deliveryInput = scanner.nextLine();
                deliveryInput = deliveryInput.trim();
                Delivery delivery = Delivery.fromText(deliveryInput);
                inning2.processDelivery(delivery, match.getDeliveryContext());
                match.setDeliveryContext(
                    inning2.getNextDeliveryContext(delivery, match.getDeliveryContext(),
                        totalOvers, totalPlayers, true, inning1));

                if(delivery.isValidDelivery())
                    ballsCounter++;

                //Match wins in middle overs...
                if(Objects.isNull(match.getDeliveryContext()))
                    break;
            }
            // All out / Overs completed  condition...
            if(Objects.isNull(match.getDeliveryContext()))
                break;
            counter++;
        }

        System.out.println("Second Inning finished...");
        System.out.println("Second Inning Scorecard...");
        System.out.println("___________________________________________________");
        inning2.printInningStat();
        System.out.println("___________________________________________________");

        System.out.println(".......MatchResult......");
        MatchResult matchResult = MatchResult.builder()
            .build();
        matchResult.processInnings(inning1, inning2, totalPlayers);

        matchResult.printMatchResult();

    }

}
