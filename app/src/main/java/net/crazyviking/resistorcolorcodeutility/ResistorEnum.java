package net.crazyviking.resistorcolorcodeutility;

/**
 * Created by patrick_star on 11/7/17.
 */

public enum ResistorEnum {
    BLACK(0) {
        public String color() {
            return "BLACK";
        }
        public String toString() {
            return "0";
        }
        public int getValue() {
            return 0;
        }
    }, BROWN(1) {
        public String color() {
            return "BROWN";
        }
        public String toString() {
            return "1";
        }
        public int getValue() {
            return 1;
        }
    }, RED(2) {
        public String color() {
            return "RED";
        }
        public String toString() {
            return "2";
        }
        public int getValue() {
            return 2;
        }
    }, ORANGE(3){
        public String color() {
            return "ORANGE";
        }
        public String toString() {
            return "3";
        }
        public int getValue() {
            return 3;
        }
    }, YELLOW(4){
        public String color() {
            return "YELLOW";
        }
        public String toString() {
            return "4";
        }
        public int getValue() {
            return 4;
        }
    }, GREEN(5){
        public String color() {
            return "GREEN";
        }
        public String toString() {
            return "5";
        }
        public int getValue() {
            return 5;
        }
    },
    BLUE(6){
        public String color() {
            return "BLUE";
        }
        public String toString() {
            return "6";
        }
        public int getValue() {
            return 6;
        }
    }, VIOLET(7){
        public String color() {
            return "VIOLET";
        }
        public String toString() {
            return "7";
        }
        public int getValue() {
            return 7;
        }
    }, GRAY(8){
        public String color() {
            return "GRAY";
        }
        public String toString() {
            return "8";
        }
        public int getValue() {
            return 8;
        }
    }, WHITE(9){
        public String color() {
            return "WHITE";
        }
        public String toString() {
            return "9";
        }
        public int getValue() {
            return 9;
        }
    }, GOLD(10){
        public String color() {
            return "GOLD";
        }
        public String toString() {
            return "10%";
        }
        public int getValue() {
            return 10;
        }
    }, SILVER(50){
        public String color() {
            return "SILVER";
        }
        public String toString() {
            return "5%";
        }
        public int getValue() {
            return 50;
        }
    };

    private int value;

    ResistorEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
