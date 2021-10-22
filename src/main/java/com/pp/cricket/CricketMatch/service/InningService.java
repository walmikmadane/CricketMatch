package com.pp.cricket.CricketMatch.service;

import com.pp.cricket.CricketMatch.model.DeliveryContext;
import com.pp.cricket.CricketMatch.model.enums.Delivery;
import com.pp.cricket.CricketMatch.service.impl.Inning;

public interface InningService
{
    public void printInningStat(DeliveryContext deliveryContext);
    public void printBattingTeamStats(
        DeliveryContext deliveryContext);
    public void printBowlingTeamStats();
    public void processDelivery(Delivery delivery, DeliveryContext deliveryContext);
    public void printOverStat();

    public DeliveryContext getNextDeliveryContext(Delivery delivery,
        DeliveryContext deliveryContext,
        int totalMatchOvers, int totalWickets, boolean secondInning,
        Inning firstInning);
}
