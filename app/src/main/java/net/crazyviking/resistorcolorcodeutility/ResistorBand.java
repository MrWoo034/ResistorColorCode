package net.crazyviking.resistorcolorcodeutility;

/**
 * Created by patrick_star on 11/8/17.
 */

public class ResistorBand {
    private ResistorEnum bandValue;
    private int colorId;

    public ResistorBand(int colorId, ResistorEnum bandValue) {
        this.bandValue = bandValue;
        this.colorId = colorId;
    }

    public ResistorEnum getBandValue() {
        return this.bandValue;
    }

    public int getColorId() {
        return this.colorId;
    }
}
