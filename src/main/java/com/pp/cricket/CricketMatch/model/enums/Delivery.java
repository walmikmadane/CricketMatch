package com.pp.cricket.CricketMatch.model.enums;

import java.util.Arrays;

public enum Delivery
{
    WD(1,false,false, "wd"),
    NB(1, false, false, "nb"),
    ZERO(0,false,true,"0"),
    ONE(1, false, true, "1"),
    TWO(2, false, true, "2"),
    THREE(3, false, true, "3"),
    FOUR(4, false, true, "4"),
    SIX(6,false, true, "6"),
    W(0, true, true, "w")
    ;
    int value;
    String text;
    boolean isWicket;
    boolean validDelivery;

    Delivery(int value, boolean isWicket, boolean validDelivery, String text)
    {
        this.value = value;
        this.isWicket = isWicket;
        this.validDelivery = validDelivery;
        this.text = text;
    }

    public static Delivery fromText(String text)
    {
        if(text.isEmpty())
            return null;
        String lowercase = text.toLowerCase();
        return Arrays.asList(Delivery.values()).stream()
            .filter(delivery -> delivery.getText().equals(lowercase)).findAny()
            .orElse(null);
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public boolean isWicket()
    {
        return isWicket;
    }

    public void setWicket(boolean wicket)
    {
        isWicket = wicket;
    }

    public boolean isValidDelivery()
    {
        return validDelivery;
    }

    public void setValidDelivery(boolean validDelivery)
    {
        this.validDelivery = validDelivery;
    }
}
