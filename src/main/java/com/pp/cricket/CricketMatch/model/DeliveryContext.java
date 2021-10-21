package com.pp.cricket.CricketMatch.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by wmadane on 10/21/2021.
 */

@Data
@Builder
public class DeliveryContext
{

    private Player onStrike;
    private Player nonStrike;
    private Player bowler;
    private long currentDeliveryOfInning;
    private boolean firstInningCompleted;
    private boolean secondInningCompleted;

    public boolean isMatchCompleted()
    {
        return (this.isFirstInningCompleted() && this.isSecondInningCompleted());
    }

    public boolean isFirstInningCompleted()
    {
        return firstInningCompleted;
    }

}
