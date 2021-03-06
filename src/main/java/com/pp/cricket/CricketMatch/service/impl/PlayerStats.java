package com.pp.cricket.CricketMatch.service.impl;
import com.pp.cricket.CricketMatch.model.Player;
import com.pp.cricket.CricketMatch.model.enums.Delivery;
import com.pp.cricket.CricketMatch.service.DeliveryService;
import lombok.Data;

/**
 * Created by wmadane on 10/21/2021.
 */

@Data
public abstract class PlayerStats implements DeliveryService
{

    public boolean doBatting ;
    public boolean doBowling;

    abstract public void printStat();
    abstract public void proceessDeliveryForRunout(Delivery delivery);
}
