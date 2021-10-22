package com.pp.cricket.CricketMatch.model.enums;

import java.util.Arrays;

public enum Delivery
{
    WD(1,false,false, "wd", false),
    NB(1, false, false, "nb", false),
    ZERO(0,false,true,"0", false),
    ONE(1, false, true, "1", false),
    TWO(2, false, true, "2", false),
    THREE(3, false, true, "3", false),
    FOUR(4, false, true, "4", false),
    SIX(6,false, true, "6", false),
    W(0, true, true, "w", false),
    R0(0, false,true, "r0", true),
    R1(1, false, true, "r1", true),
    R2(2, false, true, "r2", true),
    R3(3, false, true, "r3", true)
    ;
    int value;
    String text;
    boolean isWicket;
    boolean isRunOut ;

    boolean validDelivery;

    Delivery(int value, boolean isWicket, boolean validDelivery, String text, boolean isRunOut)
    {
        this.value = value;
        this.isWicket = isWicket;
        this.validDelivery = validDelivery;
        this.text = text;
        this.isRunOut = isRunOut;
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

    public boolean isRunOut()
    {
        return isRunOut;
    }

    public void setRunOut(boolean runOut)
    {
        isRunOut = runOut;
    }
}
